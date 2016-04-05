package edu.virginia.game.managers;

import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;

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

	// Player Two
	int vpCollected2;
	int poisonCollected2;

	/****************** Constructors ********************/

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
		case 2:
			this.vpCollected2 = newVPCollected;
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
		case 2:
			this.poisonCollected2 = newPoisonCollected;
		default:
			// error
		}
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub

	}
}
