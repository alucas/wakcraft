package heero.mc.mod.wakcraft.helper;

import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.entity.creature.IFighter;
import heero.mc.mod.wakcraft.entity.property.CharacteristicsProperty;
import heero.mc.mod.wakcraft.entity.property.FightCharacteristicsProperty;
import heero.mc.mod.wakcraft.entity.property.FightProperty;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;

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
}
