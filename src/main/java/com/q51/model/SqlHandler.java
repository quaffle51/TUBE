package com.q51.model;

import java.sql.SQLException;
import java.util.List;

public class SqlHandler {
	
	private Database db;
	private List<Tile> tileList;
	
	public SqlHandler() throws SQLException, Exception {
		db = new Database();
	}
	
	private void retrieveDataMySql() throws SQLException, Exception {
			tileList = db.getDataMySql();
	}
	
	public List<Tile> getRowsMySql() throws SQLException, Exception {
		retrieveDataMySql();
		return tileList;
	}
	
	public int getRowCountMySql() throws SQLException, Exception {
			return db.getRowCountMySql();
	}

}
