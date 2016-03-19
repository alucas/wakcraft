package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockFightWall extends BlockGeneric {

    public BlockFightWall() {
        super(new Material(MapColor.emeraldColor) {
            @Override
            public boolean isOpaque() {
                return false;
            }
        });

        setBlockUnbreakable();
        setCanBePlacedManually(false);
        setOpaque(false);
    }

    /**
     * Returns true if the given side of this block type should be rendered, if
     * the adjacent block is at the given coordinates. Args: blockAccess, x, y,
     * z, side
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, EnumFacing side) {
        return FightUtil.isFighter(Minecraft.getMinecraft().thePlayer) && FightUtil.isFighting(Minecraft.getMinecraft().thePlayer);
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, BlockPos pos, Vec3 vec1, Vec3 vec2) {
        if (world.isRemote) {
            if (!FightUtil.isFighter(Wakcraft.proxy.getClientPlayer()) || !FightUtil.isFighting(Wakcraft.proxy.getClientPlayer())) {
                return null;
            }
        }

        return super.collisionRayTrace(world, pos, vec1, vec2);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity entity) {
        if (!FightUtil.isFighter(entity) || !FightUtil.isFighting(entity)) {
            return;
        }

        super.addCollisionBoxesToList(world, pos, state, mask, list, entity);
    }
}
