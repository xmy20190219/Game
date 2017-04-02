package com.lxl.wuziqi;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PieceHandle {
	static Map<Integer, ArrayList<Piece>> map;
	static ArrayList<Piece> maxSizeList = new ArrayList<>();
	static ArrayList<Piece> pieceList;

	/**
	 * 确定电脑先一步落子位置
	 * 
	 * @param list
	 *            棋盘上现有所有棋子
	 * @return 落子坐标
	 */
	public static Point getNextPoint(ArrayList<Piece> list) {
		pieceList = list;
		ArrayList<Piece> a = getHorizonArray();
		System.out.print("水平最长\t");
		for (int i = 0; i < a.size(); i++) {
			System.out.print("<" + a.get(i).getX() + " , " + a.get(i).getY() + ">" + "\t");
		}
		System.out.println();
		return null;
	}

	public static void whoWin() {
		getHorizonArray();
	}

	/**
	 * 1、得到期盼中所有水平方向上的棋子Map 2、在Map中找到最长的水平集合
	 * 
	 * @return
	 */
	private static ArrayList<Piece> getHorizonArray() {
		System.out.println("------------------------------------------------------------------------");
		/**
		 * 1、将棋子列表中的棋子以棋子的Y坐标分组
		 */
		map = new HashMap<>();
		for (int i = 0; i < pieceList.size(); i++) {
			int y = pieceList.get(i).getY();
			if (map.containsKey(y)) {
				map.get(y).add(pieceList.get(i));
			} else {
				ArrayList<Piece> array = new ArrayList<>();
				array.add(pieceList.get(i));
				map.put(y, array);
			}
		}
		/**
		 * 2、找出最长的连续棋子
		 */
		Set<Entry<Integer, ArrayList<Piece>>> set = map.entrySet();
		Iterator<Entry<Integer, ArrayList<Piece>>> it = set.iterator();
		while (it.hasNext()) {
			// 有几行执行几次
			Entry<Integer, ArrayList<Piece>> entry = it.next();
			ArrayList<Piece> tempList = entry.getValue();
			//将同一行的棋子以X坐标排序
			Collections.sort(tempList, new Comparator<Piece>() {

				@Override
				public int compare(Piece o1, Piece o2) {
					if (o1.getX() > o2.getX()) {
						return 1;
					} else if (o1.getX() < o2.getX()) {
						return -1;
					} else {
						return 0;
					}
				}
			});
			System.out.println("总集合为");
			for (int i = 0; i < tempList.size(); i++) {
				System.out.print(tempList.get(i).getX() + "\t");
			}
			System.out.println();
			// 得到行的最长子集合
			ArrayList<Piece> a = getMaxSubList(tempList);
			System.out.println("最长的子集合");
			for (int i = 0; i < a.size(); i++) {
				System.out.print(a.get(i).getX() + "\t");
			}
			System.out.println();
			if (a.size() > maxSizeList.size()) {
				maxSizeList = a;
			}
		}
		return maxSizeList;
	}

	/**
	 * 得到数组中 最大连续 的集合
	 * 
	 * @param temp
	 * @return 最大连续子集合
	 */
	private static ArrayList<Piece> getMaxSubList(ArrayList<Piece> temp) {
		ArrayList<Piece> max = new ArrayList<>();
		// 传入集合是否为全部递增（如：123456）
		boolean isAllOrder = true;
		// 传入集合是否为全部非递增（如：13244421）
		boolean isNoOrder = true;
		// 循环：找出最长子集
		for (int i = 0; i < temp.size(); i++) {
			int k = i;
			int start = i;
			int end = i;
			int length = 0;
			for (int j = i + 1; j < temp.size(); j++) {
				if (temp.get(j).getX() - temp.get(k).getX() == 26) {
					isNoOrder = false;
					end = j;
					k++;
					length++;
				} else {
					isAllOrder = false;
					if (length > max.size()) {
						max = new ArrayList<>();
						for (int n = start; n <= end; n++) {
							max.add(temp.get(n));
						}
					}
					break;
				}
			}
		}
		if (isAllOrder) {
			return temp;
		} else if (isNoOrder) {
			ArrayList<Piece> result = new ArrayList<>();
			result.add(temp.get(0));
			return result;
		} else {
			return max;
		}
	}
}
