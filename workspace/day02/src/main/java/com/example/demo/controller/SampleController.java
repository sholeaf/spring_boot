package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;





@Controller
public class SampleController {
	@GetMapping("a")
	public void a() {
		
	}
	
	@GetMapping("b")
	public String b(int num1, int num2, Model model) {
		int result = num1+num2;
		model.addAttribute("result", result);
		return "c";
	}
	
	@GetMapping("getCookie")
	// 클라이언트의 요청 자체를 의미하는 객체
	public void getCookie(HttpServletRequest req) {
		String check = req.getHeader("Cookie");
		
		if(check != null) {
			Cookie[] cookies = req.getCookies();
			for(int i = 0 ; i < cookies.length;i++) {
				Cookie cookie = cookies[i];
				System.out.println(cookie.getName() + " : " + cookie.getValue());
			}
		}
		else {
			System.out.println("쿠키가 존재하지 않습니다.");
		}
	}
	
	@GetMapping("setCookie")
	//클라이언트에 하는 응답 자체를 의미하는 객체, "응답"에 관한 모든 정보들을 가지고있는 객체
	public void setCookie(HttpServletResponse resp) {
		Cookie cookie1 = new Cookie("Homerunball","john_mat");
		Cookie cookie2 = new Cookie("PamatChecks","no_mat");
		
		resp.addCookie(cookie1);
		resp.addCookie(cookie2);
	}
	
	@GetMapping("updateCookie")
	public void updateCookie(HttpServletRequest req,HttpServletResponse resp) {
		String check = req.getHeader("Cookie");
		
		if(check != null) {
			Cookie[] cookies = req.getCookies();
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("PamatChecks")) {
					cookie.setValue("don't_eat");
					resp.addCookie(cookie);
					break;
				}
			}
		}
		else {
			System.out.println("쿠키가 존재하지 않습니다.");
		}
	}
	
	@GetMapping("deleteCookie")
	public void deleteCookie(HttpServletRequest req,HttpServletResponse resp) {
String check = req.getHeader("Cookie");
		
		if(check != null) {
			Cookie[] cookies = req.getCookies();
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("PamatChecks")) {
					//초 단위로 지정하고, 음수로 입력하면 브라우저 종료시 쿠키 삭제(default)
					//유효기간 한 달 : cookie.setMaxAge(60*60*24*30);
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
					break;
				}
			}
		}
		else {
			System.out.println("쿠키가 존재하지 않습니다.");
		}
	}
	
	@GetMapping("setSession")
	public void setSession(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.setAttribute("loginUser", "apple");
	}
	
	@GetMapping("getSession")
	public void getSession(HttpServletRequest req) {
		HttpSession session = req.getSession();
		System.out.println(session.getAttribute("loginUser"));
	}

	
}
