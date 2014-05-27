package heero.mc.mod.wakcraft.manager;

import net.minecraft.util.ChunkCoordinates;

public class FightBlockCoordinates extends ChunkCoordinates {
	public static enum TYPE {
		NORMAL, WALL
	};

	public TYPE type;

	public FightBlockCoordinates(int x, int y, int z, TYPE type) {
		super(x, y, z);

		this.type = type;
	}

	public FightBlockCoordinates() {
		super();

		this.type = TYPE.NORMAL;
	}

	public FightBlockCoordinates set(int x, int y, int z, TYPE type) {
		super.set(x, y, z);

		this.type = type;

		return this;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof FightBlockCoordinates)) {
			return false;
		}

		FightBlockCoordinates coords = (FightBlockCoordinates) obj;
		return this.posX == coords.posX
				&& this.posY == coords.posY
				&& this.posZ == coords.posZ;
	}

	public int hashCode() {
		return (this.posX & 0xFF) + ((this.posZ & 0xFF) << 8) + ((this.posY & 0xFF) << 16);
	}
}
