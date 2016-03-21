package edu.virginia.lab1test;

import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventDispatcher;

public class PickedUpEvent extends Event {
	public static String KEY_PICKED_UP = "Key Picked Up";

	public PickedUpEvent(String eventType, IEventDispatcher source) {
		super(eventType, source);
	}
	
}
