package heero.mc.mod.wakcraft.network.handler.fight;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightStartTurn;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerClientFightStartTurn implements IMessageHandler<PacketFightStartTurn, IMessage> {
    @Override
    public IMessage onMessage(PacketFightStartTurn message, MessageContext ctx) {
        World world = Wakcraft.proxy.getClientWorld();

        EntityLivingBase entity = (EntityLivingBase) world.getEntityByID(message.fighterId);
        if (entity == null) {
            WLog.warning("No loaded entity found with the id : " + message.fighterId);
            return null;
        }

        FightManager.INSTANCE.setCurrentFighter(world, message.getFightId(), entity);

        return null;
    }
}
