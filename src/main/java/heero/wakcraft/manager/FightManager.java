package heero.wakcraft.manager;

import heero.wakcraft.WBlocks;
import heero.wakcraft.Wakcraft;
import heero.wakcraft.entity.property.FightProperty;
import heero.wakcraft.event.FightEvent;
import heero.wakcraft.event.FightEvent.Type;
import heero.wakcraft.network.packet.PacketFight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FightManager {
	// Map of <FightId, Teams<Entities<Id>>>
	protected Map<Integer, List<List<Integer>>> fights = new HashMap<Integer, List<List<Integer>>>();
	// Map of <FightId, mapBlocks<blockPos>>
	protected Map<Integer, List<ChunkCoordinates>> maps = new HashMap<Integer, List<ChunkCoordinates>>();

	@SubscribeEvent
	public void onAttackEntityEvent(AttackEntityEvent event) {
		if (event.entityPlayer.worldObj.isRemote) {
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
			int fightId = initFight((EntityPlayerMP) event.entityPlayer, target);
			if (fightId == -1) {
				return;
			}

			createFightMap(fightId, (EntityPlayerMP) event.entityPlayer);

			event.setCanceled(true);
			return;
		}

		if (properties.getFightId() != targetProperties.getFightId()) {
			event.setCanceled(true);
			return;
		}
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

		updateFight(event.entity.worldObj, fightId);
	}

	protected int initFight(EntityPlayerMP assailant, EntityLivingBase target) {
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

	protected void createFightMap(int fightId, EntityPlayerMP player) {
		int posX = (int) Math.floor(player.posX);
		int posY = (int) Math.floor(player.posY - 1.61);
		int posZ = (int) Math.floor(player.posZ);

		List<ChunkCoordinates> blockList = new ArrayList<ChunkCoordinates>();

		int radius = 5;
		for (int j = 0; j <= radius * 2; j++) {
			setFightWall(blockList, player.worldObj, posX + radius - j, posY, posZ + radius + 1);
		}
		for (int j = 0; j <= radius * 2; j++) {
			setFightWall(blockList, player.worldObj, posX + radius - j, posY, posZ - radius - 1);
		}
		for (int j = 0; j <= radius * 2; j++) {
			setFightWall(blockList, player.worldObj, posX + radius + 1, posY, posZ + radius - j);
		}
		for (int j = 0; j <= radius * 2; j++) {
			setFightWall(blockList, player.worldObj, posX - radius - 1, posY, posZ + radius - j);
		}

		maps.put(fightId, blockList);
	}

	protected void setFightWall(List<ChunkCoordinates> blockList, World world, int x, int y, int z) {
		for (; y < world.getHeight() && !world.getBlock(x, y, z).equals(Blocks.air); ++y);
		for (; y >= 0 && world.getBlock(x, y, z).equals(Blocks.air); --y);
		world.setBlock(x, y + 1, z, WBlocks.fightMovement);
		blockList.add(new ChunkCoordinates(x, y + 1, z));

		if(world.getBlock(x, y + 2, z).equals(Blocks.air)) {
			world.setBlock(x, y + 2, z, WBlocks.fightMovement);
			blockList.add(new ChunkCoordinates(x, y + 2, z));
		}
	}

	protected void removeFightMap(World world, int fightId) {
		List<ChunkCoordinates> blockList = maps.remove(fightId);

		for (ChunkCoordinates block : blockList) {
			world.setBlockToAir(block.posX, block.posY, block.posZ);
		}
	}

	protected void updateFight(World world, int fightId) {
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

	protected void stopFight(World world, int fightId) {
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

	protected void addFightersToFight(World world, List<List<Integer>> fighters, int fightId) {
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

	protected void removeFightersFromFight(World world, List<List<Integer>> fighters) {
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
