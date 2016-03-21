package edu.virginia.engine.tween;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventDispatcher;

public class TweenEvent extends Event {
	public final static String TWEEN_COMPLETE_EVENT = "tween_complete";
	DisplayObject tween;
	
	public TweenEvent(String eventType, DisplayObject object) {
		super(eventType, object);
		this.tween = object;
	}
	
	public Tween getTween(){
		return this.getTween();
	}

}
