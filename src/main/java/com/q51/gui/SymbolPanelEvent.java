package com.q51.gui;

import java.util.EventObject;

import javax.swing.Icon;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class SymbolPanelEvent extends EventObject {

	
	JRadioButton   rbSelected;
	String         name;
	JRadioButton[] rb;
	Icon[]         notSelectedIcons;
	Icon[]         selectedIcons;
	
	public SymbolPanelEvent(Object source) {
		super(source);
	}
	
	public SymbolPanelEvent(Object source, JRadioButton[] rb, JRadioButton rbSelected, String name, Icon[] notSelectedIcons, Icon[] selectedIcons) {
		super(source);
		
		this.rbSelected = rbSelected;
		this.name = name;
		this.rb = rb;
		this.notSelectedIcons = notSelectedIcons;
		this.selectedIcons = selectedIcons;
	}

	public JRadioButton getRbSelected() {
		return rbSelected;
	}

	public void setRbSelected(JRadioButton rbSelected) {
		this.rbSelected = rbSelected;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JRadioButton[] getRb() {
		return rb;
	}

	public void setRb(JRadioButton[] rb) {
		this.rb = rb;
	}

	public Icon[] getNotSelectedIcons() {
		return notSelectedIcons;
	}

	public void setNotSelectedIcons(Icon[] notSelectedIcons) {
		this.notSelectedIcons = notSelectedIcons;
	}

	public Icon[] getSelectedIcons() {
		return selectedIcons;
	}

	public void setSelectedIcons(Icon[] selectedIcons) {
		this.selectedIcons = selectedIcons;
	}
	
	

}
