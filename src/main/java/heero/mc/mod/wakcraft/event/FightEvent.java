package heero.mc.mod.wakcraft.event;

import heero.mc.mod.wakcraft.fight.FightBlockCoordinates;
import heero.mc.mod.wakcraft.fight.FightInfo.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.Event;

public class FightEvent extends Event {
	public enum Type {
		UNKNOW, START, STOP, CHANGE_STAGE,
	}

	public final World world;
	public final Type type;
	public final int fightId;

	public FightEvent(final Type type, final World world, final int fightId) {
		super();

		this.world = world;
		this.type = type;
		this.fightId = fightId;
	}

	public static class FightStartEvent extends FightEvent {
		public final List<List<EntityLivingBase>> fighters;
		public final List<List<FightBlockCoordinates>> startBlocks;

		public FightStartEvent(World world, int fightId, List<List<EntityLivingBase>> fighters, List<List<FightBlockCoordinates>> startBlocks) {
			super(Type.START, world, fightId);

			this.startBlocks = startBlocks;
			this.fighters = new ArrayList<List<EntityLivingBase>>();
			this.fighters.add(Collections.unmodifiableList(fighters.get(0)));
			this.fighters.add(Collections.unmodifiableList(fighters.get(1)));
		}
	}

	public static class FightStopEvent extends FightEvent {
		public final List<List<EntityLivingBase>> fighters;

		public FightStopEvent(World world, int fightId, List<List<EntityLivingBase>> fighters) {
			super(Type.STOP, world, fightId);

			this.fighters = new ArrayList<List<EntityLivingBase>>();
			this.fighters.add(Collections.unmodifiableList(fighters.get(0)));
			this.fighters.add(Collections.unmodifiableList(fighters.get(1)));
		}
	}

	public static class FightChangeStageEvent extends FightEvent {
		public final Stage stage;

		public FightChangeStageEvent(World world, int fightId, Stage stage) {
			super(Type.CHANGE_STAGE, world, fightId);

			this.stage = stage;
		}
	}
}
