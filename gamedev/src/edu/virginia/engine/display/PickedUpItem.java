package edu.virginia.engine.display;

public class PickedUpItem extends Sprite{

	boolean pickedUp;
	public PickedUpItem(String id, String imageFileName) {
		super(id, imageFileName);
		pickedUp = false;
	}
	public boolean isPickedUp() {
		return pickedUp;
	}
	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}
	
	

}
