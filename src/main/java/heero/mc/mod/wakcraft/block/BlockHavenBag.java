package heero.mc.mod.wakcraft.block;

import heero.mc.mod.wakcraft.WConfig;
import heero.mc.mod.wakcraft.WInfo;
import heero.mc.mod.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.mc.mod.wakcraft.havenbag.HavenBagProperties;
import heero.mc.mod.wakcraft.havenbag.HavenBagsManager;
import heero.mc.mod.wakcraft.helper.HavenBagHelper;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenBag;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;

public class BlockHavenBag extends BlockContainer {
	public BlockHavenBag() {
		super(Material.wood);

		setCreativeTab(WakcraftCreativeTabs.tabSpecialBlock);
		setBlockName("HavenBag");
		setBlockTextureName(WInfo.MODID.toLowerCase() + ":havengemworkbench");
		setBlockUnbreakable();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
	
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null || !(tile instanceof TileEntityHavenBag)) {
			FMLLog.warning("Error while loading the tile entity (%d, %d, %d)", x, y, z);
			return true;
		}

		World havenBagWorld = MinecraftServer.getServer().worldServerForDimension(WConfig.HAVENBAG_DIMENSION_ID);
		if (havenBagWorld == null) {
			FMLLog.warning("Error while loading the havenbag world : %d", WConfig.HAVENBAG_DIMENSION_ID);

			return false;
		}

		HavenBagProperties havenBagProperties = HavenBagsManager.getProperties(((TileEntityHavenBag) tile).uid);
		if (havenBagProperties == null) {
			return true;
		}

		if (havenBagProperties.isLocked()) {
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.lockHavenBag")));
			return true;
		}

		if (player instanceof EntityPlayerMP) {
			HavenBagHelper.teleportPlayerToHavenBag((EntityPlayerMP) player, ((TileEntityHavenBag) tile).uid);
		}

		return true;
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileEntityHavenBag();
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if (world.isRemote) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.canPlaceBlockManualy")));
		}

		return false;
	}
}
