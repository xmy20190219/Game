package com.lxl.wuziqi;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GoPanel extends JPanel {
	
	BufferedImage image;
	ArrayList<Piece> piecesListB = new ArrayList<>();
	ArrayList<Piece> piecesListW = new ArrayList<>();

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
		for (Piece piece : piecesListW) {
			piece.draw(g);
		}
		for (Piece piece : piecesListB) {
			piece.draw(g);
		}
		//TODO:落黑子
		//TODO：判断输赢有问题
		ArrayList<ArrayList<Piece>> allList = null;
		allList = PieceHandle.getAllMax(piecesListB, piecesListW);
		Collections.sort(allList, new Comparator<ArrayList<Piece>>() {

			@Override
			public int compare(ArrayList<Piece> o1, ArrayList<Piece> o2) {
				if (o1.size() > o2.size()) {
					return -1;
				} else if (o1.size() < o2.size()) {
					return 1;
				}
				return 0;
			}
		});
		for (ArrayList<Piece> list : allList) {
			if (list.size() == 5) {
				if (list.get(0).isWhite()) {
					System.out.println("白棋获胜");
				} else {
					System.out.println("黑棋获胜");
				}
			}
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
			ArrayList<ArrayList<Piece>> allList = null;
			if (!piecesListW.contains(piece)) {
				//人落子
				piece.setWhite(isWhite);
				piecesListW.add(piece);
//				isWhite = !isWhite;
				/**
				 * TODO:机器落子
				 * 1、找出人落子的最长路径
				 * 2、堵住这个路径
				 */
//				System.out.print("棋盘上所有棋子\t");
//				for (int i = 0; i < piecesListW.size(); i++) {
//					System.out.print("<" + piecesListW.get(i).getX() + "," + piecesListW.get(i).getY() + ">\t");
//				}
//				System.out.println();
			}
			//TODO:落黑子
			//TODO：判断输赢有问题
			allList = PieceHandle.getAllMax(piecesListB, piecesListW);
			Collections.sort(allList, new Comparator<ArrayList<Piece>>() {

				@Override
				public int compare(ArrayList<Piece> o1, ArrayList<Piece> o2) {
					if (o1.size() > o2.size()) {
						return -1;
					} else if (o1.size() < o2.size()) {
						return 1;
					}
					return 0;
				}
			});
			for (ArrayList<Piece> list : allList) {
				if (list.size() == 5) {
					if (list.get(0).isWhite()) {
						System.out.println("白棋获胜");
					} else {
						System.out.println("黑棋获胜");
					}
				}else{
					Piece p = PieceHandle.getNextBlackPiece(list);
					if (!piecesListB.contains(p) && !piecesListW.contains(p)) {
						p.setWhite(false);
						piecesListB.add(p);
						break;
					}
				}
			}
			// 重绘
			repaint();
			//局部 
			//图形的绘制
			//repaint(x, y, width, height)
		}
	}
}
