package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Sprite;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.PlayerManager;

public class PlayerStat extends Sprite {

	private Sprite halfheart;
	private Sprite wholeheart;
	private Sprite emptyheart;
	private GameManager gameManager = GameManager.getInstance();
	private PlayerManager playerManager = PlayerManager.getInstance();

	public PlayerStat(String id) {
		super(id);

		//FIXME:
		//Check notes by Leandra!s
		
		this.halfheart = new Sprite("half", "statbox/heart-half.png");
		this.addChild(halfheart);

		this.wholeheart = new Sprite("full", "statbox/heart-whole.png");
		this.addChild(wholeheart);

		this.emptyheart = new Sprite("empty", "statbox/heart-empty.png");
		this.addChild(emptyheart);
		
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
	}

}
