package heero.wakcraft.item;

import heero.wakcraft.WInfo;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemWArmor extends Item {
	public static enum TYPE {
		HELMET, AMULET, EPAULET, CHESTPLATE, CAPE, RING, BELT, BOOTS, WEAPON, PET
	}

	protected static IIcon helmetIcon;
	protected static IIcon amuletIcon;
	protected static IIcon epauletIcon;
	protected static IIcon chestplateIcon;
	protected static IIcon capeIcon;
	protected static IIcon ringIcon;
	protected static IIcon beltIcon;
	protected static IIcon bootsIcon;
	protected static IIcon weaponIcon;
	protected static IIcon petIcon;
	protected static boolean iconsInitialized = false;

	protected final TYPE type;

	@SideOnly(Side.CLIENT)
	public static IIcon getPlaceholderIcon(TYPE type) {
		switch (type) {
		case HELMET:
			return helmetIcon;
		case AMULET:
			return amuletIcon;
		case EPAULET:
			return epauletIcon;
		case CHESTPLATE:
			return chestplateIcon;
		case CAPE:
			return capeIcon;
		case RING:
			return ringIcon;
		case BELT:
			return beltIcon;
		case BOOTS:
			return bootsIcon;
		case WEAPON:
			return weaponIcon;
		case PET:
			return petIcon;
		default:
			return null;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister registerer) {
		if (!iconsInitialized) {
			iconsInitialized = true;

			String modid = WInfo.MODID.toLowerCase() + ":";
			ItemWArmor.helmetIcon = registerer.registerIcon(modid + "empty_armor_slot_helmet");
			ItemWArmor.amuletIcon = registerer.registerIcon(modid + "empty_armor_slot_amulet");
			ItemWArmor.epauletIcon = registerer.registerIcon(modid + "empty_armor_slot_epaulet");
			ItemWArmor.chestplateIcon = registerer.registerIcon(modid + "empty_armor_slot_chestplate");
			ItemWArmor.capeIcon = registerer.registerIcon(modid + "empty_armor_slot_cape");
			ItemWArmor.ringIcon = registerer.registerIcon(modid + "empty_armor_slot_ring");
			ItemWArmor.beltIcon = registerer.registerIcon(modid + "empty_armor_slot_leggings");
			ItemWArmor.bootsIcon = registerer.registerIcon(modid + "empty_armor_slot_boots");
			ItemWArmor.weaponIcon = registerer.registerIcon(modid + "empty_armor_slot_weapon");
			ItemWArmor.petIcon = registerer.registerIcon(modid + "empty_armor_slot_pet");
		}
	}

	public ItemWArmor(TYPE type) {
		this.type = type;
	}

	public TYPE getArmorType() {
		return type;
	}
}
