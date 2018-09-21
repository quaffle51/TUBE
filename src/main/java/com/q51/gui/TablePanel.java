package com.q51.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.q51.model.Tile;

@SuppressWarnings("serial")
public class TablePanel extends JPanel {

	private JTable table;
	private TileTableModel tableModel;
	private JPopupMenu popup;
	private TileTableListener tileTableListener;
	

	public TablePanel() {

		tableModel = new TileTableModel();
		table = new JTable(tableModel);
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
		
		popup = new JPopupMenu();
		
		JMenuItem showItem = new JMenuItem("Show");
		popup.add(showItem);
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				
				int row = table.rowAtPoint(e.getPoint());
				
				table.getSelectionModel().setSelectionInterval(row, row);
				
				if (e.getButton() == MouseEvent.BUTTON3) {
					popup.show(table, e.getX(), e.getY());
				}
			}
		});
		
		showItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String key = (String) table.getValueAt(row, 1);
				if (tileTableListener != null) {
					tileTableListener.rowShown(row, key);
				}
			}
		});

		setLayout(new BorderLayout());
		
		add(new JScrollPane(table), BorderLayout.CENTER);
		
		// Add a border around this panel with the given title
		setBorder(BorderFactory.createTitledBorder("Table"));
	}
	
	public int addTile(Tile tile) {
		return tableModel.addTile(tile);
	}
	
	public void setData(List<Tile> db) {
		tableModel.setData(db);
	}

	public void refresh() {
		tableModel.fireTableDataChanged();
	}
	
	public void setTileTableListener(TileTableListener listener) {
		this.tileTableListener = listener;
	}
	
	public int getRowCount() {
		return tableModel.getRowCount();
	}

}
