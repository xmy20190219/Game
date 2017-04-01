package com.lxl.wuziqi;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 棋子
 * 
 * @author litong
 *
 */
public class Piece {

	int size = 30;
	private int x;
	private int y;
	private boolean isWhite = true;

	/**
	 * 有参构造方法
	 * 
	 * @param x
	 * @param y
	 * @param isWhite
	 */
	public Piece(int x, int y) {
		super();
		this.x = x - size / 2;
		this.y = y - size / 2;
	}

	@Override
	public boolean equals(Object obj) {
		Piece p = (Piece) obj;
		if(this.getX() != p.getX() || this.getY() != p.getY()){
			return false;
		}
		return true;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}

	@Override
	public String toString() {
		return "Piece [x=" + x + ", y=" + y + ", isWhite=" + isWhite + "]";
	}

	/**
	 * 绘制
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {

		BufferedImage image;
		// g.setColor(isWhite ?Color.white:Color.BLACK);
		// g.fillOval(x, y, size, size);
		try {
			image = isWhite ? ImageIO.read(new File("stone_w2.png")) : ImageIO.read(new File("stone_b1.png"));
			g.drawImage(image, x, y, 30, 30, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
