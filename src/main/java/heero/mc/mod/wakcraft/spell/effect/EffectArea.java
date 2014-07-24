package heero.mc.mod.wakcraft.spell.effect;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MovingObjectPosition;

/**
 * Generic areas of effect.
 */
public enum EffectArea implements IEffectArea {
	/** The caster of the spell */
	CASTER(1) {
		@Override
		public List<ChunkCoordinates> getEffectCoors(final ChunkCoordinates fighterPosition, final MovingObjectPosition aimingPosition) {
			coordinates.get(0).set(fighterPosition.posX, fighterPosition.posY, fighterPosition.posZ);

			return this.coordinates;
		}
	},

	/** Area of one block. */
	POINT(1) {
		@Override
		public List<ChunkCoordinates> getEffectCoors(final ChunkCoordinates fighterPosition, final MovingObjectPosition aimingPosition) {
			coordinates.get(0).set(aimingPosition.blockX, aimingPosition.blockY, aimingPosition.blockZ);

			return this.coordinates;
		}
	},

	/** All 8 blocks around the specified block. */
	AROUND(8) {
		@Override
		public List<ChunkCoordinates> getEffectCoors(final ChunkCoordinates fighterPosition, final MovingObjectPosition aimingPosition) {
			coordinates.get(0).set(aimingPosition.blockX - 1, aimingPosition.blockY, aimingPosition.blockZ - 1);
			coordinates.get(1).set(aimingPosition.blockX - 1, aimingPosition.blockY, aimingPosition.blockZ);
			coordinates.get(2).set(aimingPosition.blockX - 1, aimingPosition.blockY, aimingPosition.blockZ + 1);
			coordinates.get(3).set(aimingPosition.blockX    , aimingPosition.blockY, aimingPosition.blockZ - 1);
			coordinates.get(4).set(aimingPosition.blockX    , aimingPosition.blockY, aimingPosition.blockZ + 1);
			coordinates.get(5).set(aimingPosition.blockX + 1, aimingPosition.blockY, aimingPosition.blockZ - 1);
			coordinates.get(6).set(aimingPosition.blockX + 1, aimingPosition.blockY, aimingPosition.blockZ);
			coordinates.get(7).set(aimingPosition.blockX + 1, aimingPosition.blockY, aimingPosition.blockZ + 1);

			return this.coordinates;
		}
	},

	/** A cross, size 1. */
	CROSS(5) {
		@Override
		public List<ChunkCoordinates> getEffectCoors(final ChunkCoordinates fighterPosition, final MovingObjectPosition aimingPosition) {
			coordinates.get(0).set(aimingPosition.blockX, aimingPosition.blockY, aimingPosition.blockZ);
			coordinates.get(1).set(aimingPosition.blockX - 1, aimingPosition.blockY, aimingPosition.blockZ);
			coordinates.get(2).set(aimingPosition.blockX + 1, aimingPosition.blockY, aimingPosition.blockZ);
			coordinates.get(3).set(aimingPosition.blockX, aimingPosition.blockY, aimingPosition.blockZ - 1);
			coordinates.get(4).set(aimingPosition.blockX, aimingPosition.blockY, aimingPosition.blockZ + 1);

			return this.coordinates;
		}
	},

	/** Vertical line, size 3. */
	LINE_V_3(3) {
		@Override
		public List<ChunkCoordinates> getEffectCoors(final ChunkCoordinates fighterPosition, final MovingObjectPosition aimingPosition) {
			coordinates.get(0).set(aimingPosition.blockX, aimingPosition.blockY, aimingPosition.blockZ);
			coordinates.get(1).set(aimingPosition.blockX, aimingPosition.blockY, aimingPosition.blockZ + 1);
			coordinates.get(2).set(aimingPosition.blockX, aimingPosition.blockY, aimingPosition.blockZ + 2);

			return this.coordinates;
		}
	},

	/** The default area, size 2. */
	AREA_2(13) {
		@Override
		public List<ChunkCoordinates> getEffectCoors(final ChunkCoordinates fighterPosition, final MovingObjectPosition aimingPosition) {
			coordinates.get( 0).set(aimingPosition.blockX - 2, aimingPosition.blockY, aimingPosition.blockZ);
			coordinates.get( 1).set(aimingPosition.blockX - 1, aimingPosition.blockY, aimingPosition.blockZ - 1);
			coordinates.get( 2).set(aimingPosition.blockX - 1, aimingPosition.blockY, aimingPosition.blockZ);
			coordinates.get( 3).set(aimingPosition.blockX - 1, aimingPosition.blockY, aimingPosition.blockZ + 1);
			coordinates.get( 4).set(aimingPosition.blockX    , aimingPosition.blockY, aimingPosition.blockZ - 2);
			coordinates.get( 5).set(aimingPosition.blockX    , aimingPosition.blockY, aimingPosition.blockZ - 1);
			coordinates.get( 6).set(aimingPosition.blockX    , aimingPosition.blockY, aimingPosition.blockZ);
			coordinates.get( 7).set(aimingPosition.blockX    , aimingPosition.blockY, aimingPosition.blockZ + 1);
			coordinates.get( 8).set(aimingPosition.blockX    , aimingPosition.blockY, aimingPosition.blockZ + 2);
			coordinates.get( 9).set(aimingPosition.blockX + 1, aimingPosition.blockY, aimingPosition.blockZ - 1);
			coordinates.get(10).set(aimingPosition.blockX + 1, aimingPosition.blockY, aimingPosition.blockZ);
			coordinates.get(11).set(aimingPosition.blockX + 1, aimingPosition.blockY, aimingPosition.blockZ + 1);
			coordinates.get(12).set(aimingPosition.blockX + 2, aimingPosition.blockY, aimingPosition.blockZ);

			return this.coordinates;
		}
	};

	protected final List<ChunkCoordinates> coordinates;

	private EffectArea(final int size) {
		this.coordinates = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			coordinates.add(new ChunkCoordinates());
		}
	}

	@Override
	public List<ChunkCoordinates> getEffectCoors(ChunkCoordinates fighterPosition, MovingObjectPosition aimingPosition) {
		return coordinates;
	}
}
