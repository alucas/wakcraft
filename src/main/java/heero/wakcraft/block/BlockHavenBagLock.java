package heero.wakcraft.block;

import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.havenbag.HavenBagHelper;
import heero.wakcraft.tileentity.TileEntityHavenBagProperties;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHavenBagLock extends Block {
	private IIcon iconSide;
	private IIcon iconTop;

	public BlockHavenBagLock() {
		super(Material.wood);
		setCreativeTab(WakcraftCreativeTabs.tabSpecialBlock);
		setBlockName("HavenBagLock");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}

		IExtendedEntityProperties properties = player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
		if (properties == null || !(properties instanceof HavenBagProperty)) {
			FMLLog.warning("Error while loading the player (%s) extended properties", player.getDisplayName());
			return true;
		}

		HavenBagProperty propertiesHB = (HavenBagProperty) properties;
		if (propertiesHB.uid != HavenBagHelper.getUIDFromCoord(x, y, z)) {
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.notYourBag")));
			return true;
		}

		TileEntityHavenBagProperties havenBagProperties = HavenBagHelper.getHavenBagProperties(player.worldObj, propertiesHB.uid);
		if (havenBagProperties == null) {
			return true;
		}

		havenBagProperties.locked = !havenBagProperties.locked;
		havenBagProperties.markDirty();

		if (player instanceof EntityPlayerMP) {
			((EntityPlayerMP) player).playerNetServerHandler.sendPacket(havenBagProperties.getDescriptionPacket());
		}

		player.addChatMessage(new ChatComponentText(havenBagProperties.locked ? StatCollector.translateToLocal("message.lockHavenBag") : StatCollector.translateToLocal("message.unlockHavenBag")));

		return true;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if (world.isRemote) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.canPlaceBlockManualy")));
		}

		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer) {
		iconSide = registerer.registerIcon(WakcraftInfo.MODID.toLowerCase() + ":havenbaglock");
		iconTop = registerer.registerIcon(WakcraftInfo.MODID.toLowerCase() + ":havenbaglock_top");
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if (side == 1) {
			return iconTop;
		}

		return iconSide;
	}
}
