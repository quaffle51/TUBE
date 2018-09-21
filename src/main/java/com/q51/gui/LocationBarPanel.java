package com.q51.gui;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class LocationBarPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private LocationPanel locationPanel;
	private ButtonPanel buttonPanel;

	public LocationBarPanel(ImagePanel imagePanel) {
		locationPanel = new LocationPanel("Go to a specific location within the ULA:", imagePanel);
		buttonPanel = new ButtonPanel("Move to an adjacent cell within within this Block:", this.locationPanel);
		setLayout(new FlowLayout(FlowLayout.CENTER, 24, 20));
		add(locationPanel);
		add(buttonPanel);
		setBorder(BorderFactory.createTitledBorder("Locations:"));
	}

	public void setLocationPanelListener(LocationPanelListener listener) {
		locationPanel.setLocationPanelListener(listener);
	}

	public void setButtonPanelListener(ButtonPanelListener listener) {
		buttonPanel.setButtonPanelListener(listener);
	}

}
