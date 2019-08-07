package com.q51.gui; 

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;


@SuppressWarnings("serial")
public class HighlightTile extends JLabel {
	
	private Graphics2D g2d;
	private JLabel label;

	public HighlightTile(JLabel label, Rectangle rect, BufferedImage matrixCell, Boolean highlight, SymbolPanel symbolPanel, String keyPrefix) {
		
		this.label = label;
		Graphics g = label.getGraphics();
        g2d = (Graphics2D) g.create();
        
        Rectangle drawRectangle = new Rectangle(0, 0, matrixCell.getWidth(), matrixCell.getHeight());
        
        g2d.drawImage(matrixCell, drawRectangle.x, drawRectangle.y, this);

		if (rect != null && highlight) {

			//System.err.println("keyPrefix = " + keyPrefix + "\t" + "row=" + this.getCellRowIndex(keyPrefix) + ", col=" + this.getCellColIndex(keyPrefix));
			// Draw the symbol
			String  symbolName   = symbolPanel.getName();                 // Name of the currently selected symbol
			Image[] symbolImages = symbolPanel.getSelectedImages();       // Images of the "Selected symbols" 
			@SuppressWarnings("deprecation")
			Image   symbolImage  = symbolImages[new Integer(symbolName)]; // The image of the currently selected symbol
			
			int x = (int)rect.getX();
			int y = (int)rect.getY();
			Point p = new Point(x/Constants.TILE_SIDE_LENGTH,y/Constants.TILE_SIDE_LENGTH);
			Boolean pad = false;
			Point[] q = Constants.getPadLocations();
			
			int rowCellIndex = this.getCellRowIndex(keyPrefix);
			int colCellIndex = this.getCellColIndex(keyPrefix);
			
			if ( !(rowCellIndex == 0 | rowCellIndex == Constants.BLOCK_ROW_COUNT - 1) && !(colCellIndex == 0 | colCellIndex == Constants.BLOCK_COL_COUNT - 1)) {
				
				for (int i=0; i<q.length; i++) {
					if (p.equals(q[i])) {
						pad = true;
						break;
					}
				}
			}
			
			g2d.drawImage(symbolImage, x, y, null); //Highlight the currently selected symbol onto the matrix_cell
			
			// Highlight the cell
			g2d.setColor(new Color(0,0,255, 64));
			g2d.fillRect(x, y, (int)rect.getWidth(), (int)rect.getHeight());
			
			if (pad) {
				try {
					symbolImage = ImageIO.read(new File(Constants.IMAGE_DIRECTORY_PREFIX  + "noentry" + ".png"));
					g2d.drawImage(symbolImage, (int)p.getX()*Constants.TILE_SIDE_LENGTH, (int)p.getY()*Constants.TILE_SIDE_LENGTH, null);
				} catch (IOException e) {
					System.err.println("Error: Icon image not found: " + e.getMessage() + ": " + Constants.IMAGE_DIRECTORY_PREFIX  + "noentry" + ".png");
					System.exit(0);
				}
			} else {
				
				if (symbolName != null && symbolName != "0") {
					if (symbolImage != null) {
						g2d.drawImage(symbolImage, (int)rect.getY()*Constants.TILE_SIDE_LENGTH, (int)rect.getX()*Constants.TILE_SIDE_LENGTH, null);
					}
				}
			}
		}

		g2d.dispose();
	}


	
	public JLabel getLabel() {
		return label;
	}
	
	public Graphics2D getGraphics() {
		return g2d;
	}
	
	private int getCellRowIndex(String keyPrefix) {
		return Integer.parseInt(keyPrefix.substring(5, 7)); // index of cell row 	
	}
	
	private int getCellColIndex(String keyPrefix) {
		return Integer.parseInt(keyPrefix.substring(7, 9)); // index of cell column	
	}
}
