package com.ww.test;

import java.sql.Connection;
import java.sql.SQLException;

import com.sina.sae.util.SaeUserInfo;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			String stockSeq = "123456";
			int len = 20 - stockSeq.length();
			System.out.println(len+"hello gitHub"+stockSeq);

			while (len > 0) {
				stockSeq = "0" + stockSeq;
				len--;
			}

			getDBConnection();
		System.out.println(len+"hello gitHub"+stockSeq);
		
		System.out.println(getCurrentJavaSqlDate("1400038103"));

	}
	public static java.sql.Date getCurrentJavaSqlDate(String date) {
		java.util.Date today = new java.util.Date();
		return new java.sql.Date(Long.parseLong(date)*1000);
	}
	
	
	
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

		String host = "localhost";
		String port = "3306";
		String username = "root";
		String password =  "123456";
//		
//		用户名　 : SAE_MYSQL_USER
//		密　　码 : SAE_MYSQL_PASS
//		主库域名 : SAE_MYSQL_HOST_M
//		从库域名 : SAE_MYSQL_HOST_S
//		端　　口 : SAE_MYSQL_PORT
//		数据库名 : SAE_MYSQL_DB
//		
		
		System.out.println("username......."+username);
		System.out.println("username......."+password);


		String dbName = "mysql"; // SaeUserInfo.getAppName();
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
