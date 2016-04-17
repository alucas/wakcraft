package heero.mc.mod.wakcraft.command;

import heero.mc.mod.wakcraft.WConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Property;

import java.util.List;
import java.util.Set;

public class CommandFightRule extends CommandBase {
    /**
     * Gets the name of the command
     */
    @Override
    public String getCommandName() {
        return "fightrule";
    }

    /**
     * Gets the usage string for the command.
     */
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.gamerule.usage";
    }

    /**
     * Callback when the command is invoked
     */
    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        final ConfigCategory category = getFightRules();

        final String propertyName = args.length > 0 ? args[0] : "";
        final String propertyValue = args.length > 1 ? buildString(args, 1) : "";

        final Property property;
        switch (args.length) {
            case 0:
                sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
                break;
            case 1:

                if (!category.containsKey(propertyName)) {
                    throw new CommandException("commands.gamerule.norule", propertyName);
                }

                property = category.get(propertyName);
                sender.addChatMessage((new ChatComponentText(propertyName)).appendText(" = ").appendText(property.getString()));
                break;
            default:

                if (!category.containsKey(propertyName)) {
                    throw new CommandException("Unknown fight rule : " + propertyName);
                }

                property = category.get(propertyName);
                switch (property.getType()) {
                    case BOOLEAN:
                        property.set("true".equals(propertyValue));
                        break;
                    case INTEGER:
                        try {
                            final int value = Integer.parseInt(propertyValue);
                            property.set(value);
                        } catch (NumberFormatException e) {
                            throw new CommandException("Can't save property " + propertyName + " : " + propertyValue + " is not a number");
                        }
                        break;
                    default:
                        throw new CommandException("Can't save property " + propertyName + " : Unimplemented format ");
                }

                saveFightRules();

                notifyOperators(sender, this, "Fight rule " + propertyName + " set to " + propertyValue);
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            Set<String> keys = getFightRules().keySet();
            String[] keyArray = new String[keys.size()];

            return getListOfStringsMatchingLastWord(args, keys.toArray(keyArray));
        }

        return null;
    }

    private void saveFightRules() {
        WConfig.saveConfig();
    }

    private ConfigCategory getFightRules() {
        return WConfig.config.getCategory(WConfig.CATEGORY_FIGHT);
    }
}