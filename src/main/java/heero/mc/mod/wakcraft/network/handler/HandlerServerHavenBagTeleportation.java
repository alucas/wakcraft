package heero.mc.mod.wakcraft.network.handler;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.WConfig;
import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.block.BlockSlab;
import heero.mc.mod.wakcraft.entity.property.HavenBagProperty;
import heero.mc.mod.wakcraft.havenbag.HavenBagGenerationHelper;
import heero.mc.mod.wakcraft.network.packet.PacketExtendedEntityProperty;
import heero.mc.mod.wakcraft.network.packet.PacketHavenBagTeleportation;
import heero.mc.mod.wakcraft.tileentity.TileEntityHavenBag;
import heero.mc.mod.wakcraft.util.HavenBagUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerServerHavenBagTeleportation implements IMessageHandler<PacketHavenBagTeleportation, IMessage> {
    @Override
    public IMessage onMessage(final PacketHavenBagTeleportation message, final MessageContext ctx) {
        IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
        mainThread.addScheduledTask(new Runnable() {
            @Override
            public void run() {
                onMessageHandler(message, ctx);
            }
        });

        return null;
    }

    protected void onMessageHandler(final PacketHavenBagTeleportation message, final MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().playerEntity;

        HavenBagProperty properties = (HavenBagProperty) player.getExtendedProperties(HavenBagProperty.IDENTIFIER);
        if (properties == null) {
            WLog.warning("Error while loading player ({}) havenbag properties", player.getDisplayName());
            return;
        }

        if (properties.isInHavenBag()) {
            HavenBagUtil.leaveHavenBag(player);

            return;
        }

        if (!player.onGround) {
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.havenbag.cantOpenInAir")));
            return;
        }

        BlockPos pos = new BlockPos(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ));

        Block block = player.worldObj.getBlockState(pos).getBlock();
        Block blockUnder = player.worldObj.getBlockState(pos.down()).getBlock();
        if (!(block instanceof BlockSlab) && !(blockUnder.isOpaqueCube())) {
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.havenbag.cantOpenHere")));
            return;
        }

        if (!properties.isInitialized()) {
            WLog.info("Init HavenBag");

            World havenBagWorld = MinecraftServer.getServer().worldServerForDimension(WConfig.getHavenBagDimensionId());
            if (havenBagWorld == null) {
                WLog.warning("Error while loading the havenbag world : {}", WConfig.getHavenBagDimensionId());

                return;
            }

            properties.setPlayerHavenBagId(player.worldObj.getUniqueDataId("havenbag"));

            WLog.info("New HavenBag attribution : {}, uid = {}", player.getDisplayName(), properties.getPlayerHavenBagId());

            boolean result = HavenBagGenerationHelper.generateHavenBag(havenBagWorld, properties.getPlayerHavenBagId());
            if (!result) {
                WLog.warning("Error during the generation of the havenbag : {}", properties.getPlayerHavenBagId());

                return;
            }
        }

        while (!(player.worldObj.getBlockState(pos).getBlock().getMaterial() == Material.air)) {
            pos = pos.up();

            if (pos.getY() >= player.worldObj.getHeight()) {
                WLog.error("No available position to create the havenbag");
                return;
            }
        }

        player.worldObj.setBlockState(pos, WBlocks.havenbag.getDefaultState());

        TileEntity tileEntity = player.worldObj.getTileEntity(pos);
        if (tileEntity == null || !(tileEntity instanceof TileEntityHavenBag)) {
            WLog.warning("Error while loading the tile entity ({}, {}, {})", pos.getX(), pos.getY(), pos.getZ());
            return;
        }

        TileEntityHavenBag tileHavenBag = (TileEntityHavenBag) tileEntity;
        tileHavenBag.uid = properties.getPlayerHavenBagId();
        tileHavenBag.markDirty();

        HavenBagUtil.teleportPlayerToHavenBag(player, properties.getPlayerHavenBagId(), pos);

//        player.playerNetServerHandler.sendPacket(tileHavenBag.getDescriptionPacket());
        Wakcraft.packetPipeline.sendTo(new PacketExtendedEntityProperty(player, HavenBagProperty.IDENTIFIER), player);
    }
}