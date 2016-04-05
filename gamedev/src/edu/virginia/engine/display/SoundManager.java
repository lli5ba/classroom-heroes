package edu.virginia.engine.display;

import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.game.managers.ProjectileManager;
import edu.virginia.game.objects.EventTypes;
import edu.virginia.game.objects.Player;

public class SoundManager implements IEventListener{
	HashMap<String, String> soundeffects;
	HashMap<String, String> music;
	AudioInputStream audioIn;
	AudioInputStream audioInSoundEffect;
	Clip clipPlaying;
	Clip clipPlayingSoundEffect;
	private static volatile SoundManager instance;

	public static SoundManager getInstance() throws LineUnavailableException {
		if (instance == null) {
			instance = new SoundManager();
		}
		return instance;
	}
	// sound effects are short and don't loop
	public void LoadSoundEffect(String id, String filename) {
		soundeffects.put(id, filename);
		return;
	}

	public void PlaySoundEffect(String id) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		clipPlayingSoundEffect.stop();
		if (soundeffects.containsKey(id)) {
			String filename = soundeffects.get(id);
			audioInSoundEffect = AudioSystem.getAudioInputStream(SoundManager.class.getResource(filename));
			clipPlayingSoundEffect = AudioSystem.getClip();
			clipPlayingSoundEffect.open(audioInSoundEffect);
			clipPlayingSoundEffect.start();
			
		} else {
			return;
		}
	}

	// music loops and plays forever
	public void LoadMusic(String id, String filename) {
		music.put(id, filename);
		return;
	}

	public void PlayMusic(String id) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		clipPlaying.stop();
		if (music.containsKey(id)) {
			String filename = music.get(id);
			audioIn = AudioSystem.getAudioInputStream(SoundManager.class.getResource(filename));
			clipPlaying.open(audioIn);
			clipPlaying.loop(clipPlaying.LOOP_CONTINUOUSLY);
		} else {
			return;
		}
	}

	public SoundManager() throws LineUnavailableException {
		instance = this;
		soundeffects = new HashMap<String, String>();
		music = new HashMap<String, String>();
		clipPlaying = AudioSystem.getClip();
		clipPlayingSoundEffect = AudioSystem.getClip();

	}

	@Override
	public void handleEvent(Event event) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		if (event.getEventType().equals(EventTypes.SWING_NET.toString())) {
			this.netSound();
		}
		
	}
	
	public void netSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		this.LoadSoundEffect("net", "net.wav");
		this.PlaySoundEffect("net");
	}
}
