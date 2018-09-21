package com.q51.gui;

import java.util.EventObject;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class ButtonPanelEvent extends EventObject  {

	private JComboBox<?> cellRowCombo;
	private JComboBox<?> cellColCombo;
	private int indexRows;
	private int indexCols;

	public ButtonPanelEvent(Object source) {
		super(source);
	}

	public ButtonPanelEvent(Object source, JComboBox<?> cellColCombo, JComboBox<?> cellRowCombo, int indexCols,
			int indexRows) {
		super(source);
		this.cellRowCombo = cellRowCombo;
		this.cellColCombo = cellColCombo;
		this.indexRows = indexRows;
		this.indexCols = indexCols;
	}

	public JComboBox<?> getCellRowCombo() {
		return cellRowCombo;
	}

	public void setCellRowCombo(JComboBox<?> cellRowCombo) {
		this.cellRowCombo = cellRowCombo;
	}

	public JComboBox<?> getCellColCombo() {
		return cellColCombo;
	}

	public void setCellColCombo(JComboBox<?> cellColCombo) {
		this.cellColCombo = cellColCombo;
	}

	public int getIndexRows() {
		return indexRows;
	}

	public void setIndexRowCombo(int indexRows) {
		this.indexRows = indexRows;
	}

	public int getIndexCols() {
		return indexCols;
	}

	public void setIndexCols(int indexCols) {
		this.indexCols = indexCols;
	}

}
