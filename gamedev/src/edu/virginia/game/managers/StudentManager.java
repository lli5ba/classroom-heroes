package edu.virginia.game.managers;

import edu.virginia.engine.events.GameEvent;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.game.objects.EventTypes;
import edu.virginia.game.objects.Player;
import edu.virginia.game.objects.Student;

/*
 * Singleton class that handles all the classmate interactions
*/
public class StudentManager implements IEventListener {
	private static volatile StudentManager instance;

	public static StudentManager getInstance() {
		if (instance == null) {
			instance = new StudentManager();
		}
		return instance;
	}

	public StudentManager() {
		instance = this;
	}

	@Override
	public void handleEvent(GameEvent event) {
		if (event.getEventType().equals(EventTypes.POISON_STUDENT.toString())) {
			// should be dispatched by the student
			Student student = (Student) event.getSource();
			// player.getPoisonBubbles.animateOnce("poison"); //play animation
			student.setPoisoned(true);
		} else if (event.getEventType().equals(EventTypes.CURE_STUDENT.toString())) {
			// should be dispatched by the student
			Student student = (Student) event.getSource();
			// play animation to show student floating back up
			student.animateOnce("floatdown", 5);
			student.setDead(false);
			student.setPoisoned(false);
			student.setCurrentHealth(student.getMaxHealth());
			student.updateHealthBar();
		}

	}
}
