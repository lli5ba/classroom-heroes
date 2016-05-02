package edu.virginia.game.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.display.ToastSprite;
import edu.virginia.engine.util.Position;
import edu.virginia.game.managers.PlayerManager;

public class Store extends DisplayObjectContainer {

	private static final String GINGER_ALE_ID = "Ginger Ale";
	private static final String CHEESE_PUFFS_ID = "Cheese Puffs";
	private ItemDetail cheesePuffsDetail;
	private ItemDetail gingerAleDetail;
	private Sprite highlight;
	private String currentHighlight;
	private ItemDetail itemToBuy;
	private PlayerManager playerManager = PlayerManager.getInstance();
	private int numPlayer;
	private ArrayList<String> prevPressedKeys = new ArrayList<String>();
	private Sprite antidote;
	private Sprite cheese;
	private Sprite VPicon;
	private Sprite previewIcon;
	private ToastSprite insuffFundsDialog;
	private NavButtonIcon contButton;

	public Store(String id, String styleCode, int numPlayerBuying) {
		super(id, "store/store-background-" + styleCode + ".png"); // store
																	// background
		this.highlight = new Sprite(id + "-highlight", "store/insert-cancel-highlight.png");
		this.highlight.setVisible(false);
		cheesePuffsDetail = new ItemDetail(CHEESE_PUFFS_ID, "store/cheese-puffs", 
				"These are da bomb! Throw them to\nkeep areas safe from projectiles!", 2);
		gingerAleDetail = new ItemDetail(GINGER_ALE_ID, "store/ginger-ale", "Cure your classmates!", 3);
		Sprite glassSheen = new Sprite("glassSheen", "store/glass-sheen.png");
		this.addChild(highlight);
		this.addChild(cheesePuffsDetail);
		this.addChild(gingerAleDetail);
		this.addChild(glassSheen);
		glassSheen.setPosition(this.getWidth() * .20, this.getHeight() * .218); // position
																				// of
																				// top-left
																				// corner
																				// of
																				// inner
																				// vending
																				// machine
		this.gingerAleDetail.setPosition(this.getWidth() * .15, this.getHeight() * .23);
		this.cheesePuffsDetail.setPosition(this.getWidth() * .15,
				this.getHeight() * .23 + this.gingerAleDetail.getHeight() * 1.05);
		
		
		this.numPlayer = numPlayerBuying;
		/* continue button */
		this.contButton = new NavButtonIcon(NavButtonIcon.BACK, 
				true, this.playerManager.getSecondaryKey(numPlayerBuying));
		this.contButton.setScaleY(.5);
		this.contButton.setScaleX(.5);
		this.addChild(contButton);
		this.contButton.setPosition(this.getWidth() * .105, this.getHeight() * .159);
		
		
		/* preview icon */
		this.previewIcon = new Sprite("preview", "statbox/soda-icon.png");
		this.addChild(previewIcon);
		this.previewIcon.setPosition(this.getWidth() * .7938, this.getHeight() * .43);
		
		/* initialize */
		this.insuffFundsCheck(); //grey out items can't afford and initialize highlight accordingly
		this.initializePuchase();
		/* icons */
		this.antidote = new Sprite("antidote", "statbox/soda-icon.png");
		this.antidote.setScaleX(.8);
		this.antidote.setScaleY(.8);
		this.addChild(antidote);
		this.antidote.setPosition(450, 70);

		this.cheese = new Sprite("cheese", "statbox/bomb-icon.png");
		this.cheese.setScaleX(.8);
		this.cheese.setScaleY(.8);
		this.addChild(cheese);
		this.cheese.setPosition(485, 70);

		this.VPicon = new Sprite("vp-icon1", "statbox/vp-icon.png");
		this.VPicon.setScaleX(.8);
		this.VPicon.setScaleY(.8);
		this.addChild(VPicon);
		this.VPicon.setPosition(415, 70);

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

	public void highlightCancel() {
		this.currentHighlight = "cancel";
		this.highlight.setVisible(true);
		this.highlight.setPosition(new Position(this.getWidth() * .8125, this.getHeight() * .507));
	}

	public void highlightInsert() {
		this.currentHighlight = "insert";
		this.highlight.setVisible(true);
		this.highlight.setPosition(new Position(this.getWidth() * .7238, this.getHeight() * .507));
	}

	public void highlightCheesePuffs() {
		this.currentHighlight = CHEESE_PUFFS_ID;
		this.cheesePuffsDetail.setHighlighted(true);
		this.gingerAleDetail.setHighlighted(false);
		this.highlight.setVisible(false);
	}

	public void highlightGingerAle() {
		this.currentHighlight = GINGER_ALE_ID;
		this.cheesePuffsDetail.setHighlighted(false);
		this.gingerAleDetail.setHighlighted(true);
		this.highlight.setVisible(false);
	}

	public void initializePuchase() { // navigate back to items
		this.previewIcon.setVisible(false);
		if(this.gingerAleDetail.isGrey() && this.cheesePuffsDetail.isGrey()) {
			this.cheesePuffsDetail.setHighlighted(false);
			this.gingerAleDetail.setHighlighted(false);
			this.highlightCancel(); // on cancel because player cannot buy anything
			this.currentHighlight = "cancel";
		} else if (this.gingerAleDetail.isGrey() && !this.cheesePuffsDetail.isGrey()) {
			//start on cheesePuffs
			this.cheesePuffsDetail.setHighlighted(true);
			this.gingerAleDetail.setHighlighted(false);
			this.highlightCheesePuffs(); // on cancel because player cannot buy anything
			this.currentHighlight = CHEESE_PUFFS_ID;
		} else if (this.gingerAleDetail.isGrey() && !this.cheesePuffsDetail.isGrey()) {
			//start on gingerAle
			this.cheesePuffsDetail.setHighlighted(false);
			this.gingerAleDetail.setHighlighted(true);
			this.highlightGingerAle(); // on cancel because player cannot buy anything
			this.currentHighlight = GINGER_ALE_ID;
		} else {
			//player free to buy anything
			this.highlightGingerAle(); // highlight the first item
			this.currentHighlight = GINGER_ALE_ID;
		}
	}

	public void startBuy() { // navigate to buy sequence
		switch (this.currentHighlight) { // switch statements b/c easy to add
											// new items
		case GINGER_ALE_ID:
			this.itemToBuy = this.gingerAleDetail;
			this.previewIcon.setImage("statbox/soda-icon.png");
			this.previewIcon.setVisible(true);
			break;
		case CHEESE_PUFFS_ID:
			this.itemToBuy = this.cheesePuffsDetail;
			this.previewIcon.setImage("statbox/bomb-icon.png");
			this.previewIcon.setVisible(true);
			break;
		default:
			System.out.println("Error in Store startBuy(). Should not happen.");
			break;
		}
		this.cheesePuffsDetail.setHighlighted(false);
		this.gingerAleDetail.setHighlighted(false);
		this.highlightCancel(); // start on cancel, so player doesn't
								// accidentally buy
		this.currentHighlight = "cancel";
	}

	
	public boolean outOfFunds() {
		//return whether player cannot buy anything
		return (this.playerManager.getVpCount() < this.gingerAleDetail.getCost()) &&
				(this.playerManager.getVpCount() < this.cheesePuffsDetail.getCost());
	}
	//grey items if not enough money
	public void insuffFundsCheck() {
		if (this.playerManager.getVpCount() < this.gingerAleDetail.getCost()) {
			this.gingerAleDetail.setGrey(true);
		} 
		if (this.playerManager.getVpCount() < this.cheesePuffsDetail.getCost()) {
			this.cheesePuffsDetail.setGrey(true);
		}
		
	}
	public void buyItem(ItemDetail item) {
		// TODO: Leandra
		// check whether enough VP, if so, decrement VP, Increase Inventory, and
		// stopBuy()
		if (this.playerManager.getVpCount() >= item.getCost() ) {
			switch (item.getId()) { // switch statements b/c easy to add new items
			case GINGER_ALE_ID:
				this.itemToBuy = this.gingerAleDetail;
				this.playerManager.setNumGingerAle(this.playerManager.getNumGingerAle() + 1);
				break;
			case CHEESE_PUFFS_ID:
				this.itemToBuy = this.cheesePuffsDetail;
				this.playerManager.setNumCheesePuffs(this.playerManager.getNumCheesePuffs() + 1);
				break;
			default:
				System.out.println("Error in Store startBuy(). Should not happen.");
				break;
			}
			//System.out.println("You bought " + item.getId() + " for " + item.getCost() + " VP");
			this.playerManager.setVpCount(this.playerManager.getVpCount()-this.itemToBuy.getCost());
			this.insuffFundsCheck();
			if (this.outOfFunds()) {
				this.highlightCancel();
				this.previewIcon.setVisible(false);
			}
		} else {
			//insufficient funds
			this.initializePuchase();
		}

	}

	public void navigateStore(ArrayList<String> pressedKeys) {
		ArrayList<String> releasedKeys = new ArrayList<String>(this.prevPressedKeys);
		releasedKeys.removeAll(pressedKeys);
		// System.out.println("Prev: " +prevPressedKeys.toString() + " Curr: " +
		// pressedKeys.toString()
		// + " Rela: " + releasedKeys.toString());
		if (releasedKeys.contains(this.playerManager.getPrimaryKey(this.numPlayer))) { // the
																						// player
																						// is
																						// selecting
																						// an
																						// action
			//System.out.println("pressed space! current highlight: " + this.currentHighlight);

			switch (this.currentHighlight) {
			case "insert":
				this.buyItem(this.itemToBuy);
				break;
			case "cancel":
				this.initializePuchase();
				break;
			default: // An item is selected
				this.startBuy();
				break;
			}
		} else if (releasedKeys.contains(this.playerManager.getSecondaryKey(this.numPlayer))) {

		} else if (releasedKeys.contains(this.playerManager.getUpKey(this.numPlayer))) {
			//System.out.println("pressed up! current highlight: " + this.currentHighlight);

			switch (this.currentHighlight) {
			case CHEESE_PUFFS_ID:
				if (!this.gingerAleDetail.isGrey()) {
					this.highlightGingerAle();
				}
				break;
			default: // do nothing if anything else is highlighted
				break;
			}
		} else if (releasedKeys.contains(this.playerManager.getDownKey(this.numPlayer))) {
			//System.out.println("pressed down! current highlight: " + this.currentHighlight);
			switch (this.currentHighlight) {
			case GINGER_ALE_ID:
				if(!this.cheesePuffsDetail.isGrey()) {
					this.highlightCheesePuffs();
				}
				break;
			default: // do nothing if anything else
				break;
			}
		} else if (releasedKeys.contains(this.playerManager.getRightKey(this.numPlayer))) {
			//System.out.println("pressed right! current highlight: " + this.currentHighlight);
			switch (this.currentHighlight) {
			case "insert":
				this.highlightCancel();
				break;
			default: // do nothing if anything else
				break;
			}
		} else if (releasedKeys.contains(this.playerManager.getLeftKey(this.numPlayer))) {
			//System.out.println("pressed left! current highlight: " + this.currentHighlight);
			switch (this.currentHighlight) {
			case "cancel":
				//only move to insert if not out of funds
				if(this.outOfFunds()) {
					//show dialog for 2 seconds
					//this.insuffFundsDialog.play(2);
				} else {
					this.highlightInsert();
				}
				break;
			default: // do nothing if anything else
				break;
			}
		}
		this.prevPressedKeys.clear();
		this.prevPressedKeys.addAll(pressedKeys);
	}

	
	
	@Override
	public void draw(Graphics g) {
		super.draw(g); // draws children
		if (this.isVisible()) {
			Font f = new Font("Dialog", Font.PLAIN, 8);
			g.setFont(f);
			g.setColor(Color.WHITE);
			g.drawString(": " + this.playerManager.getVpCount(), 
					(int)this.VPicon.getxPos() + 15, (int)this.VPicon.getyPos() + 10);
			g.drawString(": " + this.playerManager.getNumGingerAle(), 
					(int)this.antidote.getxPos() + 15, (int)this.antidote.getyPos() + 10);
			g.drawString(": " + this.playerManager.getNumCheesePuffs(), 
					(int)this.cheese.getxPos() + 15, (int)this.cheese.getyPos() + 10);
			g.setColor(Color.BLACK);
		}
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys); // draws children
		if (this.isVisible()) {
			navigateStore(pressedKeys);
		}
	}
}
