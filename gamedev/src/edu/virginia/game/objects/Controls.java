package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.game.managers.GameManager;

public class Controls extends DisplayObjectContainer {

	KeyboardKey up;
	KeyboardKey down;
	KeyboardKey left;
	KeyboardKey right;
	KeyboardKey secondaryCure;
	KeyboardKey secondaryBomb;
	KeyboardKey primaryCure;
	KeyboardKey primaryBomb;
	private int numPlayer;
	private AnimatedSprite Upplayer;
	private AnimatedSprite Downplayer;
	private AnimatedSprite Leftplayer;
	private AnimatedSprite Rightplayer;
	private GameManager gameManager = GameManager.getInstance();
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();

	public Controls(String id) {
		super(id, "instructions/instructions-background.png"); // background
		// get last character in styleCode
		//numPlayer = Integer.parseInt(id.substring(id.length() - 1)); //id is either 1 or 2
		
		this.Upplayer = new AnimatedSprite("Upplayer", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt");
		this.Upplayer.setScaleX(.8);
		this.Upplayer.setScaleY(.8);
		this.Upplayer.setPosition(400, 140);
		this.addChild(Upplayer);
		
		up = new KeyboardKey("up", numPlayer);
		this.up.setScaleX(1.0);
		this.up.setScaleY(1.0);
		this.addChild(up);
		this.up.setPosition(400, 175);

		this.Downplayer = new AnimatedSprite("Downplayer", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt");
		this.Downplayer.setScaleX(.8);
		this.Downplayer.setScaleY(.8);
		this.Downplayer.setPosition(400, 235);
		this.addChild(Downplayer);

		down = new KeyboardKey("down", numPlayer);
		this.down.setScaleX(1.0);
		this.down.setScaleY(1.0);
		this.addChild(down);
		this.down.setPosition(400, 205);
	
		this.Leftplayer = new AnimatedSprite("Leftplayer", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt");
		this.Leftplayer.setScaleX(.8);
		this.Leftplayer.setScaleY(.8);
		this.Leftplayer.setPosition(335, 205);
		this.addChild(Leftplayer);

		left = new KeyboardKey("left", numPlayer);
		this.left.setScaleX(1.0);
		this.left.setScaleY(1.0);
		this.addChild(left);
		this.left.setPosition(370, 205);
		
		this.Rightplayer = new AnimatedSprite("Rightplayer", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt");
		this.Rightplayer.setScaleX(.8);
		this.Rightplayer.setScaleY(.8);
		this.Rightplayer.setPosition(465, 205);
		this.addChild(Rightplayer);
		
		right = new KeyboardKey("right", numPlayer);
		this.right.setScaleX(1.0);
		this.right.setScaleY(1.0);
		this.addChild(right);
		this.right.setPosition(430, 205);
	
		secondaryCure = new KeyboardKey("secondaryCure", numPlayer);
		this.secondaryCure.setScaleX(1.0);
		this.secondaryCure.setScaleY(1.0);
		this.addChild(secondaryCure);
		this.secondaryCure.setPosition(200, 153);
		
		primaryCure = new KeyboardKey("primaryCure", numPlayer);
		this.primaryCure.setScaleX(1.0);
		this.primaryCure.setScaleY(1.0);
		this.addChild(primaryCure);
		this.primaryCure.setPosition(250, 153);
		
		secondaryBomb = new KeyboardKey("secondaryBomb", numPlayer);
		this.secondaryBomb.setScaleX(1.0);
		this.secondaryBomb.setScaleY(1.0);
		this.addChild(secondaryBomb);
		this.secondaryBomb.setPosition(200, 228);
		
		primaryBomb = new KeyboardKey("primaryBomb", numPlayer);
		this.primaryBomb.setScaleX(1.0);
		this.primaryBomb.setScaleY(1.0);
		this.addChild(primaryBomb);
		this.primaryBomb.setPosition(250, 228);
		
		this.setHeight(gameManager.getGameHeight());
		this.setWidth(gameManager.getGameWidth());
	}

	public void navigate(ArrayList<String> pressedKeys) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		if (releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_SPACE))
				|| releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_ENTER))) {
			this.gameManager.setActiveGameScene("classroom1");
		}
		this.prevPressedKeys.clear();
		this.prevPressedKeys.addAll(pressedKeys);
	}

	public void draw(Graphics g) {
		super.draw(g);
	}

	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if (this.isVisible()) {
			navigate(pressedKeys);
		}
	}
}
