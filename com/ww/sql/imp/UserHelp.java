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

		// 查询user
		List<User> userList = queryUser(request, null);
		StringBuffer buffero = new StringBuffer();
		for (User user : userList) {
			// name,age,sex,where,work,phone
			buffero.append("姓名:").append(user.getName()).append("\n");
			buffero.append("年龄:").append(user.getAge()).append("\n");
			buffero.append("性别:").append(user.getSex()).append("\n");
			buffero.append("职业:").append(user.getWork()).append("\n");
			buffero.append("在哪里:").append(user.getWhere()).append("\n");
			buffero.append("电话:").append(user.getPhone()).append("\n")
					.append("\n");
		}
		return buffero;
	}

	@Override
	public StringBuffer getUserbyId(HttpServletRequest request, String id) {
		// 查询user
		List<User> userList = queryUser(request, id);
		StringBuffer buffero = new StringBuffer();
		for (User user : userList) {
			// name,age,sex,where,work,phone
			buffero.append("姓名:").append(user.getName()).append("\n");
			buffero.append("年龄:").append(user.getAge()).append("\n");
			buffero.append("性别:").append(user.getSex()).append("\n");
			buffero.append("在哪里:").append(user.getWhere()).append("\n");
			buffero.append("职业:").append(user.getWork()).append("\n");
			buffero.append("电话:").append(user.getPhone()).append("\n").append("\n");
		}
		return buffero;
	}

	/**
	 * 查询BAE MySQL数据库中user表的数据
	 * 
	 * @param request
	 * @return
	 */
	private static List<User> queryUser(HttpServletRequest request, String id) {
		List<User> userList = new ArrayList<User>();

		// 从request请求头中取出IP、端口、用户名和密码
		// 数据库名称
		String host = "w.rdc.sae.sina.com.cn";
		String port = "3307";
		String username = SaeUserInfo.getAccessKey();
		String password = SaeUserInfo.getSecretKey();
		String dbName = "app_rose315"; // SaeUserInfo.getAppName();
		// JDBC URL
		String url = String.format("jdbc:mysql://%s:%s/%s", host, port, dbName);
		try {
			// 加载MySQL驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 获取数据库连接
			Connection conn = DriverManager.getConnection(url, username,
					password);
			// 定义查询SQL语句
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
			// 创建PreparedStatement对象（包含已编译的SQL语句）
			PreparedStatement ps = conn.prepareStatement(sql);
			// 执行查询并获取结果集
			ResultSet rs = ps.executeQuery();
			// 遍历查询结果集
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

			// 关闭连接，释放资源
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
