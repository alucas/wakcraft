package heero.mc.mod.wakcraft.helper;

import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.entity.creature.IFighter;
import heero.mc.mod.wakcraft.entity.property.CharacteristicsProperty;
import heero.mc.mod.wakcraft.entity.property.FightCharacteristicsProperty;
import heero.mc.mod.wakcraft.entity.property.FightProperty;
import heero.mc.mod.wakcraft.entity.property.SpellsProperty;
import heero.mc.mod.wakcraft.fight.FightBlockCoordinates;
import heero.mc.mod.wakcraft.fight.FightInfo.FightStage;
import heero.mc.mod.wakcraft.fight.FightManager;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class FightHelper {
	public static boolean isFighting(Entity entity) {
		return ((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).getFightId() != -1;
	}

	public static int getFightId(Entity entity) {
		return ((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).getFightId();
	}

	public static boolean isFighter(Entity entity) {
		return (entity instanceof IFighter) || (entity instanceof EntityPlayer);
	}

	public static boolean isAutonomousFighter(Entity entity) {
		return !(entity instanceof EntityPlayer);
	}

	public static void setStartPosition(Entity entity, @Nullable ChunkCoordinates startPosition) {
		((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).setStartPosition(startPosition);
	}

	public static ChunkCoordinates getStartPosition(Entity entity) {
		return ((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).getStartPosition();
	}

	public static int getTeam(Entity entity) {
		return ((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).getTeam();
	}

	public static void setProperties(Entity entity, int fightId, int teamId) {
		((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).setTeam(teamId);
		((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).setFightId(fightId);
	}

	public static void resetProperties(Entity entity) {
		((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).resetProperties();
	}

	public static int getFightCharacteristic(Entity entity, Characteristic characteristic) {
		return ((FightCharacteristicsProperty) entity.getExtendedProperties(FightCharacteristicsProperty.IDENTIFIER)).get(characteristic);
	}

	public static void setFightCharacteristic(Entity entity, Characteristic characteristic, int value) {
		((FightCharacteristicsProperty) entity.getExtendedProperties(FightCharacteristicsProperty.IDENTIFIER)).set(characteristic, value);
	}

	public static void resetFightCharacteristic(Entity entity, Characteristic characteristic) {
		int value = ((CharacteristicsProperty) entity.getExtendedProperties(CharacteristicsProperty.IDENTIFIER)).get(characteristic);
		((FightCharacteristicsProperty) entity.getExtendedProperties(FightCharacteristicsProperty.IDENTIFIER)).set(characteristic, value);
	}

	public static void setCurrentPosition(Entity entity, @Nullable ChunkCoordinates startPosition) {
		((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).setCurrentPosition(startPosition);
	}

	public static ChunkCoordinates getCurrentPosition(Entity entity) {
		return ((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).getCurrentPosition();
	}

	public static IInventory getSpellsInventory(Entity entity) {
		return ((SpellsProperty) entity.getExtendedProperties(SpellsProperty.IDENTIFIER)).getSpellsInventory();
	}

	public static ItemStack getCurrentSpell(EntityPlayer entity) {
		return ((SpellsProperty) entity.getExtendedProperties(SpellsProperty.IDENTIFIER)).getSpellsInventory().getStackInSlot(25 + entity.inventory.currentItem);
	}

	public static void updateDisplayName(Entity entity) {
		int maxHealth = ((CharacteristicsProperty) entity.getExtendedProperties(CharacteristicsProperty.IDENTIFIER)).get(Characteristic.HEALTH);
		if (entity instanceof EntityLiving) ((EntityLiving) entity).setCustomNameTag(getFightCharacteristic(entity, Characteristic.HEALTH) + " / " + maxHealth);
	}

	public static void resetDisplayName(Entity entity) {
		if (entity instanceof EntityLiving) ((EntityLiving) entity).setCustomNameTag("");
	}

	public static FightStage getFightStage(World world, int fightId) {
		return FightManager.INSTANCE.getFightStage(world, fightId);
	}

	public static List<List<EntityLivingBase>> getFighers(World world, int fightId) {
		return FightManager.INSTANCE.getFighters(world, fightId);
	}

	public static EntityLivingBase getCurrentFighter(World world, int fightId) {
		return FightManager.INSTANCE.getCurrentFighter(world, fightId);
	}

	public static List<List<FightBlockCoordinates>> getStartPositions(WorldClient world, int fightId) {
		return FightManager.INSTANCE.getSartPositions(world, fightId);
	}
}
