package com.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class OTPService {
	
	private static final int OTP_LENGTH = 4;
	
	public static String generateOtp() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }
}
