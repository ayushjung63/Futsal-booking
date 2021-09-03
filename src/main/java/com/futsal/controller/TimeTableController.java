package com.futsal.controller;

import com.futsal.entity.TimeTable;
import com.futsal.exception.ApiRequestException;
import com.futsal.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time")
public class TimeTableController {
	@Autowired
	private TimeTableService timeService;
	
	@PostMapping("")
	public TimeTable add(@RequestBody TimeTable time) {
		if(time.getTime()==null || time.getTime()=="")
			throw new ApiRequestException("Please specify time correctly");
		return timeService.add(time);
	}
	
	@GetMapping("")
	public List<TimeTable> viewAll() {
		List<TimeTable> all=timeService.showAll();
		if(all==null||all.size()==0)
			throw new ApiRequestException("Empty time frame");
		return all;
	}
	
	@PostMapping("/change/status/{id}")
	public TimeTable changeStatus(@RequestParam int id,@RequestBody String status) {
		System.out.println(id);
		System.out.println(status);
		if(timeService.checkTimeTable(id)==false)
			throw new ApiRequestException("No such time table exists.");
		if(status==""|| status==null)
			throw new ApiRequestException("Please enter new time table properly");
		return timeService.changeStatus(id, status);
	}
	
	@GetMapping("/status/{type}")
	public List<TimeTable> showByStatus(@PathVariable String type){
		List<TimeTable> all= timeService.showByStatus(type);
		if(all==null || all.size()==0)
			throw new ApiRequestException("Empty Data");
		return all;
	}
	
	@PostMapping("/change/time/{id}/{time}")
	public TimeTable changeTime(@PathVariable int id,@PathVariable String time) {
		if(timeService.checkTimeTable(id)==false)
			throw new ApiRequestException("Time Table not found");
		if(time==null || time=="")
			throw new ApiRequestException("Please give proper time table");
		return timeService.changeTime(id, time);
	}
	
	public boolean reset() {
		return timeService.resetStatus();
	}
}
