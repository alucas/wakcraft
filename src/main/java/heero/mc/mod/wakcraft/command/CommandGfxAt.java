package heero.mc.mod.wakcraft.command;

import net.minecraft.command.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.io.File;
import java.util.Map;
import java.util.Properties;

public class CommandGfxAt extends CommandBase implements ICommand {
    @Override
    public String getCommandName() {
        return "gfxAt";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "gfxAt <map id> <x> <z>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1 && args.length != 3) {
            throw new WrongUsageException(getCommandUsage(sender));
        }

        try {
            final int mapId = Integer.parseInt(args[0]);

            int x;
            int z;
            if (args.length == 3) {
                x = Integer.parseInt(args[1]);
                z = Integer.parseInt(args[2]);
            } else {
                MovingObjectPosition mop = sender.getCommandSenderEntity().rayTrace(10, 1.0F);
                if (mop == null) {
                    throw new WrongUsageException("You need to look at a block");
                }

                x = (int) Math.floor(mop.hitVec.xCoord);
                z = (int) Math.floor(mop.hitVec.zCoord);
            }

            printGfxAr(sender.getEntityWorld(), sender.getCommandSenderEntity(), mapId, x, z);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static void printGfxAr(final World world, final Entity player, final int mapId, final int x, final int z) {
        if (world.isRemote) {
            return;
        }

        File baseFolder = Decode.getBaseFolder(player, world);
        if (baseFolder == null) {
            return;
        }

        Properties blocks = Decode.decodeBlockFile(player, baseFolder);
        if (blocks == null) {
            return;
        }

        Map<Integer, Integer> elementsIdToGfxId = Decode.decodeElementsLib(player, baseFolder);
        if (elementsIdToGfxId == null) {
            return;
        }

        Decode.decodeGfxFiles(player, elementsIdToGfxId, blocks, baseFolder, mapId, x, z);
    }
}
