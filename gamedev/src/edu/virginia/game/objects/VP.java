package edu.virginia.game.objects;

import java.util.Random;

import edu.virginia.engine.display.PickedUpItem;
import edu.virginia.engine.events.CollisionEvent;
import edu.virginia.lab1test.PickedUpEvent;

public class VP extends PickedUpItem{
	
	public VP(String id, String imageFileName) {
		super(id, imageFileName);
		Random rand1 = new Random();
		int colorVar = (int)(rand1.nextDouble() * 3);
		String color = null;
		switch (colorVar) {
			case 0: color = "red";
					break;
			case 1: color = "yellow";
					break;
			case 2: color = "blue";
					break;
			default: color = "red";
		}
		this.animate(color);
		this.setScaleX(1);
		this.setScaleY(1);
	}
	
	public VP(String id, String imageFileName, String spriteSheetFileName, String spriteSheetSpecsFileName) {
		super(id, imageFileName, spriteSheetFileName, spriteSheetSpecsFileName);
		Random rand1 = new Random();
		int colorVar = (int)(rand1.nextDouble() * 3);
		String color = null;
		switch (colorVar) {
			case 0: color = "red";
					break;
			case 1: color = "yellow";
					break;
			case 2: color = "blue";
					break;
			default: color = "red";
		}
		this.animate(color);
		this.setScaleX(1);
		this.setScaleY(1);
	}
	
	
}
