package com.futsal.repo;

import com.futsal.entity.TimeTable;
import com.futsal.entity.TimeTable.Status;
import com.futsal.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "timetable",path = "timetable")
public interface TimeTableRepo extends JpaRepository<TimeTable,Integer> {

	@Query("From TimeTable where status=?1")
	List<TimeTable> findByStatus(TimeTable.Status status);
	
	

}
