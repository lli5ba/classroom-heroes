package edu.virginia.game.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.GameEvent;
import edu.virginia.engine.util.Position;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;

//This class represents a game screen object to be used for levels and hallway scenes.
//This way you can instantiate a GameScreen and add game elements as children.
public class Hallway extends DisplayObjectContainer {

	private Store store;
	private Sprite vendingMachine;
	private Sprite drinkMachine;
	private static PlayerManager playerManager = PlayerManager.getInstance();
	private static LevelManager levelManager = LevelManager.getInstance();
	private static GameManager gameManager = GameManager.getInstance();
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();
	private Player player1;
	private Player player2;
	private DisplayObjectContainer playArea;
	private AnimatedSprite door;
	private boolean doorOpen;

	public Hallway(String id, String styleCode) {
		super(id, "hallway/hallway-background-" + styleCode + ".png");

		player1 = new Player("Player1", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt", 1);
		player2 = new Player("Player2", "player/player2.png", "player/player-spritesheet-2.png",
				"resources/player/player-spritesheet-1-frameInfo.txt", 2);
		if (this.gameManager.getNumPlayers() == 1) {
			// set player2 inactive and invisible
			player2.setActive(false);
			player2.setVisible(false);
		}
		/*
		 * use "styleCode" to produce different combos of drink and snack
		 * machines
		 */
		String drinkCode = "0";
		String vendingCode = "0";
		switch (styleCode) {
		case "0":
			drinkCode = "0";
			vendingCode = "0";
			break;
		case "1":
			drinkCode = "1";
			vendingCode = "1";
			break;
		case "2":
			drinkCode = "0";
			vendingCode = "1";
			break;
		case "3":
			drinkCode = "1";
			vendingCode = "0";
			break;
		default:
			break;
		}
		
		vendingMachine = new Sprite(id + "-vending-machine", "hallway/vending-machine-" + vendingCode + ".png");
		drinkMachine = new Sprite(id + "-drink-machine", "hallway/drink-machine-" + drinkCode + ".png");
		
		/* add door */
		door = new AnimatedSprite(id + "-door", "classroom/door/door-default.png",
				"classroom/door/door-spritesheet.png", 
				"resources/classroom/door/door-spritesheet.txt");
		doorOpen = false;
		/* add door background */
		Sprite doorBack = new Sprite(id + "-doorbkg", "hallway/doorbkg" + styleCode +".png");
		
		/* set play area bounds */
		this.playArea = new DisplayObjectContainer("playArea", "Mario.png"); // random
																				// png
																				// file
		this.playArea.setVisible(false);
		this.playArea.setWidth(this.getWidth());
		this.playArea.setHeight(this.getHeight() * .58);
		this.addChild(playArea);
		this.playArea.setPosition(0, this.getHeight() * .3);
		
		/* set store */
		store = new Store(id + "-store", styleCode, 1);
		this.store.setVisible(false);

		
		// add as children in order to draw
		this.addChild(vendingMachine);
		this.addChild(drinkMachine);
		this.addChild(doorBack);
		this.addChild(door);
		
		this.addChild(player1);
		this.addChild(player2);
		this.addChild(store);

		this.vendingMachine.setPosition(this.getWidth() * .4181, this.getHeight() * .2267);
		this.drinkMachine.setPosition(this.getWidth() * .4181 + this.vendingMachine.getWidth() * 1.1,
				this.getHeight() * .2267);
		this.door.setPosition(this.getWidth() * .8, this.getHeight() * .21);
		doorBack.setPosition(this.getWidth() * .8, this.getHeight() * .21);
		
		this.player1.setPosition(this.getWidth() * .04, this.getHeight() * .48);
		this.player2.setPosition(this.getWidth() * .04, this.getHeight() * .6);
		this.setHeight(gameManager.getGameHeight());
		this.setWidth(gameManager.getGameWidth());
	}

	public void openStore(int numPlayer) {
		store.setVisible(true);
		store.setNumPlayer(numPlayer);
		switch (numPlayer) {
		case 1:
			this.player1.setActive(false);
			break;
		case 2:
			this.player2.setActive(false);
			break;
		default:
		}
	}

	public void closeStore(int numPlayer) {
		store.setVisible(false);
		switch (numPlayer) {
		case 1:
			this.player1.setActive(true);
			break;
		case 2:
			this.player2.setActive(true);
			break;
		default:
		}
	}

	public void navigateStore(ArrayList<String> pressedKeys) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);

