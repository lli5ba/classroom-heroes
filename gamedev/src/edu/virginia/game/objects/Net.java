package edu.virginia.game.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.virginia.engine.display.FrameInfo;
import edu.virginia.engine.display.Sprite;

public class Net extends Sprite{
	private Rectangle netHitbox;
	private HashMap<String, ArrayList<FrameInfo>> netHitboxMap;
	
	
	public Net(String id, String specsFileName) {
		super(id);
		netHitboxMap = new HashMap<String, ArrayList<FrameInfo>>();
		loadNetHitbox(specsFileName);
		setNetHitbox(new Rectangle(0,0,0,0));
	}

	public Rectangle getNetHitbox() {
		return netHitbox;
	}

	public void setNetHitbox(Rectangle netHitbox) {
		this.netHitbox = netHitbox;
	}

	public HashMap<String, ArrayList<FrameInfo>> getNetHitboxMap() {
		return netHitboxMap;
	}

	public void setNetHitboxMap(HashMap<String, ArrayList<FrameInfo>> netHitboxMap) {
		this.netHitboxMap = netHitboxMap;
	}
	
	
	
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		/*if(this.getNetHitbox() != null) {
			g.fillRect(this.getNetHitbox().x, this.getNetHitbox().y, this.getNetHitbox().width, this.getNetHitbox().height);
		}*/
	}
	
	
	
	public void loadNetHitbox(String txt_filename) {

		try {
			FileInputStream fstream = new FileInputStream(txt_filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {

				String[] tokens = strLine.split(" ");
				String name = tokens[0];
				int num_in_seq = Integer.parseInt(tokens[1]);
				int xPos = Integer.parseInt(tokens[3]);
				int yPos = Integer.parseInt(tokens[4]);
				int xWidth = Integer.parseInt(tokens[5]);
				int yHeight = Integer.parseInt(tokens[6]);
				int xPosHitbox = 0;
				int yPosHitbox = 0;
				int xWidthHitbox = xWidth;
				int yHeightHitbox = yHeight;
				if (tokens.length == 11) {
					xPosHitbox = Integer.parseInt(tokens[7]);
					yPosHitbox = Integer.parseInt(tokens[8]);
					xWidthHitbox = Integer.parseInt(tokens[9]);
					yHeightHitbox = Integer.parseInt(tokens[10]);
				}
				if (netHitboxMap.containsKey(name)) {
					ArrayList<FrameInfo> spriteArray = netHitboxMap.get(name);
					spriteArray.add(new FrameInfo(null,
							new Rectangle(xPosHitbox, yPosHitbox, xWidthHitbox, yHeightHitbox)));
					netHitboxMap.put(name, spriteArray);
				} else {
					ArrayList<FrameInfo> spriteArray = new ArrayList<FrameInfo>();
					spriteArray.add(new FrameInfo(null,
							new Rectangle(xPosHitbox, yPosHitbox, xWidthHitbox, yHeightHitbox)));
					netHitboxMap.put(name, spriteArray);

				}

			}
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

	}

}
