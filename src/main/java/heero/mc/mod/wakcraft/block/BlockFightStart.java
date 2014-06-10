package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.helper.FightHelper;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFightStart extends BlockGeneric {

	public BlockFightStart(int team) {
		super(Material.gourd);

		setBlockTextureName("fightStart" + team);
	}

	/**
	 * Not opaque, render surrounding blocks
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * Only display the top texture, for player in fight (if the player is from
	 * an other fight, that's not important)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return side == 1
				&& FightHelper.isFighter(Minecraft.getMinecraft().thePlayer)
				&& FightHelper.isFighting(Minecraft.getMinecraft().thePlayer);
	}
}
