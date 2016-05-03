package edu.virginia.game.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.display.Sprite;
import edu.virginia.game.managers.PlayerManager;

public class KeyboardKey extends Sprite{
	
	private static PlayerManager playerManager = PlayerManager.getInstance();
	public static final String UP = "up";
	public static final String DOWN = "down";
	public static final String RIGHT = "right";
	public static final String LEFT = "left";
	public static final String PRIMARY = "primary";
	public static final String SECONDARY = "secondary";
	public int numPlayer;
	public String buttonType;
	
	public KeyboardKey(String buttonType, int numPlayer) {
		super(buttonType, "controls/key.png");
		this.buttonType = buttonType;
		this.numPlayer = numPlayer;
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		if (this.isVisible()) {
			Font f = new Font("Dialog", Font.PLAIN, 12);
			if(this.getScaleX() <= .7 && this.getScaleX() > .5) {
				f = new Font("Dialog", Font.PLAIN, 10);
			} else if(this.getScaleX() <= .5 && this.getScaleX() > .3) {
				f = new Font("Dialog", Font.PLAIN, 8);
			}
			g.setFont(f);
			g.setColor(Color.BLACK);
			g.drawString(this.playerManager.getKey(this.buttonType, numPlayer), 
					(int) (this.getxPos() + this.getHeight()*.348), 
					(int) (this.getyPos() + this.getHeight()*.75));
		}

	}
	
	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
	
	}
}
