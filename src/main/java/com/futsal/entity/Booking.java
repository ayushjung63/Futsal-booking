package com.futsal.entity;

import javax.persistence.*;

@Entity
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@OneToOne
	private TimeTable time;
	@OneToOne
	private User user;
	private double price;
	private String bookedTime;
	private Status status;
	public enum Status{
		BOOKED,
		CANCELLED,
		EXPIRED
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public TimeTable getTime() {
		return time;
	}
	public void setTime(TimeTable time) {
		this.time = time;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getBookedTime() {
		return bookedTime;
	}
	public void setBookedTime(String bookedTime) {
		this.bookedTime = bookedTime;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Booking [id=" + id + ", time=" + time + ", user=" + user + ", price=" + price + ", bookedTime="
				+ bookedTime + ", status=" + status + "]";
	}
	
	
	
}
