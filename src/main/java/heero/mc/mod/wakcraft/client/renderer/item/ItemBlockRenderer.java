package heero.mc.mod.wakcraft.client.renderer.item;

import heero.mc.mod.wakcraft.WLog;
import heero.mc.mod.wakcraft.item.IBlockProvider;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemBlockRenderer implements IItemRenderer {
	private RenderBlocks renderBlocksIr = new RenderBlocks();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		if (!(stack.getItem() instanceof IBlockProvider)) {
			WLog.warning("The " + this.getClass().getCanonicalName() + " renderer is designed to work with item implementing " + IBlockProvider.class.getCanonicalName());
			return;
		}

		Block block = ((IBlockProvider) stack.getItem()).getBlock();
		block.setBlockBoundsForItemRender();

		this.renderBlocksIr.renderBlockAsItem(block, 0, 1.0F);
	}
}
