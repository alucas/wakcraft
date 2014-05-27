package heero.mc.mod.wakcraft.manager;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.entity.property.FightProperty;
import heero.mc.mod.wakcraft.event.FightEvent;
import heero.mc.mod.wakcraft.event.FightEvent.Type;
import heero.mc.mod.wakcraft.network.packet.PacketFight;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public class FightManager {
	protected static int FIGHT_WALL_HEIGHT = 3;

	// Map of <FightId, Teams<Entities<Id>>>
	protected static Map<Integer, List<List<Integer>>> fights = new HashMap<Integer, List<List<Integer>>>();
	// Map of <FightId, mapBlocks<blockPos>>
	protected static Map<Integer, List<ChunkCoordinates>> maps = new HashMap<Integer, List<ChunkCoordinates>>();

	@SubscribeEvent
	public void onAttackEntityEvent(AttackEntityEvent event) {
		if (event.entityPlayer.worldObj.isRemote) {
			return;
		}

		if (event.entityPlayer.worldObj.provider.dimensionId != 0) {
			return;
		}

		if (!(event.target instanceof EntityLivingBase)) {
			return;
		}

		EntityLivingBase target = (EntityLivingBase) event.target;
		if (!target.isEntityAlive()) {
			return;
		}

		FightProperty properties = (FightProperty) event.entityPlayer.getExtendedProperties(FightProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading the Fight properties of player : %s", event.entityPlayer.getDisplayName());
			event.setCanceled(true);
			return;
		}

		FightProperty targetProperties = (FightProperty) target.getExtendedProperties(FightProperty.IDENTIFIER);
		if (targetProperties == null) {
			FMLLog.warning("Error while loading the Fight properties of : %s", target.getClass().getName());
			event.setCanceled(true);
			return;
		}

		if (!properties.isFighting() && !targetProperties.isFighting()) {
			int posX = (int) Math.floor(event.entityPlayer.posX);
			int posY = (int) Math.floor(event.entityPlayer.posY - 1.61);
			int posZ = (int) Math.floor(event.entityPlayer.posZ);
			List<ChunkCoordinates> coords = getMapAtPos(event.entityPlayer.worldObj, posX, posY, posZ, 10);

			if (coords.size() < 100) {
				event.entityPlayer.addChatMessage(new ChatComponentText("You can't begin to fight here"));
				event.setCanceled(true);
				return;
			}

			int fightId = initFight((EntityPlayerMP) event.entityPlayer, target);
			if (fightId == -1) {
				event.setCanceled(true);
				return;
			}

			createFightMap(fightId, event.entityPlayer.worldObj, coords);

			event.setCanceled(true);
			return;
		}

		if (properties.getFightId() != targetProperties.getFightId()) {
			event.setCanceled(true);
			return;
		}
	}

	protected List<ChunkCoordinates> getMapAtPos(World world, int posX, int posY, int posZ, int radius) {
		BitSet visited = new BitSet();

		return getMapAtPos_rec(world, posX, posY, posZ, 0, 0, 0, visited, radius * radius);
	}

	protected static final int offsetX[] = new int[]{-1, 1, 0, 0};
	protected static final int offsetZ[] = new int[]{0, 0, -1, 1};
	protected List<ChunkCoordinates> getMapAtPos_rec(World world, int centerX, int centerY, int centerZ, int offsetX, int offsetY, int offsetZ, BitSet visited, int radius2) {
		visited.set(50 + offsetX + 100 * (50 + offsetY) + 10000 * (50 + offsetZ));

		if (offsetX * offsetX + offsetZ * offsetZ > radius2) {
			return null;
		}

		if (!world.getBlock(centerX + offsetX, centerY + offsetY, centerZ + offsetZ).equals(Blocks.air)) {
			if (!world.getBlock(centerX + offsetX, centerY + offsetY + 1, centerZ + offsetZ).equals(Blocks.air)) {
				if (!world.getBlock(centerX + offsetX, centerY + offsetY + 2, centerZ + offsetZ).equals(Blocks.air)) {
					return null;
				}

				if (!world.getBlock(centerX + offsetX, centerY + offsetY + 3, centerZ + offsetZ).equals(Blocks.air)) {
					return null;
				}

				offsetY += 1;
			} else if (!world.getBlock(centerX + offsetX, centerY + offsetY + 2, centerZ + offsetZ).equals(Blocks.air)) {
				return null;
			}
		} else {
			if (world.getBlock(centerX + offsetX, centerY + offsetY - 1, centerZ + offsetZ).equals(Blocks.air)) {
				return null;
			}

			if (!world.getBlock(centerX + offsetX, centerY + offsetY + 1, centerZ + offsetZ).equals(Blocks.air)) {
				return null;
			}

			if (!world.getBlock(centerX + offsetX, centerY + offsetY + 2, centerZ + offsetZ).equals(Blocks.air)) {
				return null;
			}

			offsetY -= 1;
		}

		List<ChunkCoordinates> coords = new LinkedList<ChunkCoordinates>();
		coords.add(new ChunkCoordinates(centerX + offsetX, centerY + offsetY + 1, centerZ + offsetZ));

		List<ChunkCoordinates> coords_tmp;
		for (int i = 0; i < 4; i++) {
			if (!visited.get(50 + offsetX + FightManager.offsetX[i] + 100 * (50 + offsetY) + 10000 * (50 + offsetZ + FightManager.offsetZ[i]))) {
				coords_tmp = getMapAtPos_rec(world, centerX, centerY, centerZ, offsetX + FightManager.offsetX[i], offsetY, offsetZ + FightManager.offsetZ[i], visited, radius2);
				if (coords_tmp != null) {
					coords.addAll(coords_tmp);
				}
			}
		}

		return coords;
	}

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event) {
		if (event.entityLiving.worldObj.isRemote) {
			return;
		}

		FightProperty properties = (FightProperty) event.entityLiving.getExtendedProperties(FightProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading the Fight properties of player : %s", event.entityLiving.getClass().getName());
			return;
		}

		if (!properties.isFighting()) {
			return;
		}

		int fightId = properties.getFightId();

		updateFight(event.entityLiving.worldObj, fightId);
	}


	@SubscribeEvent
	public void onPlayerLoggedOutEvent(PlayerLoggedOutEvent event) {
		if (event.player.worldObj.isRemote) {
			return;
		}

		FightProperty properties = (FightProperty) event.player.getExtendedProperties(FightProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading the Fight properties of player : %s", event.player.getClass().getName());
			return;
		}

		if (!properties.isFighting()) {
			return;
		}

		event.player.setDead();

		int fightId = properties.getFightId();

		updateFight(event.player.worldObj, fightId);
	}

	protected static int initFight(EntityPlayerMP assailant, EntityLivingBase target) {
		FightProperty assailantProperties = (FightProperty) assailant.getExtendedProperties(FightProperty.IDENTIFIER);
		if (assailantProperties == null) {
			FMLLog.warning("Error while loading the Fight properties of player : %s", assailant.getDisplayName());
			return -1;
		}

		FightProperty targetProperties = (FightProperty) target.getExtendedProperties(FightProperty.IDENTIFIER);
		if (targetProperties == null) {
			FMLLog.warning("Error while loading the Fight properties of : %s", target.getClass().getName());
			return -1;
		}

		int fightId = assailant.worldObj.getUniqueDataId("fightId");

		ArrayList<Integer> fighters1 = new ArrayList<Integer>();
		ArrayList<Integer> fighters2 = new ArrayList<Integer>();
		fighters1.add(assailant.getEntityId());
		fighters2.add(target.getEntityId());

		ArrayList<List<Integer>> fighters = new ArrayList<List<Integer>>();
		fighters.add(fighters1);
		fighters.add(fighters2);

		MinecraftForge.EVENT_BUS.post(new FightEvent(assailant.worldObj, Type.START, fightId, fighters));
		Wakcraft.packetPipeline.sendTo(new PacketFight(Type.START, fightId, fighters), assailant);

		if (target instanceof EntityPlayerMP) {
			Wakcraft.packetPipeline.sendTo(new PacketFight(Type.START, fightId, fighters), (EntityPlayerMP) target);
		}

		return fightId;
	}

	protected static void createFightMap(int fightId, World world, List<ChunkCoordinates> blockList) {
		for (ChunkCoordinates block : blockList) {
			world.setBlock(block.posX, block.posY, block.posZ, Blocks.stone);
		}

		maps.put(fightId, blockList);
	}

	protected static void removeFightMap(World world, int fightId) {
		List<ChunkCoordinates> blockList = maps.remove(fightId);
		if (blockList == null) {
			return;
		}

		for (ChunkCoordinates block : blockList) {
			world.setBlockToAir(block.posX, block.posY, block.posZ);
		}
	}

	protected static void updateFight(World world, int fightId) {
		List<List<Integer>> fight = fights.get(fightId);
		if (fight == null) {
			FMLLog.warning("Trying to update a fight who does not exist : %d", fightId);
			return;
		}

		for (int teamId = 0; teamId < 2; teamId++) {
			List<Integer> team = fight.get(teamId);

			boolean living = false;
			for (Integer entityId : team) {
				Entity entity = world.getEntityByID(entityId);
				if (entity == null || !(entity instanceof EntityLivingBase)) {
					FMLLog.warning("Wrond fighting entity id");
					return;
				}

				if (!((EntityLivingBase) entity).isEntityAlive()) {
					continue;
				}

				living = true;
			}

			if (!living) {
				stopFight(world, fightId);
				removeFightMap(world, fightId);

				return;
			}
		}
	}

	protected static void stopFight(World world, int fightId) {
		List<List<Integer>> fight = fights.get(fightId);
		if (fight == null) {
			FMLLog.warning("Trying to stop a fight who does not exist : %d", fightId);
			return;
		}

		MinecraftForge.EVENT_BUS.post(new FightEvent(world, Type.STOP, fightId, fight));

		for (int teamId = 0; teamId < 2; teamId++) {
			List<Integer> team = fight.get(teamId);

			for (Integer entityId : team) {
				Entity entity = world.getEntityByID(entityId);
				if (entity == null || !(entity instanceof EntityLivingBase)) {
					FMLLog.warning("Wrond fighting entity id");
					return;
				}

				if (entity instanceof EntityPlayerMP) {
					Wakcraft.packetPipeline.sendTo(new PacketFight(Type.STOP, fightId, fight), (EntityPlayerMP) entity);
				}
			}
		}
	}

	protected static void stopFights(World world) {
		for (int fightId : fights.keySet()) {
			stopFight(world, fightId);
			removeFightMap(world, fightId);
		}
	}

	public static void teardown(World world) {
		stopFights(world);
	}

	@SubscribeEvent
	public void onFightEvent(FightEvent event) {
		switch (event.type) {
		case START:
			addFightersToFight(event.world, event.fighters, event.fightId);

			fights.put(event.fightId, event.fighters);
			break;

		case STOP:
			removeFightersFromFight(event.world, event.fighters);

			fights.remove(event.fightId);
			break;

		default:
			break;
		}
	}

	protected static void addFightersToFight(World world, List<List<Integer>> fighters, int fightId) {
		Iterator<List<Integer>> teams = fighters.iterator();
		while (teams.hasNext()) {
			Iterator<Integer> entities = teams.next().iterator();
			while (entities.hasNext()) {
				Entity entity = world.getEntityByID(entities.next());
				if (entity == null || !(entity instanceof EntityLivingBase)) {
					FMLLog.warning("Wrond fighting entity id");
					return;
				}

				FightProperty entityProperties = (FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER);
				if (entityProperties == null) {
					FMLLog.warning("Error while loading the Fight properties of player : %s", entity.getClass().getName());
					return;
				}

				entityProperties.setFightId(fightId);
			}
		}
	}

	protected static void removeFightersFromFight(World world, List<List<Integer>> fighters) {
		Iterator<List<Integer>> teams = fighters.iterator();
		while (teams.hasNext()) {
			Iterator<Integer> entities = teams.next().iterator();
			while (entities.hasNext()) {
				Entity entity = world.getEntityByID(entities.next());
				if (entity == null || !(entity instanceof EntityLivingBase)) {
					FMLLog.warning("Wrond fighting entity id");
					return;
				}

				FightProperty entityProperties = (FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER);
				if (entityProperties == null) {
					FMLLog.warning("Error while loading the Fight properties of player : %s", entity.getClass().getName());
					return;
				}

				entityProperties.resetFightId();
			}
		}
	}
}
