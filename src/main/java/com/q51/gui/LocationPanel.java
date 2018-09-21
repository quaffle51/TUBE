package com.q51.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LocationPanel extends JPanel {

	private static JComboBox<ComboCategory> blockRowCombo = null;
	private static JComboBox<ComboCategory> blockColCombo = null;
	private static JComboBox<ComboCategory> cellRowCombo = null;
	private static JComboBox<ComboCategory> cellColCombo = null;
	private LocationPanelListener locationPanelListener;

	public LocationPanel(String title, ImagePanel imagePanel) {

		this.setBorder(BorderFactory.createTitledBorder(title));

		// There are 9 "blocks" in the TUBE ULA image in a grid 3 blocks by 3 blocks
		final ComboCategory[] blockRows = new ComboCategory[3];
		for (int i = 0; i < blockRows.length; i++) {
			blockRows[i] = new ComboCategory(i, Integer.toString(i));
		}
		final DefaultComboBoxModel<ComboCategory> model1 = new DefaultComboBoxModel<ComboCategory>(blockRows);
		blockRowCombo = new JComboBox<ComboCategory>(model1);
		blockRowCombo.setSelectedIndex(0);
		blockRowCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionHandler();
			}
		});
		JLabel blockRowLbl = new JLabel("Block Row:");
		blockRowLbl.setDisplayedMnemonic(KeyEvent.VK_B);
		blockRowLbl.setLabelFor(blockRowCombo);

		final ComboCategory[] blockCols = new ComboCategory[3];
		for (int i = 0; i < blockCols.length; i++) {
			blockCols[i] = new ComboCategory(i, Integer.toString(i));
		}
		final DefaultComboBoxModel<ComboCategory> model2 = new DefaultComboBoxModel<ComboCategory>(blockCols);
		for (int i = 0; i < blockCols.length; i++) {
			blockRows[i] = new ComboCategory(i, Integer.toString(i));
		}
		blockColCombo = new JComboBox<ComboCategory>(model2);
		blockColCombo.setSelectedIndex(0);
		blockColCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionHandler();
			}
		});
		JLabel blockColLbl = new JLabel("Block Column:");
		blockColLbl.setDisplayedMnemonic(KeyEvent.VK_C);
		blockColLbl.setLabelFor(blockColCombo);

		// Each "block" has 11 rows of matrix-cells
		final ComboCategory[] cellRows = new ComboCategory[11];
		for (int i = 0; i < cellRows.length; i++) {
			cellRows[i] = new ComboCategory(i, Integer.toString(i));
		}
		final DefaultComboBoxModel<ComboCategory> model3 = new DefaultComboBoxModel<ComboCategory>(cellRows);
		cellRowCombo = new JComboBox<ComboCategory>(model3);
		cellRowCombo.setSelectedIndex(0);
		cellRowCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionHandler();
			}
		});
		JLabel cellRowLbl = new JLabel("Cell Row:");
		cellRowLbl.setDisplayedMnemonic(KeyEvent.VK_R);
		cellRowLbl.setLabelFor(cellRowCombo);

		final ComboCategory[] cellCols = new ComboCategory[10];
		for (int i = 0; i < cellCols.length; i++) {
			cellCols[i] = new ComboCategory(i, Integer.toString(i));
		}
		final DefaultComboBoxModel<ComboCategory> model4 = new DefaultComboBoxModel<ComboCategory>(cellCols);
		cellColCombo = new JComboBox<ComboCategory>(model4);
		cellColCombo.setSelectedIndex(0);
		cellColCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionHandler();
			}
		});
		JLabel cellColLbl = new JLabel("Cell Column:");
		cellColLbl.setDisplayedMnemonic(KeyEvent.VK_E);
		cellColLbl.setLabelFor(cellColCombo);

		setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
		// Add components to panel
		add(blockRowLbl);
		add(blockRowCombo);
		add(blockColLbl);
		add(blockColCombo);
		add(cellRowLbl);
		add(cellRowCombo);
		add(cellColLbl);
		add(cellColCombo);

	}

	private void actionHandler() {
		ComboCategory blockRow = (ComboCategory) blockRowCombo.getSelectedItem();
		ComboCategory blockCol = (ComboCategory) blockColCombo.getSelectedItem();
		ComboCategory cellRow = (ComboCategory) cellRowCombo.getSelectedItem();
		ComboCategory cellCol = (ComboCategory) cellColCombo.getSelectedItem();

		LocationPanelEvent ev = new LocationPanelEvent(this, blockRow.toString(), blockCol.toString(),
				cellRow.toString(), cellCol.toString());

		if (locationPanelListener != null) {
			locationPanelListener.locationPanelEventOccurred(ev);
		}
	}

	public static JComboBox<?> getBlockRowLst() {
		return blockRowCombo;
	}

	public static JComboBox<?> getBlockColLst() {
		return blockColCombo;
	}

	public static JComboBox<?> getCellRowLst() {
		return cellRowCombo;
	}

	public static JComboBox<?> getCellColLst() {
		return cellColCombo;
	}

	public void setLocationPanelListener(LocationPanelListener listener) {
		this.locationPanelListener = listener;
	}

}

class ComboCategory {
	private int id;
	private String text;

	public ComboCategory(int id, String text) {
		this.id = id;
		this.text = text;
	}

	public String toString() {
		return text;
	}

	public int getID() {
		return id;
	}
}
