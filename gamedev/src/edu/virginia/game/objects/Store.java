package edu.virginia.game.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
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

	public Store(String id, String styleCode, int numPlayerBuying) {
		super(id, "store/store-background-" + styleCode + ".png"); // store
																	// background
		this.highlight = new Sprite(id + "-highlight", "store/insert-cancel-highlight.png");
		this.highlight.setVisible(false);
		cheesePuffsDetail = new ItemDetail(CHEESE_PUFFS_ID, "store/cheese-puffs.png", "These are da bomb!", 2);
		gingerAleDetail = new ItemDetail(GINGER_ALE_ID, "store/ginger-ale.png", "Cure your classmates!", 2);
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
		this.highlightGingerAle(); // highlight the first item
		this.currentHighlight = GINGER_ALE_ID;
		this.numPlayer = numPlayerBuying;

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
	}

	public void highlightGingerAle() {
		this.currentHighlight = GINGER_ALE_ID;
		this.cheesePuffsDetail.setHighlighted(false);
		this.gingerAleDetail.setHighlighted(true);
	}

	public void stopBuy() { // navigate back to items
		this.highlightGingerAle();
		this.highlight.setVisible(false);
		this.currentHighlight = GINGER_ALE_ID;
	}

	public void startBuy() { // navigate to buy sequence
		switch (this.currentHighlight) { // switch statements b/c easy to add
											// new items
		case GINGER_ALE_ID:
			this.itemToBuy = this.gingerAleDetail;
			break;
		case CHEESE_PUFFS_ID:
			this.itemToBuy = this.cheesePuffsDetail;
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

	public void buyItem(ItemDetail item) {
		// TODO: Leandra
		// check whether enough VP, if so, decrement VP, Increase Inventory, and
		// stopBuy()
		// Only buy if enough VP
		if (this.playerManager.getVpCount() >= this.playerManager.getVpCount()-item.getCost()) {
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
			System.out.println("You bought " + item.getId() + " for " + item.getCost() + " VP");
			this.playerManager.setVpCount(this.playerManager.getVpCount()-this.itemToBuy.getCost());
			this.stopBuy();
		} else {
			//insufficient funds
			this.stopBuy();
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
			System.out.println("pressed space! current highlight: " + this.currentHighlight);

			switch (this.currentHighlight) {
			case "insert":
				this.buyItem(this.itemToBuy);
				break;
			case "cancel":
				this.stopBuy();
				break;
			default: // An item is selected
				this.startBuy();
				break;
			}
		} else if (releasedKeys.contains(this.playerManager.getSecondaryKey(this.numPlayer))) {

		} else if (releasedKeys.contains(this.playerManager.getUpKey(this.numPlayer))) {
			System.out.println("pressed up! current highlight: " + this.currentHighlight);

			switch (this.currentHighlight) {
			case CHEESE_PUFFS_ID:
				this.highlightGingerAle();
				break;
			default: // do nothing if anything else is highlighted
				break;
			}
		} else if (releasedKeys.contains(this.playerManager.getDownKey(this.numPlayer))) {
			System.out.println("pressed down! current highlight: " + this.currentHighlight);
			switch (this.currentHighlight) {
			case GINGER_ALE_ID:
				this.highlightCheesePuffs();
				break;
			default: // do nothing if anything else
				break;
			}
		} else if (releasedKeys.contains(this.playerManager.getRightKey(this.numPlayer))) {
			System.out.println("pressed right! current highlight: " + this.currentHighlight);
			switch (this.currentHighlight) {
			case "insert":
				this.highlightCancel();
				break;
			default: // do nothing if anything else
				break;
			}
		} else if (releasedKeys.contains(this.playerManager.getLeftKey(this.numPlayer))) {
			System.out.println("pressed left! current highlight: " + this.currentHighlight);
			switch (this.currentHighlight) {
			case "cancel":
				this.highlightInsert();
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
			g.drawString("VP Remaining: " + this.playerManager.getVpCount(), 415, 60);
			g.drawString("Ginger Ale: " + this.playerManager.getNumGingerAle(), 415, 70);
			g.drawString("Da Bombs: " + this.playerManager.getNumCheesePuffs(), 415, 80);
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
