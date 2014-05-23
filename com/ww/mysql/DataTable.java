package com.ww.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * ���ݼ���װ
 * 
 * @author Administrator
 * 
 */
public class DataTable {

	public String[] column;// ���ֶ�
	public String[][] row; // ��ֵ
	public int rowCount = 0;// ����
	public int colCoun = 0;// ����

	public DataTable() {
		super();
	}

	public DataTable(String[] column, String[][] row, int rowCount, int colCoun) {
		super();
		this.column = column;
		this.row = row;
		this.rowCount = rowCount;
		this.colCoun = colCoun;
	}

	public void setDataTable(ArrayList<HashMap<String, String>> list) {
		rowCount = list.size();
		colCoun = list.get(0).size();
		column = new String[colCoun];
		row = new String[rowCount][colCoun];
		for (int i = 0; i < rowCount; i++) {
			Set<Map.Entry<String, String>> set = list.get(i).entrySet();
			int j = 0;
			for (Iterator<Map.Entry<String, String>> it = set.iterator(); it
					.hasNext();) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it
						.next();
				row[i][j] = entry.getValue();
				if (i == rowCount - 1) {
					column[j] = entry.getKey();
				}
				j++;
			}
		}
	}

	public String[] getColumn() {
		return column;
	}

	public void setColumn(String[] column) {
		this.column = column;
	}

	public String[][] getRow() {
		return row;
	}

	public void setRow(String[][] row) {
		this.row = row;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getColCoun() {
		return colCoun;
	}

	public void setColCoun(int colCoun) {
		this.colCoun = colCoun;
	}

}
