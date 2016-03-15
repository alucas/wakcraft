package heero.mc.mod.wakcraft.spell.effect;

import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic areas of effect.
 */
public enum EffectArea implements IEffectArea {
    /**
     * The caster of the spell
     */
    CASTER() {
        @Override
        public List<BlockPos> getEffectCoors(final BlockPos fighterPosition, final int targetPosX, final int targetPosY, final int targetPosZ) {
            List<BlockPos> coordinates = new ArrayList<>();
            coordinates.add(fighterPosition);

            return coordinates;
        }
    },

    /**
     * Area of one block.
     */
    POINT() {
        @Override
        public List<BlockPos> getEffectCoors(final BlockPos fighterPosition, final int targetPosX, final int targetPosY, final int targetPosZ) {
            List<BlockPos> coordinates = new ArrayList<>();
            coordinates.add(new BlockPos(targetPosX, targetPosY, targetPosZ));

            return coordinates;
        }
    },

    /**
     * All 8 blocks around the specified block.
     */
    AROUND() {
        @Override
        public List<BlockPos> getEffectCoors(final BlockPos fighterPosition, final int targetPosX, final int targetPosY, final int targetPosZ) {
            List<BlockPos> coordinates = new ArrayList<>();
            coordinates.add(new BlockPos(targetPosX - 1, targetPosY, targetPosZ - 1));
            coordinates.add(new BlockPos(targetPosX - 1, targetPosY, targetPosZ));
            coordinates.add(new BlockPos(targetPosX - 1, targetPosY, targetPosZ + 1));
            coordinates.add(new BlockPos(targetPosX, targetPosY, targetPosZ - 1));
            coordinates.add(new BlockPos(targetPosX, targetPosY, targetPosZ + 1));
            coordinates.add(new BlockPos(targetPosX + 1, targetPosY, targetPosZ - 1));
            coordinates.add(new BlockPos(targetPosX + 1, targetPosY, targetPosZ));
            coordinates.add(new BlockPos(targetPosX + 1, targetPosY, targetPosZ + 1));

            return coordinates;
        }
    },

    /**
     * A cross, size 1.
     */
    CROSS() {
        @Override
        public List<BlockPos> getEffectCoors(final BlockPos fighterPosition, final int targetPosX, final int targetPosY, final int targetPosZ) {
            List<BlockPos> coordinates = new ArrayList<>();
            coordinates.add(new BlockPos(targetPosX, targetPosY, targetPosZ));
            coordinates.add(new BlockPos(targetPosX - 1, targetPosY, targetPosZ));
            coordinates.add(new BlockPos(targetPosX + 1, targetPosY, targetPosZ));
            coordinates.add(new BlockPos(targetPosX, targetPosY, targetPosZ - 1));
            coordinates.add(new BlockPos(targetPosX, targetPosY, targetPosZ + 1));

            return coordinates;
        }
    },

    /**
     * Vertical line, size 3.
     */
    LINE_V_3() {
        @Override
        public List<BlockPos> getEffectCoors(final BlockPos fighterPosition, final int targetPosX, final int targetPosY, final int targetPosZ) {
            List<BlockPos> coordinates = new ArrayList<>();
            coordinates.add(new BlockPos(targetPosX, targetPosY, targetPosZ));
            coordinates.add(new BlockPos(targetPosX, targetPosY, targetPosZ + 1));
            coordinates.add(new BlockPos(targetPosX, targetPosY, targetPosZ + 2));

            return coordinates;
        }
    },

    /**
     * The default area, size 2.
     */
    AREA_2() {
        @Override
        public List<BlockPos> getEffectCoors(final BlockPos fighterPosition, final int targetPosX, final int targetPosY, final int targetPosZ) {
            List<BlockPos> coordinates = new ArrayList<>();
            coordinates.add(new BlockPos(targetPosX - 2, targetPosY, targetPosZ));
            coordinates.add(new BlockPos(targetPosX - 1, targetPosY, targetPosZ - 1));
            coordinates.add(new BlockPos(targetPosX - 1, targetPosY, targetPosZ));
            coordinates.add(new BlockPos(targetPosX - 1, targetPosY, targetPosZ + 1));
            coordinates.add(new BlockPos(targetPosX, targetPosY, targetPosZ - 2));
            coordinates.add(new BlockPos(targetPosX, targetPosY, targetPosZ - 1));
            coordinates.add(new BlockPos(targetPosX, targetPosY, targetPosZ));
            coordinates.add(new BlockPos(targetPosX, targetPosY, targetPosZ + 1));
            coordinates.add(new BlockPos(targetPosX, targetPosY, targetPosZ + 2));
            coordinates.add(new BlockPos(targetPosX + 1, targetPosY, targetPosZ - 1));
            coordinates.add(new BlockPos(targetPosX + 1, targetPosY, targetPosZ));
            coordinates.add(new BlockPos(targetPosX + 1, targetPosY, targetPosZ + 1));
            coordinates.add(new BlockPos(targetPosX + 2, targetPosY, targetPosZ));

            return coordinates;
        }
    };

    @Override
    public List<BlockPos> getEffectCoors(BlockPos fighterPosition, BlockPos targetPosition) {
        return getEffectCoors(fighterPosition, targetPosition.getX(), targetPosition.getY(), targetPosition.getZ());
    }
}
