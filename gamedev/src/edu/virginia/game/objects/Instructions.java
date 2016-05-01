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
	private AnimatedSprite poisonPlayer;
	private AnimatedSprite dead;
	private AnimatedSprite revived;
	private AnimatedSprite bombPlayer;
	private AnimatedSprite bomb;

	public Instructions(String id) {
		super(id, "instructions/instructions-background.png"); // background

		this.vp0 = new AnimatedSprite("vp0", "projectiles/vp0.png", "projectiles/vpsheet.png",
				"resources/projectiles/vpsheetspecs.txt");
		this.vp0.setScaleX(.5);
		this.vp0.setScaleY(.5);
		this.vp0.setPosition(25, 70);
		this.animate("red");
		this.addChild(vp0);

		this.vp1 = new AnimatedSprite("vp1", "projectiles/vp1.png", "projectiles/vpsheet.png",
				"resources/projectiles/vpsheetspecs.txt");
		this.vp1.setScaleX(.5);
		this.vp1.setScaleY(.5);
		this.vp1.setPosition(40, 70);
		this.animate("yellow");
		this.addChild(vp1);

		this.vp2 = new AnimatedSprite("vp2", "projectiles/vp2.png", "projectiles/vpsheet.png",
				"resources/projectiles/vpsheetspecs.txt");
		this.vp2.setScaleX(.5);
		this.vp2.setScaleY(.5);
		this.vp2.setPosition(55, 70);
		this.animate("blue");
		this.addChild(vp2);

		this.vpPlayer = new AnimatedSprite("vpPlayer", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt");
		this.vpPlayer.setScaleX(1.0);
		this.vpPlayer.setScaleY(1.0);
		this.vpPlayer.setPosition(90, 55);
		this.addChild(vpPlayer);
		
		this.thrownVP = new AnimatedSprite("vp0", "projectiles/vp0.png", "projectiles/vpsheet.png",
				"resources/projectiles/vpsheetspecs.txt");
		this.thrownVP.setScaleX(.5);
		this.thrownVP.setScaleY(.5);
		this.thrownVP.setPosition(135, 70);
		this.animate("red");
		this.addChild(thrownVP);
		
		this.vending = new Sprite("vending", "hallway/vending-machine-0.png");
		this.vending.setScaleX(.7);
		this.vending.setScaleY(.7);
		this.vending.setPosition(25, 108);
		this.addChild(vending);
		
		this.drink = new Sprite("drink", "hallway/drink-machine-0.png");
		this.drink.setScaleX(.7);
		this.drink.setScaleY(.7);
		this.drink.setPosition(50, 108);
		this.addChild(drink);
		
		this.vending1 = new Sprite("vending", "hallway/vending-machine-1.png");
		this.vending1.setScaleX(.7);
		this.vending1.setScaleY(.7);
		this.vending1.setPosition(125, 108);
		this.addChild(vending1);
		
		this.drink1 = new Sprite("drink", "hallway/drink-machine-1.png");
		this.drink1.setScaleX(.7);
		this.drink1.setScaleY(.7);
		this.drink1.setPosition(150, 108);
		this.addChild(drink1);
		
		this.poisonPlayer = new AnimatedSprite("poisonPlayer", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt");
		this.poisonPlayer.setScaleX(1.0);
		this.poisonPlayer.setScaleY(1.0);
		this.poisonPlayer.setPosition(40, 162);
		this.addChild(poisonPlayer);

		this.poison = new AnimatedSprite("poison", "projectiles/poison.png", "projectiles/poison-spritesheet.png",
				"resources/projectiles/poison-spritesheet.txt");
		this.poison.setScaleX(.5);
		this.poison.setScaleY(.5);
		this.poison.setPosition(100, 180);
		this.animate("poison");
		this.addChild(poison);
		
		this.dead = new AnimatedSprite("dead", "student/student-default-0.png", "student/student-spritesheet-0.png",
				"resources/student/student-spritesheet-0-frameInfo.txt");
		this.dead.setScaleX(.8);
		this.dead.setScaleY(.8);
		this.dead.setPosition(50, 242);
		this.addChild(dead);
		
		this.revived = new AnimatedSprite("revived", "student/student-default-0.png", "student/student-spritesheet-0.png",
				"resources/student/student-spritesheet-0-frameInfo.txt");
		this.revived.setScaleX(.8);
		this.revived.setScaleY(.8);
		this.revived.setPosition(130, 242);
		this.addChild(revived);

		this.bombPlayer = new AnimatedSprite("vpPlayer", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt");
		this.bombPlayer.setScaleX(1.0);
		this.bombPlayer.setScaleY(1.0);
		this.bombPlayer.setPosition(40, 264);
		this.addChild(bombPlayer);
		
		this.bomb = new AnimatedSprite("bomb", "smokebomb/smokebomb-default.png", "smokebomb/smokebomb-spritesheet.png",
				"resources/smokebomb/smokebomb-spritesheet.txt");
		this.bomb.setScaleX(.8);
		this.bomb.setScaleY(.8);
		this.bomb.setPosition(80, 284);
		this.addChild(bomb);

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

		Font f = new Font("Dialog", Font.BOLD, 50);
		Font h = new Font("Dialog", Font.BOLD, 25);
		Font i = new Font("Monospaced", Font.BOLD, 15);
		g.setFont(f);
		g.drawString("Welcome to the classroom!", 40, 70);
		g.setFont(h);
		g.drawString("Collect VP", 40, 120);
		g.drawString("Use VP to buy from store at end of level", 40, 235);
		g.drawString("Collect poison before it hits you or your classmates", 40, 360);
		g.drawString("If hit, cure classmates with antidote", 40, 475);
		g.drawString("Aim and throw smoke bombs to protect area", 40, 590);
		g.setFont(i);
		g.drawString("VP", 75, 200);
	}

	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if (this.isVisible()) {
			navigate(pressedKeys);
		}
	}
}