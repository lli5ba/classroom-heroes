package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
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
	private Sprite playerIcon;
	private DisplayObjectContainer keys;
	private AnimatedSprite vp0;
	private AnimatedSprite vp1;
	private AnimatedSprite vp2;

	public Instructions(String id) {
		super(id, "instructions/instructions-background.png"); // background

		this.playerIcon = new Sprite("playerIcon", "player/player1.png");
		this.playerIcon.setScaleX(1.0);
		this.playerIcon.setScaleY(1.0);
		this.addChild(playerIcon);
		this.playerIcon.setPosition(20, 40);

		this.keys = new DisplayObjectContainer("keys", "instructions/keys-arrowkeys.png");
		this.keys.setScaleX(.4);
		this.keys.setScaleY(.4);
		this.addChild(keys);
		this.keys.setPosition(20, 95);

		this.vp0 = new AnimatedSprite("vp0", "projectiles/vp0.png", "projectiles/vpsheet.png",
				"resources/projectiles/vpsheetspecs.txt");
		this.vp0.setScaleX(.8);
		this.vp0.setScaleY(.8);
		this.vp0.setPosition(15, 140);
		this.animate("red");
		this.addChild(vp0);
		
		this.vp1 = new AnimatedSprite("vp1", "projectiles/vp1.png", "projectiles/vpsheet.png",
				"resources/projectiles/vpsheetspecs.txt");
		this.vp1.setScaleX(.8);
		this.vp1.setScaleY(.8);
		this.vp1.setPosition(30, 140); 
		this.animate("yellow");
		this.addChild(vp1);
		
		this.vp2 = new AnimatedSprite("vp2", "projectiles/vp2.png", "projectiles/vpsheet.png",
				"resources/projectiles/vpsheetspecs.txt");
		this.vp2.setScaleX(.8);
		this.vp2.setScaleY(.8);
		this.vp2.setPosition(45, 140); 
		this.animate("blue");
		this.addChild(vp2);
		
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
		Font h = new Font("Dialog", Font.BOLD, 20);
		Font i = new Font("Dialog", Font.BOLD, 60);
		g.setFont(f);
		g.drawString("Welcome to the classroom!", 20, 50);
		g.setFont(h);
		g.drawString("Here is your player:", 20, 80);
		g.drawString("Use arrow keys to move", 20, 200);
		g.drawString("Here is a VP: ", 20, 310);
		
		
		g.setFont(i);
		g.drawString("INSTRUCTIONS WILL BE FINISHED BY FINAL", 10, 400);
		g.drawString("Use space bar to swing net", 10, 450);
		g.drawString("Use b to cure classmates when poisoned", 10, 500);
		g.drawString("Use b+spacebar to throw smoke bombs", 10, 550);
		g.drawString("Press enter or spacebar to begin", 10, 600);

	}

	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if (this.isVisible()) {
			navigate(pressedKeys);
		}
	}
}