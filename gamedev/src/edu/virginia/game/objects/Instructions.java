package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.game.main.ClassroomHeroes;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;

public class Instructions extends AnimatedSprite {

	private LevelManager levelManager = LevelManager.getInstance();
	private PlayerManager playerManager = PlayerManager.getInstance();
	private GameManager gameManager = GameManager.getInstance();
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();
	private AnimatedSprite vp0;
	private AnimatedSprite vp1;
	private AnimatedSprite vp2;
	private AnimatedSprite vpPlayer;
	private AnimatedSprite thrownVP;
	private Sprite vending;
	private Sprite vending1;
	private Sprite drink;
	private Sprite drink1;
	private AnimatedSprite poison;
	private Player poisonPlayer;
	private AnimatedSprite dead;
	private AnimatedSprite revived;
	private AnimatedSprite bombPlayer;
	private AnimatedSprite bomb;
	private AnimatedSprite bombExplode;
	private NavButtonIcon contButton;
	private Sprite notebook;

	public Instructions(String id) {
		super(id, "instructions/wood-grain-background.png"); // background
		
		this.notebook = new Sprite("notebook", "instructions/instructions-background.png");
		this.addChild(notebook);
		
		this.vp0 = new AnimatedSprite("vp0", "projectiles/vp0.png", "projectiles/vpsheet.png",
				"resources/projectiles/vpsheetspecs.txt");
		this.vp0.setScaleX(.5);
		this.vp0.setScaleY(.5);
		this.vp0.setPosition(225, 85);
		this.vp0.animate("red");
		this.addChild(vp0);

		this.vp1 = new AnimatedSprite("vp1", "projectiles/vp1.png", "projectiles/vpsheet.png",
				"resources/projectiles/vpsheetspecs.txt");
		this.vp1.setScaleX(.5);
		this.vp1.setScaleY(.5);
		this.vp1.setPosition(240, 85);
		this.vp1.animate("yellow");
		this.addChild(vp1);

		this.vp2 = new AnimatedSprite("vp2", "projectiles/vp2.png", "projectiles/vpsheet.png",
				"resources/projectiles/vpsheetspecs.txt");
		this.vp2.setScaleX(.5);
		this.vp2.setScaleY(.5);
		this.vp2.setPosition(255, 85);
		this.vp2.animate("blue");
		this.addChild(vp2);

		this.vpPlayer = new AnimatedSprite("vpPlayer", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt");
		this.vpPlayer.setScaleX(.8);
		this.vpPlayer.setScaleY(.8);
		this.vpPlayer.setPosition(290, 75);
		this.vpPlayer.animate("netright");
		this.addChild(vpPlayer);
		
		this.thrownVP = new AnimatedSprite("vp0", "projectiles/vp0.png", "projectiles/vpsheet.png",
				"resources/projectiles/vpsheetspecs.txt");
		this.thrownVP.setScaleX(.5);
		this.thrownVP.setScaleY(.5);
		this.thrownVP.setPosition(335, 85);
		this.thrownVP.animate("red");
		this.addChild(thrownVP);
		
		this.vending = new Sprite("vending", "hallway/vending-machine-0.png");
		this.vending.setScaleX(.6);
		this.vending.setScaleY(.6);
		this.vending.setPosition(225, 118);
		this.addChild(vending);
		
		this.drink = new Sprite("drink", "hallway/drink-machine-0.png");
		this.drink.setScaleX(.6);
		this.drink.setScaleY(.6);
		this.drink.setPosition(250, 118);
		this.addChild(drink);
		
		this.vending1 = new Sprite("vending", "hallway/vending-machine-1.png");
		this.vending1.setScaleX(.6);
		this.vending1.setScaleY(.6);
		this.vending1.setPosition(325, 118);
		this.addChild(vending1);
		
		this.drink1 = new Sprite("drink", "hallway/drink-machine-1.png");
		this.drink1.setScaleX(.6);
		this.drink1.setScaleY(.6);
		this.drink1.setPosition(350, 118);
		this.addChild(drink1);
		
		this.poisonPlayer = new Player("poisonPlayer", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt", 1);
		this.poisonPlayer.setScaleX(.8);
		this.poisonPlayer.setScaleY(.8);
		this.poisonPlayer.setPosition(240, 172);
		this.addChild(poisonPlayer);
		this.poisonPlayer.animateBubblesContinuous();
		

		this.poison = new AnimatedSprite("poison", "projectiles/poison.png", "projectiles/poison-spritesheet.png",
				"resources/projectiles/poison-spritesheet.txt");
		this.poison.setScaleX(.5);
		this.poison.setScaleY(.5);
		this.poison.setPosition(300, 190);
		this.poison.animate("poison");
		this.addChild(poison);
		
		this.dead = new AnimatedSprite("dead", "student/student-default-0.png", "student/student-spritesheet-0.png",
				"resources/student/student-spritesheet-0-frameInfo.txt");
		this.dead.setScaleX(.7);
		this.dead.setScaleY(.7);
		this.dead.setPosition(250, 252);
		this.dead.animate("fallleft");
		this.addChild(dead);
		
		this.revived = new AnimatedSprite("revived", "student/student-default-0.png", "student/student-spritesheet-0.png",
				"resources/student/student-spritesheet-0-frameInfo.txt");
		this.revived.setScaleX(.7);
		this.revived.setScaleY(.7);
		this.revived.setPosition(330, 252);
		this.revived.animate("floatleft");
		this.addChild(revived);

		this.bombPlayer = new AnimatedSprite("vpPlayer", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt");
		this.bombPlayer.setScaleX(.8);
		this.bombPlayer.setScaleY(.8);
		this.bombPlayer.setPosition(240, 274);
		this.addChild(bombPlayer);
		
		this.bomb = new AnimatedSprite("bomb", "smokebomb/smokebomb-default.png", "smokebomb/smokebomb-spritesheet.png",
				"resources/smokebomb/smokebomb-spritesheet.txt");
		this.bomb.setScaleX(.8);
		this.bomb.setScaleY(.8);
		this.bomb.setPosition(280, 294);
		this.bomb.animate("puffbag");
		this.addChild(bomb);
		
		this.bombExplode = new AnimatedSprite("bomb", "smokebomb/smokebomb-default.png", "smokebomb/smokebomb-spritesheet.png",
				"resources/smokebomb/smokebomb-spritesheet.txt");
		this.bombExplode.setScaleX(.8);
		this.bombExplode.setScaleY(.8);
		this.bombExplode.setPosition(320, 280);
		this.bombExplode.animate("smoke2", .2);
		this.addChild(bombExplode);

		/* continue button */
		this.contButton = new NavButtonIcon(NavButtonIcon.CONTINUE, 
				true, KeyEvent.getKeyText(KeyEvent.VK_B));
		//this.contButton.setScaleY(.5);
		//this.contButton.setScaleX(.5);
		this.addChild(contButton);
		this.contButton.setPosition(this.getWidth() * .78, this.getHeight() * .88);
		
		this.setHeight(gameManager.getGameHeight());
		this.setWidth(gameManager.getGameWidth());
	}

	public void navigate(ArrayList<String> pressedKeys) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		if (releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_SPACE))
				|| releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_ENTER))
				|| releasedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_B))) {
			this.gameManager.setActiveGameScene("controls1");
		}
		this.prevPressedKeys.clear();
		this.prevPressedKeys.addAll(pressedKeys);
	}

	public void draw(Graphics g) {
		super.draw(g);

		Font f = new Font("Dialog", Font.BOLD, 100);
		Font h = new Font("Dialog", Font.BOLD, 22);
		Font i = new Font("Monospaced", Font.BOLD, 15);
		g.setFont(f);
		g.drawString("Welcome to the classroom!", 30, 85);
		g.setFont(h);
		g.drawString("Collect VP", 440, 155);
		g.drawString("Use VP to buy from store at end of level", 440, 255);
		g.drawString("Collect poison before it hits you or your classmates", 440, 380);
		g.drawString("If hit, cure classmates with ginger ale", 440, 495);
		g.drawString("Aim and throw cheese puff smoke bombs to protect an area", 440, 610);
		g.setFont(i);
		g.drawString("VP", 575, 230);
	}

	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if (this.isVisible()) {
			navigate(pressedKeys);
		}
	}
}