package heero.mc.mod.wakcraft.network.handler;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.WConfig;
import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.entity.property.HavenBagProperty;
import heero.mc.mod.wakcraft.havenbag.HavenBagGenerationHelper;
import heero.mc.mod.wakcraft.helper.HavenBagHelper;
import heero.mc.mod.wakcraft.network.packet.PacketExtendedEntityProperty;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagTeleportation;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenBag;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerServerHavenBagTeleportation implements IMessageHandler<PacketHavenBagTeleportation, IMessage> {
	@Override
	public IMessage onMessage(PacketHavenBagTeleportation message, MessageContext ctx) {
		EntityPlayer player = ctx.getServerHandler().playerEntity;

		HavenBagProperty properties = (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
		if (properties == null) {
			WLog.warning("Error while loading player (%s) havenbag properties", player.getDisplayName());
			return null;
		}

		if (properties.isInHavenBag()) {
			if (player instanceof EntityPlayerMP) {
				HavenBagHelper.leaveHavenBag((EntityPlayerMP) player);
			}

			return null;
		}

		if (! player.onGround) {
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cantOpenHavenBagInAir")));
			return null;
		}

		int posX = MathHelper.floor_double(player.posX);
		int posY = MathHelper.floor_double(player.posY - player.yOffset);
		int posZ = MathHelper.floor_double(player.posZ);

		Block block = player.worldObj.getBlock(posX, posY - 1, posZ);
		if (block == null || (block != null && !block.isOpaqueCube())) {
			player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cantOpenHavenBagHere")));
			return null;
		}

		// Initialization
		if (properties.getUID() == -1) {
			World havenBagWorld = MinecraftServer.getServer().worldServerForDimension(WConfig.getHavenBagDimensionId());
			if (havenBagWorld == null) {
				WLog.warning("Error while loading the havenbag world : %d", WConfig.getHavenBagDimensionId());

				return null;
			}

			properties.setUID(player.worldObj.getUniqueDataId("havenbag"));

			WLog.info("New HavenBag atribution : %s, uid = %d", player.getDisplayName(), properties.getUID());

			boolean result = HavenBagGenerationHelper.generateHavenBag(havenBagWorld, properties.getUID());
			if (!result) {
				WLog.warning("Error during the generation of the havenbag : %d", properties.getUID());

				return null;
			}
		}

		player.worldObj.setBlock(posX, posY, posZ, WBlocks.havenbag);

		TileEntity tileEntity = player.worldObj.getTileEntity(posX, posY, posZ);
		if (tileEntity == null || !(tileEntity instanceof TileEntityHavenBag)) {
			WLog.warning("Error while loading the tile entity (%d, %d, %d)", posX, posY, posZ);
			return null;
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

		return null;
	}
}