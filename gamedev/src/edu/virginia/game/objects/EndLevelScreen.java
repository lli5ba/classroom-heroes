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

	public static final String WIN = "You survived this round! (:";
	public static final String LOSE_NO_HEALTH = "Out of health! You lose :( ";
	public static final String LOSE_STUDENTS = "All your friends perished! You lose :( ";
	public static final String WIN_GAME = "You graduated! (:";
	private Sprite highlight;
	private String currentHighlight;
	private String dialog;
	private PlayerManager playerManager = PlayerManager.getInstance();
	private LevelManager levelManager = LevelManager.getInstance();
	private GameManager gameManager = GameManager.getInstance();
	private int numPlayer;
	private int experience1;
	private int experience2;
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();
	private Attributes attributes;
	private NavButtonIcon contButton;

	public EndLevelScreen(String id) {
		super(id, "end-level/notebook.png"); // notebook
		this.highlight = new Sprite(id + "-highlight", "store/insert-cancel-highlight.png");
		this.setExperience(0);
		this.setDialog(WIN);
		this.numPlayer = 1;
		
		/* attributes module */
		this.attributes = new Attributes("attributes", numPlayer);
		this.addChild(attributes);
		this.attributes.setPosition(this.getWidth() * 0.25, this.getHeight() * 0.4);
		this.attributes.setVisible(false);
		/* continue button */
		this.contButton = new NavButtonIcon(NavButtonIcon.CONTINUE, 
				true, this.playerManager.getSecondaryKey(this.numPlayer));
		//this.contButton.setScaleY(.5);
		//this.contButton.setScaleX(.5);
		this.addChild(contButton);
		this.contButton.setPosition(this.getWidth() * .75, this.getHeight() * .77);
	}

	
	
	public int getNumPlayer() {
		return numPlayer;
	}

	public void setNumPlayer(int numPlayer) {
		this.numPlayer = numPlayer;
		this.attributes.setNumPlayer(numPlayer);
		this.contButton.setButtonId(this.playerManager.getSecondaryKey(numPlayer));
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
				if (this.numPlayer == 1 && this.gameManager.getNumPlayers() == 2) {
					this.setNumPlayer(2);
				}
				else if (this.numPlayer == 2 || 
						(this.numPlayer == 1 && this.gameManager.getNumPlayers() == 1) ) {
					this.gameManager.setActiveGameScene("hallway" + (this.gameManager.getNumLevel()-2)); //FIXME: incorporate "numLevel" to decide which to load
				
				}
			} else if (releasedKeys.contains(this.playerManager.getUpKey(this.numPlayer))) {
				
			} else if (releasedKeys.contains(this.playerManager.getDownKey(this.numPlayer))) {
				
			} else if (releasedKeys.contains(this.playerManager.getRightKey(this.numPlayer))) {
				
			} else if (releasedKeys.contains(this.playerManager.getLeftKey(this.numPlayer))) {
				
			}
		} else if(this.dialog.equals(LOSE_STUDENTS) || this.dialog.equals(LOSE_NO_HEALTH) || this.dialog.equals(WIN_GAME)) {
			//lost level, press secondaryKey to replay
			if (releasedKeys.contains(this.playerManager.getSecondaryKey(this.numPlayer))) { 
				if (this.numPlayer == 1 && this.gameManager.getNumPlayers() == 2) {
					this.setNumPlayer(2);
				}
				else if (this.numPlayer == 2 || 
						(this.numPlayer == 1 && this.gameManager.getNumPlayers() == 1) ) {
					this.gameManager.setActiveGameScene("title");
					this.gameManager.restartGame();
				}
				
			}
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
			g.drawString(this.dialog, 140, 90);
			f = new Font("Dialog", Font.PLAIN, 12);
			g.setFont(f);
			if(this.dialog.equals(WIN)) {
				if (this.numPlayer == 1) {
					g.drawString("Player 1", 140, 110);
					g.drawString("Experience Points Earned: " +
							this.experience1, 140, 125);
				} else {
					g.drawString("Player 2", 140, 110);
					g.drawString("Experience Points Earned: " +
							this.experience2, 140, 125);
				}
				g.drawString("Attribute Points Remaining: " +
						this.playerManager.getAttrPoints(this.numPlayer), 140, 139);
				/*g.drawString("Current Movement Speed: " +
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
						" to Continue)", 340, 255); */
			} else if(this.dialog.equals(WIN_GAME)){
				if (this.numPlayer == 1) {
					g.drawString("Player 1", 140, 110);
					g.drawString("Total Experience Points Earned: " +
							this.experience1, 140, 125);
				} else {
					g.drawString("Player 2", 140, 110);
					g.drawString("Total Experience Points Earned: " +
							this.experience2, 140, 125);
				}
				g.drawString("Your Final Grade: " +
						this.playerManager.getGrade(numPlayer), 140, 139);
			} else { //lose
				if (this.numPlayer == 1) {
					g.drawString("Player 1", 140, 110);
					g.drawString("Total Experience Points Earned: " +
							this.experience1, 140, 125);
				} else {
					g.drawString("Player 2", 140, 110);
					g.drawString("Total Experience Points Earned: " +
							this.experience2, 140, 125);
				}
				g.drawString("Your Final Grade: " +
						this.playerManager.getGrade(numPlayer), 140, 139);
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
		if(this.dialog.equals(WIN)) {
			if(this.attributes != null) {
				this.attributes.setVisible(true);
				this.attributes.setDrawChildren(true);
			}
		} else {
			if(this.attributes != null) {
				this.attributes.setVisible(false);
				this.attributes.setDrawChildren(false);
			}
		}
	}



	public int getExperience() {
		return experience1;
	}


	public void setExperience(int experience) {
		this.experience1 = experience;
	}
	
	public void setExperience(int exp1, int exp2) {
		this.experience1 = exp1;
		this.experience2 = exp2;
	}
}