		if (!store.isVisible()) {
			int range = 10;
			switch (this.gameManager.getNumPlayers()) {
			case 1: // only check one player
				if (releasedKeys.contains(this.playerManager.getPrimaryKey(1))
						&& this.player1.inRangeGlobal(this.vendingMachine, range)) {
					// player near vending machine
					this.openStore(1);
				}
				break;
			case 2: // check two players
				// FIXME: randomize which player gets first check?
				if (this.player1.isActive() && this.player2.isActive()) {
					if (releasedKeys.contains(this.playerManager.getPrimaryKey(1))
							&& this.player1.inRangeGlobal(this.vendingMachine, range)) {
						// player near vending machine
						this.openStore(1);
					} else if (releasedKeys.contains(this.playerManager.getPrimaryKey(2))
							&& this.player2.inRangeGlobal(this.vendingMachine, range)) {
						// player near vending machine
						this.openStore(2);
					}
				}
			}
		} else { // store is visible, check for close (secondary key)
			switch (this.gameManager.getNumPlayers()) {
			case 1: // only check one player
				if (releasedKeys.contains(this.playerManager.getSecondaryKey(1))) {
					this.closeStore(1);
				}
				break;
			case 2: // check two players
				// FIXME: randomize which player gets first check?
				if (!this.player1.isActive() && releasedKeys.contains(this.playerManager.getSecondaryKey(1))) {
					this.closeStore(1);
				} else if (!this.player2.isActive() && releasedKeys.contains(this.playerManager.getSecondaryKey(2))) {
					this.closeStore(2);
				}
			}
		}
		this.prevPressedKeys.clear();
		this.prevPressedKeys.addAll(pressedKeys);
	}

	public void enteringLevel() {
		// FIXME
	}

	public void collisionPrediction(ArrayList<String> pressedKeys) {
		// FIXME: check between players, sprites, and walls of background
	}

	public void movePlayer(ArrayList<String> pressedKeys, Player player) {
		if (player != null && player.getNetHitbox() != null) {
			if (player.isActive()) {
				this.moveSpriteCartesianAnimate(pressedKeys, player);
			}
			// if there are no keys being pressed, and sprite is walking, then
			// stop the animation
			if (pressedKeys.isEmpty() && player.isPlaying()
					&& Arrays.asList(player.CARDINAL_DIRS).contains(player.getCurrentAnimation())) {
				player.stopAnimation();
			}

		}
	}
	
	public void checkDoorCollision() {
		if(this.door.inRangeGlobal(this.player1, 75)
				|| this.door.inRangeGlobal(this.player2, 75)) {
			if (!doorOpen) {
				this.door.animateOnceLock("dooropen", 5);
				doorOpen = true;
			}
		}  else {
			if (doorOpen) {
				this.door.animateOnceLock("doorclose", 5);
				doorOpen = false;
			}
		}
	}

	public void switchScenes() {
		if (this.player1 != null && this.player1.collidesWithGlobal(this.door)) {
			this.gameManager.setActiveGameScene("classroom" + gameManager.getNumLevel());
			if(gameManager.getNumLevel() >= 5)
			{
				this.gameManager.setActiveGameScene("weimer");
			}
		}
		else if (this.player2 != null && this.player2.collidesWithGlobal(this.door)) {
			this.gameManager.setActiveGameScene("classroom" + gameManager.getNumLevel());
			if(gameManager.getNumLevel() >= 5)
			{
				this.gameManager.setActiveGameScene("weimer");
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g); // draws children
		/*
		 * if (this.player1 != null) { this.player1.drawHitboxGlobal(g); }
		 */
		/*
		 * if (this.vendingMachine != null) {
		 * this.vendingMachine.drawHitboxGlobal(g); }
		 */

//		if (this.playArea != null ) {
//			this.playArea.drawHitboxGlobal(g);
//		}
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys); // updates children
		this.navigateStore(pressedKeys);
		if (this.player1 != null) {
			this.movePlayer(pressedKeys, this.player1);
			if (this.gameManager.getNumPlayers() == 2) {
				this.movePlayer(pressedKeys, this.player2);
			}
			this.switchScenes();
			this.checkDoorCollision();
		}
	}
	
	public void moveSpriteCartesianAnimate(ArrayList<String> pressedKeys, Player player) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		double speed = this.playerManager.getSpeed(player.getNumPlayer());
		/*
		 * Make sure this is not null. Sometimes Swing can auto cause an extra
		 * frame to go before everything is initialized
		 */
		Position originalPos = new Position(player.getxPos(), player.getyPos());

		if (player != null && player.getNetHitbox() != null 
				&& !pressedKeys.contains(this.playerManager.getSecondaryKey(player.getNumPlayer()))) {
			/*
			 * update player's position depending on key pressed
			 */

			if (pressedKeys.contains(this.playerManager.getUpKey(player.getNumPlayer()))) {

				player.setyPos(player.getyPos() - speed);
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));

				if (!player.isPlaying() || player.getCurrentAnimation() != "up") {
					player.animate("up");
				}
				player.setDirection("up");
				

			}
			if (pressedKeys.contains(this.playerManager.getDownKey(player.getNumPlayer()))) {

				player.setyPos(player.getyPos() + speed);
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));
				if (!player.isPlaying() || player.getCurrentAnimation() != "down") {
					player.animate("down");
				}
				player.setDirection("down");
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), player));
				
			}
			if (pressedKeys.contains(this.playerManager.getLeftKey(player.getNumPlayer()))) {
				player.setxPos(player.getxPos() - speed);
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));

				if (!player.isPlaying() || player.getCurrentAnimation() != "left") {
					player.animate("left");
				}
				player.setDirection("left");
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), player));
				
			}
			if (pressedKeys.contains(this.playerManager.getRightKey(player.getNumPlayer()))) {

				player.setxPos(player.getxPos() + speed);
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), this));

				if (!player.isPlaying() || player.getCurrentAnimation() != "right") {
					player.animate("right");
				}
				player.setDirection("right");
				player.dispatchEvent(new GameEvent(EventTypes.WALK.toString(), player));
				
			}
			if (releasedKeys.contains(this.playerManager.getPrimaryKey(player.getNumPlayer()))) {
				String currentDir = player.getDirection();
				// Until we have combined net and walking animation, net
				// animation overrides walking animation
				if (player.isPlaying() && !player.getCurrentAnimation().contains("net")) {
					// System.out.println("STOPPING\n");
					player.stopAnimation();
				}

				player.animateOnceLock("net" + currentDir, this.playerManager.getSwingSpeed(player.getNumPlayer()));
				player.dispatchEvent(new GameEvent(EventTypes.SWING_NET.toString(), player));
			}

			// this revises the net animation mid-swing
			// if direction changes and animateOnce net sequence is playing
			if (player.isPlaying() && player.getCurrentAnimation().contains("net")
					&& !player.getCurrentAnimation().contains(player.getDirection())) {
				player.setCurrentAnimation("net" + player.getDirection());
			}
			// System.out.println("position: " + player.getHitboxGlobal().getX()
			// + ", " + player.getHitboxGlobal().getY());

			// FIXME: check for collisions
			if (playerCollision(player.getHitboxGlobal(), player.getNumPlayer())) {
				player.setPosition(originalPos);// move the player back
			} else {
				// don't move the player
			}
		}
	}

	// Rectangle r is the players global hitbox
	private boolean playerCollision(Rectangle r, int numPlayer) {
		/* Check collisions with vending machines*/
		Rectangle other = this.vendingMachine.getHitboxGlobal();
		other.grow(0, (int) (-45));
		if (other.intersects(r)) {
			return true;
		}
		other = this.drinkMachine.getHitboxGlobal();
		other.grow(0, (int) (-45));
		if (other.intersects(r)) {
			return true;
		}
		/* Check collisions with the other player */
		switch (numPlayer) {
		case 1:
			if (r.intersects(this.player2.getHitboxGlobal())) {
				return true;
			}
			break;
		case 2:
			if (r.intersects(this.player1.getHitboxGlobal())) {
				return true;
			}
			break;
		default:
			break;
		}
		/* Check whether player is not inside of the play area */
		if (!(this.playArea.getHitboxGlobal().contains(r))) {
			return true;
		}
		return false;
	}
}
