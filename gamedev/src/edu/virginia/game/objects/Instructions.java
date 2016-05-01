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
	private AnimatedSprite poison;

	public Instructions(String id) {
		super(id, "instructions/instructions-background.png"); // background

		this.vp0 = new AnimatedSprite("vp0", "projectiles/vp0.png", "projectiles/vpsheet.png",
				"resources/projectiles/vpsheetspecs.txt");
		this.vp0.setScaleX(.5);
		this.vp0.setScaleY(.5);
		this.vp0.setPosition(25, 125);
		this.animate("red");
		this.addChild(vp0);

		this.vp1 = new AnimatedSprite("vp1", "projectiles/vp1.png", "projectiles/vpsheet.png",
				"resources/projectiles/vpsheetspecs.txt");
		this.vp1.setScaleX(.5);
		this.vp1.setScaleY(.5);
		this.vp1.setPosition(40, 125);
		this.animate("yellow");
		this.addChild(vp1);

		this.vp2 = new AnimatedSprite("vp2", "projectiles/vp2.png", "projectiles/vpsheet.png",
				"resources/projectiles/vpsheetspecs.txt");
		this.vp2.setScaleX(.5);
		this.vp2.setScaleY(.5);
		this.vp2.setPosition(55, 125);
		this.animate("blue");
		this.addChild(vp2);

		this.vpPlayer = new AnimatedSprite("vp2", "player/player1.png", "player/player-spritesheet-1.png",
				"resources/player/player-spritesheet-1-frameInfo.txt");
		this.vpPlayer.setScaleX(1.0);
		this.vpPlayer.setScaleY(1.0);
		this.vpPlayer.setPosition(80, 110);
		this.addChild(vpPlayer);
		
		this.thrownVP = new AnimatedSprite("vp0", "projectiles/vp0.png", "projectiles/vpsheet.png",
				"resources/projectiles/vpsheetspecs.txt");
		this.thrownVP.setScaleX(.5);
		this.thrownVP.setScaleY(.5);
		this.thrownVP.setPosition(25, 125);
		this.animate("red");
		this.addChild(thrownVP);

		this.poison = new AnimatedSprite("poison", "projectiles/poison.png", "projectiles/poison-spritesheet.png",
				"resources/projectiles/poison-spritesheet.txt");
		this.poison.setScaleX(.5);
		this.poison.setScaleY(.5);
		this.poison.setPosition(40, 180);
		this.animate("poison");
		this.addChild(poison);

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
		g.drawString("VP", 95, 320);
	}

	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if (this.isVisible()) {
			navigate(pressedKeys);
		}
	}
}