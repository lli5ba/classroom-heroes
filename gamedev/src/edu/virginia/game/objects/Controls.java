package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.PlayerManager;

public class Controls extends DisplayObjectContainer {

	private PlayerManager playerManager = PlayerManager.getInstance();
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
	private Player Downplayer;
	private AnimatedSprite Leftplayer;
	private AnimatedSprite Rightplayer;
	private GameManager gameManager = GameManager.getInstance();
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();
	private NavButtonIcon contButton;
	private AnimatedSprite notebook;
	private Sprite notebook2;
	private boolean turnPage;


	public Controls(String id) {
		super(id, "instructions/wood-grain-background.png"); // background
		
		this.notebook2 = new Sprite("notebook", "notebook/default.png");
		this.addChild(notebook2);
		
		this.notebook = new AnimatedSprite("notebook", "notebook/default.png",
				"notebook/notebook-spritesheet.png", "resources/notebook/notebook-spritesheet.txt");
		this.notebook.setHeight(gameManager.getGameHeight());
		this.notebook.setWidth(gameManager.getGameWidth());
		
		this.turnPage = false;
		
		// get last character in styleCode
		this.numPlayer = Integer.parseInt(id.substring(id.length() - 1)); //id is either 1 or 2
		//this.numPlayer = 1;
		
		if(this.numPlayer == 1) {
			this.Upplayer = new AnimatedSprite("Upplayer", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt");
		} else {
			this.Upplayer = new AnimatedSprite("Upplayer", "player/player2.png", "player/player-spritesheet-2.png",
					"resources/player/player-spritesheet-1-frameInfo.txt");
		}
		this.Upplayer.setScaleX(.8);
		this.Upplayer.setScaleY(.8);
		this.Upplayer.setPosition(400, 140);
		this.Upplayer.animate("up", .3);
		this.addChild(Upplayer);
		
		up = new KeyboardKey("up", numPlayer);
		this.up.setScaleX(1.0);
		this.up.setScaleY(1.0);
		this.addChild(up);
		this.up.setPosition(400, 175);

		if (this.numPlayer == 1) {
			this.Downplayer = new Player("Downplayer", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt", 1);
		} else {
			this.Downplayer = new Player("Downplayer", "player/player2.png", "player/player-spritesheet-2.png",
					"resources/player/player-spritesheet-1-frameInfo.txt", 2);
		}
		this.Downplayer.setScaleX(.8);
		this.Downplayer.setScaleY(.8);
		this.Downplayer.setPosition(400, 235);
		this.Downplayer.animate("down", .3);
		this.addChild(Downplayer);

		down = new KeyboardKey("down", numPlayer);
		this.down.setScaleX(1.0);
		this.down.setScaleY(1.0);
		this.addChild(down);
		this.down.setPosition(400, 205);
	
		if (this.numPlayer == 1) {
			this.Leftplayer = new AnimatedSprite("Leftplayer", "player/player1.png", "player/player-spritesheet-1.png",
					"resources/player/player-spritesheet-1-frameInfo.txt");
		} else {
			this.Leftplayer = new AnimatedSprite("Leftplayer", "player/player2.png", "player/player-spritesheet-2.png",
					"resources/player/player-spritesheet-1-frameInfo.txt");
		}
		
		this.Leftplayer.setScaleX(.8);
		this.Leftplayer.setScaleY(.8);
		this.Leftplayer.setPosition(335, 205);
		this.Leftplayer.animate("left", .3);
		this.addChild(Leftplayer);

		left = new KeyboardKey("left", numPlayer);
		this.left.setScaleX(1.0);
		this.left.setScaleY(1.0);
		this.addChild(left);
		this.left.setPosition(370, 205);
		
		if (this.numPlayer == 1) {
			this.Rightplayer = new AnimatedSprite("Rightplayer", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt");
		} else {
			this.Rightplayer = new AnimatedSprite("Rightplayer", "player/player2.png", "player/player-spritesheet-2.png",
					"resources/player/player-spritesheet-1-frameInfo.txt");
		}
		this.Rightplayer.setScaleX(.8);
		this.Rightplayer.setScaleY(.8);
		this.Rightplayer.setPosition(465, 205);
		this.Rightplayer.animate("right", .3);
		this.addChild(Rightplayer);
		
		right = new KeyboardKey("right", numPlayer);
		this.right.setScaleX(1.0);
		this.right.setScaleY(1.0);
		this.addChild(right);
		this.right.setPosition(430, 205);
	
		secondaryCure = new KeyboardKey("secondary", numPlayer);
		this.secondaryCure.setScaleX(1.0);
		this.secondaryCure.setScaleY(1.0);
		this.addChild(secondaryCure);
		this.secondaryCure.setPosition(200, 153);
		
		primaryCure = new KeyboardKey("primary", numPlayer);
		this.primaryCure.setScaleX(1.0);
		this.primaryCure.setScaleY(1.0);
		this.addChild(primaryCure);
		this.primaryCure.setPosition(250, 153);
		
		secondaryBomb = new KeyboardKey("secondary", numPlayer);
		this.secondaryBomb.setScaleX(1.0);
		this.secondaryBomb.setScaleY(1.0);
		this.addChild(secondaryBomb);
		this.secondaryBomb.setPosition(200, 228);
		
		primaryBomb = new KeyboardKey("primary", numPlayer);
		this.primaryBomb.setScaleX(1.0);
		this.primaryBomb.setScaleY(1.0);
		this.addChild(primaryBomb);
		this.primaryBomb.setPosition(250, 228);
		
		/* continue button */
		this.contButton = new NavButtonIcon(NavButtonIcon.CONTINUE, 
				true, this.playerManager.getSecondaryKey(numPlayer));
		this.addChild(contButton);
		this.contButton.setPosition(this.getWidth() * .78, this.getHeight() * .88);
		
		this.setDrawChildren(true);
		this.setHeight(gameManager.getGameHeight());
		this.setWidth(gameManager.getGameWidth());
	}

	public void navigate(ArrayList<String> pressedKeys) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		if (releasedKeys.contains(this.playerManager.getSecondaryKey(this.numPlayer))
				|| releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_ENTER)) ) {
			if (this.numPlayer == 1 && this.gameManager.getNumPlayers() == 2) {
				//this.gameManager.setActiveGameScene("controls2");
				this.turnPage = true;
				this.notebook.animateOnce("turn", 4);
				this.setDrawChildren(false);
			}
			else if (this.numPlayer == 2 || 
					(this.numPlayer == 1 && this.gameManager.getNumPlayers() == 1) ) {
				this.gameManager.setActiveGameScene("classroom1");  //move on
			}
		}
		this.prevPressedKeys.clear();
		this.prevPressedKeys.addAll(pressedKeys);
	}

