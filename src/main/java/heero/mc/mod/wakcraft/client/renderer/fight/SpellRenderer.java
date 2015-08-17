package heero.mc.mod.wakcraft.client.renderer.fight;

import heero.mc.mod.wakcraft.util.FightUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;

public class SpellRenderer extends ItemRenderer {
	public SpellRenderer(Minecraft minecraft) {
		super(minecraft);
	}

	@Override
	public void updateEquippedItem() {
		this.prevEquippedProgress = this.equippedProgress;

        EntityPlayerSP player = this.mc.thePlayer;
		int currentItemId = player.inventory.currentItem;

		ItemStack itemstack = FightUtil.getSpellsInventory(player).getStackInSlot(25 + currentItemId);
		boolean flag = this.equippedItemSlot == currentItemId && itemstack == this.itemToRender;

		if (this.itemToRender == null && itemstack == null) {
			flag = true;
		}

		if (itemstack != null
				&& this.itemToRender != null
				&& itemstack != this.itemToRender
				&& itemstack.getItem() == this.itemToRender.getItem()
				&& itemstack.getItemDamage() == this.itemToRender
						.getItemDamage()) {
			this.itemToRender = itemstack;
			flag = true;
		}

		float f = 0.4F;
		float f1 = flag ? 1.0F : 0.0F;
		float f2 = f1 - this.equippedProgress;

		if (f2 < -f) {
			f2 = -f;
		}

		if (f2 > f) {
			f2 = f;
		}

		this.equippedProgress += f2;

		if (this.equippedProgress < 0.1F) {
			this.itemToRender = itemstack;
			this.equippedItemSlot = player.inventory.currentItem;
		}
	}
}
