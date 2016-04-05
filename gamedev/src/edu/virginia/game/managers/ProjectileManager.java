package edu.virginia.game.managers;

import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.GameEvent;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.game.objects.EventTypes;
<<<<<<< HEAD
import edu.virginia.game.objects.PickedUpItem;
import edu.virginia.game.objects.Player;
import edu.virginia.game.objects.Poison;
import edu.virginia.game.objects.VP;
=======
>>>>>>> 0af037cce39afb87991f83cdd618c275cf043360

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
<<<<<<< HEAD
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
			
=======
	public void handleEvent(Event event) {
		if(event.getEventType().equals(EventTypes.PICKUP_POISON.toString())) {
>>>>>>> 0af037cce39afb87991f83cdd618c275cf043360
			
		}

	}
}
