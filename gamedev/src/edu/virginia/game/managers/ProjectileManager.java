package edu.virginia.game.managers;

import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.game.objects.EventTypes;

/*
 * Singleton class that handles all the VP and Poison events
*/
public class ProjectileManager implements IEventListener {
	private static volatile ProjectileManager instance;

	public static ProjectileManager getInstance() {
		if (instance == null) {
			instance = new ProjectileManager();
		}
		return instance;
	}

	public ProjectileManager() {
		instance = this;
	}

	@Override
	public void handleEvent(Event event) {
		if(event.getEventType().equals(EventTypes.PICKUP_POISON.toString())) {
			
		} else if(event.getEventType().equals(EventTypes.PICKUP_VP.toString())) {
			
		}

	}
}
