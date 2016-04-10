package heero.mc.mod.wakcraft.command;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.network.packet.PacketProfession;
import heero.mc.mod.wakcraft.profession.ProfessionManager;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

public class CommandJobLevel extends CommandBase implements ICommand {
    protected final String COMMAND_NAME = "jobLevel";

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return COMMAND_NAME + " <job name> [new level]";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender.getCommandSenderEntity() instanceof EntityPlayerMP && super.canCommandSenderUseCommand(sender);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1 || args.length > 2) {
            throw new WrongUsageException(getCommandUsage(sender));
        }

        final EntityPlayerMP player = (EntityPlayerMP) sender.getCommandSenderEntity();
        final String argProfessionName = args[0];

        final ProfessionManager.PROFESSION profession;
        try {
            profession = ProfessionManager.PROFESSION.valueOf(argProfessionName.toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("message.profession.unknown", argProfessionName)));
            return;
        }

        if (args.length == 2) {
            final String argNewLevel = args[1];
            final int newLevel;
            try {
                newLevel = Integer.parseInt(argNewLevel);
            } catch (NumberFormatException e) {
                sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("message.profession.notANumber", argNewLevel)));
                return;
            }

            ProfessionManager.setXp(player, profession, ProfessionManager.getXpFromLevel(newLevel > 100 ? 100 : newLevel < 0 ? 0 : newLevel));
            Wakcraft.packetPipeline.sendTo(new PacketProfession(player, profession), player);
        }

        final int level = ProfessionManager.getLevel(player, profession);

        sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("message.profession.level", profession.name().toLowerCase(), level)));
    }
}
