package edu.virginia.game.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.util.Position;
import edu.virginia.game.managers.GameManager;
import edu.virginia.game.managers.LevelManager;
import edu.virginia.game.managers.PlayerManager;

public class EndLevelScreen extends DisplayObjectContainer {

	public static final String WIN = "You survived Classroom 1! (:";
	public static final String LOSE_NO_HEALTH = "Out of health! You lose :( ";
	public static final String LOSE_STUDENTS = "All your friends perished! You lose :( ";
	private Sprite highlight;
	private String currentHighlight;
	private String dialog;
	private PlayerManager playerManager = PlayerManager.getInstance();
	private LevelManager levelManager = LevelManager.getInstance();
	private GameManager gameManager = GameManager.getInstance();
	private int numPlayer;
	private int experience;
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();

	public EndLevelScreen(String id) {
		super(id, "end-level/notebook.png"); // notebook
		this.highlight = new Sprite(id + "-highlight", "store/insert-cancel-highlight.png");
		this.setExperience(0);
		this.setDialog(WIN);
		this.numPlayer = 1;
	}

	
	
	public int getNumPlayer() {
		return numPlayer;
	}

	public void setNumPlayer(int numPlayer) {
		this.numPlayer = numPlayer;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		this.setDrawChildren(visible); // don't draw children if store isn't
										// visible
	}


	public void navigateScreen(ArrayList<String> pressedKeys) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		if (this.dialog.equals(WIN)) {
			if (releasedKeys.contains(this.playerManager.getPrimaryKey(this.numPlayer))) { 
				
			} else if (releasedKeys.contains(this.playerManager.getSecondaryKey(this.numPlayer))) {
				//exit
				this.gameManager.setActiveGameScene("hallway" + (this.gameManager.getNumLevel()-1)); //FIXME: incorporate "numLevel" to decide which to load
			} else if (releasedKeys.contains(this.playerManager.getUpKey(this.numPlayer))) {
				//buy movement speed
				if(this.playerManager.getAttrPoints(numPlayer) > 0) {
					this.playerManager.setSpeed(this.playerManager.getSpeed(numPlayer) + 1, numPlayer);
					this.playerManager.setAttrPoints(this.playerManager.getAttrPoints(numPlayer) - 1, numPlayer);
				}
			} else if (releasedKeys.contains(this.playerManager.getDownKey(this.numPlayer))) {
				//buy swing speed
				if(this.playerManager.getAttrPoints(numPlayer) > 0) {
					this.playerManager.setSwingSpeed(this.playerManager.getSwingSpeed(numPlayer) + 1, numPlayer);
					this.playerManager.setAttrPoints(this.playerManager.getAttrPoints(numPlayer) - 1, numPlayer);
					}
			} else if (releasedKeys.contains(this.playerManager.getRightKey(this.numPlayer))) {
				//buy health
				if(this.playerManager.getAttrPoints(numPlayer) > 0) {
					this.playerManager.setMaxHealth(this.playerManager.getMaxHealth(numPlayer) + 1, numPlayer);
					this.playerManager.setHealth(this.playerManager.getHealth(numPlayer) + 1, numPlayer);
					this.playerManager.setAttrPoints(this.playerManager.getAttrPoints(numPlayer) - 1, numPlayer);
				}	
			} else if (releasedKeys.contains(this.playerManager.getLeftKey(this.numPlayer))) {
				
			}
		} else {
			//lost level, press secondaryKey to replay
		}
		this.prevPressedKeys.clear();
		this.prevPressedKeys.addAll(pressedKeys);
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g); // draws children
		if (this.isVisible()) {
			Font f = new Font("Dialog", Font.BOLD, 20);
			g.setFont(f);
			g.drawString(this.dialog, 140, 100);
			f = new Font("Dialog", Font.PLAIN, 12);
			g.setFont(f);
			if(this.dialog.equals(WIN)) {
				g.drawString("Experience Points Earned: " +
						this.experience, 140, 120);
				g.drawString("Attribute Points Remaining: " +
						this.playerManager.getAttrPoints(this.numPlayer), 140, 139);
				g.drawString("Current Movement Speed: " +
						(int)this.playerManager.getSpeed(this.numPlayer), 140, 180);
				g.drawString("(Press " +
						this.playerManager.getUpKey(this.numPlayer).toString() +
						" to buy)", 340, 180);
				g.drawString("Current Swing Speed: " +
						(int)this.playerManager.getSwingSpeed(this.numPlayer), 140, 205);
				g.drawString("(Press " +
						this.playerManager.getDownKey(this.numPlayer).toString() +
						" to buy)", 340, 205);
				g.drawString("Current Max Health: " +
						(int)this.playerManager.getMaxHealth(this.numPlayer), 140, 230);
				g.drawString("(Press " +
						this.playerManager.getRightKey(this.numPlayer).toString() +
						" to buy)", 340, 230);
				g.drawString("(Press " +
						this.playerManager.getSecondaryKey(this.numPlayer).toString() +
						" to Continue)", 340, 255);
			} else {
				g.drawString("Close Game and Restart ", 140, 120);
				//FIXME... should be able to 
				//"Replay level" keep temporary stats in LevelManager, so we can easily reset them after a level
			}
		}
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys); // draws children
		if (this.isVisible()) {
			navigateScreen(pressedKeys);
		}
	}

	public String getDialog() {
		return dialog;
	}

	public void setDialog(String dialog) {
		this.dialog = dialog;
	}



	public int getExperience() {
		return experience;
	}



	public void setExperience(int experience) {
		this.experience = experience;
	}
}
