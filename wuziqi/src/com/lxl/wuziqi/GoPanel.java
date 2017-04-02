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
	ArrayList<Piece> piecesList = new ArrayList<>();

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
			//判断输赢
//			PieceHandle.whoWin();
			int x = e.getX();
			int y = e.getY();
			int px = (x - (x - 15) / 26 * 26 - 15) > 13 ? ((x - 15) / 26 + 1) * 26 + 15 - 10: ((x - 15) / 26 * 26 + 15) - 10;
			int py = (y - (y - 15) / 26 * 26 - 15) > 13 ? ((y - 15) / 26 + 1) * 26 + 15 - 10: ((y - 15) / 26 * 26 + 15) - 10;
			Piece piece = new Piece(px, py);
			if (!piecesList.contains(piece)) {
				//人落子
				piece.setWhite(isWhite);
				piecesList.add(piece);
//				isWhite = !isWhite;
				/**
				 * TODO:机器落子
				 * 1、找出人落子的最长路径
				 * 2、堵住这个路径
				 */
				PieceHandle.getNextPoint(piecesList);
			}
			// 重绘
			repaint();
			//局部 
			//图形的绘制
			//repaint(x, y, width, height)
		}
	}
}
