package edu.virginia.engine.events;

import java.io.IOException;
import edu.virginia.engine.events.GameEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public interface IEventListener {
	public void handleEvent(GameEvent event) throws LineUnavailableException, IOException, UnsupportedAudioFileException; // equivalent to notify

}
