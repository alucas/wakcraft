package heero.mc.mod.wakcraft.event;

import heero.mc.mod.wakcraft.fight.FightBlockCoordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.Event;

public class FightEvent extends Event {
	public enum Type {
		UNKNOW, START, STOP,
	}

	public final World world;
	public final Type type;
	public final int fightId;
	public final List<List<EntityLivingBase>> fighters;
	public final List<FightBlockCoordinates> startBlocks;

	protected FightEvent(final Type type, final World world, final int fightId, final List<List<EntityLivingBase>> fighters) {
		super();

		this.world = world;
		this.type = type;
		this.startBlocks = null;
		this.fightId = fightId;

		this.fighters = new ArrayList<List<EntityLivingBase>>();
		this.fighters.add(Collections.unmodifiableList(fighters.get(0)));
		this.fighters.add(Collections.unmodifiableList(fighters.get(1)));
	}

	protected FightEvent(final Type type, final World world, final int fightId, final List<List<EntityLivingBase>> fighters, List<FightBlockCoordinates> startBlocks) {
		super();

		this.world = world;
		this.type = type;
		this.startBlocks = startBlocks;
		this.fightId = fightId;

		this.fighters = new ArrayList<List<EntityLivingBase>>();
		this.fighters.add(Collections.unmodifiableList(fighters.get(0)));
		this.fighters.add(Collections.unmodifiableList(fighters.get(1)));
	}

	public static FightEvent getStartInstance(final World world, final int fightId, final List<List<EntityLivingBase>> fighters, List<FightBlockCoordinates> startBlocks) {
		return new FightEvent(Type.START, world, fightId, fighters, startBlocks);
	}

	public static FightEvent getStopInstance(final World world, final int fightId, final List<List<EntityLivingBase>> fighters) {
		return new FightEvent(Type.STOP, world, fightId, fighters);
	}
}
