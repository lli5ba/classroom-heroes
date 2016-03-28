package edu.virginia.engine.display;

public class PickedUpItem extends AnimatedSprite{

	boolean pickedUp;
	public PickedUpItem(String id, String imageFileName) {
		super(id, imageFileName);
		pickedUp = false;
	}
	
	public PickedUpItem(String id, String imageFileName, String spriteSheetFileName, String spriteSheetSpecsFileName) {
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