	public void draw(Graphics g) {
		super.draw(g);
		if (this.notebook != null && this.turnPage) {
			this.notebook.draw(g);
			Font f = new Font("Dialog", Font.BOLD, 100);
			g.setFont(f);
			g.drawString("Controls", 30, 85);
		}
		if (!this.notebook.isPlaying() && !this.turnPage) {
			Font f = new Font("Dialog", Font.BOLD, 100);
			g.setFont(f);
			g.drawString("Controls", 30, 85);
			f = new Font("Dialog", Font.PLAIN, 50);
			g.setFont(f);
			g.drawString("Player " + this.numPlayer, 445, 195);
			
			Font h = new Font("Dialog", Font.BOLD, 18);
			g.setFont(h);
			drawString(g, "Cure Student\n(when nearby)", 440, 420);
			g.drawString("Swing net", 590, 420);
			g.drawString("+", 560, 540);
			g.drawString("Aim", 490, 590);
			g.drawString("Throw Smoke Bomb", 580, 590);
		}
	}
	
	private void drawString(Graphics g, String text, int x, int y) {
		double inc = 0;
        for (String line : text.split("\n")) {
            g.drawString(line, x, y += inc);
            inc += g.getFontMetrics().getHeight();
        }
    }

	public void checkSwitch(ArrayList<String> pressedKeys) {
		if (this.turnPage && !this.notebook.isPlaying()){
			this.gameManager.setActiveGameScene("controls2");
		} else {
			this.notebook.update(pressedKeys);
		}
	}

	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if (this.isVisible()) {
			if (!this.turnPage)
				navigate(pressedKeys);
			this.checkSwitch(pressedKeys);
		}
	}
}
