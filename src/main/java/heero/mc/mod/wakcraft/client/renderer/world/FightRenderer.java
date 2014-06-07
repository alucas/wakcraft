package heero.mc.mod.wakcraft.client.renderer.world;

import heero.mc.mod.wakcraft.WBlocks;
import heero.mc.mod.wakcraft.helper.FightHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.event.RenderHandEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FightRenderer extends IRenderHandler {
	@Override
	@SideOnly(Side.CLIENT)
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		EntityPlayer player = mc.thePlayer;

		renderMovement(partialTicks, world, mc, player);
		renderDirection(partialTicks, world, mc, player);
	}

	public void renderMovement(float partialTicks, WorldClient world, Minecraft mc, EntityPlayer player) {
		RenderBlocks renderBlocks = new RenderBlocks(world);

		int posX = (int) Math.floor(player.posX);
		int posY = (int) (player.posY - 1.63);
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

		int movement = 5;
		for (int x = posX - movement; x <= posX + movement; x++) {
			for (int z = posZ - movement; z <= posZ + movement; z++) {
				if (Math.abs(posX - x) + Math.abs(posZ - z) > movement) continue;

				int y = posY;
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
		int posY = (int) (player.posY - 1.63);
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
			int y = posY;
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

	@SubscribeEvent
	public void onRenderHandEvent(RenderHandEvent event) {
		Minecraft mc = Minecraft.getMinecraft();

		if (!FightHelper.isFighter(mc.thePlayer)) {
			return;
		}

		if (!FightHelper.isFighting(mc.thePlayer)) {
			return;
		}

		render(event.partialTicks, mc.theWorld, mc);
	}
}
