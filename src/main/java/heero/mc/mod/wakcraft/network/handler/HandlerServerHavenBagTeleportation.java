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
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cantOpenHavenBagInAir")));
            return;
        }

        BlockPos pos = new BlockPos(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY - player.getYOffset()), MathHelper.floor_double(player.posZ));

        Block block = player.worldObj.getBlockState(pos).getBlock();
        Block blockUnder = player.worldObj.getBlockState(pos.down()).getBlock();
        if (!(block instanceof BlockSlab) && !(blockUnder.isOpaqueCube())) {
            player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.cantOpenHavenBagHere")));
            return;
        }

        // Initialization
        if (properties.getUID() == -1) {
            WLog.info("Init HavenBag");

            World havenBagWorld = MinecraftServer.getServer().worldServerForDimension(WConfig.getHavenBagDimensionId());
            if (havenBagWorld == null) {
                WLog.warning("Error while loading the havenbag world : {}", WConfig.getHavenBagDimensionId());

                return;
            }

            properties.setUID(player.worldObj.getUniqueDataId("havenbag"));

            WLog.info("New HavenBag attribution : {}, uid = {}", player.getDisplayName(), properties.getUID());

            boolean result = HavenBagGenerationHelper.generateHavenBag(havenBagWorld, properties.getUID());
            if (!result) {
                WLog.warning("Error during the generation of the havenbag : {}", properties.getUID());

                return;
            }
        }

        while (!(player.worldObj.getBlockState(pos).getBlock().getMaterial() == Material.air)) {
            pos = pos.up();

            if (pos.getY() >= player.worldObj.getHeight()) {
                System.out.println("error: " + pos.getY());
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
        tileHavenBag.uid = properties.getUID();
        tileHavenBag.markDirty();

        HavenBagUtil.teleportPlayerToHavenBag(player, properties.getUID());

//        player.playerNetServerHandler.sendPacket(tileHavenBag.getDescriptionPacket());
        Wakcraft.packetPipeline.sendTo(new PacketExtendedEntityProperty(player, HavenBagProperty.IDENTIFIER), player);
    }
}