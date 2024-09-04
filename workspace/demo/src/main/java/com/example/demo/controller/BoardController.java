package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.Criteria;
import com.example.demo.domain.PageDTO;
import com.example.demo.service.BoardService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@GetMapping("list")
	public void list(Criteria cri, HttpServletRequest req, Model model) {
		List<BoardDTO> list = service.getList(cri);
		model.addAttribute("list",list);
		model.addAttribute("pageMaker", new PageDTO(service.getTotal(), cri));
		Cookie[] cookies = req.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("w")) {
					model.addAttribute("w",cookie.getValue());
					break;
				}
			}
		}
	}
	
//	0. 응답해야할 것이 View인지 데이터인지
//		> @ResponseBody 어노테이션 붙이기
//		> 리턴타입은 응답할 데이터의 타입
//	1. 요청명과 View의 이름이 같은지
//		> 요청명 : /board/write / View 이름 : /board/write.html
//		> 같으면 void, 다르다면 String
//	2. 요청 보낼 때 파라미터가 있는지
//		> GET방식의 요청인 경우 쿼리스트링(?param=value)이 있는지
//		> POST방식의 요청인 경우 form 안에 input, textarea 등이 있는지
//	3. 데이터 처리가 필요한지
//		> 결과 페이지로 들고 가야 할 데이터가 있는지
//		> 시스템의 변화가 있는지
//		> 그 외에는 알아서
	
	@GetMapping("write")
	public void write(@ModelAttribute("cri") Criteria cri, Model model) {
	}
	
	@PostMapping("write")
	public String write(BoardDTO board, HttpServletResponse resp) {
		if(service.regist(board)) {
			Cookie cookie = new Cookie("w", "t");
			cookie.setPath("/");
			cookie.setMaxAge(5);
			resp.addCookie(cookie);
			return "redirect:/board/list";
		}
		else {
			return "redirect:/board/list";
		}
	}
	
	@GetMapping("get")
	public void get(Criteria cri, long boardnum, Model model) {
		model.addAttribute("cri",cri);
		model.addAttribute("board",service.getDetail(boardnum));
	}
}














