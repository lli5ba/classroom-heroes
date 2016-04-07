package edu.virginia.game.managers;

import java.util.ArrayList;

import edu.virginia.engine.events.GameEvent;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.game.objects.EventTypes;
import edu.virginia.game.objects.Player;
import edu.virginia.game.objects.Student;

/*
 * Singleton class that handles all the current level (time, what level, etc) 
*/
public class LevelManager implements IEventListener {
	private static volatile LevelManager instance;
	public int gameHeight = 500;
	public int gameWidth = 800;
	int numLevel;
	// Some way to keep track of time?

	/* Temporary Level Stats to Award Bonuses? */
	// Combined Stats

	// int studentsCured;

	// Player One
	int vpCollected1;
	int poisonCollected1;
	int studentsCured1;

	// Player Two
	int vpCollected2;
	int poisonCollected2;
	int studentsCured2;

	/* Constructors */

	public void calcEXP(ArrayList<Student> s, double currentHP, double maxHP, boolean flask) {
		/*
		double exp = 0;
		for (Student stud: s)
		{
			exp += stud.getHealth();   //Health should be a max of 25000
		}
		exp += currentHP/maxHP*50000;
		
		if(flask)
			exp*=1.2;
		*/
	}

	public static LevelManager getInstance() {
		if (instance == null) {
			instance = new LevelManager();
		}
		return instance;
	}

	public LevelManager() {
		instance = this;
		numLevel = 1; // start on level 1
		vpCollected1 = 0;
		poisonCollected1 = 0;
		vpCollected2 = 0;
		poisonCollected2 = 0;
	}

	/*********** Temporary Level Stats Getters and Setters *********/

	public int getVPCollected(int numPlayer) {
		switch (numPlayer) {
		case 1:
			return this.vpCollected1;
		case 2:
			return this.vpCollected2;
		default:
			return -1; // error
		}
	}

	public void setSpeed(int newVPCollected, int numPlayer) {
		switch (numPlayer) {
		case 1:
			this.vpCollected1 = newVPCollected;
			break;
		case 2:
			this.vpCollected2 = newVPCollected;
			break;
		default:
			// error
		}
	}

	public int getPoisonCollected(int numPlayer) {
		switch (numPlayer) {
		case 1:
			return this.poisonCollected1;
		case 2:
			return this.poisonCollected2;
		default:
			return -1; // error
		}
	}

	public void setPoisonCollected(int newPoisonCollected, int numPlayer) {
		switch (numPlayer) {
		case 1:
			this.poisonCollected1 = newPoisonCollected;
			break;
		case 2:
			this.poisonCollected2 = newPoisonCollected;
			break;
		default:
			// error
		}
	}
	
	public int getStudentsCured(int numPlayer) {
		switch (numPlayer) {
		case 1:
			return this.studentsCured1;
		case 2:
			return this.studentsCured2;
		default:
			return -1; // error
		}
	}

	public void setStudentsCured(int newStudentsCured, int numPlayer) {
		switch (numPlayer) {
		case 1:
			this.studentsCured1 = newStudentsCured;
			break;
		case 2:
			this.studentsCured1 = newStudentsCured;
			break;
		default:
			// error
		}
	}

	@Override
	public void handleEvent(GameEvent event) {
		if (event.getEventType().equals(EventTypes.PICKUP_VP.toString())) {
			//should be dispatched by player
			Player player = (Player) event.getSource();
			int id = player.getNumPlayer();
			this.setPoisonCollected(this.getVPCollected(id) + 1, id);
		} else if (event.getEventType().equals(EventTypes.PICKUP_POISON.toString())) {
			//should be dispatched by player
			Player player = (Player) event.getSource();
			int id = player.getNumPlayer();
			this.setPoisonCollected(this.getVPCollected(id) + 1, id);
		} else if (event.getEventType().equals(EventTypes.CURE_STUDENT.toString())) {
			Player player = (Player) event.getSource();
			int id = player.getNumPlayer();
			this.setStudentsCured(this.getStudentsCured(id) + 1, id);
		}

	}
}
