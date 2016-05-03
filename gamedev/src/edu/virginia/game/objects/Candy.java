package edu.virginia.game.objects;

import java.util.Random;

import edu.virginia.engine.events.CollisionEvent;
import edu.virginia.game.main.PickedUpEvent;

public class Candy extends PickedUpItem {

	public Candy(String id, String imageFileName) {
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

	public Candy(String id) {
		super(id, "candy/candy0.png", "projectiles/candy-spritesheet.png",
				"resources/projectiles/candy-spritesheet.txt");
		Random rand1 = new Random();
		int colorVar = (int) (rand1.nextDouble() * 3);
		String color = null;
		switch (colorVar) {
		case 0:
			color = "red";
			this.setImage("candy/candy0.png");
			break;
		case 1:
			color = "yellow";
			this.setImage("candy/candy1.png");
			break;
		case 2:
			color = "blue";
			this.setImage("candy/candy2.png");
			break;
		default:
			color = "red";
		}
		
		this.animate(color);
		this.setScaleX(1);
		this.setScaleY(1);
	}

}
