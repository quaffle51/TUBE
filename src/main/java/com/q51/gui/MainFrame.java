package com.q51.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import com.q51.controller.Controller;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private ImagePanel imagePanel;
	private SymbolPanel symbolPanel;
	private LocationBarPanel locationBarPanel;
	private MatrixCell matrixCell;
	private Controller controller;
	private JFileChooser fileChooser;
	private TablePanel tablePanel;
	private PrefsDialog prefsDialog;
	private Preferences prefs;
	private JTabbedPane tabPane;

	public MainFrame(String title) {
		super(title);
		setJMenuBar(createMenuBar());

		symbolPanel = new SymbolPanel("Connection Symbols");
		matrixCell = new MatrixCell(symbolPanel);
		imagePanel = new ImagePanel("Images", this.matrixCell);
		locationBarPanel = new LocationBarPanel(imagePanel);
		tablePanel = new TablePanel();
		prefsDialog = new PrefsDialog(this);
		tabPane = new JTabbedPane();

		prefs = Preferences.userRoot().node("db");

		try {
			controller = new Controller();
		} catch (SQLException ex) {
			ex.getMessage();
			JOptionPane.showMessageDialog(this, "A database error has occurred with error code: " + ex.getErrorCode(),
					"Database Error: " + ex.getErrorCode(), JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Exception Error",
					JOptionPane.ERROR_MESSAGE);
		}

		try {
			controller.connectToMySql();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to database!", "Database Connection Error",
					JOptionPane.ERROR_MESSAGE);
		}

		tablePanel.setData(controller.getTiles());

		tablePanel.setTileTableListener(new TileTableListener() {
			public void rowShown(int row, String key) {
				System.out.println("Row=" + row + ", Key=" + key);
			}

		});

		prefsDialog.setPrefsListener(new PrefsListener() {
			public void preferencesSet(String user, String password, int port) {
				prefs.put("user", user);
				prefs.put("password", password);
				prefs.putInt("port", port);

			}
		});

		String user = prefs.get("user", "");
		String password = prefs.get("password", "");
		Integer port = prefs.getInt("port", 3306);
		prefsDialog.setDefaults(user, password, port);

		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new TileFileFilter());

		setLayout(new BorderLayout(20, 0));

