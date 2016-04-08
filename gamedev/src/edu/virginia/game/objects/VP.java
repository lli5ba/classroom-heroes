package edu.virginia.game.objects;

import java.util.Random;

import edu.virginia.engine.events.CollisionEvent;
import edu.virginia.game.main.PickedUpEvent;

public class VP extends PickedUpItem {

	public VP(String id, String imageFileName) {
		super(id, imageFileName);
		Random rand1 = new Random();
		int colorVar = (int) (rand1.nextDouble() * 3);
		String color = null;
		switch (colorVar) {
		case 0:
			color = "red";
			break;
		case 1:
			color = "yellow";
			break;
		case 2:
			color = "blue";
			break;
		default:
			color = "red";
		}
		this.animate(color);
		this.setScaleX(1);
		this.setScaleY(1);
	}

	public VP(String id) {
		super(id, "projectiles/vp0.png", "projectiles/vpsheet.png",
				"resources/projectiles/vpsheetspecs.txt");
		Random rand1 = new Random();
		int colorVar = (int) (rand1.nextDouble() * 3);
		String color = null;
		switch (colorVar) {
		case 0:
			color = "red";
			this.setImage("projectiles/vp0.png");
			break;
		case 1:
			color = "yellow";
			this.setImage("projectiles/vp1.png");
			break;
		case 2:
			color = "blue";
			this.setImage("projectiles/vp2.png");
			break;
		default:
			color = "red";
		}
		
		this.animate(color);
		this.setScaleX(1);
		this.setScaleY(1);
	}

}
