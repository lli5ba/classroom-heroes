package edu.virginia.game.main;

import edu.virginia.engine.events.GameEvent;
import edu.virginia.engine.events.IEventDispatcher;

public class PickedUpEvent extends GameEvent {
	public static String KEY_PICKED_UP = "Key Picked Up";

	public PickedUpEvent(String eventType, IEventDispatcher source) {
		super(eventType, source);
	}

}
