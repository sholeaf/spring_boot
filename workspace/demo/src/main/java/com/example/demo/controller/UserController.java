package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user/*")
public class UserController {
	
	@Autowired
	private UserService service;
	
//	GET
//	/user/join		회원가입 페이지로 이동
//	/user/login		로그인 페이지로 이동(인덱스)
//	/user/checkId	넘겨진 파라미터로 아이디 중복 체크
//		params
//			userid		검사하고자 하는 유저의 아이디
//		returns
//			O / X
//	/user/logout	세션 로그아웃 처리
//
//	POST
//	/user/join		넘겨진 파라미터로 회원가입 처리
//		params
//	/user/login		넘겨진 파라미터로 로그인 처리
//		params
	@GetMapping("join")
	public void replace() {}
	
	@GetMapping("login")
	public String login() {
		return "redirect:/";
	}
	
	@GetMapping("checkId")
	@ResponseBody
	public String checkId(String userid) {
		if(service.checkId(userid)) {
			return "O";
		}
		else {
			return "X";
		}
	}
	
	@GetMapping("logout")
	public String logout(HttpServletRequest req) {
		req.getSession().invalidate();
		return "redirect:/";
	}
	
	
}














