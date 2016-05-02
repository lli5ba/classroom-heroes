package edu.virginia.engine.display;

import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.util.GameClock;

public class ToastSprite extends Sprite {
	private GameClock gameClockPlay;
	private double seconds;
	
	public ToastSprite(String id, String fileName) {
		super(id, fileName);
		this.gameClockPlay = new GameClock();
		this.seconds = 0;
		this.setVisible(false);
	}
	


	public void checkNotVisible(){
		if(this.isVisible()) {
			if (this.gameClockPlay.getElapsedTime() > (this.seconds*1000)) {
				this.setVisible(false);
			}
		}
	}
	public void play(double seconds) {
		//start timer
		this.setVisible(true);
		this.gameClockPlay.resetGameClock();
		this.seconds = seconds;
	}
	
	@Override
	public void draw(Graphics g) {
		if (isVisible()) {
			super.draw(g);
		}

	}
	
	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
	
		//show for only a number of seconds
		checkNotVisible();
		
		
	}

}
