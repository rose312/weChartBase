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

		String host = "localhost";
		String port = "3306";
		String username = "root";
		String password =  "123456";
//		
//		�û����� : SAE_MYSQL_USER
//		�ܡ����� : SAE_MYSQL_PASS
//		�������� : SAE_MYSQL_HOST_M
//		�ӿ����� : SAE_MYSQL_HOST_S
//		�ˡ����� : SAE_MYSQL_PORT
//		���ݿ��� : SAE_MYSQL_DB
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
