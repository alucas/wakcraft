package heero.mc.mod.wakcraft.network.handler;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.entity.property.HavenBagProperty;
import heero.mc.mod.wakcraft.havenbag.HavenBagHelper;
import heero.mc.mod.wakcraft.havenbag.HavenBagProperties;
import heero.mc.mod.wakcraft.havenbag.HavenBagsManager;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagProperties;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagVisitors;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerServerHavenBagVisitors implements IMessageHandler<PacketHavenBagVisitors, IMessage> {
	@Override
	public IMessage onMessage(PacketHavenBagVisitors message, MessageContext ctx) {
		EntityPlayer player = ctx.getServerHandler().playerEntity;

		HavenBagProperty properties = (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
		if (properties == null) {
			FMLLog.warning("Error while loading player (%s) havenbag properties", player.getDisplayName());
			return null;
		}

		int havenBagUid = HavenBagHelper.getUIDFromCoord((int)player.posX, (int)player.posY, (int)player.posZ);
		if (properties.getUID() != havenBagUid) {
			FMLLog.warning("Player (%s) tried to update the permission of havenbag %d", player.getDisplayName(), havenBagUid);
			return null;
		}

		HavenBagProperties hbProperties = HavenBagsManager.getProperties(havenBagUid);
		if (hbProperties == null) {
			return null;
		}

		if (message.action == PacketHavenBagVisitors.ACTION_ADD) {
			hbProperties.setRight(message.playerName, hbProperties.getRight(message.playerName) | message.right);
			HavenBagsManager.setProperties(havenBagUid, hbProperties);
		} else if (message.action == PacketHavenBagVisitors.ACTION_REMOVE) {
			hbProperties.setRight(message.playerName, hbProperties.getRight(message.playerName) & ((~message.right) & 0xF));
			HavenBagsManager.setProperties(havenBagUid, hbProperties);
		} else {
			FMLLog.warning("Unknow action : %d", message.action);
			return null;
		}

		for (Object entity : player.worldObj.loadedEntityList) {
			if (entity instanceof EntityPlayerMP) {
				EntityPlayerMP playerMP = (EntityPlayerMP) entity;
				properties = (HavenBagProperty) playerMP.getExtendedProperties(HavenBagProperty.IDENTIFIER);
				if (properties == null) {
					FMLLog.warning("Error while loading player (%s) havenbag properties", playerMP.getDisplayName());
					continue;
				}

				if (properties.getHavenBag() != havenBagUid) {
					continue;
				}

				Wakcraft.packetPipeline.sendTo(new PacketHavenBagProperties(havenBagUid), playerMP);
			}
		}

		return null;
	}
}