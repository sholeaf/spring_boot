package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.mapper.BoardMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.UserDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Controller
@RequestMapping("/user/*")
public class UserController {

	@Autowired
	UserMapper umapper;
	
	@Autowired
	BoardMapper bmapper;
	
	//	/views/user/joinview.jsp
	//여러 요청 주소를 하나의 메소드에 매핑하는 방법
	@GetMapping(value = {"joinview","loginview"})
	public void replace() {}
	
	@PostMapping("joinOk")
	public String joinOk(UserDTO dto, HttpServletResponse resp ) throws Exception {
		
		
		if(umapper.insertUser(dto) == 1) {
			Cookie cookie = new Cookie("joinid", dto.getUserid());
			cookie.setMaxAge(60*5);
			resp.addCookie(cookie);
			return "redirect:/user/loginview";
		}
		else {
			return "/user/joinview";
		}
	}
	@PostMapping("loginOk")
	public String loginOk(String userid, String userpw, HttpServletRequest req) throws Exception{
		UserDTO user = umapper.getUserByUserid(userid);
		
		if(user != null) {
			if(user.getUserpw().equals(userpw)) {
				req.getSession().setAttribute("loginUser", user.getUserid());
				return "redirect:/user/main";
			}
		}
		return "/user/loginview";
	}
	
	@GetMapping("logout")
	public String logout(HttpServletRequest req) {
//		특정키에 해당하는 데이터만 지우기
//		req.getSession().removeAttribute("loginUser");
		
//		초기화
		req.getSession().invalidate();
		return "user/loginview";
	}
	
	@GetMapping("checkId")
	@ResponseBody
	public String checkId(String userid) throws Exception{
		UserDTO user = umapper.getUserByUserid(userid);
		
		if(user != null) {
			return "X";
		}
		else {
			return "O";
		}
	}
	
	@GetMapping("main")
	public String main(Model model) throws Exception {
		model.addAttribute("list",bmapper.getList());
		return "/user/main";
	}
}
