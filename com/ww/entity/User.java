package com.ww.entity;

public class User {

	private int id;

	private String name;

	private String age;

	private String date;

	private String sex;

	private String work;

	private String where;

	private String phone;




	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + ", date="
				+ date + ", sex=" + sex + ", work=" + work + ", where=" + where
				+ ", phone=" + phone + "]";
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
