package heero.mc.mod.wakcraft.fight;

import heero.mc.mod.wakcraft.client.setting.KeyBindings;
import heero.mc.mod.wakcraft.fight.FightInfo.Stage;
import heero.mc.mod.wakcraft.helper.FightHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class FightClientEventsHandler {
	protected IRenderHandler fightWorldRender;

	public FightClientEventsHandler(IRenderHandler fightWorldRender) {
		this.fightWorldRender = fightWorldRender;
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

			if (FightManager.INSTANCE.getFightStage(player.worldObj, FightHelper.getFightId(player)) != Stage.PREFIGHT) {
				return;
			}

			FightManager.INSTANCE.selectPosition(player);
		}
	}
}
