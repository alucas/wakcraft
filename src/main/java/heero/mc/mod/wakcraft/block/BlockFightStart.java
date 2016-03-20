package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFightStart extends BlockGenericTransparent {

    public BlockFightStart() {
        super(Material.gourd);

        setCanBePlacedManually(false);
    }

    /**
     * Only display the top texture, for player in fight (if the player is from
     * an other fight, that's not important)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.UP
                && FightUtil.isFighter(Minecraft.getMinecraft().thePlayer)
                && FightUtil.isFighting(Minecraft.getMinecraft().thePlayer);
    }
}
