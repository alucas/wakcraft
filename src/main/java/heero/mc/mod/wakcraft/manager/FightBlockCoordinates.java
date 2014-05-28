package heero.mc.mod.wakcraft.manager;

import net.minecraft.util.ChunkCoordinates;

public class FightBlockCoordinates extends ChunkCoordinates {
	public static enum TYPE {
		NORMAL, WALL, START
	};

	protected TYPE type;
	// NORMAL : 1 = onGround
	// WALL
	// START : 0 = team0, 1 = team1
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
