package com.q51;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

class SchematicConnection {
	

	private Set<Point> hasConnection; // a set of points

	
	private BufferedImage schematic = null;
	private Graphics2D g2d;
	private ArrayList<Location> al;

	public enum ComponentEnd {
		RCST, RCSB, RL1B, RL2T, RL2B, TR1B, TR2B, TR3B, TR4B, TR5ES, XU1B, XU1T, XU2B, XU2T, XU3B, XU3T, GRND, TR12ES, TR34ES, TR23ES
	}
	
	public SchematicConnection(BufferedImage schematic) {
		if (schematic == null) {
			System.err.println("Schematic is null!");
			System.exit(-1);
		}
		this.schematic = schematic;

		al = new ArrayList<Location>();
		
		addConnection(ComponentEnd.RCST);
		addConnection(ComponentEnd.RCSB);
		addConnection(ComponentEnd.RL1B);
		addConnection(ComponentEnd.RL2T);
		addConnection(ComponentEnd.RL2B);
		addConnection(ComponentEnd.TR1B);
		addConnection(ComponentEnd.TR2B);
		addConnection(ComponentEnd.TR3B);
		addConnection(ComponentEnd.TR4B);
		addConnection(ComponentEnd.TR5ES);
		addConnection(ComponentEnd.GRND);
		addConnection(ComponentEnd.XU1B);
		addConnection(ComponentEnd.XU1T);
		addConnection(ComponentEnd.XU2B);
		addConnection(ComponentEnd.XU2T);
		addConnection(ComponentEnd.XU3B);
		addConnection(ComponentEnd.XU3T);

		this.setDefaultConnections(al,16);
		
	}
	
	private void setDefaultConnections(ArrayList<Location> al, int alpha) {
		// Draw red border round the schematic image
		g2d = schematic.createGraphics();
		g2d.setColor(Color.RED);
		g2d.drawRect(0, 0, schematic.getWidth() - 1, schematic.getHeight() - 1);
		

		// Draw some faint rectangles at some areas of connections
		g2d.setColor(new Color(255, 0, 0, alpha));
		
		Iterator<Location> itr = al.iterator(); 
		
		//traverse elements of ArrayList object to add the rectangles
        while(itr.hasNext()){  
            Location l = (Location)itr.next();  
            g2d.fillRect(l.y,l.x,l.w,l.h);
        }
		
        // Annotate the schematicg2d.setPaint(Color.red);
        g2d.setColor(new Color(0, 0, 0, 127));

        
        AttributedString text = new AttributedString("RL1");
        text.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        text.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 2, 3);
        g2d.drawString(text.getIterator(), 240, 100);
        
        text = new AttributedString("RL2");
        text.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        text.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 2, 3);
        g2d.drawString(text.getIterator(), 100, 100);
        
        text = new AttributedString("Xu1");
        text.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        text.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 2, 3);
        g2d.drawString(text.getIterator(), 72, 140);
        
        text = new AttributedString("Xu2");
        text.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        text.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 2, 3);
        g2d.drawString(text.getIterator(), 35, 140);
        
        text = new AttributedString("Xu3");
        text.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        text.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 2, 3);
        g2d.drawString(text.getIterator(), 0, 140);
        
        text = new AttributedString("Vs2");
        text.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        text.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 2, 3);
        g2d.drawString(text.getIterator(), 125, 12);
        
        text = new AttributedString("Vs1");
        text.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        text.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 2, 3);
        g2d.drawString(text.getIterator(), 362, 12);
        
        text = new AttributedString("TR1");
        text.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        text.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 2, 3);
        g2d.drawString(text.getIterator(), 24, 190);
        
        text = new AttributedString("TR2");
        text.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        text.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 2, 3);
        g2d.drawString(text.getIterator(), 90, 190);
        
        text = new AttributedString("TR3");
        text.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        text.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 2, 3);
        g2d.drawString(text.getIterator(), 164, 190);
        
        text = new AttributedString("TR4");
        text.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        text.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 2, 3);
        g2d.drawString(text.getIterator(), 232, 190);
        
        text = new AttributedString("TR5");
        text.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        text.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 2, 3);
        g2d.drawString(text.getIterator(), 341, 340);
        
        text = new AttributedString("Rcs");
        text.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        g2d.drawString(text.getIterator(), 338, 100);
        
		g2d.dispose();
	}
	
	public void addConnection(Point p) {
		if (!hasConnection.contains(p)) {
			hasConnection.add(p);
		}
	}
	
	public void removeConnection(Point p) {
		if (hasConnection.contains(p)) {
			hasConnection.remove(p);
		}
	}
	
	
	public void addConnection(ComponentEnd c) {
		switch (c) {
		case RCST : al.add(new Location(366,  30,  12, 22)); // RCS Top
					break;
		case RL2B : al.add(new Location(128, 128,  12, 35)); // RL Left Bottom
					break;
		case RL1B :	al.add(new Location(269, 128,  12, 35)); // RL Right Bottom
					break;
		case RCSB :	al.add(new Location(366, 128,  12, 35)); // RCS Bottom
					break;
		case RL2T : al.add(new Location(128,  30,  12, 22)); // RL Left Top
					break;
		case GRND : al.add(new Location(322, 345,  12, 24)); // GND
					break;
		case TR5ES	: al.add(new Location(292, 260,  18, 22)); // Connect TR5 emitters together
					break;
		case XU1B:	al.add(new Location( 73, 100,  18, 22)); // There is a connection to the bottom of the Xu (Right)
					break;
		case XU2B:	al.add(new Location( 36, 100,  18, 22)); // There is a connection to the bottom of the Xu (Middle)
					break;
		case XU3B:	al.add(new Location(  0, 100,  18, 22)); // There is a connection to the bottom of the Xu (Left)
					break;
		case XU1T:	al.add(new Location( 73,  13,  18, 22)); // There is a connection to the Top of the Xu (Right)
					break;
		case XU2T:	al.add(new Location( 36,  13,  18, 22)); // There is a connection to the Top of the Xu (Middle)
					break;
		case XU3T:	al.add(new Location(  0,  13,  18, 22)); // There is a connection to the Top of the Xu (Left)
					break;
		case TR1B:  al.add(new Location( 18, 206,   7,  6)); // TR1 Base
					break;
		case TR2B:  al.add(new Location( 84, 206,   7,  6)); // TR2 Base
					break;
		case TR3B:  al.add(new Location(159, 206,   7,  6)); // TR3 Base
					break;
		case TR4B:  al.add(new Location(225, 206,   7,  6)); // TR4 Base
					break;
		case TR12ES:al.add(new Location( 64, 252,  72,  3)); // Connection between TR1E and TR2E
					break;
		case TR34ES:al.add(new Location(206, 252,  72,  3)); // Connection between TR3E and TR4E
					break;
		case TR23ES: al.add(new Location(132, 252,  75,  3)); // Connection between TR2E and TR3E
					break;
		default:	System.err.println("Error: Connection is unknown: " + c.toString());
					System.exit(-1);
					
		}
	}
}

class Location {
	public int y;
	public int x;
	public int w;
	public int h;
	
	Location(int topLeftCornerY,int topLeftCornerX, int width, int height) {
		this.x = topLeftCornerX;
		this.y = topLeftCornerY;
		this.w = width;
		this.h = height;
	}
}
