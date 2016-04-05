package edu.virginia.engine.events;

public class CollisionEvent extends GameEvent {
	public static String COLLISION = "Collision Detected";

	public CollisionEvent(String eventType, IEventDispatcher source) {
		super(eventType, source);
	}
}
