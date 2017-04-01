package com.lxl.wuziqi;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GoPanel extends JPanel {

	BufferedImage image;
	List<Piece> piecesList = new ArrayList<>();

	public GoPanel() {
		try {
			image = ImageIO.read(new File("board.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		addMouseListener(new PanelListener());
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		// 棋盘
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		// 棋子
		for (Piece piece : piecesList) {
			piece.draw(g);
		}
	}

	/**
	 * 面板中鼠标事件的监听（适配器模式）
	 * 
	 * @author litong
	 *
	 */
	class PanelListener extends MouseAdapter {
		boolean isWhite = true;

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			int x = e.getX();
			int y = e.getY();
//			int px = (x - (x - 15) / 30 * 30 - 15) > 13 ? ((x - 15) / 30 * 30 + 15 + 26) : ((x - 15) / 30 * 30 + 15);
//			int py = (y - (y - 15) / 30 * 30 - 15) > 13 ? ((y - 15) / 30 * 30 + 15 + 26) : ((y - 15) / 30 * 30 + 15);
			Piece piece = new Piece(x, y);
			System.out.println(piece.toString());
			if (!piecesList.contains(piece)) {
				piece.setWhite(isWhite);
				piecesList.add(piece);
				isWhite = !isWhite;
			}
			// 重绘
			repaint();
			//局部 
			//图形的绘制
			//repaint(x, y, width, height)
		}
	}
}
