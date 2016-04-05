package edu.virginia.engine.display;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class FrameInfo {
	private BufferedImage image;
	private Rectangle hitbox;

	public FrameInfo(BufferedImage image, Rectangle hitbox) {
		this.image = image;
		this.hitbox = hitbox;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}

	@Override
	public String toString() {
		return "FrameInfo [image=" + image.toString() + ", hitbox=" + hitbox.toString() + "]";
	}

}
