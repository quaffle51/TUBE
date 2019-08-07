package com.q51.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.q51.gui.Constants;

public class Database {

	private List<Tile> tiles;
	private Connection connection;

	public Database() throws SQLException, Exception {
		tiles = this.getDataMySql();
	}

	public void addTileMySql(Tile tile) throws SQLException, Exception {
		tiles.add(tile);
		Collections.sort(tiles, new Comparator<Tile>() {
            public int compare(Tile t1, Tile t2) {
                return t2.getId() > t1.getId() ? -1 : t1.getKey() == t2.getKey() ? 0 : 1;
            }
        });
		addUpdateRemoveTileMySql(tile);
	}

	public void removeTileMySql(String key) throws SQLException, Exception {
		addUpdateRemoveTileMySql(new Tile(getID(key), key, "0", '▢'));
		Iterator<Tile> itr = tiles.iterator();
		while (itr.hasNext()) {
			Tile tile = itr.next();
			if (tile.getKey().equals(key)) {
				tiles.remove(tile);
				break;
			}
		}
	}

	public List<Tile> getTiles() {
		return Collections.unmodifiableList(tiles);
	}

	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		Tile[] slates = tiles.toArray(new Tile[tiles.size()]);

		oos.writeObject(slates);

		oos.close();
	}

	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);

		try {
			Tile[] slates = (Tile[]) ois.readObject();
			tiles.clear();
			tiles.addAll(Arrays.asList(slates));
		} catch (ClassNotFoundException e) {
			System.err.println("Error: " + e.getMessage());
		}

		ois.close();
	}

	// =======================================================================================

	public void connect() throws Exception {
		if (connection != null) {
			return;
		}

		String driver = "com.mysql.cj.jdbc.Driver";
		String database = "ULA";
		String user = "q51";
		String pwd = "q+64vethU8%hfg";
		String port = "3306";

		Class.forName(driver);
		
		String url = "jdbc:mysql://localhost:" + port + "/" + database
				+ "?useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true&useSSL=false";
		connection = DriverManager.getConnection(url, user, pwd);

		System.out.println("Connected: " + connection);
	}

	public void disconnect() throws SQLException {
		if (connection != null) {
				connection.close();
				connection = null;
		}

	}

	// =======================================================================================

	public void saveAll() throws SQLException, Exception {
		if (connection == null) {
			connect();
		}
		
		for (Tile tile : tiles) {
			addUpdateRemoveTileMySql(tile);
		}
	}

	private void addUpdateRemoveTileMySql(Tile tile) throws SQLException, Exception {
		if (connection == null) {
			connect();
		}
		
		String checkSql = "select count(*) as count from tiles where id = ?";
		PreparedStatement checkStmt = connection.prepareStatement(checkSql);

		String insertSql = "insert into tiles (id, location, name, text) values (?,?,?,?)";
		PreparedStatement insertStmt = connection.prepareStatement(insertSql);

		String updateSql = "update tiles set name=?, text=? where id=?";
		PreparedStatement updateStmt = connection.prepareStatement(updateSql);

		String deleteSql = "delete from tiles where id=?";
		PreparedStatement deleteStmt = connection.prepareStatement(deleteSql);

		String getRowSql = "select * from tiles where id=?";
		PreparedStatement getRowStmt = connection.prepareStatement(getRowSql);

		int id = tile.getId();
		String location = tile.getKey();
		String name = tile.getName();
		String text = "" + tile.getText();
		checkStmt.setInt(1, id);
		ResultSet checkResult = checkStmt.executeQuery();
		checkResult.next();
		int count = checkResult.getInt(1);

		if (count == 0) { // The tile is not in the database so add it so long as it is not a blank tile
			insertStmt.setInt(1, id);
			insertStmt.setString(2, location);
			insertStmt.setString(3, name);
			insertStmt.setString(4, "" + text);
			System.out.println("Count == 0:TRUE, " + id + ":" + location + ":" + name + ":" + text);
			if (!name.equals("0")) { // Check for a blank tile
				insertStmt.execute();
			}

		} else { // The tile exists in the database, so update it.

			getRowStmt.setInt(1, id);

			ResultSet columns = getRowStmt.executeQuery();
			columns.next();
			String dbTileName = columns.getString(3);
			System.out.println("===================> " + dbTileName + ":" + name);

			if (!dbTileName.equals("0") & !name.equals("0")) {
				updateStmt.setString(1, name);
				updateStmt.setString(2, "" + text);
				updateStmt.setInt(3, id);
				updateStmt.executeUpdate();
				System.out.println("Count == 0 FALSE, updating tile at location: " + location + " from " + dbTileName
						+ " to " + name);
			} else {
				if (!dbTileName.equals("0") & name.equals("0")) {
					deleteStmt.setInt(1, id);
					System.out.println("Count == 0 FALSE, deleting tile at location: " + location + ".");
					deleteStmt.execute();
				}
			}
		}

		getRowStmt.close();
		deleteStmt.close();
		updateStmt.close();
		checkStmt.close();
		insertStmt.close();
	}

	public LinkedList<Tile> getDataMySql() throws SQLException, Exception {
		if (connection == null) {
			connect();
		}

		String getRows = "select * from tiles";
		PreparedStatement getRowsStmt = connection.prepareStatement(getRows);
		ResultSet rows = getRowsStmt.executeQuery();

		final List<Tile> rowList = new LinkedList<Tile>();
		try {
			while (rows.next()) {
				Tile tile = new Tile(rows.getInt(1), rows.getString(2), rows.getString(3), rows.getString(4).charAt(0));
				rowList.add(tile);
			}
		} catch (SQLException e) {
			System.err.println("ERROR: " + e.getMessage());
		}

		return (LinkedList<Tile>) rowList;
	}

	public int getRowCountMySql() throws SQLException, Exception {
		if (connection == null) {
			connect();
		}

		String getRowCount = "select count(*) as count from tiles";
		PreparedStatement getRowsStmt = connection.prepareStatement(getRowCount);
		ResultSet row = getRowsStmt.executeQuery();
		row.next();

		return row.getInt(1);

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

}
