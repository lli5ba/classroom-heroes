package edu.virginia.game.objects;

import edu.virginia.engine.display.PickedUpItem;

public class Poison extends PickedUpItem {

	boolean pickedUp;

	public Poison(String id, String imageFileName) {
		super(id, imageFileName);
		pickedUp = false;
	}

	public Poison(String id, String imageFileName, String spriteSheetFileName, String spriteSheetSpecsFileName) {
		super(id, imageFileName, spriteSheetFileName, spriteSheetSpecsFileName);
		pickedUp = false;
	}

	public boolean isPickedUp() {
		return pickedUp;
	}

	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

}
