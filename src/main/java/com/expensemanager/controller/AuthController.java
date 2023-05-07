package com.expensemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.expensemanager.entity.UserEntity;
import com.expensemanager.repository.UserRepository;
import com.service.OTPService;

@RestController
@CrossOrigin
public class AuthController {
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/user/generateOTP")
	public ResponseEntity<ResponseBean<UserEntity>> generateOTP(@RequestParam("email") String email) {
		ResponseBean<UserEntity> response = new ResponseBean<>();
		UserEntity user =  userRepo.findByEmail(email);
			
		String otp = OTPService.generateOtp();
		System.out.println(otp);
		if(user == null) {
			response.setMessage("Please Enter Valid Email");
			return ResponseEntity.unprocessableEntity().body(response);
			
		}else {
			user.setOtp(otp);
			userRepo.save(user);
			response.setData(user);
			response.setMessage("Your One Time Password is : " + otp);
			return ResponseEntity.ok(response);
		}
		
		
	} 
	
	@GetMapping("/user/verifyOTP")
	public ResponseEntity<ResponseBean<UserEntity>> verifyOTP(@RequestParam("email")String email, @RequestParam("otp") String otp) {
		ResponseBean<UserEntity> response = new ResponseBean<>();
		UserEntity user =  userRepo.findByEmail(email);
		
		if(user == null) {
			response.setMessage("Please Enter Valid Email");
			response.setData(user);
			return ResponseEntity.unprocessableEntity().body(response);
		}else if(!user.getOtp().equals(otp) ) {
			System.out.println(user.getOtp());
			System.out.println(otp);
			response.setMessage("Please Enter Valid OTP");
			response.setData(user);
			return ResponseEntity.unprocessableEntity().body(response);
		}else {
			response.setMessage("Verification Successfull!");
			response.setData(user);
			return ResponseEntity.ok(response);
		}
	}
	
	@GetMapping("/user/resetPassword")
	public ResponseEntity<ResponseBean<UserEntity>> resetPassword(@RequestParam("email")String email, @RequestParam("password") String password) {
		ResponseBean<UserEntity> response = new ResponseBean<>();
		UserEntity user =  userRepo.findByEmail(email);
		
		if(user == null) {
			response.setMessage("Please Enter Valid Email");
			response.setData(user);
			return ResponseEntity.unprocessableEntity().body(response);
		}else {
			user.setPassword(password);
			userRepo.save(user);
			response.setMessage("Password Updated!");
			response.setData(user);
			return ResponseEntity.ok(response);
		}
	}
}
