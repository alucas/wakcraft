package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.creativetab.WCreativeTabs;
import heero.mc.mod.wakcraft.entity.property.HavenBagProperty;
import heero.mc.mod.wakcraft.util.HavenBagUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class BlockHavenBagVisitors extends BlockGeneric {
    public BlockHavenBagVisitors() {
        super(Material.wood);
        setCreativeTab(WCreativeTabs.tabSpecialBlock);
        setUnlocalizedName(Reference.MODID + "_HavenBagVisitors");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            return true;
        }

        IExtendedEntityProperties properties = player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
        if (properties == null || !(properties instanceof HavenBagProperty)) {
            WLog.warning("Error while loading the player (%s) extended properties", player.getDisplayName());
            return true;
        }

        HavenBagProperty propertiesHB = (HavenBagProperty) properties;
        if (propertiesHB.getUID() != HavenBagUtil.getUIDFromCoord(pos)) {
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.notYourBag")));
            return true;
        }

        Wakcraft.proxy.openHBVisitorsGui(player);

        return true;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        if (world.isRemote) {
            Wakcraft.proxy.getClientPlayer().addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.canPlaceBlockManualy")));
        }

        return false;
    }

//    TODO
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(IIconRegister registerer) {
//		iconSide = registerer.registerIcon(Reference.MODID.toLowerCase() + ":havenbagvisitors");
//		iconTop = registerer.registerIcon(Reference.MODID.toLowerCase() + ":havenbagvisitors_top");
//	}
//
//	/**
//	 * Gets the block's texture. Args: side, meta
//	 */
//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(int side, int metadata) {
//		if (side == 1) {
//			return iconTop;
//		}
//
//		return iconSide;
//	}
}
