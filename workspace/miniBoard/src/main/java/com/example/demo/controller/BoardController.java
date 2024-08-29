package com.example.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.mapper.BoardMapper;
import com.example.demo.model.dto.BoardDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	BoardMapper bmapper;
	
	// /views/user/joinview.jsp
	// 여러 요청 주소를 하나의 메소드에 매핑하는 방법
	@GetMapping(value = { "writeview"})
	public void replace() {
	}

	// 데이터 수집 -> 처리 -> 응답 생성 및 응답하기
	@PostMapping("writeOk")
	public String writeOk(BoardDTO board, HttpServletResponse resp) throws Exception {

		if (bmapper.insertBoard(board) == 1) {
			Cookie cookie = new Cookie("w", "t");
			cookie.setMaxAge(1);
			cookie.setPath("/");
			resp.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("w", "f");
			cookie.setMaxAge(1);
			cookie.setPath("/");
			resp.addCookie(cookie);
		}
		// forward(길 찾는 사람 손 잡고 함께 가기)
		// ex) return "/user/main";
		// /user/main.jsp 를 해석해서 바로 응답

		// redirect(길 찾는 사람에게 가는 방법 알려주기)
		// ex) return "redirect:/user/main";
		// /user/main 으로 재요청 유도
		return "redirect:/user/main";
	}

	// 서버사이드 랜더링
	@GetMapping(value={"getview","update"})
	public void getview(int boardnum, Model model) throws Exception {
		model.addAttribute("board", bmapper.getBoardByNum(boardnum));
	}

	// 클라이언트 사이드 랜더링
	@GetMapping("getDetail")
	@ResponseBody
	public BoardDTO getDetail(int boardnum) throws Exception {
		return bmapper.getBoardByNum(boardnum);
	}

	

	@PostMapping("updateOk")
	public String updateOk(BoardDTO board,HttpServletResponse resp) throws Exception {
		
		if (bmapper.updateBoard(board) == 1) {
			Cookie cookie = new Cookie("u", "t");
			cookie.setMaxAge(1);
			cookie.setPath("/");
			resp.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("u", "f");
			cookie.setMaxAge(1);
			cookie.setPath("/");
			resp.addCookie(cookie);
		}
		
		return "redirect:/board/getview?boardnum="+board.getBoardnum();

	}
	@GetMapping("delete")
	public String delete(int boardnum,HttpServletResponse resp) throws Exception {

		if (bmapper.deleteBoardByNum(boardnum) == 1) {
			Cookie cookie = new Cookie("d", "t");
			cookie.setMaxAge(1);
			cookie.setPath("/");
			resp.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("d", "f");
			cookie.setMaxAge(1);
			cookie.setPath("/");
			resp.addCookie(cookie);
		}
		
		return "redirect:/user/main";
	}
}
