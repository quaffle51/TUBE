package com.q51.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.q51.model.ULAHandler;
import com.q51.model.ULA;

public class MatrixCell extends JPanel {

	private static final long serialVersionUID = 1L;

	private BufferedImage block_r_c_cell_r_c;

	private JLabel label;
	private Rectangle[] rects;
	private SymbolPanel symbolPanel;
	
	private Pad[] tiles;
	private int[][] imagePixels;
	
	private MatrixCellListener matrixCellListener;

	public MatrixCell(SymbolPanel symbolPanel) {
		this.symbolPanel = symbolPanel;
		try {
			block_r_c_cell_r_c = ImageIO.read(new File(
					Constants.BLOCK_DIRECTORY_PREFIX + Constants.DEFAULT_BLOCK_ROW + "_" + Constants.DEFAULT_BLOCK_COL
							+ "/cell_" + Constants.DEFAULT_CELL_ROW + "_" + Constants.DEFAULT_CELL_COL + ".png"));
			block_r_c_cell_r_c = resize(block_r_c_cell_r_c, Constants.MATRIX_CELL_WIDTH, Constants.MATRIX_CELL_HEIGHT);
			label = new JLabel(new ImageIcon(block_r_c_cell_r_c));
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage() + Constants.BLOCK_DIRECTORY_PREFIX
					+ Constants.DEFAULT_BLOCK_ROW + "_" + Constants.DEFAULT_BLOCK_COL + "/cell_"
					+ Constants.DEFAULT_CELL_ROW + "_" + Constants.DEFAULT_CELL_COL + ".png" + " @@@");
			System.exit(-1);
		}

		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(label);

		label.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (rects == null) {
					initRects();
				}
				String keyPrefix = getKeyPrefix();
				Point p = e.getPoint();
				Rectangle rect = null;
				for (int j = 0; j < rects.length; j++) {
					if (rects[j].contains(p)) {
						rect = rects[j];
						break;
					}
				}
				
				MatrixCellEvent ev = new MatrixCellEvent(this, label, rect, block_r_c_cell_r_c, true, symbolPanel, keyPrefix, MouseAction.MOUSE_MOVED);
				
				if (matrixCellListener != null) {
					matrixCellListener.matrixCellEventOccurred(ev);
				}
				
