package com.futsal.entity;

public class UserDaoBuilder{
	private int id;
	private String name;
	private long contact;
	private String email;
	private String address;
	
	public UserDaoBuilder setId(int id) {
		this.id = id;
		return this;
	}
	public UserDaoBuilder setName(String name) {
		this.name = name;
		return this;
	}
	public UserDaoBuilder setContact(long contact) {
		this.contact = contact;
		return this;
	}
	public UserDaoBuilder setEmail(String email) {
		this.email = email;
		return this;
	}
	public UserDaoBuilder setAddress(String address) {
		this.address = address;
		return this;
	}
	public UserDAO getUserDAO() {
		return new UserDAO(id,name,contact,email,address);
	}
	
}
