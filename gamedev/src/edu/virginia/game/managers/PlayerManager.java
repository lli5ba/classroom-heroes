package edu.virginia.game.managers;

import java.awt.event.KeyEvent;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.PickedUpItem;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.game.objects.EventTypes;
import edu.virginia.game.objects.Player;

/*
 * Singleton class that handles all the player details for players 1 and 2 and updates them
 * appropriately once per frame.
*/
public class PlayerManager implements IEventListener {
	private static volatile PlayerManager instance;
	/* Player One Stats */
	private double speed1;
	private int health1;
	private int maxHealth1;
	private int experience1; // total for the entire game (can calculate level
								// based on experience)
	private int swingSpeed1;
	private String primaryKey1;
	private String secondaryKey1;
	private String upKey1;
	private String downKey1;
	private String rightKey1;
	private String leftKey1;

	/* Player Two Stats */
	private double speed2;
	private int health2;
	private int maxHealth2;
	private int experience2; // total for the entire game (can calculate level
								// based on experience)
	private int swingSpeed2;
	private String primaryKey2;
	private String secondaryKey2;
	private String upKey2;
	private String downKey2;
	private String rightKey2;
	private String leftKey2;

	/* Combined Inventory */
	private int vpCount;
	private int numGingerAle;
	private int numCheesePuffs;

	public static PlayerManager getInstance() {
		if (instance == null) {
			instance = new PlayerManager();
		}
		return instance;
	}

	public PlayerManager() {
		instance = this;

		/* P1 Default Stats */
		speed1 = 1;
		maxHealth1 = 5;
		health1 = maxHealth1;
		experience1 = 0;
		swingSpeed1 = 1;
		/* P1 Default Key Mappings */
		primaryKey1 = KeyEvent.getKeyText(KeyEvent.VK_SPACE);
		secondaryKey1 = KeyEvent.getKeyText(KeyEvent.VK_B);
		upKey1 = KeyEvent.getKeyText(KeyEvent.VK_UP);
		downKey1 = KeyEvent.getKeyText(KeyEvent.VK_DOWN);
		rightKey1 = KeyEvent.getKeyText(KeyEvent.VK_RIGHT);
		leftKey1 = KeyEvent.getKeyText(KeyEvent.VK_LEFT);

		/* P2 Default Stats */
		speed2 = 1;
		maxHealth2 = 5;
		health2 = maxHealth2;
		experience2 = 0;
		swingSpeed2 = 1;
		/* P2 Default Key Mappings */
		primaryKey2 = KeyEvent.getKeyText(KeyEvent.VK_1);
		secondaryKey2 = KeyEvent.getKeyText(KeyEvent.VK_2);
		upKey2 = KeyEvent.getKeyText(KeyEvent.VK_W);
		downKey2 = KeyEvent.getKeyText(KeyEvent.VK_S);
		rightKey2 = KeyEvent.getKeyText(KeyEvent.VK_D);
		leftKey2 = KeyEvent.getKeyText(KeyEvent.VK_A);
	}

	/*-------------Player Stats Getters and Setters---------------------*/
	public double getSpeed(int numPlayer) {
		switch (numPlayer) {
		case 1:
			return speed1;
		case 2:
			return speed2;
		default:
			return -1; // error
		}
	}

	public void setSpeed(double newSpeed, int numPlayer) {
		switch (numPlayer) {
		case 1:
			this.speed1 = newSpeed;
		case 2:
			this.speed2 = newSpeed;
		default:
			// error
		}
	}

	public int getHealth(int numPlayer) {
		switch (numPlayer) {
		case 1:
			return health1;
		case 2:
			return health2;
		default:
			return -1; // error
		}
	}

	public void setHealth(int newHealth, int numPlayer) {
		switch (numPlayer) {
		case 1:
			this.health1 = newHealth;
		case 2:
			this.health2 = newHealth;
		default:
			// error
		}
	}

	public int getMaxHealth(int numPlayer) {
		switch (numPlayer) {
		case 1:
			return maxHealth1;
		case 2:
			return maxHealth2;
		default:
			return -1; // error
		}
	}

	public void setMaxHealth(int newMaxHealth, int numPlayer) {
		switch (numPlayer) {
		case 1:
			this.maxHealth1 = newMaxHealth;
		case 2:
			this.maxHealth2 = newMaxHealth;
		default:
			// error
		}
	}

	public int getExperience(int numPlayer) {
		switch (numPlayer) {
		case 1:
			return experience1;
		case 2:
			return experience2;
		default:
			return -1; // error
		}
	}

