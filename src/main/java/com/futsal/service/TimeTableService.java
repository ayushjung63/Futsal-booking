package com.futsal.service;

import com.futsal.entity.TimeTable;
import com.futsal.entity.TimeTable.Status;
import com.futsal.repo.TimeTableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TimeTableService {
	@Autowired
	private TimeTableRepo timeRepo;
	
	public TimeTable add(TimeTable time) {
		LocalDateTime dateTime=LocalDateTime.now();
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyy");
		String current=dateTime.format(format);
		
		time.setDate(current);
		time.setStatus(Status.AVAILABLE);
		return timeRepo.save(time);
	}
	
	public List<TimeTable> showAll() {
		return timeRepo.findAll();
	}
	
	public boolean checkTimeTable(int id) {
		return timeRepo.existsById(id);
	}
	
	public TimeTable changeTime(int id,String time) {
		TimeTable dbTime=timeRepo.getById(id);
		dbTime.setTime(time);
		return timeRepo.save(dbTime);
	}
	
	public List<TimeTable> showByStatus(String status){
		List<TimeTable> time=null;
		if(status.equals("booked"))
			time = timeRepo.findByStatus(Status.BOOKED);
		else if(status.equals("available"))
			time=timeRepo.findByStatus(Status.AVAILABLE);
		else 
			return time;
		return time;
	}
	
	public boolean checkBooked(int id) {
		TimeTable response= timeRepo.getById(id) ;
		if(response.getStatus()==Status.AVAILABLE) return true;
		else return false;
	}
	
	public TimeTable changeStatus(int id,String book) {
		TimeTable response= timeRepo.getById(id) ;
		if(book.equals("available")) {
			response.setStatus(Status.AVAILABLE);
			timeRepo.save(response);
		}
		else if(book.equals("book")) {
			response.setStatus(Status.BOOKED);
			timeRepo.save(response);
		}
		else {
			return response;
		}
		return response;
	}
	
	public boolean resetStatus() {
		LocalDateTime dateTime=LocalDateTime.now();
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyy");
		String current=dateTime.format(format);
		
		List<TimeTable> all=timeRepo.findAll();
		if(all==null) return false;
		for(int i=0;i<all.size();i++) {
			String dbTime=all.get(i).getDate();
			String date=dbTime.substring(0,10);
			if(current.equals(date)==false) {
				all.get(i).setStatus(Status.AVAILABLE);
				all.get(i).setDate(current);
				timeRepo.save(all.get(i));
			}
		}
		return true;
	}
}
