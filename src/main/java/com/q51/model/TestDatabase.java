package com.q51.model;

import java.sql.SQLException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class TestDatabase {

	private static char[] symbols = { '▢', '┅', '│', '┏', '┓', '┛', '┗', '┳', '┫', '┻', '┣', '╋', '⛫' };

	public static void main(String[] args) throws SQLException, Exception {
		System.out.println("Running databas test");

		Database db = new Database();

		try {
			db.connect();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			System.exit(-1);
		}
		ULAHandler jh;
		TreeMap<String, String> ulaConnections;
		ULA ula;
		jh = new ULAHandler();
		ula = jh.getUla();
		ulaConnections = (TreeMap<String, String>) ula.getULAConnections();
		System.out.println("INFO: Size of ulaConnection is: " + ulaConnections.size() + "\n");
//		TreeMap<String, String> mappings = ula.getMapping("");

		for (Map.Entry<String, String> entry : filterPrefix(ulaConnections, "").entrySet()) {
			String location = entry.getKey();
			String name = entry.getValue();
			int id = getID(location);
			char text = symbols[Integer.parseInt(name)];

			db.addTileMySql(new Tile(id, location, name, text));
		}

		try {
			db.saveAll();
			System.out.println("\n\nSave to db complete.");
		} catch (SQLException e) {
			System.err.println("*** Error: " + e.getMessage());
		}

		try {
			db.disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static <V> SortedMap<String, V> filterPrefix(SortedMap<String, V> baseMap, String prefix) {
		if (prefix.length() > 0) {
			char nextLetter = (char) (prefix.charAt(prefix.length() - 1) + 1);
			String end = prefix.substring(0, prefix.length() - 1) + nextLetter;
			return baseMap.subMap(prefix, end);
		}
		return baseMap;
	}

	private static int getID(String key) {
		int blockRowIndex = Integer.parseInt(key.substring(0, 2)); // index of block row
		int blockColIndex = Integer.parseInt(key.substring(2, 4)); // index of block column
		int cellRowIndex = Integer.parseInt(key.substring(5, 7)); // index of cell row
		int cellColIndex = Integer.parseInt(key.substring(7, 9)); // index of cell column
		int tileRowIndex = Integer.parseInt(key.substring(10, 12)); // index of tile row
		int tileColIndex = Integer.parseInt(key.substring(12)); // index of tile column

		int nBlockCols = 3;
		int nCellCols = 10;
		int nCellRows = 11;
		int nTileCols = 15;
		int nTileRows = 15;

		int numberOfCells = nCellCols * nCellRows;
		int numberOfTiles = nTileCols * nTileRows;

		int k = nBlockCols * blockRowIndex + blockColIndex;
		int m = nCellCols * cellRowIndex + cellColIndex;
		int o = nTileCols * tileRowIndex + tileColIndex;

		return (numberOfCells * numberOfTiles * k) + (numberOfTiles * m) + o;

	}
}
