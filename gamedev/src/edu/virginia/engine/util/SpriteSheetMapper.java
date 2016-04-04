package edu.virginia.engine.util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;


/**
 * 
 * */
public class SpriteSheetMapper extends Game implements MouseListener{

	private static String currentDirectory = "resources/player/";
	private static String textFileName = "resources/player/player1sheespecs.txt";;
	private Sprite currentSprite;
	private boolean waitingForClick;
	private Position currPosition;
	
	
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 * */
	public SpriteSheetMapper() {
		super("Classroom Heroes",
				200, 200); 
		currentSprite = new Sprite("sprite", "player/player1.png");
		currPosition = new Position(0, 0);
		waitingForClick = true;
		this.getMainFrame().addMouseListener(this);
	
	}
	
	
	public void changeScreens() {
		//TODO: Leandra
	}

	
	
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys);
	
		if (this.currentSprite != null) {
			currentSprite.update(pressedKeys);
		}
	}
	
	
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
	 * the screen, we need to make sure to override this method and call mario's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);

		if(currentSprite != null) {
			currentSprite.draw(g);
		}
		
	}
	
	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 * @throws LineUnavailableException 
	 * */
	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		SpriteSheetMapper mapper = new SpriteSheetMapper();
		mapper.start();
		
		try {
			FileInputStream fstream = new FileInputStream(textFileName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {

				String[] tokens = strLine.split(" ");
				String imageFileName = tokens[0] + " " + tokens[1];
				mapper.setCurrentSprite(new Sprite(imageFileName, imageFileName + ".png"));
				//while(mapper.getWaitingForClick);
				//System.out.println("Adding image at " + xPos + "," + yPos + "," + xWidth + "," + yHeight);
				

			}
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		
	}

	
	public Position getCurrPosition() {
		return this.currPosition;
	}
	
	public boolean isWaitingForClick() {
		return this.waitingForClick;
	}
	
	public void setCurrentSprite(Sprite sprite) {
		this.currentSprite = sprite;
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if (waitingForClick) {
			int x=e.getX();
		    int y=e.getY();
		    System.out.println(x+","+y);
		    currPosition = new Position(x, y);
		    waitingForClick = false; 
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	
}
