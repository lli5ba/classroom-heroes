package edu.virginia.game.objects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
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

	public Hallway(String id, String styleCode) {
		super(id, "hallway/hallway-background-" + styleCode + ".png");

		player1 = new Player("Player1", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt", 1);
		player2 = new Player("Player2", "player/player1.png", "player/player-spritesheet-1.png",
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
		store = new Store(id + "-store", styleCode, 1);
		this.store.setVisible(false);

		vendingMachine = new Sprite(id + "-vending-machine", "hallway/vending-machine-" + vendingCode + ".png");
		drinkMachine = new Sprite(id + "-drink-machine", "hallway/drink-machine-" + drinkCode + ".png");
		// add as children in order to draw
		this.addChild(vendingMachine);
		this.addChild(drinkMachine);
		this.addChild(player1);
		this.addChild(player2);
		this.addChild(store);

		this.vendingMachine.setPosition(this.getWidth() * .4181, this.getHeight() * .2267);
		this.drinkMachine.setPosition(this.getWidth() * .4181 + this.vendingMachine.getWidth() * 1.1,
				this.getHeight() * .2267);

		this.player1.setPosition(this.getWidth() * .04, this.getHeight() * .48);
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
		} else { // store is visible, check for close (secondary key)
			switch (this.gameManager.getNumPlayers()) {
			case 1: // only check one player
				if (releasedKeys.contains(this.playerManager.getSecondaryKey(1))) {
					this.closeStore(1);
				}
				break;
			case 2: // check two players
				// FIXME: randomize which player gets first check?
				if (releasedKeys.contains(this.playerManager.getSecondaryKey(1))) {
					this.closeStore(1);
				} else if (releasedKeys.contains(this.playerManager.getSecondaryKey(2))) {
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

	public void movePlayer(ArrayList<String> pressedKeys, Player player){
		if (player != null && player.getNet() != null) {
			if (player.isActive()) {
				player.moveSpriteCartesianAnimate(pressedKeys);
			}
			// if there are no keys being pressed, and sprite is walking, then
			// stop the animation
			if (pressedKeys.isEmpty() && player.isPlaying()
					&& Arrays.asList(player.CARDINAL_DIRS).contains(player.getCurrentAnimation())) {
				player.stopAnimation();
			}

		}
	}
	
	public void switchScenes() {
		if(this.player1 != null && 
				this.player1.getxPosGlobal() > this.gameManager.getGameWidth()) {
			this.gameManager.setActiveGameScene("classroom2");
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
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys); // updates children
		this.navigateStore(pressedKeys);
		this.movePlayer(pressedKeys, this.player1);
		if (this.gameManager.getNumPlayers() == 2) {
			this.movePlayer(pressedKeys, this.player2);
		}
		this.switchScenes();
	}
}
