package com.futsal.repo;

import com.futsal.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "booking",path = "booking")
public interface BookingRepo extends JpaRepository<Booking, Long> {
	
	@Query("From Booking where user=?1 ")
	List<Booking> findByUser(User user);
	
	@Query("From Booking where status=?1")
	Booking.Status getByStatus(Booking.Status status);
	
	@Query("From Booking where status=?1")
	List<Booking> getBookingByStatus(Booking.Status status);

}
