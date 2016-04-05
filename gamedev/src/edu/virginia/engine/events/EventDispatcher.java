package edu.virginia.engine.events;

import java.util.ArrayList;
import java.util.HashMap;

public class EventDispatcher implements IEventDispatcher {

	HashMap<String, ArrayList<IEventListener>> observers;

	public EventDispatcher() {
		observers = new HashMap<String, ArrayList<IEventListener>>();
	}

	@Override
	public void addEventListener(IEventListener listener, String eventType) {
		if (observers.containsKey(eventType)) {
			observers.get(eventType).add(listener);
		} else {
			ArrayList<IEventListener> listeners = new ArrayList<IEventListener>();
			listeners.add(listener);
			observers.put(eventType, listeners);
		}
	}

	@Override
	public void removeEventListener(IEventListener listener, String eventType) {
		if (observers.containsKey(eventType)) {
			observers.get(eventType).remove(listener);
		}
	}

	@Override
	public void dispatchEvent(Event event) {
		if (observers.containsKey(event.getEventType())) {
			for (IEventListener observer : observers.get(event.getEventType())) {
				observer.handleEvent(event);
			}
		}
	}

	@Override
	public boolean hasEventListener(IEventListener listener, String eventType) {
		if (observers.containsKey(eventType)) {
			if (observers.get(eventType).contains(listener)) {
				return true;
			}
		}
		return false;

	}

}
