package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.TimeMapper;
import com.example.demo.mapper.UserMapper;

@Controller
public class SampleController {
	
	@Autowired
	TimeMapper mapper;
	
	@Autowired
	UserMapper mapper2;
	
	@GetMapping("time1")
	public void time1() {
		System.out.println("test : " +mapper.getTime1());
	}
	
	@GetMapping("time2")
	public void time2() {
		System.out.println("test : " +mapper.getTime2());
	}
	
	@GetMapping("user1")
	public void user1() {
		System.out.println("test : "+mapper2.getUsernameByUserid("apple"));
	}
	
	@GetMapping("user2")
	public void user2() {
		UserDTO user = mapper2.getUserByUserid("apple");
		System.out.println("test : " +user);
	}
	
}
