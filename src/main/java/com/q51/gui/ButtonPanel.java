package com.q51.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

// Panel to hold buttons that perform actions on the application

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel {

	private JPanel buttonPanel;
	private ButtonPanelListener buttonPanelListener;

	public ButtonPanel(String title, LocationPanel locationPanel) {

		setButtonPanel(new JPanel());

		// Add a border around this panel with the given title
		setBorder(BorderFactory.createTitledBorder(title));

		// Save the current state of the application to file

		String imgLocation;

		JButton goUpBtn = new JButton();
		goUpBtn.setName("UP");
		goUpBtn.setMnemonic(KeyEvent.VK_UP);
		goUpBtn.setToolTipText("Display the matrix cell from the row above");
		imgLocation = Constants.IMAGE_DIRECTORY_PREFIX + "Up24.gif";
		goUpBtn.setIcon(new ImageIcon(imgLocation, "Display the matrix cell from the row above"));
		goUpBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionHandler(e);
			}
		});

		// Load the save state of the application:
		JButton goLeftBtn = new JButton();
		goLeftBtn.setName("LEFT");
		goLeftBtn.setMnemonic(KeyEvent.VK_LEFT);
		goLeftBtn.setToolTipText("Display the matrix cell from the column on the left");
		imgLocation = Constants.IMAGE_DIRECTORY_PREFIX + "Back24.gif";
		goLeftBtn.setIcon(new ImageIcon(imgLocation, "Load matrix cell in the column to the left"));
		goLeftBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionHandler(e);
			}
		});

		// Reset the current matrix-cell to an "empty" cell
		JButton goDownBtn = new JButton();
		goDownBtn.setName("DOWN");
		goDownBtn.setMnemonic(KeyEvent.VK_DOWN);
		goDownBtn.setToolTipText("Display the matrix cell from the row below");
		imgLocation = Constants.IMAGE_DIRECTORY_PREFIX + "Down24.gif";
		goDownBtn.setIcon(new ImageIcon(imgLocation, "Display the matrix cell from the row below"));
		goDownBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionHandler(e);
			}
		});

		// "Tidy up" and exit the application
		JButton goRightBtn = new JButton();
		goRightBtn.setName("RIGHT");
		goRightBtn.setMnemonic(KeyEvent.VK_RIGHT);
		goRightBtn.setToolTipText("Display the matrix cell from the column on the right");
		imgLocation = Constants.IMAGE_DIRECTORY_PREFIX + "Forward24.gif";
		goRightBtn.setIcon(new ImageIcon(imgLocation, "Load matrix cell in the column to the Right"));
		goRightBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionHandler(e);
			}
		});

		setLayout(new FlowLayout(FlowLayout.CENTER, 26, 4));
		add(goLeftBtn);
		add(goUpBtn);
		add(goDownBtn);
		add(goRightBtn);
	}

	protected void actionHandler(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();

		int indexRows = -1;
		int indexCols = -1;

		JComboBox<?> cellRowCombo = null;
		JComboBox<?> cellColCombo = null;

		switch (clicked.getName()) {
		case "UP":
			cellRowCombo = LocationPanel.getCellRowLst();
			indexRows = ((ComboCategory) cellRowCombo.getSelectedItem()).getID();
			indexRows = (indexRows + 1) % cellRowCombo.getItemCount();
			break;
		case "DOWN":
			cellRowCombo = LocationPanel.getCellRowLst();
			indexRows = ((ComboCategory) cellRowCombo.getSelectedItem()).getID();
			indexRows = (indexRows + cellRowCombo.getItemCount() - 1) % cellRowCombo.getItemCount();
			break;
		case "LEFT":
			cellColCombo = LocationPanel.getCellColLst();
			indexCols = ((ComboCategory) cellColCombo.getSelectedItem()).getID();
			indexCols = (indexCols + cellColCombo.getItemCount() - 1) % cellColCombo.getItemCount();
			break;
		case "RIGHT":
			cellColCombo = LocationPanel.getCellColLst();
			indexCols = ((ComboCategory) cellColCombo.getSelectedItem()).getID();
			indexCols = (indexCols + 1) % cellColCombo.getItemCount();
			break;
		default:
			System.err.println("Error: Unknown button clicked!");
		}

		ButtonPanelEvent ev = new ButtonPanelEvent(this, cellColCombo, cellRowCombo, indexCols, indexRows);

		if (buttonPanelListener != null) {
			buttonPanelListener.buttonPanelEventOccurred(ev);
		}
	}

	public JPanel getActionPanel() {
		return buttonPanel;
	}

	public void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	public void setButtonPanelListener(ButtonPanelListener listener) {
		this.buttonPanelListener = listener;
	}

}

