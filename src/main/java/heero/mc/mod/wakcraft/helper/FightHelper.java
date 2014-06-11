package heero.mc.mod.wakcraft.helper;

import heero.mc.mod.wakcraft.entity.creature.IFighter;
import heero.mc.mod.wakcraft.entity.property.FightProperty;
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

	public static void setStartPosition(Entity entity, ChunkCoordinates position) {
		if (entity instanceof IFighter) {
			((IFighter) entity).setStartPosition(position);
		}
	}

	public static ChunkCoordinates getStartPosition(Entity entity) {
		if (entity instanceof IFighter) {
			return ((IFighter) entity).getStartPosition();
		}

		return null;
	}

	public static int getTeam(Entity entity) {
		return ((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).getTeam();
	}

	public static void setProperties(Entity entity, int fightId, int teamId) {
		((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).setTeam(teamId);
		((FightProperty) entity.getExtendedProperties(FightProperty.IDENTIFIER)).setFightId(fightId);
	}
}
