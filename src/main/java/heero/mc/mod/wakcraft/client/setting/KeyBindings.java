package heero.mc.mod.wakcraft.client.setting;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyBindings {
	private static String HAVEN_BAG = "key.havenbag";
	private static String FIGHT_SELECT_POSITION = "key.fight.selectPosition";

	private static String CATEGORY_INVENTORY = "key.categories.inventory";
	private static String CATEGORY_FIGHT = "key.categories.fight";

	public static KeyBinding havenBag;
	public static KeyBinding fightSelectPosition;

	public static void init() {
		havenBag = new KeyBinding(HAVEN_BAG, Keyboard.KEY_H, CATEGORY_INVENTORY);
		fightSelectPosition = new KeyBinding(FIGHT_SELECT_POSITION, Keyboard.KEY_APOSTROPHE, CATEGORY_FIGHT);

		KeyBinding[] keys = { havenBag, fightSelectPosition };
		for (int i = 0; i < keys.length; i++) {
			ClientRegistry.registerKeyBinding(keys[i]);
		}
	}
}
