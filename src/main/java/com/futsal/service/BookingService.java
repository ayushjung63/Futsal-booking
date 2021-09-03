package com.futsal.service;

import com.futsal.entity.*;
import com.futsal.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
	@Autowired
	private BookingRepo bookingRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private TimeTableService timeService;
	
	public List<Booking> allBooking(){
		return bookingRepo.findAll();
	}
	
	public Booking bookFutsal(Booking b) {
		LocalDateTime dateTime=LocalDateTime.now();
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
		String bookedDate=dateTime.format(format);
		User user=b.getUser();
				
		if(userRepo.existsById(user.getId())==false) return null;
		
		b.setBookedTime(bookedDate);
		b.setStatus(Booking.Status.BOOKED);
		timeService.changeStatus(b.getTime().getId(), "book");
		return bookingRepo.save(b);
	}
	
	public Booking.Status checkStatus(long id, Booking.Status status) {
		Booking booked=bookingRepo.getById(id);
		if(booked.getStatus()== Booking.Status.BOOKED)
			return Booking.Status.BOOKED;
		else if(booked.getStatus()== Booking.Status.CANCELLED)
			return Booking.Status.CANCELLED;
		else if(booked.getStatus()== Booking.Status.EXPIRED)
			return Booking.Status.EXPIRED;
		else
			return null;
	}
	
	public List<Booking> bookingByStatus(Booking.Status status){
		return bookingRepo.getBookingByStatus(status);
	}
	
	public List<Booking> viewMyBooking(int id){
		User user=new User();
		user.setId(id);
		return bookingRepo.findByUser(user);
	}
	
	public Booking particularBooking(long id) {
		Booking booking= bookingRepo.getById(id);
		return booking;
	}
	
	public boolean cancelFutsal(long id) {
		Booking b=bookingRepo.getById(id);
		b.setStatus(Booking.Status.CANCELLED);
		TimeTable time=timeService.changeStatus(b.getTime().getId(), "available");
		if(time!=null) return true;
		return false;
	}
	
	public boolean checkBooking(long id) {
		if(bookingRepo.existsById(id)==true) return true;
		return false;
	}
	
	public List<Booking> getTodayBooking(){
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyy");
		LocalDateTime dateTime=LocalDateTime.now();
		String current=dateTime.format(format);
		
		List<Booking> allBooking=bookingRepo.findAll();
		List<Booking> today=new ArrayList<Booking>();
		
		for(int i=0;i<allBooking.size();i++) {
			String dbTime=allBooking.get(i).getBookedTime();
			String date=dbTime.substring(0,10);
			
			if(date.equals(current)==true) {
				today.add(allBooking.get(i));
			}	
		}
		return today;
	}
	
	public boolean expireBooking() {
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyy");
		LocalDateTime dateTime=LocalDateTime.now();
		String current=dateTime.format(format);
		
		List<Booking> allBooking=allBooking();
		if(allBooking==null) return false;
		for(int i=0;i<allBooking.size();i++) {
			String bookedTime=allBooking.get(i).getBookedTime();
			String newDate=bookedTime.substring(0, 10);
			if(newDate.equals(current)==false) {
				allBooking.get(i).setStatus(Booking.Status.EXPIRED);
				timeService.changeStatus(allBooking().get(i).getTime().getId(),"available");
				bookingRepo.save(allBooking.get(i));
			}
		}
		return true;
	}
	
}
