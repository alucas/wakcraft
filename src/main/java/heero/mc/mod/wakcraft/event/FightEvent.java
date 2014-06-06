package heero.mc.mod.wakcraft.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.Event;

public class FightEvent extends Event {
	public enum Type {
		START, STOP,
	}

	public final World world;
	public final Type type;
	public final int fightId;
	public final List<List<EntityLivingBase>> fighters;

	public FightEvent(final World world, final Type type, final int fightId) {
		super();

		this.world = world;
		this.type = type;
		this.fightId = fightId;
		this.fighters = new ArrayList<List<EntityLivingBase>>();
	}

	public FightEvent(final World world, final Type type, final int fightId, final List<List<EntityLivingBase>> fighters) {
		super();

		this.world = world;
		this.type = type;
		this.fightId = fightId;
		this.fighters = new ArrayList<List<EntityLivingBase>>();
		this.fighters.add(Collections.unmodifiableList(fighters.get(0)));
		this.fighters.add(Collections.unmodifiableList(fighters.get(1)));
	}
}
