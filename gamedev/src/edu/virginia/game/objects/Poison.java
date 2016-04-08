package edu.virginia.game.objects;

public class Poison extends PickedUpItem {


	public Poison(String id) {
		super(id, "projectiles/poison.png", 
				"projectiles/poison-spritesheet.png", "resources/projectiles/poison-spritesheet.txt");
		this.animate("poison");
	}

}
