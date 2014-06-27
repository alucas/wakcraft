package heero.mc.mod.wakcraft.fight;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.client.gui.fight.GuiFightOverlay;
import heero.mc.mod.wakcraft.client.renderer.fight.FighterRenderer;
import heero.mc.mod.wakcraft.client.setting.KeyBindings;
import heero.mc.mod.wakcraft.fight.FightInfo.FightStage;
import heero.mc.mod.wakcraft.helper.FightHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class FightClientEventsHandler {
	protected IRenderHandler fightWorldRender;
	protected GuiFightOverlay guiFightOverlay;
	protected FighterRenderer fighterRenderer;

	public FightClientEventsHandler(IRenderHandler fightWorldRender, GuiFightOverlay guiFightOverlay, FighterRenderer fighterRenderer) {
		this.fightWorldRender = fightWorldRender;
		this.guiFightOverlay = guiFightOverlay;
		this.fighterRenderer = fighterRenderer;
	}

	@SubscribeEvent
	public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
		Minecraft mc = Minecraft.getMinecraft();

		fightWorldRender.render(event.partialTicks, mc.theWorld, mc);
	}

	@SubscribeEvent
	public void onRenderLivingPreEvent(RenderLivingEvent.Pre event) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		EntityLivingBase entity = event.entity;

		if (!FightHelper.isFighter(player) || !FightHelper.isFighting(player)) {
			return;
		}

		if (FightHelper.isFighter(entity) && FightHelper.getFightId(entity) == FightHelper.getFightId(player)) {
			return;
		}

		event.setCanceled(true);
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (KeyBindings.fightSelectPosition.isPressed()) {
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			if (!FightHelper.isFighter(player) || !FightHelper.isFighting(player)) {
				return;
			}

			if (FightManager.INSTANCE.getFightStage(player.worldObj, FightHelper.getFightId(player)) != FightStage.PREFIGHT) {
				return;
			}

			if (FightHelper.getStartPosition(player) != null) {
				FightManager.INSTANCE.selectPosition(player, null);
				return;
			}

			ChunkCoordinates position = new ChunkCoordinates(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY - player.yOffset), MathHelper.floor_double(player.posZ));
			FightManager.INSTANCE.selectPosition(player, position);
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlayEventPre(RenderGameOverlayEvent.Pre event) {
		if (event.type == ElementType.HOTBAR) {
			EntityPlayer player = Wakcraft.proxy.getClientPlayer();
			if (!FightHelper.isFighter(player) || !FightHelper.isFighting(player)) {
				return;
			}

			guiFightOverlay.renderFighterHotbar(player, event.resolution, event.partialTicks);

			event.setCanceled(true);
			return;
		}
	}

	@SubscribeEvent
	public void onRenderHandEvent(RenderHandEvent event) {
		EntityPlayer player = Wakcraft.proxy.getClientPlayer();
		if (!FightHelper.isFighter(player) || !FightHelper.isFighting(player)) {
			return;
		}

		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		fighterRenderer.renderHand(event.partialTicks, event.renderPass);

		event.setCanceled(true);
		return;
	}

	@SubscribeEvent
	public void onServerTickEvent(ServerTickEvent event) {
		if (event.phase != Phase.END) {
			return;
		}

		EntityPlayer player = Wakcraft.proxy.getClientPlayer();
		if (!FightHelper.isFighter(player) || !FightHelper.isFighting(player) || !(player instanceof EntityPlayerSP)) {
			return;
		}

		fighterRenderer.updateRenderer((EntityPlayerSP) player);
	}
}
