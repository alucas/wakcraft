package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.WInfo;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class SpellsProperty implements IExtendedEntityProperties, ISynchProperties {
	public static final String IDENTIFIER = WInfo.MODID + "Spells";

	protected static String TAG_SPELLS = "Spells";
	protected static String TAG_SLOT = "Slot";

	protected IInventory spellsInventory = new InventoryBasic("spellsInventory", false, 25);

	@Override
	public void init(Entity entity, World world) {
	}

	@Override
	public void saveNBTData(NBTTagCompound tagRoot) {
		NBTTagList tagSpells = new NBTTagList();

		for (int i = 0; i < spellsInventory.getSizeInventory(); i++) {
			ItemStack spell = spellsInventory.getStackInSlot(i);
			if (spell == null) {
				continue;
			}

			NBTTagCompound tagSpell = new NBTTagCompound();
			tagSpell.setInteger(TAG_SLOT, i);
			spell.writeToNBT(tagSpell);

			tagSpells.appendTag(tagSpell);
		}

		tagRoot.setTag(TAG_SPELLS, tagSpells);
	}

	@Override
	public void loadNBTData(NBTTagCompound tagRoot) {
		NBTTagList tagCharacteristics = tagRoot.getTagList(TAG_SPELLS, 10);

		for (int i = 0; i < tagCharacteristics.tagCount(); i++) {
			NBTTagCompound tagSpell = tagCharacteristics.getCompoundTagAt(i);
			Integer slotId = tagSpell.getInteger(TAG_SLOT);
			ItemStack spell = ItemStack.loadItemStackFromNBT(tagSpell);

			spellsInventory.setInventorySlotContents(slotId, spell);
		}
	}

	@Override
	public NBTTagCompound getClientPacket() {
		NBTTagCompound tagRoot = new NBTTagCompound();

		saveNBTData(tagRoot);

		return tagRoot;
	}

	@Override
	public void onClientPacket(NBTTagCompound tagRoot) {
		loadNBTData(tagRoot);
	}
}
