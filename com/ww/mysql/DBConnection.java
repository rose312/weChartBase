package com.ww.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import com.sina.sae.util.SaeUserInfo;

/**
 * ���ݿ����Ӳ�MYSQL
 * 
 * @author Administrator
 * 
 */
public class DBConnection {

	/**
	 * �������ݿ�
	 * 
	 * @return
	 */
	public static Connection getDBConnection() {
		// 1. ע������
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ��ȡ���ݿ������

		String host = "w.rdc.sae.sina.com.cn";
		String port = "3307";
		String username = SaeUserInfo.getAccessKey();
		String password = SaeUserInfo.getSecretKey();

		String dbName = "app_rose315"; // SaeUserInfo.getAppName();
		System.out.println(dbName + username + password);
		// JDBC URL
		String url = String.format("jdbc:mysql://%s:%s/%s", host, port, dbName);
		try {
			Connection conn = java.sql.DriverManager.getConnection(url,
					username, password);
			return conn;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