				e.consume();
			}
		});
		
		label.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint();
				Rectangle rect = null;
				String keyPrefix = getKeyPrefix();
				for (int i = 0; i < rects.length; i++) {
					if (rects[i].contains(p)) {
						rect = rects[i];
						break;
					}
				}
				MatrixCellEvent ev = new MatrixCellEvent(this, label, rect, block_r_c_cell_r_c, null, symbolPanel, keyPrefix, MouseAction.MOUSE_CLICKED);
				
				if (matrixCellListener != null) {
					matrixCellListener.matrixCellEventOccurred(ev);
				}
				
				e.consume();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				MatrixCellEvent ev = new MatrixCellEvent(this, label, null, block_r_c_cell_r_c, false, symbolPanel, null, MouseAction.MOUSE_EXITED);
				
				if (matrixCellListener != null) {
					matrixCellListener.matrixCellEventOccurred(ev);
				}
				
				e.consume();
			}

		});

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGrid();
		drawColouredPads();
	}

	private void drawColouredPads() {

		Graphics2D g2d = block_r_c_cell_r_c.createGraphics();
		tiles = new Pad[Constants.PAD_COLUMNS.length];
		for (int i = 0; i < Constants.PAD_COLUMNS.length; i++) {
			tiles[i] = new Pad(Constants.PAD_ROWS[i], Constants.PAD_COLUMNS[i], Constants.PAD_COLOUR[i]);
		}
		for (int i = 0; i < tiles.length; i++) {
			g2d.setColor(tiles[i].colour);
			int padEdgeDimesion = 16;
			int padOffset = 5;
			g2d.fillRect(tiles[i].y * Constants.TILE_SIDE_LENGTH + padOffset,
					tiles[i].x * Constants.TILE_SIDE_LENGTH + padOffset, padEdgeDimesion, padEdgeDimesion);
		}

		Image[] images = symbolPanel.getSelectedImages(); // Images of the "Selected symbols"

		// Draw the connections to connect TR5 Collects to the Ground pads
		g2d.drawImage(images[3], 7 * Constants.TILE_SIDE_LENGTH, 10 * Constants.TILE_SIDE_LENGTH, null);
		g2d.drawImage(images[1], 8 * Constants.TILE_SIDE_LENGTH, 10 * Constants.TILE_SIDE_LENGTH, null);
		g2d.drawImage(images[4], 9 * Constants.TILE_SIDE_LENGTH, 10 * Constants.TILE_SIDE_LENGTH, null);
		g2d.drawImage(images[6], 7 * Constants.TILE_SIDE_LENGTH, 11 * Constants.TILE_SIDE_LENGTH, null);
		g2d.drawImage(images[1], 8 * Constants.TILE_SIDE_LENGTH, 11 * Constants.TILE_SIDE_LENGTH, null);
		g2d.drawImage(images[5], 9 * Constants.TILE_SIDE_LENGTH, 11 * Constants.TILE_SIDE_LENGTH, null);

		// Draw the connections between the Left and Right TR5 Emitters
		g2d.drawImage(images[2], 7 * Constants.TILE_SIDE_LENGTH, 7 * Constants.TILE_SIDE_LENGTH, null);
		g2d.drawImage(images[2], 9 * Constants.TILE_SIDE_LENGTH, 7 * Constants.TILE_SIDE_LENGTH, null);
		g2d.drawImage(images[2], 7 * Constants.TILE_SIDE_LENGTH, 8 * Constants.TILE_SIDE_LENGTH, null);
		g2d.drawImage(images[2], 9 * Constants.TILE_SIDE_LENGTH, 8 * Constants.TILE_SIDE_LENGTH, null);

		// Draw the connections between TR5 bases
		g2d.drawImage(images[1], 7 * Constants.TILE_SIDE_LENGTH, 9 * Constants.TILE_SIDE_LENGTH, null);
		g2d.drawImage(images[1], 8 * Constants.TILE_SIDE_LENGTH, 9 * Constants.TILE_SIDE_LENGTH, null);
		g2d.drawImage(images[1], 9 * Constants.TILE_SIDE_LENGTH, 9 * Constants.TILE_SIDE_LENGTH, null);
		g2d.dispose();
	}

	private void drawGrid() {
		Graphics2D g2d = block_r_c_cell_r_c.createGraphics();
		float thickness = 2;
		g2d.setStroke(new BasicStroke(thickness));
		g2d.setColor(Color.RED);
		int offsetX = 0;
		int offsetY = 0;
		g2d.drawRect(offsetX, offsetY, Constants.TILE_SIDE_LENGTH * Constants.NUMBER_OF_TILES + 4,
				Constants.TILE_SIDE_LENGTH * Constants.NUMBER_OF_TILES + 4); // Draw bounding rectangle
		for (int i = 0; i <= Constants.TILE_SIDE_LENGTH * Constants.NUMBER_OF_TILES; i += Constants.TILE_SIDE_LENGTH) { // Draw
																													// Column
																													// lines
			g2d.drawLine(i + offsetX, 0 + offsetY, i + offsetX,
					Constants.TILE_SIDE_LENGTH * Constants.NUMBER_OF_TILES + offsetY);
		}
		for (int i = 0; i <= Constants.TILE_SIDE_LENGTH * Constants.NUMBER_OF_TILES; i += Constants.TILE_SIDE_LENGTH) { // Draw
																													// Row
																													// lines
			g2d.drawLine(0 + offsetX, i + offsetY, Constants.TILE_SIDE_LENGTH * Constants.NUMBER_OF_TILES + offsetX,
					i + offsetY);
		}
		g2d.translate(-100, 100);
		g2d.dispose();

	}

	public void setMatrixCellListener(MatrixCellListener listener) {
		this.matrixCellListener = listener;
	}

	public void updateCellImage(String blockRow, String blockCol, String cellRow, String cellCol, TreeMap<String, String> mappings) {
		try {
			block_r_c_cell_r_c = ImageIO.read(new File(Constants.BLOCK_DIRECTORY_PREFIX + blockRow + "_" + blockCol
					+ "/cell_" + cellRow + "_" + cellCol + ".png"));
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage() + Constants.BLOCK_DIRECTORY_PREFIX + blockRow + "_" + blockCol
					+ "/cell_" + cellRow + "_" + cellCol + ".png" + " >>>");
			System.exit(-1);
		}
		block_r_c_cell_r_c = resize(block_r_c_cell_r_c, Constants.MATRIX_CELL_WIDTH, Constants.MATRIX_CELL_HEIGHT);
		imagePixels = ImagePixels.convertTo2D(block_r_c_cell_r_c);
		setImage(block_r_c_cell_r_c, mappings);
		symbolPanel.setDefaults();
	}

	public void setImage(BufferedImage matrixCell, TreeMap<String, String> mappings) {
		// This method is invoked each time any of the Location JComboboxes are changed.
		block_r_c_cell_r_c = matrixCell;
		label.setIcon(new ImageIcon(matrixCell));
		drawExistingConnections(mappings);

	}

	public void drawConnection(Rectangle rect) {
		Graphics2D g2d = this.block_r_c_cell_r_c.createGraphics();
		ImagePixels.restorePixels(block_r_c_cell_r_c, imagePixels, rect);
		String nameOfConnection = symbolPanel.getName(); // the name of the currently selected connection symbol
		Image[] images = symbolPanel.getSelectedImages(); // the array of possible selected images
		Image image = images[new Integer(nameOfConnection)]; // this is the symbol image
		g2d.drawImage(image, (int) rect.getX(), (int) rect.getY(), null); 
		drawGrid();
		g2d.dispose();
	}

	

	public void drawExistingConnections(TreeMap<String, String> mappings) {
		if (rects == null) {
			initRects();
		}

		Set<String> keys = mappings.keySet();
		if (keys != null) {
			Iterator<String> itr = keys.iterator();
			String key;
			String name;

			while (itr.hasNext()) {
				key = itr.next();
				name = mappings.get(key);

				if (key.length() == 14 && name != null && !name.equals("0")) {
					int tRow = Integer.parseInt(key.substring(10, 12));
					int tCol = Integer.parseInt(key.substring(12, key.length()));
					symbolPanel.setName(name);
					symbolPanel.setSymbol(Integer.parseInt(name));
					Rectangle rect = rects[Constants. NUMBER_OF_TILES * tRow + tCol];
					drawConnection(rect);
				} else {
					System.err.println("WARNING: Invalid " + key + ", having value: " + name);
					itr.remove();
				}
			}
			symbolPanel.setName("0");
			symbolPanel.setSymbol(0);
		}

	}

	private BufferedImage resize(BufferedImage img, int height, int width) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

	private void initRects() {
		Icon icon = label.getIcon();
		if (icon == null) {
			System.err.println("icon is null!");
			System.exit(-1);
		}
		int iconWidth = icon.getIconWidth();
		int iconHeight = icon.getIconHeight();
		int w = iconWidth / Constants. NUMBER_OF_TILES;
		int h = iconHeight / Constants. NUMBER_OF_TILES;

		rects = new Rectangle[Constants. NUMBER_OF_TILES * Constants. NUMBER_OF_TILES];
		for (int row = 0; row < Constants. NUMBER_OF_TILES; row++) {
			for (int col = 0; col < Constants. NUMBER_OF_TILES; col++) {
				rects[Constants. NUMBER_OF_TILES * row + col] = new Rectangle(
						col * Constants.TILE_SIDE_LENGTH, row * Constants.TILE_SIDE_LENGTH, w, h);
			}
		}

	}
	
	private String getBlockLocation() {
		int col = LocationPanel.getBlockColLst().getSelectedIndex();
		int row = LocationPanel.getBlockRowLst().getSelectedIndex();
		String key = "" + String.format("%02d", row) + String.format("%02d", col);
		return key;
	}

	private String getCellLocation() {
		int col = LocationPanel.getCellColLst().getSelectedIndex();
		int row = LocationPanel.getCellRowLst().getSelectedIndex();
		String key = String.format("%02d", row) + String.format("%02d", col);
		return "" + key;
	}

	private String getKeyPrefix() {
		return getBlockLocation() + "_" + getCellLocation() + "_";
	}

}
