package com.example.demo.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.dto.UserDTO;

@Controller
@RequestMapping("/ajax/*")
public class AjaxController {
	@GetMapping(value = {"ajaxTest","rank","userlist"})
	public void replace() {}

	@GetMapping("test")
//	@ResponseBody : 리턴하는 데이터가 응답의 데이터 자체라는 뜻
	@ResponseBody
	public String test() {
		return "Test Data";
	}
	
	@GetMapping("list")
	@ResponseBody
	public ArrayList<UserDTO> list() {
		ArrayList<UserDTO> list = new ArrayList<>();
		list.add(new UserDTO());
		list.get(0).setUserid("apple");
		list.get(0).setUserpw("1234");
		list.get(0).setUsername("김사과");
		
		list.add(new UserDTO());
		list.get(1).setUserid("banana");
		list.get(1).setUserpw("1234");
		list.get(1).setUsername("반하나");
		
		list.add(new UserDTO());
		list.get(2).setUserid("cherry");
		list.get(2).setUserpw("1234");
		list.get(2).setUsername("이체리");
		
		return list;
	}
}