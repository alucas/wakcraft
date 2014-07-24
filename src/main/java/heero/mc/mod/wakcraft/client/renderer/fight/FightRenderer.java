package heero.mc.mod.wakcraft.client.renderer.fight;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.characteristic.Characteristic;
import heero.mc.mod.wakcraft.fight.FightBlockCoordinates;
import heero.mc.mod.wakcraft.fight.FightInfo.FightStage;
import heero.mc.mod.wakcraft.fight.FightManager;
import heero.mc.mod.wakcraft.helper.FightHelper;
import heero.mc.mod.wakcraft.spell.IActiveSpell;
import heero.mc.mod.wakcraft.spell.IRangeMode;
import heero.mc.mod.wakcraft.spell.RangeMode;
import heero.mc.mod.wakcraft.spell.effect.EffectArea;
import heero.mc.mod.wakcraft.spell.effect.IEffectArea;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.IRenderHandler;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FightRenderer extends IRenderHandler {
	@Override
	@SideOnly(Side.CLIENT)
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		EntityPlayer player = mc.thePlayer;

		if (!FightHelper.isFighter(player)) {
			return;
		}

		if (!FightHelper.isFighting(player)) {
			return;
		}

		int fightId = FightHelper.getFightId(player);
		FightStage fightStage = FightManager.INSTANCE.getFightStage(world, fightId);

		if (fightStage == FightStage.PREFIGHT) {
			List<List<FightBlockCoordinates>> startBlocks = FightManager.INSTANCE.getSartPositions(world, fightId);
			if (startBlocks != null) {
				renderStartPosition(partialTicks, world, mc, player, startBlocks);
			}
		} else if (fightStage == FightStage.FIGHT) {
			EntityLivingBase currentFighter = FightManager.INSTANCE.getCurrentFighter(world, FightHelper.getFightId(player));
			if (currentFighter != player) {
				return;
			}

			//renderMovement(partialTicks, world, mc, player);
			//renderDirection(partialTicks, world, mc, player);
			renderSpellRange(partialTicks, world, mc, player);
		}
	}

	public void renderStartPosition(float partialTicks, WorldClient world, Minecraft mc, EntityPlayer player, List<List<FightBlockCoordinates>> startBlocks) {
		RenderBlocks renderBlocks = new RenderBlocks(world);

		double deltaX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
		double deltaY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
		double deltaZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

		mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

		OpenGlHelper.glBlendFunc(774, 768, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		GL11.glPushMatrix();
		GL11.glPolygonOffset(-5.0F, -5.0F);
		GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		Tessellator par1Tessellator = Tessellator.instance;
		par1Tessellator.startDrawingQuads();
		par1Tessellator.setTranslation(-deltaX, -deltaY, -deltaZ);
		par1Tessellator.disableColor();

		for (int i = 0; i < startBlocks.size(); i++) {
			List<FightBlockCoordinates> startBlocksOfTeam = startBlocks.get(i);

			for (FightBlockCoordinates block : startBlocksOfTeam) {
				renderBlocks.renderBlockByRenderType((i == 0) ? WBlocks.fightStart1 : WBlocks.fightStart2, block.posX, block.posY - 1, block.posZ);
			}
		}

		par1Tessellator.draw();
		par1Tessellator.setTranslation(0.0D, 0.0D, 0.0D);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glPolygonOffset(0.0F, 0.0F);
		GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
	}

	public void renderMovement(float partialTicks, WorldClient world, Minecraft mc, EntityPlayer player) {
		RenderBlocks renderBlocks = new RenderBlocks(world);

		double deltaX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
		double deltaY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
		double deltaZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

		mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

		OpenGlHelper.glBlendFunc(774, 768, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		GL11.glPushMatrix();
		GL11.glPolygonOffset(-5.0F, -5.0F);
		GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		Tessellator par1Tessellator = Tessellator.instance;
		par1Tessellator.startDrawingQuads();
		par1Tessellator.setTranslation(-deltaX, -deltaY, -deltaZ);
		par1Tessellator.disableColor();

		ChunkCoordinates currentPosition = FightHelper.getCurrentPosition(player);
		int movement = FightHelper.getFightCharacteristic(player, Characteristic.MOVEMENT);
		for (int x = currentPosition.posX - movement; x <= currentPosition.posX + movement; x++) {
			for (int z = currentPosition.posZ - movement; z <= currentPosition.posZ + movement; z++) {
				if (Math.abs(currentPosition.posX - x) + Math.abs(currentPosition.posZ - z) > movement) continue;

				int y = currentPosition.posY - 1;
				for (; y < world.getHeight() && !world.isAirBlock(x, y + 1, z); ++y);
				for (; y >= 0 && world.isAirBlock(x, y, z); --y);

				if (!world.getBlock(x, y + 1, z).equals(WBlocks.fightInsideWall)) {
					continue;
				}

				renderBlocks.renderBlockByRenderType(WBlocks.fightMovement, x, y, z);
			}
		}

		par1Tessellator.draw();
		par1Tessellator.setTranslation(0.0D, 0.0D, 0.0D);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glPolygonOffset(0.0F, 0.0F);
		GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
	}

	public void renderDirection(float partialTicks, WorldClient world, Minecraft mc, EntityPlayer player) {
		RenderBlocks renderBlocks = new RenderBlocks(world);

		int posX = (int) Math.floor(player.posX);
		int posY = (int) (player.posY - player.yOffset);
		int posZ = (int) Math.floor(player.posZ);

		double deltaX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
		double deltaY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
		double deltaZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

		mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

		OpenGlHelper.glBlendFunc(774, 768, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		GL11.glPushMatrix();
		GL11.glPolygonOffset(-5.0F, -5.0F);
		GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		Tessellator par1Tessellator = Tessellator.instance;
		par1Tessellator.startDrawingQuads();
		par1Tessellator.setTranslation(-deltaX, -deltaY, -deltaZ);
		par1Tessellator.disableColor();

		int size = 3;
		for (int x = posX - size; x <= posX + size; x++) {
			int y = posY - 1;
			for (; y < world.getHeight() && !world.isAirBlock(x, y + 1, posZ); ++y);
			for (; y >= 0 && world.isAirBlock(x, y, posZ); --y);

			if (!world.getBlock(x, y + 1, posZ).equals(WBlocks.fightInsideWall)) {
				continue;
			}

			renderBlocks.renderBlockByRenderType(WBlocks.fightDirection, x, y, posZ);
		}

		for (int z = posZ - size; z <= posZ + size; z++) {
			int y = posY;
			for (; y < world.getHeight() && !world.isAirBlock(posX, y + 1, z); ++y);
			for (; y >= 0 && world.isAirBlock(posX, y, z); --y);

			if (!world.getBlock(posX, y + 1, z).equals(WBlocks.fightInsideWall)) {
				continue;
			}

			renderBlocks.renderBlockByRenderType(WBlocks.fightDirection, posX, y, z);
		}

		par1Tessellator.draw();
		par1Tessellator.setTranslation(0.0D, 0.0D, 0.0D);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glPolygonOffset(0.0F, 0.0F);
		GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
	}

	private void renderSpellRange(float partialTicks, WorldClient world,
			Minecraft mc, EntityPlayer player) {
		RenderBlocks renderBlocks = new RenderBlocks(world);

		double deltaX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
		double deltaY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
		double deltaZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

		mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

		OpenGlHelper.glBlendFunc(774, 768, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		GL11.glPushMatrix();
		GL11.glPolygonOffset(-5.0F, -5.0F);
		GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		Tessellator par1Tessellator = Tessellator.instance;
		par1Tessellator.startDrawingQuads();
		par1Tessellator.setTranslation(-deltaX, -deltaY, -deltaZ);
		par1Tessellator.disableColor();

		ChunkCoordinates currentPosition = FightHelper.getCurrentPosition(player);
		ItemStack stack = FightHelper.getCurrentSpell(player);
		int rangeMin = 0;
		int rangeMax = 0;
		IRangeMode rangeMode = RangeMode.DEFAULT;
		IEffectArea effectArea = EffectArea.POINT;

		if (stack != null && stack.getItem() instanceof IActiveSpell) {
			IActiveSpell spell = (IActiveSpell) stack.getItem();
			int spellLevel = spell.getLevel(stack.getItemDamage());
			rangeMin = spell.getRangeMin(spellLevel);
			rangeMax = spell.getRangeMax(spellLevel);
			rangeMode = spell.getRangeMode();
			effectArea = spell.getDisplayEffectArea();
		}

		if (rangeMode == RangeMode.LINE) {
			displayBlockCross(renderBlocks, WBlocks.fightMovement, world, currentPosition.posX, currentPosition.posY, currentPosition.posZ, rangeMin, rangeMax);
		} else {
			displayBlocksArea(renderBlocks, WBlocks.fightMovement, world, currentPosition.posX, currentPosition.posY, currentPosition.posZ, rangeMin, rangeMax);
		}

		do {
			MovingObjectPosition target = player.rayTrace(rangeMax + 2, partialTicks);
			if (target == null) {
				break;
			}

			if (rangeMode == RangeMode.LINE) {
				if (target.blockX != currentPosition.posX && target.blockZ != currentPosition.posZ) {
					break;
				}

				int distanceX = MathHelper.abs_int(currentPosition.posX - target.blockX);
				if (currentPosition.posZ == target.blockZ && (distanceX < rangeMin || distanceX > rangeMax)) {
					break;
				}

				int distanceZ = MathHelper.abs_int(currentPosition.posZ - target.blockZ);
				if (currentPosition.posX == target.blockX && (distanceZ < rangeMin || distanceZ > rangeMax)) {
					break;
				}
			} else {
				int distanceX = MathHelper.abs_int(currentPosition.posX - target.blockX);
				int distanceZ = MathHelper.abs_int(currentPosition.posZ - target.blockZ);

				if (distanceX + distanceZ > rangeMax) {
					break;
				}

				if (distanceX + distanceZ < rangeMin) {
					break;
				}
			}

			displayBlocks(renderBlocks, WBlocks.fightDirection, world, effectArea.getEffectCoors(currentPosition, target));
		} while(false);

		
		par1Tessellator.draw();
		par1Tessellator.setTranslation(0.0D, 0.0D, 0.0D);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glPolygonOffset(0.0F, 0.0F);
		GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
	}

	private int getFirstAirBlockY(IBlockAccess blockAccess, int posX, int posY, int posZ) {
		for (; posY < blockAccess.getHeight() && !blockAccess.isAirBlock(posX, posY + 1, posZ); ++posY);
		for (; posY >= 0 && blockAccess.isAirBlock(posX, posY, posZ); --posY);

		return posY + 1;
	}

	private void displayBlocksArea(RenderBlocks renderBlocks, Block displayBlock, IBlockAccess blockAccess, int centerX, int centerY, int centerZ, int rangeMin, int rangeMax) {
		for (int x = centerX - rangeMax; x <= centerX + rangeMax; x++) {
			for (int z = centerZ - rangeMax; z <= centerZ + rangeMax; z++) {
				if (Math.abs(centerX - x) + Math.abs(centerZ - z) > rangeMax) continue;
				if (Math.abs(centerX - x) + Math.abs(centerZ - z) < rangeMin) continue;

				int y = getFirstAirBlockY(blockAccess, x, centerY - 1, z) - 1;

				// Do not display block outside the fighting zone
				if (!blockAccess.getBlock(x, y + 1, z).equals(WBlocks.fightInsideWall)) {
					continue;
				}

				renderBlocks.renderBlockByRenderType(displayBlock, x, y, z);
			}
		}
	}

	private void displayBlockCross(RenderBlocks renderBlocks, Block displayBlock, IBlockAccess blockAccess, int posX, int posY, int posZ, int rangeMin, int rangeMax) {
		for (int x = posX - rangeMax; x <= posX + rangeMax; x++) {
			if (Math.abs(posX - x) > rangeMax) continue;
			if (Math.abs(posX - x) < rangeMin) continue;
			
			int z = posZ;
			int y = getFirstAirBlockY(blockAccess, x, posY - 1, z) - 1;

			// Do not display block outside the fighting zone
			if (!blockAccess.getBlock(x, y + 1, z).equals(WBlocks.fightInsideWall)) {
				continue;
			}
			
			renderBlocks.renderBlockByRenderType(displayBlock, x, y, z);
		}

		for (int z = posZ - rangeMax; z <= posZ + rangeMax; z++) {
			if (Math.abs(posZ - z) > rangeMax) continue;
			if (Math.abs(posZ - z) < rangeMin) continue;

			int x = posX;
			int y = getFirstAirBlockY(blockAccess, x, posY - 1, z) - 1;

			// Do not display block outside the fighting zone
			if (!blockAccess.getBlock(x, y + 1, z).equals(WBlocks.fightInsideWall)) {
				continue;
			}

			renderBlocks.renderBlockByRenderType(displayBlock, x, y, z);
		}
	}

	private void displayBlocks(RenderBlocks renderBlocks, Block displayBlock, IBlockAccess blockAccess, List<ChunkCoordinates> coordinatesList) {
		for (ChunkCoordinates coordinates : coordinatesList) {
			int y = getFirstAirBlockY(blockAccess, coordinates.posX, coordinates.posY - 1, coordinates.posZ) - 1;

			// Do not display block outside the fighting zone
			if (!blockAccess.getBlock(coordinates.posX, y + 1, coordinates.posZ).equals(WBlocks.fightInsideWall)) {
				continue;
			}

			renderBlocks.renderBlockByRenderType(displayBlock, coordinates.posX, y, coordinates.posZ);
		}
	}
}
