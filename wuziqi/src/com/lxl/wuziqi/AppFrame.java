package com.lxl.wuziqi;

import javax.swing.JFrame;

public class AppFrame extends JFrame {

	private  GoPanel goPanel;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法
	 */
	public AppFrame() {
		initUi();
		setVisible(true);
		
	}

	/**
	 * 初始化用户接口
	 */
	private void initUi() {
		setSize(506, 528);
		setTitle("五子棋");
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		goPanel = new GoPanel();
		add(goPanel);
	}
}
