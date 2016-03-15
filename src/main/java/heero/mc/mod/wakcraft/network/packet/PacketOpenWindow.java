package heero.mc.mod.wakcraft.network.packet;

import heero.mc.mod.wakcraft.network.GuiId;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketOpenWindow implements IMessage {
    public GuiId windowId;

    public PacketOpenWindow() {
    }

    public PacketOpenWindow(GuiId windowId) {
        this.windowId = windowId;
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        int index = buffer.readInt();
        if (index < 0 || index > GuiId.values().length) {
            this.windowId = GuiId.UNKNOW;
            return;
        }

        this.windowId = GuiId.values()[index];
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(windowId.ordinal());
    }
}