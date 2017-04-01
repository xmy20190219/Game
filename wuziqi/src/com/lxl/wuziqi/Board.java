package com.lxl.wuziqi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Board extends JFrame{
	final int gx = 30;
	final int gy = 30;
	final int width = 600;
	final int height = 600;
	int n = 1;
	final int maxNum = 144;
	JPanel boardPanel = new JPanel();
//	JLabel label = new JLabel();
	ArrayList<Chess> chess = new ArrayList<>();
	public Board(){
		this.setTitle("五子棋");
		boardPanel.setSize(width, height);
		this.add(boardPanel, BorderLayout.CENTER);
//		label.setText("黑棋落子");
//		this.add(label, BorderLayout.NORTH);
		this.setSize(600, 600);
		this.setResizable(false);
		this.setDefaultCloseOperation(3);
		boardPanel.setBackground(Color.PINK);
		boardPanel.setLayout(new BorderLayout(0, 0));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		addMouseClick();
	}
	private void addMouseClick() {
		boardPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				int px = (x / gx) * gx + gx / 2;
				int py = (y / gy) * gy + gy / 2;
				Chess c = new Chess(px, py);
				if((n & 1) == 1){
					c.setColor(Color.BLACK);
//					label.setText("白棋落子");
				}else{
					c.setColor(Color.WHITE);
//					label.setText("黑棋落子");
				}
				if(!chess.contains(c)){
					chess.add(c);
					n++;
					repaint();
				}
			}
		});
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//绘制棋盘格，旗子
		for(int i = 0;i < 20;i++){
			g.drawLine(0, 30 * i, width, 30 * i);
			g.drawLine(30 * i, 0, 30 * i, height);
		}
		//画出旗子
		for(int i = 0; i< chess.size();i++){
			Chess ch = chess. get(i);
			g.setColor(ch.getColor());
			g.fillOval(ch.getX(), ch.getY(), 30, 30);
		}
	}
	public static void main(String[] args) {
		new Board();
	}
}
