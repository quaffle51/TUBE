package com.q51.gui; 

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ImagePixels {
	
	static int[][] convertTo2D(BufferedImage image) {
		int[][] pixels = new int[Constants.MATRIX_CELL_WIDTH][Constants.MATRIX_CELL_HEIGHT];

		for( int row = 0; row < Constants.MATRIX_CELL_HEIGHT; row++ ) {
		    for( int col = 0; col < Constants.MATRIX_CELL_WIDTH; col++ ) {
		        pixels[row][col] = image.getRGB( col, row );
		    }
		}
		return pixels;
	}
	
	
	static BufferedImage restorePixels(BufferedImage matrixCell, int[][] pixels, Rectangle rect) {		
		if (pixels == null) {
			System.err.println("ImagePixels: pixels are null! Cannot restore pixels.");
			System.exit(-1);
		}
		
	    int matrixCellWidth;
	    int matrixCellHeight;

	    matrixCellWidth  = matrixCell.getWidth();
	    matrixCellHeight = matrixCell.getHeight();

	    for (int row = 0; row < matrixCellHeight; row++) {
	        for (int col = 0; col < matrixCellWidth; col++) {
	        	if (rect != null & rect.contains(col, row)) {
	                int rgb = pixels[row][col];
	                matrixCell.setRGB(col, row, rgb);
	        	}    
	        }
	    }
	    
	    return matrixCell;
	}
}
