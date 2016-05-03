package edu.virginia.game.objects;

//Has list of events so that string for each event is always the same
public enum EventTypes {
	PICKUP_VP("pickup VP"),
	PICKUP_POISON("pickup poison"),
	POISON_PLAYER("poison player"),
	POISON_STUDENT("poison student"),
	WIN_LEVEL("win level"),
	START_LEVEL("start level"),
	LOSE_LEVEL("lose level"),
	CURE_STUDENT("cure student"),
	THROW_SMOKEBOMB("throw smokebomb"), 
	SWING_NET("swing net"),
	WALK("walk"),
	PICKUP_CANDY("pickup candy");
	;
	
	private final String text;
	
	private EventTypes(final String text) {
        this.text = text;
    }

	@Override
	public String toString() {
		return text;
	}
}
