package com.futsal.controller;


import com.futsal.entity.*;
import com.futsal.exception.*;
import com.futsal.service.BookingService;
import com.futsal.service.TimeTableService;
import com.futsal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("api/booking")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	@Autowired
	private UserService userService;
	@Autowired
	private TimeTableService timeService;
	
	@GetMapping("")
	public List<Booking> allBooking(){
		List<Booking> all=bookingService.allBooking();
		if(all==null)
			throw new ApiRequestException("No Booking Available");
		return all;
	}
	
	@PostMapping("")
	public Booking bookFutsal(@RequestBody Booking b) {
		if(b.getUser()==null)
			throw new ApiRequestException("Please add user detail properly");
		if(userService.checkUser(b.getUser().getId())==false)
			throw new ApiRequestException("User doesnot exits");
		
		if(b.getTime()==null)
			throw new ApiRequestException("Time no defined properly");
		if(timeService.checkTimeTable(b.getTime().getId())==false)
			throw new ApiRequestException("No such time table exists");
		if(timeService.checkBooked(b.getTime().getId())==false)
			throw new ApiRequestException("Time Table already booked");
		
		Booking result= bookingService.bookFutsal(b);
		if(result==null)
			throw new ApiRequestException("Unable to book at this moment.Please try again later");
		return result;
	}
	
	@GetMapping("/cancel/{id}")
	public boolean cancelFutsal(@PathVariable long id) {
		if(bookingService.checkBooking(id)==false)
			throw new ApiRequestException("No such booking exists");
		if(bookingService.checkStatus(id, Booking.Status.CANCELLED)== Booking.Status.CANCELLED)
			throw new ApiRequestException("Booking already cancelled");
		return bookingService.cancelFutsal(id);
	}
	
	@GetMapping("/mybooking/{id}")
	public List<Booking> getMyBooking(@PathVariable int id){
		if(userService.checkUser(id)==false)
			throw new ApiRequestException("No such user exists. Please login an try again");
		List<Booking> myBooking= bookingService.viewMyBooking(id);
		if(myBooking==null)
			throw new ApiRequestException("No Booking Done");
		return myBooking;
	}
	
	@GetMapping("/{id}")
	public Booking getParticularBooking(@PathVariable long id) {
		if(bookingService.checkBooking(id)==false)
			throw new ApiRequestException("Sorry : No such booking Available");
		Booking booking= bookingService.particularBooking(id);
		if(booking==null)
			throw new ApiRequestException("Sorry : No such booking Available");
		else
			return booking;		
	}
	
	@GetMapping("/today")
	public List<Booking> todayBooking() {
		List<Booking> all= bookingService.getTodayBooking();
		if(all==null)
			throw new ApiRequestException("Sorry : No such booking Available");
		else
			return all;
	}
	
	@GetMapping("/status/{status}")
	public List<Booking> getByStatus(@PathVariable String status){
		List<Booking> all=null;
		if(status==null|| status=="")
			throw new ApiRequestException("Please enter status properly");
		if(status.equals("booked")) {
			System.out.println("Booking.........");
			all= bookingService.bookingByStatus(Booking.Status.BOOKED);
		}
		else if(status.equals("cancelled"))
			all= bookingService.bookingByStatus(Booking.Status.CANCELLED);
		else if(status.equals("expired"))
			all= bookingService.bookingByStatus(Booking.Status.EXPIRED);
		else
			throw new ApiRequestException("Specify proper status");
		
		if(all==null)
			throw new ApiRequestException("No booking avaibale");
		return all;
	}
	
	public boolean expireBooking() {
		return bookingService.expireBooking();
	}
	
}
