package com.q51.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.q51.model.Tile;

@SuppressWarnings("serial")
public class TileTableModel extends AbstractTableModel {

	private List<Tile> listOfTile;

	private String[] colNames = { "ID", "Key", "Name", "Symbol" };

	public TileTableModel() {
	}

	public void setData(List<Tile> listOfTile) {
		this.listOfTile = listOfTile;
	}
	
	public int addTile(Tile tile) {
		listOfTile.add(tile);
		return listOfTile.size();
	}
	
	public int removeTile(Tile tile) {
		listOfTile.remove(tile);
		return listOfTile.size();
	}

	public int getColumnCount() { // implemented method
		return colNames.length;
	}

	public int getRowCount() { // implemented method
		return listOfTile.size();
	}

	public Object getValueAt(int row, int col) { // implemented method
		Tile tile = listOfTile.get(row);

		switch (col) {
		case 0:
			return tile.getId();
		case 1:
			return tile.getKey();
		case 2:
			return tile.getName();
		case 3:
			return tile.getText();
		}

		return null;
	}

	public String getColumnName(int column) {
		return colNames[column];
	}

}
