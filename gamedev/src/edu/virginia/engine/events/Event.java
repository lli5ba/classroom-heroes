package edu.virginia.engine.events;

public class Event {
	String eventType;
	IEventDispatcher source; // the object that created this event with the new
								// keyword

	public Event(String eventType, IEventDispatcher source) {
		this.eventType = eventType;
		this.source = source;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public IEventDispatcher getSource() {
		return source;
	}

	public void setSource(IEventDispatcher source) {
		this.source = source;
	}

}