	public void setExperience(int newExperience, int numPlayer) {
		switch (numPlayer) {
		case 1:
			this.experience1 = newExperience;
		case 2:
			this.experience2 = newExperience;
		default:
			// error
		}
	}

	public int getSwingSpeed(int numPlayer) {
		switch (numPlayer) {
		case 1:
			return swingSpeed1;
		case 2:
			return swingSpeed2;
		default:
			return -1; // error
		}
	}

	public void setSwingSpeed(int newSwingSpeed, int numPlayer) {
		switch (numPlayer) {
		case 1:
			this.swingSpeed1 = newSwingSpeed;
		case 2:
			this.swingSpeed2 = newSwingSpeed;
		default:
			// error
		}
	}

	/*--------------Key Mapping Getters and Setters---------------------*/

	public String getPrimaryKey(int numPlayer) {
		switch (numPlayer) {
		case 1:
			return primaryKey1;
		case 2:
			return primaryKey2;
		default:
			return null; // error
		}
	}

	public void setPrimaryKey(String newPrimaryKey, int numPlayer) {
		switch (numPlayer) {
		case 1:
			this.primaryKey1 = newPrimaryKey;
		case 2:
			this.primaryKey2 = newPrimaryKey;
		default:
			// error
		}
	}

	public String getSecondaryKey(int numPlayer) {
		switch (numPlayer) {
		case 1:
			return secondaryKey1;
		case 2:
			return secondaryKey2;
		default:
			return null; // error
		}
	}

	public void setSecondaryKey(String newSecondaryKey, int numPlayer) {
		switch (numPlayer) {
		case 1:
			this.secondaryKey1 = newSecondaryKey;
		case 2:
			this.secondaryKey2 = newSecondaryKey;
		default:
			// error
		}
	}

	public String getUpKey(int numPlayer) {
		switch (numPlayer) {
		case 1:
			return upKey1;
		case 2:
			return upKey2;
		default:
			return null; // error
		}
	}

	public void setUpKey(String newUpKey, int numPlayer) {
		switch (numPlayer) {
		case 1:
			this.upKey1 = newUpKey;
		case 2:
			this.upKey2 = newUpKey;
		default:
			// error
		}
	}

	public String getDownKey(int numPlayer) {
		switch (numPlayer) {
		case 1:
			return downKey1;
		case 2:
			return downKey2;
		default:
			return null; // error
		}
	}

	public void setDownKey(String newDownKey, int numPlayer) {
		switch (numPlayer) {
		case 1:
			this.downKey1 = newDownKey;
		case 2:
			this.downKey2 = newDownKey;
		default:
			// error
		}
	}

	public String getRightKey(int numPlayer) {
		switch (numPlayer) {
		case 1:
			return rightKey1;
		case 2:
			return rightKey2;
		default:
			return null; // error
		}
	}

	public void setRightKey(String newRightKey, int numPlayer) {
		switch (numPlayer) {
		case 1:
			this.rightKey1 = newRightKey;
		case 2:
			this.rightKey2 = newRightKey;
		default:
			// error
		}
	}

	public String getLeftKey(int numPlayer) {
		switch (numPlayer) {
		case 1:
			return leftKey1;
		case 2:
			return leftKey2;
		default:
			return null; // error
		}
	}

	public void setLeftKey(String newLeftKey, int numPlayer) {
		switch (numPlayer) {
		case 1:
			this.leftKey1 = newLeftKey;
		case 2:
			this.leftKey2 = newLeftKey;
		default:
			// error
		}
	}

	/*-------------Combined Inventory Getters and Setters---------------------*/
	public int getVpCount() {
		return vpCount;
	}

	public void setVpCount(int vpCount) {
		this.vpCount = vpCount;
	}

	public int getNumGingerAle() {
		return numGingerAle;
	}

	public void setNumGingerAle(int numGingerAle) {
		this.numGingerAle = numGingerAle;
	}

	public int getNumCheesePuffs() {
		return numCheesePuffs;
	}

	public void setNumCheesePuffs(int numCheesePuffs) {
		this.numCheesePuffs = numCheesePuffs;
	}

	@Override
	public void handleEvent(Event event) {
		if (event.getEventType().equals(EventTypes.POISON_PLAYER.toString())) {
			// should be dispatched by the player
			Player player = (Player) event.getSource();
			// player.getPoisonBubbles.animateOnce("poison"); //play animation
			int currentHealth = this.getHealth(player.getNumPlayer());
			this.setHealth(currentHealth - 1, player.getNumPlayer()); // decrease
																		// health
		} else if (event.getEventType().equals(EventTypes.PICKUP_VP.toString())) {
			this.setVpCount(this.vpCount + 1);
		}

	}

}
