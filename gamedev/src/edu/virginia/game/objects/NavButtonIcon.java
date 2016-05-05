package edu.virginia.game.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.display.Sprite;

public class NavButtonIcon extends Sprite{
	public static final String BACK = "back";
	public static final String CONTINUE = "continue";
	private boolean words;
	private String buttonId;
	private int xKeyLoc;
	
	
	public NavButtonIcon(String id, boolean words, String buttonName) {
		super(id, "button-nav/back-button.png");
		if (words == true) {
			if (id.equals(BACK)) {
				this.setImage("button-nav/back-button-words.png");
				xKeyLoc = 15;
			} else if (id.equals(CONTINUE)) {
				this.setImage("button-nav/continue-button-words.png");
				xKeyLoc = 61;
			}
		} else {
			if (id.equals(BACK)) {
				this.setImage("button-nav/back-button.png");
				xKeyLoc = 15;
			} else if (id.equals(CONTINUE)) {
				this.setImage("button-nav/continue-button.png");
				xKeyLoc = 0;
			}
		}
		this.words = words;
		this.setButtonId(buttonName);
	}

	public double calcScaledXKeyLoc(){
		return this.getScaleX()*this.xKeyLoc;
	}

	public boolean hasWords() {
		return words;
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
			if (this.buttonId.equals("Space") || this.buttonId.equals(KeyEvent.getKeyText(KeyEvent.VK_ENTER))) {
				f = new Font("Dialog", Font.BOLD, 6);
				g.setFont(f);
				g.drawString(this.buttonId, 
						(int) (this.getxPos() + this.calcScaledXKeyLoc() + this.getHeight()*.228), 
						(int) (this.getyPos() + this.getHeight()*.65));
			} else {
				g.drawString(this.buttonId, 
						(int) (this.getxPos() + this.calcScaledXKeyLoc() + this.getHeight()*.348), 
						(int) (this.getyPos() + this.getHeight()*.75));
			}
		}

	}
	
	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
	
	}


	public String getButtonId() {
		return buttonId;
	}


	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}

	
}