//		add(locationBarPanel, BorderLayout.NORTH);
//		add(symbolPanel, BorderLayout.CENTER);
//		add(imagePanel, BorderLayout.SOUTH);
//		add(tablePanel, BorderLayout.EAST);
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.add(locationBarPanel, BorderLayout.NORTH);
		main.add(symbolPanel, BorderLayout.CENTER);
		main.add(imagePanel, BorderLayout.SOUTH);
		
		tabPane.add("Main", main);
		tabPane.addTab("Table", tablePanel);
		add(tabPane, BorderLayout.CENTER);


		locationBarPanel.setLocationPanelListener(new LocationPanelListener() {
			@Override
			public void locationPanelEventOccurred(LocationPanelEvent e) {
				String blockRow = e.getBlockRow();
				String blockCol = e.getBlockCol();
				String cellRow = e.getCellRow();
				String cellCol = e.getCellCol();
				String keyPrefix = "" + String.format("%02d", Integer.parseInt(blockRow))
						+ String.format("%02d", Integer.parseInt(blockCol)) + "_"
						+ String.format("%02d", Integer.parseInt(cellRow))
						+ String.format("%02d", Integer.parseInt(cellCol)) + "_";
				;

				matrixCell.updateCellImage(blockRow, blockCol, cellRow, cellCol, controller.getMapping(keyPrefix));
			}
		});

		locationBarPanel.setButtonPanelListener(new ButtonPanelListener() {
			@Override
			public void buttonPanelEventOccurred(ButtonPanelEvent e) {
				JComboBox<?> cellColCombo = e.getCellColCombo();
				JComboBox<?> cellRowCombo = e.getCellRowCombo();
				int indexCols = e.getIndexCols();
				int indexRows = e.getIndexRows();

				if (indexCols == -1) {
					cellRowCombo.setSelectedIndex(indexRows);
				} else {
					cellColCombo.setSelectedIndex(indexCols);
				}

			}
		});

		symbolPanel.setSymbolPanelListener(new SymbolPanelListener() {
			public void symbolPanelEventOccurred(SymbolPanelEvent e) {
				String name = e.getName();
				JRadioButton[] rb = e.getRb();
				JRadioButton rbSelected = e.getRbSelected();
				Icon[] notSelectedIcons = e.getNotSelectedIcons();
				Icon[] selectedIcons = e.getSelectedIcons();

				for (int i = 0; i < rb.length; i++) {
					rb[i].setIcon(notSelectedIcons[i]);
				}
				rbSelected.setIcon(selectedIcons[new Integer(name)]);
			}
		});

		matrixCell.setMatrixCellListener(new MatrixCellListener() {

			public void matrixCellEventOccurred(MatrixCellEvent e) {
				BufferedImage block_r_c_cell_r_c = e.getBlock_r_c_cell_r_c();
				String keyPrefix = e.getKeyPrefix();
				JLabel label = e.getLabel();
				Rectangle rect = e.getRect();

				switch (e.getMouseAction()) {

				case MOUSE_CLICKED:
					int x = (int) rect.getX();
					int y = (int) rect.getY();
					Point p = new Point(x / Constants.TILE_SIDE_LENGTH, y / Constants.TILE_SIDE_LENGTH);
					Boolean pad = false;
					Point[] q = Constants.getPadLocations();
					for (int i = 0; i < q.length; i++) {
						if (p.equals(q[i])) {
							pad = true;
							break;
						}
					}
					if (!pad) {
						String name = symbolPanel.getName();
						String symbol = symbolPanel.getSymbol(Integer.parseInt(name));
						matrixCell.drawConnection(rect);
						if (!name.equals("0")) {
							try {
								controller.addTileMySql(rect, keyPrefix, name, symbol.charAt(0));
							} catch (SQLException ex) {
								JOptionPane.showMessageDialog(MainFrame.this,
										"A database error has occurred with error code: " + ex.getErrorCode(),
										"Database Error: " + ex.getErrorCode(), JOptionPane.ERROR_MESSAGE);
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(MainFrame.this, "Error: " + ex.getMessage(),
										"Exception Error", JOptionPane.ERROR_MESSAGE);
							}

						} else {
							// name of connection is "0", a blank tile, so check to see if there is an
							// existing connection in this tile if so remove it.
							try {
								controller.removeTile(rect, keyPrefix);
							} catch (SQLException ex) {
								ex.getMessage();
								JOptionPane.showMessageDialog(MainFrame.this,
										"A database error has occurred with error code: " + ex.getErrorCode(),
										"Database Error: " + ex.getErrorCode(), JOptionPane.ERROR_MESSAGE);
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(MainFrame.this, "Error: " + ex.getMessage(),
										"Exception Error", JOptionPane.ERROR_MESSAGE);
							}
						}
						tablePanel.refresh();
					}
					break;

				case MOUSE_EXITED:
					new HighlightTile(label, null, block_r_c_cell_r_c, false, symbolPanel, null);
					break;

				case MOUSE_MOVED:
					new HighlightTile(label, rect, block_r_c_cell_r_c, true, symbolPanel, keyPrefix);
					break;

				default:
					System.err.println("Error: Unknown mouse action occurred!");
				}
			}
		});

		matrixCell.updateCellImage("0", "0", "0", "0", controller.getMapping("0000_0000_"));

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				try {
					controller.disconnectFromMySqy();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "An error occurred when disconnecting from the database!", "Database Closing Error", JOptionPane.ERROR_MESSAGE);
				}
				dispose();
				System.gc();
			}
		});

		setMinimumSize(new Dimension(1325, 514));
		setResizable(false);
		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem exportDataItem = new JMenuItem("Export Data...");
		JMenuItem importDataItem = new JMenuItem("Import Data...");
		JMenuItem exitItem = new JMenuItem("Exit");

		fileMenu.add(exportDataItem);
		fileMenu.add(importDataItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		JMenu windowMenu = new JMenu("Window");
		JMenu showMenu = new JMenu("Show");
		JMenuItem prefsItem = new JMenuItem("Preference...");

		JMenuItem showFormItemMatrixCell = new JCheckBoxMenuItem("Matrix Cell");
		showFormItemMatrixCell.setSelected(true);
		JMenuItem showFormItemSchematic = new JCheckBoxMenuItem("Schematic of Matrix Cell");
		showFormItemSchematic.setSelected(true);

		showMenu.add(showFormItemMatrixCell);
		showMenu.add(showFormItemSchematic);
		windowMenu.add(showMenu);
		windowMenu.add(prefsItem);

		menuBar.add(fileMenu);
		menuBar.add(windowMenu);

		prefsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prefsDialog.setVisible(true);
			}
		});

		importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

		importDataItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.loadFromFile(fileChooser.getSelectedFile());
						tablePanel.refresh();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		exportDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

		exportDataItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.saveToFile(fileChooser.getSelectedFile());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to file.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		showFormItemMatrixCell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) ev.getSource();
				imagePanel.getCellLabel().setVisible(menuItem.isSelected());
			}
		});

		showFormItemSchematic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) ev.getSource();
				imagePanel.getSchematicLabel().setVisible(menuItem.isSelected());
			}
		});

		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int action = JOptionPane.showConfirmDialog(MainFrame.this,
						"Do you really want to exit the application?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
				if (action == JOptionPane.OK_OPTION) {
					WindowListener[] listeners = getWindowListeners();
					
					for(WindowListener listener: listeners) {
						listener.windowClosing(new WindowEvent(MainFrame.this, 0));
					}
				}
			}
		});

		return menuBar;
	}

}
