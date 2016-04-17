package heero.mc.mod.wakcraft.network.handler.fight;

import heero.mc.mod.wakcraft.fight.FightHelper;
import heero.mc.mod.wakcraft.fight.FightInfo;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightCastSpell;
import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerServerFightCastSpell implements IMessageHandler<PacketFightCastSpell, IMessage> {
    @Override
    public IMessage onMessage(final PacketFightCastSpell message, final MessageContext ctx) {
        IThreadListener mainThread = Minecraft.getMinecraft();
        mainThread.addScheduledTask(new Runnable() {
            @Override
            public void run() {
                onMessageHandler(message, ctx);
            }
        });

        return null;
    }

    public void onMessageHandler(final PacketFightCastSpell message, final MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;

        if (!FightUtil.isFighter(player)) {
            throw new RuntimeException("The entity " + player + " is not a valid fighter");
        }

        if (!FightUtil.isFighting(player)) {
            throw new RuntimeException("The entity " + player + " is not fighting");
        }

        int fightId = FightUtil.getFightId(player);
        if (FightUtil.getFightStage(player.worldObj, fightId) != FightInfo.FightStage.FIGHT) {
            throw new RuntimeException("The entity " + player + " can't cast a spell, the fight is not in the right stage ( " + FightUtil.getFightStage(player.worldObj, fightId) + " )");
        }

        if (FightUtil.getCurrentFighter(player.worldObj, fightId) != player) {
            throw new RuntimeException("The entity " + player + " can't cast a spell, it's not his turn");
        }

        FightHelper.tryCastSpell(player, message.targetPosition);
    }
}
