package com.q51.gui;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.EventObject;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class MatrixCellEvent extends EventObject {

	private SymbolPanel symbolPanel;
	private JLabel label;
	private Rectangle rect;
	private BufferedImage block_r_c_cell_r_c;
	private Boolean highlight;
	private String keyPrefix;
	private MouseAction mouseAction;

	public MatrixCellEvent(Object source, JLabel label, Rectangle rect, BufferedImage block_r_c_cell_r_c, Boolean highlight,
			SymbolPanel symbolPanel, String keyPrefix, MouseAction mouseAction) {
		super(source);
		this.label = label;
		this.rect = rect;
		this.block_r_c_cell_r_c = block_r_c_cell_r_c;
		this.highlight = highlight;
		this.symbolPanel = symbolPanel;
		this.keyPrefix = keyPrefix;
		this.setMouseAction(mouseAction);
	}

	public SymbolPanel getSymbolPanel() {
		return symbolPanel;
	}

	public void setSymbolPanel(SymbolPanel symbolPanel) {
		this.symbolPanel = symbolPanel;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public BufferedImage getBlock_r_c_cell_r_c() {
		return block_r_c_cell_r_c;
	}

	public void setBlock_r_c_cell_r_c(BufferedImage block_r_c_cell_r_c) {
		this.block_r_c_cell_r_c = block_r_c_cell_r_c;
	}

	public Boolean getHighlight() {
		return highlight;
	}

	public void setHighlight(Boolean highlight) {
		this.highlight = highlight;
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public MouseAction getMouseAction() {
		return mouseAction;
	}

	public void setMouseAction(MouseAction mouseAction) {
		this.mouseAction = mouseAction;
	}

}
