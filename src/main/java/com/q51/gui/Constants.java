package com.q51.gui;

import java.awt.Color;
import java.awt.Point;

public class Constants {

	public static final int PANE_WIDTH = 1400;
	public static final int PANE_HEIGHT = 514;
	public static final int MATRIX_CELL_WIDTH = 390;
	public static final int MATRIX_CELL_HEIGHT = 390;
	public static final String DEFAULT_BLOCK_ROW = "0";
	public static final String DEFAULT_BLOCK_COL = "0";
	public static final String DEFAULT_CELL_ROW = "0";
	public static final String DEFAULT_CELL_COL = "0";
	public static final String SELECTED = "red";
	public static final String NOT_SELECTED = "blue";
	public static final String IMAGE_DIRECTORY_PREFIX = "src/main/resources/Images/";
	public static final String BLOCK_DIRECTORY_PREFIX = "src/main/resources/block_";
	public static final String JSON_FILENAME = "src/main/resources/json/ula.json";

	public static final int NUMBER_OF_TILES = 15;
	public static final int TILE_SIDE_LENGTH = 26;
	public static final int LEFT = 0;
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;

	public static final int[] PAD_ROWS = { 1, 1, 1, 1, 3, 3, 4, 4, 4, 4, 4, 6, 7, 7, 7, 7, 8, 8, 8, 8, 8, 9, 9, 9, 9,
			10, 10, 10, 10, 10, 11, 11, 11, 11, 12, 13, 13, 13, 13 };
	public static final int[] PAD_COLUMNS = { 4, 6, 8, 12, 10, 12, 1, 4, 5, 7, 8, 1, 4, 7, 9, 12, 1, 4, 7, 9, 12, 7, 8,
			9, 12, 1, 4, 7, 8, 9, 4, 7, 8, 9, 11, 4, 6, 8, 12 };

	public static final int ALPHA = 127;
	public static Color RED = new Color(255, 0, 0, ALPHA);
	public static Color BLUE = new Color(0, 0, 255, ALPHA);
	public static final Color[] PAD_COLOUR = { RED, RED, RED, RED, RED, BLUE, RED, BLUE, RED, RED, BLUE, RED, BLUE, RED,
			RED, BLUE, RED, RED, RED, RED, RED, BLUE, RED, BLUE, BLUE, RED, RED, RED, RED, RED, BLUE, BLUE, BLUE, BLUE,
			RED, RED, RED, BLUE, BLUE };

	public static Point[] getPadLocations() {
		Point[] PAD_LOCATIONS = new Point[PAD_ROWS.length];
		for (int i = 0; i < PAD_ROWS.length; i++) {
			if (!(PAD_COLUMNS[i] == 12 & PAD_ROWS[i] == 8)) {// This pad is different from the rest; it provides Vs to
																// RL
				PAD_LOCATIONS[i] = new Point(PAD_COLUMNS[i], PAD_ROWS[i]);
			}
		}
		return PAD_LOCATIONS;
	}
}
