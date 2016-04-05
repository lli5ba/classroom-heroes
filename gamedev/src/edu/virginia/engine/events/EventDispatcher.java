package edu.virginia.engine.events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
				try {
					observer.handleEvent(event);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
