package com.ww.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import com.sina.sae.util.SaeUserInfo;

/**
 * 数据库连接层MYSQL
 * 
 * @author Administrator
 * 
 */
public class DBConnection {

	/**
	 * 连接数据库
	 * 
	 * @return
	 */
	public static Connection getDBConnection() {
		// 1. 注册驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 获取数据库的连接

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
