package com.q51.controller;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.q51.gui.Constants; // okay; don't import com.q51.model items into com.q51.gui!
import com.q51.model.Database;
import com.q51.model.SqlHandler;
import com.q51.model.Tile;
import com.q51.model.ULA;
import com.q51.model.ULAHandler;

public class Controller {

	private Database database;
	private ULAHandler jasonHandler;
	private SqlHandler sqlHandler;
	private TreeMap<String, String> ulaConnections;
	private ULA ula;
	private Rectangle rect;
	private String keyPrefix;
	private String key;
	private String name;
	private char symbol;
	private List<Tile> mySqlDatabase;
	
	public Controller() throws SQLException, Exception {
		database = new Database();
		jasonHandler = new ULAHandler();
		ula = jasonHandler.getUla();
		ulaConnections = (TreeMap<String, String>) ula.getULAConnections();
		
		sqlHandler = new SqlHandler();
		
		mySqlDatabase = sqlHandler.getRowsMySql();
		
		System.out.println("INFO: Size of ulaConnection is:  " + ulaConnections.size() + ".");
		System.out.println("INFO: Size of MySql database is: " + sqlHandler.getRowCountMySql());
	}
	
	public void connectToMySql() throws Exception {
		database.connect();
	}
	
	public void disconnectFromMySqy() throws SQLException {
		database.disconnect();
	}
	
	
	public Tile addTileMySql(Rectangle rect, String keyPrefix, String name, char symbol) throws SQLException, Exception {
		this.rect = rect;
		this.keyPrefix = keyPrefix;
		this.name = name;
		this.symbol = symbol;
		this.key = getKey();
		updateJson();
		database.removeTileMySql(getKey(rect, keyPrefix));  // don't want duplicate entries in the table if the tile is clicked on twice with the same symbol selected.
		Tile tile = new Tile(getID(key), key, name, symbol);
		database.addTileMySql(tile);
		return tile;
	}
	

	private int getID(String key) {
		int blockRowIndex = Integer.parseInt(key.substring(0, 2)); // index of block row
		int blockColIndex = Integer.parseInt(key.substring(2, 4)); // index of block column
		int cellRowIndex = Integer.parseInt(key.substring(5, 7)); // index of cell row
		int cellColIndex = Integer.parseInt(key.substring(7, 9)); // index of cell column
		int tileRowIndex = Integer.parseInt(key.substring(10, 12)); // index of tile row
		int tileColIndex = Integer.parseInt(key.substring(12)); // index of tile column

		int nBlockCols = Constants.NUMBER_OF_BLOCK_COLS;
		int nCellCols = Constants.BLOCK_COL_COUNT;
		int nCellRows = Constants.BLOCK_ROW_COUNT;
		int nTileCols = Constants.NUMBER_OF_TILES;
		int nTileRows = Constants.NUMBER_OF_TILES;

		int numberOfCells = nCellCols * nCellRows;
		int numberOfTiles = nTileCols * nTileRows;

		int k = nBlockCols * blockRowIndex + blockColIndex;
		int m = nCellCols * cellRowIndex + cellColIndex;
		int o = nTileCols * tileRowIndex + tileColIndex;

		return (numberOfCells * numberOfTiles * k) + (numberOfTiles * m) + o;

	}

	public void add(String key, String value) {
		ulaConnections.put(key, value);
		writeJson();
	}

	public void remove(String key) {
		ulaConnections.remove(key);
		writeJson();
	}

	public Set<String> getKeys() {
		if (ula == null) {
			System.err.println("Error: ula is null!");
			System.exit(-1);
		}
		return ula.getULAConnections().keySet();
	}

	public String getValue(String key) {
		return ula.getULAConnections().get(key);
	}

	public void writeJson() {
		jasonHandler.writeJson();
	}

	public TreeMap<String, String> getMapping(String keyPrefix) {
		return ula.getMapping(keyPrefix);
	}

	private String getKey() {
		return getKey(this.rect, this.keyPrefix);
	}

	private void updateJson() {
		ulaConnections.put(key, name.toString()); // Overwrites any existing entry, otherwise creates a new one.
		jasonHandler.writeJson();
	}
	
	public List<Tile> getMySqlTiles() {
		return mySqlDatabase;
	}

	public List<Tile> getTiles() {
		return database.getTiles();
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
	
	
	public String getValue(Rectangle rect, String keyPrefix) {
		return getValue(getKey(rect, keyPrefix));
	}
	
	public void removeTile(Rectangle rect, String keyPrefix) throws SQLException, Exception {
		database.removeTileMySql(getKey(rect, keyPrefix));
		this.remove(getKey(rect, keyPrefix));
	}
	
	private String getKey(Rectangle rect, String keyPrefix) {
		int row = (int) rect.getY() / Constants.TILE_SIDE_LENGTH;
		int col = (int) rect.getX() / Constants.TILE_SIDE_LENGTH;
		return keyPrefix + String.format("%02d%02d", row, col);
	}
	
	public void saveToFile(File file) throws IOException {
		database.saveToFile(file);
	}
	
	public void loadFromFile(File file) throws IOException {
		database.loadFromFile(file);
	}
	
}
