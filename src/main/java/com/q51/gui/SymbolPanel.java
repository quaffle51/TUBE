package com.q51.gui;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class SymbolPanel extends JPanel {

	private String[] symbols = { "▢", "┅", "│", "┏", "┓", "┛", "┗", "┳", "┫", "┻", "┣", "╋", "⛫" };
	private String[] symToolTips = { "No connecton", "Horizontal connection", "Vertical Connection",
			"Corner connection", "Corner connection", "Corner connection", "Corner connection", "Tee connection",
			"Tee connection", "Tee connection", "Tee connection", "Cross connection", "Vs to RL" };
	private String[] iconPrefixes = { "blank", "horizontal", "vertical", "corner1", "corner2", "corner3", "corner4",
			"tee1", "tee2", "tee3", "tee4", "cross", "vs" };
	private Image[] selectedImages = new Image[symbols.length];
	private Image[] notSelectedImages = new Image[symbols.length];
	private Icon[] selectedIcons = new Icon[symbols.length];
	private Icon[] notSelectedIcons = new Icon[symbols.length];
	private JRadioButton[] rb;
	private String name;
	private String symbol;
	private ButtonGroup group = null;
	private SymbolPanelListener symbolPanelListener;
	public SymbolPanel(String title) {

		for (int i = 0; i < symbols.length; i++) {
			try {
				Image image = ImageIO.read(new File(
						Constants.IMAGE_DIRECTORY_PREFIX + iconPrefixes[i] + "_" + Constants.SELECTED + ".png"));
				Icon icon = new ImageIcon(image);
				selectedIcons[i] = icon;
				selectedImages[i] = image;
			} catch (IOException e) {
				System.err.println("Error: Icon image not found: " + e.getMessage() + ": "
						+ Constants.IMAGE_DIRECTORY_PREFIX + iconPrefixes[i] + "_" + Constants.SELECTED + ".png");
				System.exit(-1);
			}

			try {
				Image image = ImageIO.read(new File(
						Constants.IMAGE_DIRECTORY_PREFIX + iconPrefixes[i] + "_" + Constants.NOT_SELECTED + ".png"));
				Icon icon = new ImageIcon(image);
				notSelectedIcons[i] = icon;
				notSelectedImages[i] = image;

			} catch (IOException e) {
				System.err.println("Error: Icon image not found: " + e.getMessage() + ": "
						+ Constants.IMAGE_DIRECTORY_PREFIX + iconPrefixes[i] + "_" + Constants.NOT_SELECTED + ".png");
				System.exit(-1);
			}
		}

		// Set the layout of the panel
		setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
		setSymbolPanel(new JPanel());
		// Add a border around this panel with the given title
		setBorder(BorderFactory.createTitledBorder(title));

		group = new ButtonGroup();

		// These radio buttons select the symbol to set a grid location to
		rb = new JRadioButton[symbols.length];
		for (int i = 0; i < symbols.length; i++) {
			rb[i] = new JRadioButton();
			rb[i].setFont(new java.awt.Font("Code2000", 1, 14));
			rb[i].setForeground(new java.awt.Color(0, 0, 255));
			rb[i].setName(Integer.toString(i));
			rb[i].setText(symbols[i]);

			rb[i].setToolTipText(symToolTips[i]);
			rb[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JRadioButton rbSelected = (JRadioButton) e.getSource();
					name = rbSelected.getName();

					SymbolPanelEvent ev = new SymbolPanelEvent(this, rb, rbSelected, name, notSelectedIcons,
							selectedIcons);

					if (symbolPanelListener != null) {
						symbolPanelListener.symbolPanelEventOccurred(ev);
					}
				}
			});
			group.add(rb[i]);
			// Add the components horizontally across the panel
			add(rb[i]);
		}
		// No Connection is the default connection type
		setDefaults();

	}

	public void setDefaults() {

		for (int i = 0; i < symbols.length; i++) {
			rb[i].setIcon(notSelectedIcons[i]);
			rb[i].setSelected(false);
		}
		rb[0].setSelected(true);
		rb[0].setIcon(selectedIcons[0]);
		name = rb[0].getName();
		symbol = rb[0].getText();
	}

	public Icon[] getNotSelectedIcons() {
		return notSelectedIcons;
	}

	public void setNotSelectedIcons(Icon[] notSelectedIcons) {
		this.notSelectedIcons = notSelectedIcons;
	}

	public Image[] getNotSelectedImages() {
		return notSelectedImages;
	}

	public void setNotSelectedImages(Image[] notSelectedImages) {
		this.notSelectedImages = notSelectedImages;
	}

	public Image[] getSelectedImages() {
		return selectedImages;
	}

	public Icon[] getSelectedIcons() {
		return selectedIcons;
	}

	public String[] getSymbols() {
		return symbols;
	}

	public void setSymbol(String[] symbol) {
		this.symbols = symbol;
	}

	public String[] getSymToolTip() {
		return symToolTips;
	}

	public void setSymToolTip(String[] symToolTip) {
		this.symToolTips = symToolTip;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public void setSymbolPanel(JPanel symbolPanel) {
	}

	public String getSelectedButtonText() {
		for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				return button.getText();
			}
		}

		return null;
	}

	public void setSymbolPanelListener(SymbolPanelListener listener) {
		this.symbolPanelListener = listener;
	}

	public String getSymbol() {
		return symbol;
	}
	
	public String getSymbol(int index) {
		if (index >= 0 & index < symbols.length) {
			return symbols[index];
		} else {
			System.err.println("WARNING: index out of range!");
		}
		return null;
	}
	
	public void setSymbol(int index) {
		if (index >= 0 & index < symbols.length) {
			symbol = symbols[index];
		} else {
			System.err.println("WARNING: index out of range!");
		}
		
	}
}

