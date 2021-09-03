package com.futsal.repo;

import com.futsal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user",path = "user")
public interface UserRepo extends JpaRepository<User, Integer> {
	
	@Query("From User where contact=?1")
	User findByContact(long contact);
	
	@Query("From User where email=?1")
	User findByEmail(String email);

}
