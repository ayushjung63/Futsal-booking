package com.futsal.controller;


import com.futsal.entity.*;
import com.futsal.exception.*;
import com.futsal.service.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public String register(@RequestBody User user) {

		if (user.getName() == null || user.getAddress() == null || user.getContact() == 0 || user.getEmail() == null
				|| user.getPassword() == null)
			throw new ApiRequestException("Please provide all details properly");
		System.out.println(userService.checkEmail(user.getEmail()));
		System.out.println(userService.checkContact(user.getContact()));

		if (userService.checkEmail(user.getEmail()) == true)
			throw new ApiRequestException("Account with provided email address already exists");
		if (userService.checkContact(user.getContact()) == true)
			throw new ApiRequestException("Account with provided contact detail already exists");

		if (userService.registerUser(user) == false)
			throw new ApiRequestException("Server Problem encountered. Please try again later");
		else
			return "User added Sucessfully";
	}

	@PostMapping("/login")
	public String login(@RequestBody User user) {
		if (user.getContact() == 0 || user.getPassword() == null || user.getPassword() == "")
			throw new ApiRequestException("Please provide login details correctly.");
		if (userService.checkContact(user.getContact()) == false)
			throw new ApiRequestException("No such account exists");
		UserDAO data = userService.login(user);
		if (data == null)
			throw new ApiRequestException("Bad Credential");
		return "Logged IN";
	}
	
	

	@PostMapping("/forgot")
	public String resetPassword(@RequestBody User user) {
		if (userService.checkContact(user.getContact()) == false)
			throw new ApiRequestException("No such account exists");
		if(userService.resetPassword(user)==true)
			return "Password sucessfully reset";
		return "Server Problem encountered. Please try again later";
	}
}
