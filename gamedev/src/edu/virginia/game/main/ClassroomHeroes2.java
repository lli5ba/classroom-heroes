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

import edu.virginia.game.managers.ProjectileManager;

import edu.virginia.game.objects.Classroom;
import edu.virginia.game.objects.Hallway;
import edu.virginia.game.objects.ItemDetail;
import edu.virginia.game.objects.PickedUpItem;
import edu.virginia.game.objects.Player;
import edu.virginia.game.objects.Poison;
import edu.virginia.game.objects.Store;
import edu.virginia.game.objects.VP;

/**
 * 
 * */
public class ClassroomHeroes2 extends Game {
	private static PlayerManager playerManager = PlayerManager.getInstance();
	private static LevelManager levelManager = LevelManager.getInstance();
	private static GameManager gameManager = GameManager.getInstance();
	private static ProjectileManager projectileManager = ProjectileManager.getInstance();
	SoundManager mySoundManager;
	TweenJuggler myTweenJuggler = TweenJuggler.getInstance();
	private GameClock gameClock;
	Hallway hallway0 = new Hallway("hallway0", "0");
	Classroom classroom1 = new Classroom("classroom1");

	/**
	 * Constructor. See constructor in Game.java for details on the parameters
	 * given
	 * 
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	public ClassroomHeroes2() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		super("Classroom Heroes", gameManager.getGameWidth(), gameManager.getGameHeight());
		gameClock = new GameClock();

	}

	public void changeScreens() {
		// TODO: Leandra
	}

	/**
	 * Engine will automatically call this update method once per frame and pass
	 * to us the set of keys (as strings) that are currently being pressed down
	 */
	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		if (this.classroom1 != null) {
			classroom1.update(pressedKeys);
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

		if (classroom1 != null) {
			classroom1.draw(g);
		}

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
		ClassroomHeroes2 game = new ClassroomHeroes2();
		game.start();
	}
}
