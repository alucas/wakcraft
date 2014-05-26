package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WBlocks;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSlabWakfu extends BlockSlab {

	public BlockSlabWakfu() {
		super(Material.ground, WBlocks.wakfuFull);

		setBlockName("SlabWakfu");
		setBlockTextureName("wakfuGreen");
		setBlockTextureName(ForgeDirection.UP, 0, "wakfuRed");
		setBlockTextureName(ForgeDirection.UP, 1, "wakfuBlue");
		setBlockTextureName(ForgeDirection.UP, 4, "wakfuBlue");
		setBlockTextureName(ForgeDirection.UP, 2, "wakfuOrange");
		setBlockTextureName(ForgeDirection.UP, 5, "wakfuOrange");
		setBlockTextureName(ForgeDirection.UP, 8, "wakfuOrange");
		setBlockTextureName(ForgeDirection.UP, 3, "wakfuGreen");
		setBlockTextureName(ForgeDirection.UP, 6, "wakfuGreen");
		setBlockTextureName(ForgeDirection.UP, 9, "wakfuGreen");
	}
}
