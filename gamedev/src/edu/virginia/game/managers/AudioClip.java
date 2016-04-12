package edu.virginia.game.managers;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioClip {

	String filename;
	AudioInputStream audioIn;
	Clip clipPlaying;
	public AudioClip(String filename) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		this.filename = filename;
		clipPlaying = AudioSystem.getClip();
		audioIn = AudioSystem.getAudioInputStream(SoundManager.class.getResource(filename));
	}
	
	public Clip getClip() {
		return this.clipPlaying;
	}

}
