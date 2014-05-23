package com.ww.entity;

public class Place {

	private String name;

	private Location location;

	private String address;

	private String telephone;

	private String uid;

	@Override
	public String toString() {
		return "Place [name=" + name + ", location=" + location + ", address="
				+ address + ", telephone=" + telephone + ", uid=" + uid + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
