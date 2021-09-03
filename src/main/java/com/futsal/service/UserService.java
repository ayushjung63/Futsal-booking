package com.futsal.service;

import com.futsal.entity.User;
import com.futsal.entity.UserDAO;
import com.futsal.entity.UserDaoBuilder;
import com.futsal.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;
	
	public boolean registerUser(User user) {
		System.out.println(user);
		User register= userRepo.save(user);
		if(register!=null) return true;
		return false;
	}
	
	public UserDAO login(User user) {
		UserDAO loggedUser=null;
		User dbUser=userRepo.findByContact(user.getContact());
		System.out.println(dbUser);
		if(dbUser.getPassword().equals(user.getPassword())) {
			loggedUser=new UserDaoBuilder().setId(dbUser.getId()).setAddress(dbUser.getAddress())
					.setContact(dbUser.getContact()).setEmail(dbUser.getEmail()).getUserDAO();
			return loggedUser;
		} 
		return loggedUser;
	}
	
	public boolean resetPassword(User user) {
		User dbUser=userRepo.findByContact(user.getContact());
		if(dbUser!=null) {
			dbUser.setPassword(user.getPassword());
			userRepo.save(dbUser);
			return true;
		}
		return false;
	}
	
	
	public boolean checkUser(int id) {
		return userRepo.existsById(id);
	}
	
	public boolean checkEmail(String email) {
		 User user=userRepo.findByEmail(email);
		 if(user!=null) return true;
		 return false;
	}
	
	public boolean checkContact(long contact) {
		 User user= userRepo.findByContact(contact);
		 if(user!=null) return true;
		 return false;
	}
	
}
