package com.filter;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.expensemanager.entity.UserEntity;
import com.expensemanager.repository.UserRepository;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


@Component
public class AuthTokenFilter implements Filter {
	
	@Autowired
	UserRepository userRepo;


	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) (req);
		HttpServletResponse response = (HttpServletResponse) (resp);

		System.out.println("AuthTokenFilter");
//		Enumeration<String> n = request.getHeaderNames();
//
//		while (n.hasMoreElements()) {
//			System.out.println("==> " + n.nextElement());
//		}

		String url = request.getRequestURL().toString();
		System.out.println(url);

		String token = request.getHeader("token");
		System.out.println(token);
		System.out.println("Method => " + request.getMethod());
		if (url.contains("/public/")) {
			chain.doFilter(request, response);
		} else if (request.getMethod().toString().equals("options")) {
			System.out.println("PreFlight....");
			chain.doFilter(request, response);
		} else if (token == null || token.trim().length() != 16) {
			if (request.getMethod().equals("options")) {
				System.out.println("--------------");
				chain.doFilter(request, response);

			}
			System.out.println("Token is Null");
			String userJsonString = new Gson().toJson(token);

			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			out.print(userJsonString);

		} else {
			UserEntity userExist = userRepo.findByToken(token);

			if (userExist == null) {
				String userJsonString = new Gson().toJson("Invalid Access");

				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				out.print(userJsonString);

			}

			chain.doFilter(req, resp);
		}
		
	}

}
