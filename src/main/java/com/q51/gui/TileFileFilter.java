package com.q51.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class TileFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		
		if (file.isDirectory()) {
			return true;
		}
		
		String name = file.getName();
		
		String extension = Utils.setFileExtension(name);
		
		if (extension == null ) {
			return false;
		}
		
		if (extension.equals("til")) {
			return true;
		}
		
		return false;
	}

	@Override
	public String getDescription() {
		return "Tile database files (*.til)";
	}

}
