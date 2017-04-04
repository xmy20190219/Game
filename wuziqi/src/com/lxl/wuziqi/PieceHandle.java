package com.lxl.wuziqi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PieceHandle {
	static Map<Integer, ArrayList<Piece>> map;
	static ArrayList<Piece> maxSizeList = new ArrayList<>();
	static ArrayList<Piece> pieceList;
	static ArrayList<ArrayList<Piece>> allList = new ArrayList<>();
	private static ArrayList<Piece> array1;
	private static ArrayList<Piece> array2;
	
	public static ArrayList<ArrayList<Piece>> getAllMax(ArrayList<Piece> a, ArrayList<Piece> b){
		array1 = a;
		array2 = b;
		getMaxList(a);
		getMaxList(b);
		return allList;
	}
	/**
	 * 确定电脑先一步落子位置
	 * 
	 * @param list
	 *            棋盘上现有所有棋子
	 * @return 落子坐标
	 */
	public static ArrayList<Piece> getMaxList(ArrayList<Piece> list) {

		pieceList = list;
		// 判断水平最大值最大值
		ArrayList<Piece> a = getHorizonArray();
		// System.out.print("水平最长\t");
		// for (int i = 0; i < a.size(); i++) {
		// System.out.print("<" + a.get(i).getX() + " , " + a.get(i).getY() +
		// ">" + "\t");
		// }
		// System.out.println();
		// 判断竖直最大值最大值
		a = getVerticalArray();
		// System.out.print("竖直最长\t");
		// for (int i = 0; i < a.size(); i++) {
		// System.out.print("<" + a.get(i).getX() + " , " + a.get(i).getY() +
		// ">" + "\t");
		// }
		// System.out.println();
		// 判断45°方向最大值
		a = getDiagonalArray();
		// for (int i = 0; i < a.size(); i++) {
		// System.out.print("<" + a.get(i).getX() + " , " + a.get(i).getY() +
		// ">" + "\t");
		// }
		// System.out.println();
		// for (int i = 0; i < maxSizeList.size(); i++) {
		// System.out.print("<" + maxSizeList.get(i).getX() + " , " +
		// maxSizeList.get(i).getY() + ">" + "\t");
		// }
		// System.out.println();
		return maxSizeList;
	}

	private static ArrayList<Piece> getD2MaxSubList(ArrayList<Piece> list) {
		ArrayList<Piece> temp = list;
		int x = 0;
		int y = 0;
		// 是否添加第一个元素，代表是否为第一次
		boolean flag = true;
		ArrayList<Piece> a = new ArrayList<>();
		for (int i = 0; i < temp.size(); i++) {
			if (flag) {
				a.add(temp.get(i));
			}
			x = temp.get(i).getX() - 26;
			y = temp.get(i).getY() - 26;
			Piece p = new Piece(x, y);
			while (temp.contains(p)) {
				a.add(p);
				x = p.getX() - 26;
				y = p.getY() - 26;
				p = new Piece(x, y);
			}
			if (a.size() > maxSizeList.size()) {
				maxSizeList = a;
			}
			allList.add(a);
			a = new ArrayList<>();
		}
		return maxSizeList;
	}

	/**
	 * 遍历所有点 判断是否存在下一个点（该点为右下方最近的一个点）
	 * 
	 * @return 返回45°方向最大连续子集
	 */
	private static ArrayList<Piece> getDiagonalArray() {
		ArrayList<Piece> list = pieceList;
		/**
		 * 将棋盘上的棋子进行排序 排序方式：从上到下，从左到右
		 */
		Collections.sort(list, new Comparator<Piece>() {
			// 需要改进
			@Override
			public int compare(Piece o1, Piece o2) {
				if (o1.getX() > o2.getX() && o1.getY() > o2.getY()) {
					return 1;
				}
				if (o1.getX() < o2.getX() && o1.getY() < o2.getY()) {
					return -1;
				}
				if (o1.getX() == o2.getX() && o1.getY() > o2.getY()) {
					return 1;
				}
				if (o1.getX() == o2.getX() && o1.getY() < o2.getY()) {
					return -1;
				}
				if (o1.getY() == o2.getY() && o1.getX() > o2.getX()) {
					return 1;
				}
				if (o1.getY() == o2.getY() && o1.getX() < o2.getX()) {
					return -1;
				}
				// 可以没有相等判断（因为不允许棋子落到同一个点）
				return 0;
			}
		});
		// 判读是否存在下一个点

		// 得到行的最长子集合
		ArrayList<Piece> a = getD1MaxSubList(list);
		if (a.size() > maxSizeList.size()) {
			maxSizeList = a;
		}
		a = getD2MaxSubList(list);
		if (a.size() > maxSizeList.size()) {
			maxSizeList = a;
		}
		return maxSizeList;
	}

	/**
	 * 判断输赢
	 * 
	 * @param w
	 * @param b
	 * @return 返回棋子最多的集合
	 */
	public static ArrayList<Piece> whoWin(ArrayList<Piece> w, ArrayList<Piece> b) {
		if (w.size() == 5) {
			System.out.println("白棋获胜");
			return w;
		}
		if (b.size() == 5) {
			System.out.println("黑棋获胜");
			return b;
		}
		return b.size() > w.size() ? b : w;
	}

	/**
	 * 1、得到期盼中所有水平方向上的棋子Map 2、在Map中找到最长的竖直集合
	 * 
	 * @return
	 */
	private static ArrayList<Piece> getHorizonArray() {
		/**
		 * 1、将棋子列表中的棋子以棋子的X坐标分组
		 */
		map = new HashMap<>();
		for (int i = 0; i < pieceList.size(); i++) {
			int x = pieceList.get(i).getX();
			if (map.containsKey(x)) {
				map.get(x).add(pieceList.get(i));
			} else {
				ArrayList<Piece> array = new ArrayList<>();
				array.add(pieceList.get(i));
				map.put(x, array);
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

			// for (int i = 0; i < tempList.size(); i++) {
			// System.out.println(tempList.get(i));
			// }
			// System.out.println("");

			// 将同一行的棋子以Y坐标排序
			Collections.sort(tempList, new Comparator<Piece>() {

				@Override
				public int compare(Piece o1, Piece o2) {
					if (o1.getY() > o2.getY()) {
						return 1;
					} else if (o1.getY() < o2.getY()) {
						return -1;
					} else {
						// 可以没有相等判断（因为不允许棋子落到同一个点）
						return 0;
					}
				}
			});
			// System.out.println("竖直方向总集合为");
			// for (int i = 0; i < tempList.size(); i++) {
			// System.out.print(tempList.get(i).getY() + "\t");
			// }
			// System.out.println();
			// 得到行的最长子集合
			ArrayList<Piece> a = getVMaxSubList(tempList);
			// System.out.println("竖直方向最长的子集合");
			// for (int i = 0; i < a.size(); i++) {
			// System.out.print(a.get(i).getY() + "\t");
			// }
			// System.out.println();
			if (a.size() > maxSizeList.size()) {
				maxSizeList = a;
			}
		}
		return maxSizeList;
	}

	/**
	 * 1、得到期盼中所有竖直方向上的棋子Map 2、在Map中找到最长的水平集合
	 * 
	 * @return
	 */
	private static ArrayList<Piece> getVerticalArray() {
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
			// 将同一行的棋子以X坐标排序
			Collections.sort(tempList, new Comparator<Piece>() {

				@Override
				public int compare(Piece o1, Piece o2) {
					if (o1.getX() > o2.getX()) {
						return 1;
					} else if (o1.getX() < o2.getX()) {
						return -1;
					} else {
						// 可以没有相等判断（因为不允许棋子落到同一个点）
						return 0;
					}
				}
			});
			// System.out.println("水平方向总集合为");
			// for (int i = 0; i < tempList.size(); i++) {
			// System.out.print(tempList.get(i).getX() + "\t");
			// }
			// System.out.println();
			// 得到行的最长子集合
			ArrayList<Piece> a = getHMaxSubList(tempList);
			// System.out.println("水平方向最长的子集合");
			// for (int i = 0; i < a.size(); i++) {
			// System.out.print(a.get(i).getX() + "\t");
			// }
			// System.out.println();
			if (a.size() > maxSizeList.size()) {
				maxSizeList = a;
			}
		}
		return maxSizeList;
	}

	/**
	 * 得到垂直数组中 最大连续 的集合
	 * 
	 * @param temp
	 * @return 最大连续子集合
	 */
	private static ArrayList<Piece> getVMaxSubList(ArrayList<Piece> temp) {
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
				if (temp.get(j).getY() - temp.get(k).getY() == 26) {
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
			allList.add(temp);
			return temp;
		} else if (isNoOrder) {
			ArrayList<Piece> result = new ArrayList<>();
			result.add(temp.get(0));
			allList.add(result);
			return result;
		} else {
			allList.add(max);
			return max;
		}
	}

	/**
	 * 得到水平数组中 最大连续 的集合
	 * 
	 * @param temp
	 * @return 最大连续子集合
	 */
	private static ArrayList<Piece> getHMaxSubList(ArrayList<Piece> temp) {
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
			allList.add(temp);
			return temp;
		} else if (isNoOrder) {
			ArrayList<Piece> result = new ArrayList<>();
			result.add(temp.get(0));
			allList.add(result);
			return result;
		} else {
			allList.add(max);
			return max;
		}
	}

	/**
	 * 得到45°数组中 最大连续 的集合
	 * 
	 * @param temp
	 * @return 最大连续子集合
	 */
	private static ArrayList<Piece> getD1MaxSubList(ArrayList<Piece> list) {
		ArrayList<Piece> temp = list;
		int x = 0;
		int y = 0;
		// 是否添加第一个元素，代表是否为第一次
		boolean flag = true;
		ArrayList<Piece> a = new ArrayList<>();
		for (int i = 0; i < temp.size(); i++) {
			if (flag) {
				a.add(temp.get(i));
			}
			x = temp.get(i).getX() + 26;
			y = temp.get(i).getY() + 26;
			Piece p = new Piece(x, y);
			while (temp.contains(p)) {
				a.add(p);
				x = p.getX() + 26;
				y = p.getY() + 26;
				p = new Piece(x, y);
			}
			if (a.size() > maxSizeList.size()) {
				maxSizeList = a;
			}
			allList.add(a);
			a = new ArrayList<>();
		}
		return maxSizeList;
	}

	public static Piece getNextBlackPiece(ArrayList<Piece> list) {
		if (list.size() == 1) {
			// 默认水平、右侧添加下一个黑棋
			// 如果x到达边界则向左侧添加
			int x = list.get(0).getX();
			if (x < 473) {
				x += 26;
			} else {
				x -= 26;
			}
			return new Piece(x, list.get(0).getY());
		} else {
			// 判断方向（水平、竖直、45°）
			int x = list.get(list.size() - 1).getX() - list.get(0).getX();
			int y = list.get(list.size() - 1).getY() - list.get(0).getY();
			if (x == 0) {// 竖直
				y = list.get(list.size() - 1).getY();
				if (y < 473 && y > 5) {
					y += 26;
					Piece p = new Piece(list.get(list.size() - 1).getX(), y);
					if (array1.contains(p) || array2.contains(p)) {
						y = list.get(0).getY();
					}
					return p;
				} else {
					return null;
				}
			} else if (y == 0) {// 水平
				x = list.get(list.size() - 1).getX();
				if (x < 473) {
					x += 26;
					return new Piece(x, list.get(0).getY());
				} else {
					return new Piece(list.get(0).getX() - 26, list.get(0).getY());
				}
			} else {// 45°
				x = list.get(list.size() - 1).getX();
				y = list.get(list.size() - 1).getY();
				if (x < 473 && y < 473) {
					return new Piece(x + 26, y + 26);
				} else {
					return new Piece(list.get(0).getX() - 26, list.get(0).getY() - 26);
				}
			}
		}
	}
}
