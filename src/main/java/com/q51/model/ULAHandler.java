package com.q51.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.q51.gui.Constants;

public class ULAHandler {

	private ULA ula;

	public ULAHandler() {
		readJson();
	}

	public void writeJson() {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().serializeNulls();
		Gson gson = builder.create();
		FileWriter writer;
		try {
			writer = new FileWriter(new File(Constants.JSON_FILENAME));
			gson.toJson(ula, writer);
			writer.close();
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void readJson() {
		ula = null;
		File f = new File(Constants.JSON_FILENAME);
		if (f.isFile() && f.canRead()) {
			try {
				Stream<String> stream = Files.lines(Paths.get(Constants.JSON_FILENAME));
				final StringBuilder builder = new StringBuilder();
				stream.forEach((val) -> {
					builder.append(val);
				});
				String concatenatedString = builder.toString();
				Gson gson = new Gson();
				ula = gson.fromJson(concatenatedString, ULA.class);
				try {

				} finally {
					stream.close();
				}
			} catch (IOException e) {
				System.err.println("Error: " + e.getMessage());
			}
		} else {
			ula = new ULA();
		}
	}

	public ULA getUla() {
		return ula;
	}

	public void setUla(ULA ula) {
		this.ula = ula;
	}
}
