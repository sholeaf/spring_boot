package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {
	@GetMapping("test1")
	public void test1(int data) {
		
	}
	
	@GetMapping("test2")
	@ResponseBody
	public int test2(int num1, int num2) {
		return num1+num2;
	}
}
