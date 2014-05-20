package heero.wakcraft.event;

import cpw.mods.fml.common.eventhandler.Event;

public class FightEvent extends Event {
	public enum Type {
		START, STOP,
	}

	public Type type;
	public int fightId;

	public FightEvent(Type type, int fightId) {
		super();

		this.type = type;
		this.fightId = fightId;
	}
}
