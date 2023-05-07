package com.expensemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.constants.RoleConstants;
import com.expensemanager.entity.UserEntity;
import com.expensemanager.repository.UserRepository;

@RestController
@CrossOrigin
public class LoginController {
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/login")
	public ResponseEntity<ResponseBean<UserEntity>> userLogin(@RequestParam("username") String username,
																@RequestParam("password") String password) {
		ResponseBean<UserEntity> response = new ResponseBean<>();
		UserEntity user = userRepo.findByEmail(username);
		System.out.println(user.getPassword());
		
		if(!user.getPassword().equals(password)) {
			response.setMessage("Invalid Password!");
			return ResponseEntity.badRequest().body(response);
		} else {
			response.setData(user);
			response.setMessage("Login Successfull!");
			return ResponseEntity.ok(response);
		}
		
	}
}
