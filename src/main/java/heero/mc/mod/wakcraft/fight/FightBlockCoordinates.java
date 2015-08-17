package heero.mc.mod.wakcraft.fight;

import net.minecraft.util.BlockPos;

public class FightBlockCoordinates extends BlockPos {
	public static enum TYPE {
		NORMAL, WALL
	}

	protected TYPE type;
	// NORMAL
	// WALL
	protected int metadata;

	public FightBlockCoordinates(int x, int y, int z, TYPE type) {
		super(x, y, z);

		this.type = type;
		this.metadata = 0;
	}

	public FightBlockCoordinates(int x, int y, int z, TYPE type, int metadata) {
		super(x, y, z);

		this.type = type;
		this.metadata = metadata;
	}

	public int hashCode() {
		return (this.getX() & 0xFF) + ((this.getZ() & 0xFF) << 8) + ((this.getY() & 0xFF) << 16);
	}

	public TYPE getType() {
		return type;
	}
}
