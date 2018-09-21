package com.q51.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {

	MatrixCell matrixCell;
	JLabel label1;
	JLabel label2;

	public ImagePanel(String title, MatrixCell matrixCell) {

		this.matrixCell = matrixCell;

		// Set the layout of the panel
		setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		// Add a border around this panel with the given title
		setBorder(BorderFactory.createTitledBorder(title));
		BufferedImage cell = null;
		try {
			cell = ImageIO.read(new File(Constants.IMAGE_DIRECTORY_PREFIX + "cell.png"));
			cell = resize(cell, Constants.MATRIX_CELL_WIDTH, Constants.MATRIX_CELL_HEIGHT);
			label1 = new JLabel(new ImageIcon(cell));
			label1.setSize(new Dimension(390, 390));
			add(label1);

		} catch (IOException e) {
			System.err
					.println("Error: " + e.getMessage() + ":" + Constants.IMAGE_DIRECTORY_PREFIX + "cell.png" + " ###");
			System.exit(-1);
		}

		BufferedImage schematic = null;
		try {
			schematic = ImageIO.read(new File(Constants.IMAGE_DIRECTORY_PREFIX + "schematic.png"));
			schematic = resize(schematic, Constants.MATRIX_CELL_WIDTH, Constants.MATRIX_CELL_HEIGHT);
			label2 = new JLabel(new ImageIcon(schematic));
			label2.setSize(new Dimension(390, 390));
			add(label2);
		} catch (IOException e) {
			System.err.println(
					"Error: " + e.getMessage() + ": " + Constants.IMAGE_DIRECTORY_PREFIX + "schematic.png" + "+++");
			System.exit(-1);
		}

		add(this.matrixCell);

	}

	private BufferedImage resize(BufferedImage img, int height, int width) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}
	
	public JLabel getCellLabel() {
		return label1;
	}
	
	public JLabel getSchematicLabel() {
		return label2;
	}

}
