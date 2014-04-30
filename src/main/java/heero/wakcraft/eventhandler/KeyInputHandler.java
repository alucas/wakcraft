package heero.wakcraft.eventhandler;

import heero.wakcraft.client.setting.KeyBindings;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyInputHandler {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(KeyBindings.havenBag.isPressed()) {
            System.out.println("ping");
        }
    }
}