package edu.virginia.game.managers;

import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.GameEvent;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.game.objects.EventTypes;
import edu.virginia.game.objects.PickedUpItem;
import edu.virginia.game.objects.Player;
import edu.virginia.game.objects.Poison;
import edu.virginia.game.objects.VP;

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
	public void handleEvent(GameEvent event) {
		if (event.getEventType().equals(EventTypes.PICKUP_POISON.toString())) {
			// should be dispatched by the poison
			Poison poison = (Poison) event.getSource();
			if (poison.isVisible()) { // has faded out already
				poison.setVisible(false);
			}
		} else if (event.getEventType().equals(EventTypes.PICKUP_VP.toString())) {
			// should be dispatched by the vp
			VP vp = (VP) event.getSource();
			if (vp.isVisible()) { // has faded out already
				vp.setVisible(false);
			}
			
			
		}

	}
}
