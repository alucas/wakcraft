package heero.wakcraft.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import cpw.mods.fml.common.eventhandler.Event;

public class FightEvent extends Event {
	public enum Type {
		START, STOP,
	}

	public final Type type;
	public final int fightId;
	public final List<List<EntityLivingBase>> fighters;

	public FightEvent(final Type type, final int fightId) {
		super();

		this.type = type;
		this.fightId = fightId;
		this.fighters = new ArrayList<List<EntityLivingBase>>();
	}

	public FightEvent(final Type type, final int fightId, final List<List<EntityLivingBase>> fighters) {
		super();

		this.type = type;
		this.fightId = fightId;
		this.fighters = new ArrayList<List<EntityLivingBase>>();
		this.fighters.add(Collections.unmodifiableList(fighters.get(0)));
		this.fighters.add(Collections.unmodifiableList(fighters.get(1)));
	}
}
