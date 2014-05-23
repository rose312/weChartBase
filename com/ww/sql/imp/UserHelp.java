package com.ww.sql.imp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.sina.sae.util.SaeUserInfo;
import com.ww.entity.User;

public class UserHelp implements UserUtil {

	public UserHelp() {
	}

	@Override
	public StringBuffer getUsers(HttpServletRequest request) {

		// ��ѯuser
		List<User> userList = queryUser(request, null);
		StringBuffer buffero = new StringBuffer();
		for (User user : userList) {
			// name,age,sex,where,work,phone
			buffero.append("����:").append(user.getName()).append("\n");
			buffero.append("����:").append(user.getAge()).append("\n");
			buffero.append("�Ա�:").append(user.getSex()).append("\n");
			buffero.append("ְҵ:").append(user.getWork()).append("\n");
			buffero.append("������:").append(user.getWhere()).append("\n");
			buffero.append("�绰:").append(user.getPhone()).append("\n")
					.append("\n");
		}
		return buffero;
	}

	@Override
	public StringBuffer getUserbyId(HttpServletRequest request, String id) {
		// ��ѯuser
		List<User> userList = queryUser(request, id);
		StringBuffer buffero = new StringBuffer();
		for (User user : userList) {
			// name,age,sex,where,work,phone
			buffero.append("����:").append(user.getName()).append("\n");
			buffero.append("����:").append(user.getAge()).append("\n");
			buffero.append("�Ա�:").append(user.getSex()).append("\n");
			buffero.append("������:").append(user.getWhere()).append("\n");
			buffero.append("ְҵ:").append(user.getWork()).append("\n");
			buffero.append("�绰:").append(user.getPhone()).append("\n").append("\n");
		}
		return buffero;
	}

	/**
	 * ��ѯBAE MySQL���ݿ���user�������
	 * 
	 * @param request
	 * @return
	 */
	private static List<User> queryUser(HttpServletRequest request, String id) {
		List<User> userList = new ArrayList<User>();

		// ��request����ͷ��ȡ��IP���˿ڡ��û���������
		// ���ݿ�����
		String host = "w.rdc.sae.sina.com.cn";
		String port = "3307";
		String username = SaeUserInfo.getAccessKey();
		String password = SaeUserInfo.getSecretKey();
		String dbName = "app_rose315"; // SaeUserInfo.getAppName();
		// JDBC URL
		String url = String.format("jdbc:mysql://%s:%s/%s", host, port, dbName);
		try {
			// ����MySQL����
			Class.forName("com.mysql.jdbc.Driver");
			// ��ȡ���ݿ�����
			Connection conn = DriverManager.getConnection(url, username,
					password);
			// �����ѯSQL���
			String sql = "";
			if (isNumeric(id)) {
				if ("0".equals(id)) {
					sql = "select name,age,sex,iswhere,inwork,phone from user";
				} else {
					sql = "select name,age,sex,iswhere,inwork,phone from user where id ='"
							+ id + "'";
				}
			} else {
				sql = "select name,age,sex,iswhere,inwork,phone from user where name like'%"
						+ id + "%'";
			}
			// ����PreparedStatement���󣨰����ѱ����SQL��䣩
			PreparedStatement ps = conn.prepareStatement(sql);
			// ִ�в�ѯ����ȡ�����
			ResultSet rs = ps.executeQuery();
			// ������ѯ�����
			while (rs.next()) {
				User user = new User();
				user.setName(rs.getString("name"));
				user.setAge(rs.getString("age"));
				user.setSex(rs.getString("sex"));
				user.setWhere(rs.getString("iswhere"));
				user.setWork(rs.getString("inwork"));
				user.setPhone(rs.getString("phone"));
				userList.add(user);
			}

			// �ر����ӣ��ͷ���Դ
			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

}
