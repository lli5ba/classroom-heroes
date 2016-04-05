package edu.virginia.game.managers;

import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;

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
		// TODO Auto-generated method stub

	}
}
