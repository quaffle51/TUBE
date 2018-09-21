package com.q51.gui;

import java.util.EventObject;

@SuppressWarnings("serial")
public class LocationPanelEvent extends EventObject {

	private String blockRow;
	private String blockCol;
	private String cellRow;
	private String cellCol;

	public LocationPanelEvent(Object source) {
		super(source);
	}

	public LocationPanelEvent(Object source, String blockRow, String blockCol, String cellRow, String cellCol) {
		super(source);
		this.blockRow = blockRow;
		this.blockCol = blockCol;
		this.cellRow = cellRow;
		this.cellCol = cellCol;
	}

	public String getBlockRow() {
		return blockRow;
	}

	public void setBlockRow(String blockRow) {
		this.blockRow = blockRow;
	}

	public String getBlockCol() {
		return blockCol;
	}

	public void setBlockCol(String blockCol) {
		this.blockCol = blockCol;
	}

	public String getCellRow() {
		return cellRow;
	}

	public void setCellRow(String cellRow) {
		this.cellRow = cellRow;
	}

	public String getCellCol() {
		return cellCol;
	}

	public void setCellCol(String cellCol) {
		this.cellCol = cellCol;
	}

}
