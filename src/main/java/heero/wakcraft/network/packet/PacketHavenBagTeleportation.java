package heero.wakcraft.network.packet;

import heero.wakcraft.Wakcraft;
import heero.wakcraft.WBlocks;
import heero.wakcraft.WConfig;
import heero.wakcraft.entity.property.HavenBagProperty;
import heero.wakcraft.havenbag.HavenBagGenerationHelper;
import heero.wakcraft.havenbag.HavenBagHelper;
import heero.wakcraft.tileentity.TileEntityHavenBag;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;

public class PacketHavenBagTeleportation implements IPacket {
	public PacketHavenBagTeleportation() {
	}

	public PacketHavenBagTeleportation(EntityPlayer player) {
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, PacketBuffer buffer) {
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		HavenBagProperty properties = (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading player (%s) havenbag properties", player.getDisplayName());
			return;
		}

		if (properties.isInHavenBag()) {
			if (player instanceof EntityPlayerMP) {
				HavenBagHelper.leaveHavenBag((EntityPlayerMP) player);
			}

			return;
		}

		if (! player.onGround) {
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cantOpenHavenBagInAir")));
			return;
		}

		int posX = (int)Math.floor(player.posX);
		int posY = (int)Math.floor(player.posY - 0.1) + 1;
		int posZ = (int)Math.floor(player.posZ);

		Block block = player.worldObj.getBlock(posX, posY - 1, posZ);
		if (block == null || (block != null && !block.isOpaqueCube())) {
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cantOpenHavenBagHere")));
			return;
		}

		// Initialisation
		if (properties.getUID() == -1) {
			World havenBagWorld = MinecraftServer.getServer().worldServerForDimension(WConfig.havenBagDimensionId);
			if (havenBagWorld == null) {
				FMLLog.warning("Error while loading the havenbag world : %d", WConfig.havenBagDimensionId);

				return;
			}

			properties.setUID(player.worldObj.getUniqueDataId("havenbag"));

			FMLLog.info("New HavenBag atribution : %s, uid = %d", player.getDisplayName(), properties.getUID());

			boolean result = HavenBagGenerationHelper.generateHavenBag(havenBagWorld, properties.getUID());
			if (!result) {
				FMLLog.warning("Error during the generation of the havenbag : %d", properties.getUID());

				return;
			}
		}

		player.worldObj.setBlock(posX, posY, posZ, WBlocks.havenbag);

		TileEntity tileEntity = player.worldObj.getTileEntity(posX, posY, posZ);
		if (tileEntity == null || !(tileEntity instanceof TileEntityHavenBag)) {
			FMLLog.warning("Error while loading the tile entity (%d, %d, %d)", posX, posY, posZ);
			return;
		}

		TileEntityHavenBag tileHavenBag = (TileEntityHavenBag) tileEntity;
		tileHavenBag.uid = properties.getUID();
		tileHavenBag.markDirty();


		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP) player;

			HavenBagHelper.teleportPlayerToHavenBag(playerMP, properties.getUID());

			playerMP.playerNetServerHandler.sendPacket(tileHavenBag.getDescriptionPacket());
			Wakcraft.packetPipeline.sendTo(new PacketExtendedEntityProperty(playerMP, HavenBagProperty.IDENTIFIER), playerMP);
		}
	}
}