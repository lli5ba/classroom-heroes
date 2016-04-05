package edu.virginia.engine.events;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public interface IEventListener {
	public void handleEvent(Event event) throws LineUnavailableException, IOException, UnsupportedAudioFileException; // equivalent to notify

}
