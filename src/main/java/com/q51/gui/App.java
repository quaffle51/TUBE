package com.q51.gui; 

import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame("Reverse Engineering of TUBE ULA");
			}
		});
	}
}
