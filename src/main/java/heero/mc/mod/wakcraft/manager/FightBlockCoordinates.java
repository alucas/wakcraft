package heero.mc.mod.wakcraft.manager;

import net.minecraft.util.ChunkCoordinates;

public class FightBlockCoordinates extends ChunkCoordinates {
	public static enum TYPE {
		NORMAL, WALL, START1, START2
	};

	protected TYPE type;

	public FightBlockCoordinates(int x, int y, int z, TYPE type) {
		super(x, y, z);

		this.type = type;
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
