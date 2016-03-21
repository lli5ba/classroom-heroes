package edu.virginia.engine.display;

import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
	HashMap<String, String> soundeffects;
	HashMap<String, String> music;
	AudioInputStream audioIn;
	Clip clipPlaying;
	
	//sound effects are short and don't loop
	public void LoadSoundEffect(String id, String filename) {
		soundeffects.put(id, filename);
		return;
	}
	public void PlaySoundEffect(String id) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		clipPlaying.stop();
		if (soundeffects.containsKey(id)) {
			String filename = soundeffects.get(id);
			audioIn = AudioSystem.getAudioInputStream(SoundManager
					.class.getResource(filename));
			clipPlaying = AudioSystem.getClip();
			clipPlaying.open(audioIn);
			clipPlaying.start();
		} else {
			return;
		}
	}
	
	//music loops and plays forever
	public void LoadMusic(String id, String filename) {
		music.put(id, filename);
		return;
	}
	public void PlayMusic(String id) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		clipPlaying.stop();
		if (music.containsKey(id)) {
			String filename = music.get(id);
			audioIn = AudioSystem.getAudioInputStream(SoundManager
					.class.getResource(filename));
			clipPlaying.open(audioIn);
			clipPlaying.loop(clipPlaying.LOOP_CONTINUOUSLY);
		} else {
			return;
		}
	}
	public SoundManager() throws LineUnavailableException {
		soundeffects = new HashMap<String, String>();
		music = new HashMap<String, String>();
		clipPlaying = AudioSystem.getClip();
		
	}
}
