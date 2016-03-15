package heero.mc.mod.wakcraft.entity.property;

import heero.mc.mod.wakcraft.Reference;
import heero.mc.mod.wakcraft.WItems;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class SpellsProperty implements IExtendedEntityProperties, ISynchProperties {
    public static final String IDENTIFIER = Reference.MODID + "Spells";

    protected static String TAG_SPELLS = "Spells";
    protected static String TAG_SLOT = "Slot";

    protected IInventory spellsInventory = new InventoryBasic("spellsInventory", false, 34);

    @Override
    public void init(Entity entity, World world) {
        spellsInventory.setInventorySlotContents(0, new ItemStack(WItems.spellThunderbolt));
        spellsInventory.setInventorySlotContents(1, new ItemStack(WItems.spellJudgment));
        spellsInventory.setInventorySlotContents(2, new ItemStack(WItems.spellSuperIopPunch));
        spellsInventory.setInventorySlotContents(3, new ItemStack(WItems.spellCelestialSword));
        spellsInventory.setInventorySlotContents(4, new ItemStack(WItems.spellIopsWrath));
        spellsInventory.setInventorySlotContents(5, new ItemStack(WItems.spellShaker));
        spellsInventory.setInventorySlotContents(6, new ItemStack(WItems.spellRocknoceros));
        spellsInventory.setInventorySlotContents(7, new ItemStack(WItems.spellImpact));
        spellsInventory.setInventorySlotContents(8, new ItemStack(WItems.spellCharge));
        spellsInventory.setInventorySlotContents(9, new ItemStack(WItems.spellDevastate));
        spellsInventory.setInventorySlotContents(10, new ItemStack(WItems.spellJabs));
        spellsInventory.setInventorySlotContents(11, new ItemStack(WItems.spellFlurry));
        spellsInventory.setInventorySlotContents(12, new ItemStack(WItems.spellIntimidation));
        spellsInventory.setInventorySlotContents(13, new ItemStack(WItems.spellGuttingGust));
        spellsInventory.setInventorySlotContents(14, new ItemStack(WItems.spellUppercut));
        spellsInventory.setInventorySlotContents(15, new ItemStack(WItems.spellJump));
        spellsInventory.setInventorySlotContents(16, new ItemStack(WItems.spellDefensiveStance));
        spellsInventory.setInventorySlotContents(17, new ItemStack(WItems.spellFlatten));
        spellsInventory.setInventorySlotContents(18, new ItemStack(WItems.spellBraveryStandard));
        spellsInventory.setInventorySlotContents(19, new ItemStack(WItems.spellIncrease));
        spellsInventory.setInventorySlotContents(20, new ItemStack(WItems.spellVirility));
        spellsInventory.setInventorySlotContents(21, new ItemStack(WItems.spellCompulsion));
        spellsInventory.setInventorySlotContents(22, new ItemStack(WItems.spellAuthority));
        spellsInventory.setInventorySlotContents(23, new ItemStack(WItems.spellShowOff));
        spellsInventory.setInventorySlotContents(24, new ItemStack(WItems.spellLockingPro));
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

    public IInventory getSpellsInventory() {
        return spellsInventory;
    }
}
