package edu.virginia.game.main;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.plaf.synth.SynthSeparatorUI;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.CollisionEvent;
import edu.virginia.engine.tween.Tween;
import edu.virginia.engine.tween.TweenEvent;
import edu.virginia.engine.tween.TweenJuggler;
import edu.virginia.engine.tween.TweenTransitions;
import edu.virginia.engine.tween.TweenableParam;
import edu.virginia.engine.util.GameClock;
import edu.virginia.engine.util.Position;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;
import edu.virginia.game.managers.SoundManager;
import edu.virginia.game.objects.Classroom;
import edu.virginia.game.objects.Classroom2;
import edu.virginia.game.objects.Classroom3;
import edu.virginia.game.objects.Classroom4;
import edu.virginia.game.objects.Controls;
import edu.virginia.game.objects.Hallway;
import edu.virginia.game.objects.Instructions;
import edu.virginia.game.objects.ItemDetail;
import edu.virginia.game.objects.PickedUpItem;
import edu.virginia.game.objects.Poison;
import edu.virginia.game.objects.Store;
import edu.virginia.game.objects.TitleScreen;
import edu.virginia.game.objects.VP;
import edu.virginia.game.objects.WeimerBonus;

/**
 * 
 * */
public class ClassroomHeroes extends Game {
	private static PlayerManager playerManager = PlayerManager.getInstance();
	private static LevelManager levelManager = LevelManager.getInstance();
	private static GameManager gameManager = GameManager.getInstance();
	SoundManager soundManager = SoundManager.getInstance();
	TweenJuggler myTweenJuggler = TweenJuggler.getInstance();
	private GameClock gameClock;

	/**
	 * Constructor. See constructor in Game.java for details on the parameters
	 * given
	 * 
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	public ClassroomHeroes() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		super("Classroom Heroes", gameManager.getGameWidth(), gameManager.getGameHeight());
		gameClock = new GameClock();

	}

	public void changeScreens() {
		// TODO: Leandra
	}

	public void sceneMusic(String id, String filename) {
		if (soundManager != null) {
			soundManager.stopAll();
			if (!soundManager.isPlayingMusic()) {
				soundManager.LoadMusic(id, filename);
				try {
					soundManager.PlayMusic(id);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Engine will automatically call this update method once per frame and pass
	 * to us the set of keys (as strings) that are currently being pressed down
	 */
	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		// this updates the corresponding active game scene!!
		this.gameManager.update(pressedKeys);

		/***
		 * create an asset and add it to the gameManager if it does not exist
		 **/
		// assumes active game scene is either "hallway[0-3]" or classroom[1-5]
		// creates the current scene if it does not yet exist
		if (!this.gameManager.activeGameSceneIsCreated()) {
			String sceneName = this.gameManager.getActiveGameScene();
			if (sceneName.contains("title")) {
				TitleScreen title = new TitleScreen(sceneName);
				// add scene to gameManager
				this.gameManager.addGameScene(sceneName, title);
				sceneMusic("title", "title.wav");
			} else if (sceneName.contains("instructions")) {
				Instructions instruction = new Instructions(sceneName);
				// add scene to gameManager
				this.gameManager.addGameScene(sceneName, instruction);
				sceneMusic("instruction", "instructions.wav");
			} else if (sceneName.contains("controls")) {
				// create a hallway
				// get last character in styleCode
				String styleCode = sceneName.substring(sceneName.length() - 1);
				Controls controls = null;
				controls = new Controls(sceneName);
				// add scene to gameManager
				this.gameManager.addGameScene(sceneName, controls);
			} else if (sceneName.contains("hallway")) {
				// create a hallway
				// get last character in styleCode
				String styleCode = sceneName.substring(sceneName.length() - 1);
				Hallway hallway = null;
				hallway = new Hallway(sceneName, styleCode);
				// add scene to gameManager
				this.gameManager.addGameScene(sceneName, hallway);
				sceneMusic("hallway", "hallway.wav");
			}
			else if (sceneName.contains("classroom")) {
				if (sceneName.equals("classroom1")) {
					// create a classroom
					Classroom classroom = null;
					try {
						classroom = new Classroom(sceneName);
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
						e.printStackTrace();
					}
					// add scene to gameManager
					this.gameManager.addGameScene(sceneName, classroom);
					this.gameManager.setNumLevel(2);
				}
				else if (sceneName.equals("classroom2")) {
					
					// create a classroom
					Classroom2 classroom2 = null;
					try {
						classroom2 = new Classroom2(sceneName);
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// add scene to gameManager

					this.gameManager.addGameScene(sceneName, classroom2);
					this.gameManager.setNumLevel(3);
				}
				else if (sceneName.equals("classroom3")) {
					
					// create a classroom
					Classroom3 classroom3 = null;
					try {
						classroom3 = new Classroom3(sceneName);
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// add scene to gameManager

					this.gameManager.addGameScene(sceneName, classroom3);
					this.gameManager.setNumLevel(4);
				}
				else if (sceneName.equals("classroom4")) {
					
					// create a classroom
					Classroom4 classroom4 = null;
					try {
						classroom4 = new Classroom4(sceneName);
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// add scene to gameManager

					this.gameManager.addGameScene(sceneName, classroom4);
					this.gameManager.setNumLevel(5);
				}
			}
			else if (sceneName.equals("weimer")) {
				
				WeimerBonus weimer = null;
				try {
					weimer = new WeimerBonus(sceneName);
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
				
				this.gameManager.addGameScene(sceneName, weimer);
			}
			
		}
	}

	/**
	 * Engine automatically invokes draw() every frame as well. If we want to
	 * make sure mario gets drawn to the screen, we need to make sure to
	 * override this method and call mario's draw method.
	 */
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		this.gameManager.draw(g);
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts
	 * the timer that calls update() and draw() every frame
	 * 
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		ClassroomHeroes game = new ClassroomHeroes();
		game.start();
	}
}
