package heero.mc.mod.wakcraft.fight;

import heero.mc.mod.wakcraft.Wakcraft;
import heero.mc.mod.wakcraft.client.gui.fight.GuiFightOverlay;
import heero.mc.mod.wakcraft.client.renderer.fight.FighterRenderer;
import heero.mc.mod.wakcraft.client.setting.KeyBindings;
import heero.mc.mod.wakcraft.fight.FightInfo.FightStage;
import heero.mc.mod.wakcraft.util.FightUtil;
import heero.mc.mod.wakcraft.network.packet.fight.PacketFightCastSpell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

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

		if (!FightUtil.isFighter(player) || !FightUtil.isFighting(player)) {
			return;
		}

		if (FightUtil.isFighter(entity) && FightUtil.getFightId(entity) == FightUtil.getFightId(player)) {
			return;
		}

		event.setCanceled(true);
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (KeyBindings.fightSelectPosition.isPressed()) {
			EntityPlayer player = Wakcraft.proxy.getClientPlayer();
			if (!FightUtil.isFighter(player) || !FightUtil.isFighting(player)) {
				return;
			}

			if (FightUtil.getFightStage(player.worldObj, FightUtil.getFightId(player)) != FightStage.PREFIGHT) {
				return;
			}

			if (FightUtil.getStartPosition(player) != null) {
				FightUtil.selectPosition(player, null);
				return;
			}

            BlockPos position = new BlockPos(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY - player.getYOffset()), MathHelper.floor_double(player.posZ));
			FightUtil.selectPosition(player, position);
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlayEventPre(RenderGameOverlayEvent.Pre event) {
		if (event.type == ElementType.HOTBAR) {
			EntityPlayer player = Wakcraft.proxy.getClientPlayer();
			if (!FightUtil.isFighter(player) || !FightUtil.isFighting(player)) {
				return;
			}

			guiFightOverlay.renderFighterHotbar(player, event.resolution, event.partialTicks);
			guiFightOverlay.renderCharacteristics(player, event.resolution);

			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onRenderHandEvent(RenderHandEvent event) {
		EntityPlayer player = Wakcraft.proxy.getClientPlayer();
		if (!FightUtil.isFighter(player) || !FightUtil.isFighting(player)) {
			return;
		}

		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		fighterRenderer.renderHand(event.partialTicks, event.renderPass);

		event.setCanceled(true);
	}

	@SubscribeEvent
	public void onServerTickEvent(TickEvent.ServerTickEvent event) {
		if (event.phase != TickEvent.Phase.END) {
			return;
		}

		EntityPlayer player = Wakcraft.proxy.getClientPlayer();
		if (!FightUtil.isFighter(player) || !FightUtil.isFighting(player) || !(player instanceof EntityPlayerSP)) {
			return;
		}

		fighterRenderer.updateRenderer((EntityPlayerSP) player);
	}

	private boolean isAttackKeyDown = false;

	@SubscribeEvent
	public void onMouseEvent(MouseEvent event) {
		EntityPlayer player = Wakcraft.proxy.getClientPlayer();
		if (!FightUtil.isFighter(player) || !FightUtil.isFighting(player)) {
			return;
		}

		Minecraft mc = Minecraft.getMinecraft();
		int fightId = FightUtil.getFightId(player);
		if (FightUtil.getFightStage(player.worldObj, fightId) != FightInfo.FightStage.FIGHT) {
			return;
		}

		if (FightUtil.getCurrentFighter(mc.theWorld, fightId) != player) {
			return;
		}

		if (!GameSettings.isKeyDown(mc.gameSettings.keyBindAttack)) {
			isAttackKeyDown = false;

			return;
		}

		if (isAttackKeyDown) {
			return;
		}

		isAttackKeyDown = true;

		BlockPos fighterPosition = FightUtil.getCurrentPosition(player);
		MovingObjectPosition targetPosition = player.rayTrace(20, 1);
		ItemStack spellStack = FightUtil.getCurrentSpell(player);
		if (targetPosition == null || !heero.mc.mod.wakcraft.fight.FightUtil.isAimingPositionValid(fighterPosition, targetPosition, spellStack)) {
			return;
		}

		Wakcraft.packetPipeline.sendToServer(new PacketFightCastSpell(FightUtil.getFightId(player), targetPosition.func_178782_a()));
		event.setCanceled(true);
	}
}
