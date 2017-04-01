package com.lxl.wuziqi;

import java.awt.Color;

public class Chess {
	
	private int x;
	private int y;
	private Color color;
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Chess(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	@Override
	public boolean equals(Object obj) {
		Chess c = (Chess)obj;
		if(this.getX() != c.getX() || this.getY() != c.getY() || this.getColor().equals(c.getColor())){
			return false;
		}
		return true;
	}
	
}
