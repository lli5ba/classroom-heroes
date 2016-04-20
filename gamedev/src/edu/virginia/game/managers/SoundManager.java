package edu.virginia.game.managers;

import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import edu.virginia.engine.events.GameEvent;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.game.objects.EventTypes;
import edu.virginia.game.objects.Player;

public class SoundManager implements IEventListener {
	HashMap<String, AudioClip> soundeffect;
	HashMap<String, String> music;
	
	AudioInputStream audioIn; //for music
	Clip clipPlaying; // for music
	
	AudioInputStream audioInSoundEffect;
	Clip clipPlayingSoundEffect;
	private static volatile SoundManager instance;
	private String soundEffect1;

	public static SoundManager getInstance() throws LineUnavailableException {
		if (instance == null) {
			instance = new SoundManager();
		}
		return instance;
	}

	// sound effects are short and don't loop
	public void LoadSoundEffect(String id, String filename) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		if(!soundeffect.containsKey(id)) {
			soundeffect.put(id, new AudioClip(filename));
		}
		return;
	}

	public void PlaySoundEffect(String id) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (soundeffect.containsKey(id)) {
			AudioClip clipToPlay = soundeffect.get(id);
			if(!clipToPlay.getClip().isRunning()) {
				clipToPlay.startPlaying();
			}
		} else {
			return;
		}
	}
	


	// music loops and plays forever
	public void LoadMusic(String id, String filename) {
		music.put(id, filename);
		return;
	}

	public boolean isPlayingMusic() {
		return clipPlaying.isRunning();
	}
	public void stopMusic() {
		clipPlaying.stop();
		clipPlaying.close();
		
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
		soundeffect = new HashMap<String, AudioClip>();
		music = new HashMap<String, String>();
		clipPlaying = AudioSystem.getClip();
		clipPlayingSoundEffect = AudioSystem.getClip();
		soundEffect1 = "";
	}

	@Override
	public void handleEvent(GameEvent event)
			throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		if (event.getEventType().equals(EventTypes.SWING_NET.toString())) {
			if(!this.isPlayingSoundEffect1()) {
				this.LoadSoundEffect("net", "net.wav");
				this.PlaySoundEffect("net");
			}
		} else if (event.getEventType().equals(EventTypes.WALK.toString())) {
			if(!this.isPlayingSoundEffect1()) {
				this.LoadSoundEffect("walk", "walk.wav");
				this.PlaySoundEffect("walk");
			}
			// FIXME: Walk occurs at end of key press
			// FIXME: can only play walk OR net, not both at same time
		} else if (event.getEventType().equals(EventTypes.PICKUP_VP.toString())) {
			if(!this.isPlayingSoundEffect1()) {
				this.LoadSoundEffect("vp", "vp.wav");
				this.PlaySoundEffect("vp");
			}
		} else if (event.getEventType().equals(EventTypes.PICKUP_POISON.toString())) {
			if(!this.isPlayingSoundEffect1()) {
				this.LoadSoundEffect("poison", "poison.wav");
				this.PlaySoundEffect("poison");
			}
		} else if (event.getEventType().equals(EventTypes.CURE_STUDENT.toString())) {
			if(!this.isPlayingSoundEffect1()) {
				this.LoadSoundEffect("cured", "cured.wav");
				this.PlaySoundEffect("cured");
			}
		} else if (event.getEventType().equals(EventTypes.POISON_STUDENT.toString())) {
			if(!this.isPlayingSoundEffect1()) {
				this.LoadSoundEffect("dead", "dead.wav");
				this.PlaySoundEffect("dead");
			}
		}
	}
	
	public String getSoundEffect1() {
		return soundEffect1;
	}

	public void setSoundEffect1(String soundEffect1) {
		this.soundEffect1 = soundEffect1;
	}

	public boolean isPlayingSoundEffect1() {
		return this.clipPlayingSoundEffect.isRunning();
	}

	public void stopAll() {
		this.clipPlaying.close();
		this.clipPlayingSoundEffect.close();
		this.clipPlaying.stop();
		this.clipPlayingSoundEffect.stop();
		
	}




}
