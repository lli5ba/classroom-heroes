package edu.virginia.game.objects;

public class Poison extends PickedUpItem {

	public Poison(String id, String imageFileName) {
		super(id, imageFileName);
	}

	public Poison(String id, String imageFileName, String spriteSheetFileName, String spriteSheetSpecsFileName) {
		super(id, imageFileName, spriteSheetFileName, spriteSheetSpecsFileName);
	}

}
