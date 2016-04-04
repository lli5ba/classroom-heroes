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
import java.io.PrintWriter;
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

	private static String currentDirectory = "spritesheetmapper/";
	private static String textFileName = "resources/spritesheetmapper/player-spritesheet-1";
	private Sprite currentSprite;
	private boolean waitingForClick;
	private Position currPosition;
	private static int xBias = 3;
	private static int yBias = 25;
	
	
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 * */
	public SpriteSheetMapper() {
		super("Classroom Heroes",
				200, 200); 
		currentSprite = null;
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
		
		PrintWriter writer = new PrintWriter(textFileName +"-frameInfo.txt", "UTF-8");
		PrintWriter writerOnly = new PrintWriter(textFileName +"-frameInfo-only.txt", "UTF-8");
		
		try {
			FileInputStream fstream = new FileInputStream(textFileName + ".txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				
				String[] tokens = strLine.split(" ");
				String imageFileName = tokens[0] + " " + tokens[1];
				mapper.setCurrentSprite(new Sprite(imageFileName, currentDirectory + imageFileName + ".png"));
				
				mapper.setWaitingForClick(true);
				int xPos = 0;
				int yPos = 0;
				int width = mapper.getCurrentSprite().getUnscaledWidth();
				int height = mapper.getCurrentSprite().getUnscaledHeight();
				
				while (mapper.isWaitingForClick()) {
					System.out.print(""); // stall until position is set
				}
				;

				Position p1 = mapper.getCurrPosition();
				xPos = (int) p1.getX();
				yPos = (int) p1.getY();

				mapper.setWaitingForClick(true);
				while (mapper.isWaitingForClick()) {
					System.out.print(""); // stall until position is set
				}
				;

				Position p2 = mapper.getCurrPosition();
				width = (int) calcWidth(p1, p2);
				height = (int) calcHeight(p1, p2);
				System.out.println(xPos + " " + yPos + " " + width + " " + height );
				writer.println(strLine +" " + xPos + " " + yPos + " " + width + " " + height);
				writerOnly.println(xPos + " " + yPos + " " + width + " " + height);
			}
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		writer.close();
		writerOnly.close();
		
	}

	public static int calcHeight(Position p1, Position p2) {
		int y2 = (int) p2.getY();
		int y1 = (int) p1.getY();
		return Math.abs(y2-y1);
	}
	
	public static int calcWidth(Position p1, Position p2) {
		int x2 = (int) p2.getX();
		int x1 = (int) p1.getX();
		return Math.abs(x2-x1);
	}
	public Position getCurrPosition() {
		return this.currPosition;
	}
	
	public Sprite getCurrentSprite() {
		return this.currentSprite;
	}
	
	public boolean isWaitingForClick() {
		return this.waitingForClick;
	}
	
	public void setCurrentSprite(Sprite sprite) {
		this.currentSprite = sprite;
		
	}
	
	public void setWaitingForClick(Boolean waiting) {
		this.waitingForClick = waiting;
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if (waitingForClick) {
			int x=e.getX();
		    int y=e.getY();
		    //System.out.println(x+","+y);
		    currPosition = new Position(x - xBias, y - yBias);
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
