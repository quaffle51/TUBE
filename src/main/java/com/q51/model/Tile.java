package com.q51.model;

import java.io.Serializable;

public class Tile implements Serializable {

	private static final long serialVersionUID = 2042952896965790887L;
	
	private int id; // the unique key in the database
	private String key;
	private String name;
	private char text;

	public Tile(int id, String key, String name, char text) {
		this.id = id;
		this.key = key;
		this.name = name;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getText() {
		return text;
	}

	public void setText(char text) {
		this.text = text;
	}


}
