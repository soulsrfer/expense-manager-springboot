package com.expensemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.constants.RoleConstants;
import com.expensemanager.entity.RoleEntity;
import com.expensemanager.entity.UserEntity;
import com.expensemanager.repository.RoleRepository;
import com.expensemanager.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@CrossOrigin
@RestController

public class UserController {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@PostMapping("/user/create")
	public ResponseEntity<ResponseBean<UserEntity>> saveUser(@RequestBody UserEntity user) {
		System.out.println("user creation!");
		ResponseBean<UserEntity> response = new ResponseBean<>();
		UserEntity user2 = userRepo.findByEmail(user.getEmail());
		RoleEntity role = roleRepo.findByName(RoleConstants.USER_ROLE);
//		System.out.println(role.getName());
		
		if(user2 == null) {
			user.setRole(role);
//			System.out.println(user.getRole());
			userRepo.save(user);
			response.setData(user);
			response.setMessage("your profile is created!");
			return ResponseEntity.ok(response);
			
		} else {
			response.setData(user2);
			response.setMessage("your profile already exists!");
			return ResponseEntity.unprocessableEntity().body(response);
		}
	}
	

	
}
