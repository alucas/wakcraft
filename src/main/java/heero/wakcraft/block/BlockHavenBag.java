package heero.wakcraft.block;

import cpw.mods.fml.common.FMLLog;
import heero.wakcraft.WakcraftInfo;
import heero.wakcraft.creativetab.WakcraftCreativeTabs;
import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.havenbag.HavenBagManager;
import heero.wakcraft.tileentity.TileEntityHavenBag;
import heero.wakcraft.tileentity.TileEntityHavenBagProperties;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class BlockHavenBag extends BlockContainer {
	public BlockHavenBag() {
		super(Material.wood);

		setCreativeTab(WakcraftCreativeTabs.tabSpecialBlock);
		setBlockName("HavenBag");
		setBlockTextureName(WakcraftInfo.MODID.toLowerCase() + ":havengemworkbench");
		setBlockUnbreakable();
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
	
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null || !(tile instanceof TileEntityHavenBag)) {
			FMLLog.warning("Error while loading the tile entity (%d, %d, %d)", x, y, z);
			return true;
		}

		TileEntityHavenBagProperties havenBagProperties = HavenBagManager.getHavenBagProperties(player.worldObj, ((TileEntityHavenBag) tile).uid);
		if (havenBagProperties == null) {
			return true;
		}

		if (havenBagProperties.locked) {
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.lockHavenBag")));
			return true;
		}

		HavenBagManager.teleportPlayerToHavenBag(player, ((TileEntityHavenBag) tile).uid);

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
