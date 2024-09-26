package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.PBoardDTO;
import com.example.demo.service.PBoardService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/pboard/*")
public class PBoardController {

	@Autowired
	private PBoardService pbservice;

	@GetMapping("list")
	public void getPBoard(HttpServletRequest req, Model model) {
		Long boardnum = pbservice.getStartnum();
		ArrayList<PBoardDTO> list = pbservice.getList(boardnum, 12);
		Long lastBoardnum = list.get(list.size() - 1).getBoardnum();
		
		model.addAttribute("lastBoardnum", lastBoardnum);
		model.addAttribute("list", list);

		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("w")) {
					model.addAttribute("w", cookie.getValue());
					break;
				}
			}
		}
		System.out.println("초기 BOARDNUM : " + boardnum);
		System.out.println(pbservice.getList(boardnum, 12));// 데이터 제대로 담겨있음
	}

	@GetMapping("loadMore")
	@ResponseBody
	public List<PBoardDTO> loadMorePBoard(@RequestParam(required = false) Long lastBoardnum) {
		List<PBoardDTO> list = pbservice.getList(lastBoardnum != null ? lastBoardnum - 1 : 0L, 12);
		System.out.println("loadmore 에서 초기 보드넘" + lastBoardnum);
		System.out.println(list);
		return list;
	}

	@PostMapping("write")
	public String write(PBoardDTO pboard, MultipartFile[] files, HttpServletResponse resp) throws Exception {
		if(files == null) {
			Cookie cookie = new Cookie("w", "n");
			cookie.setPath("/");
			cookie.setMaxAge(1);
			resp.addCookie(cookie);
			return "redirect:/pboard/list";
		}
		
		if (pbservice.regist(pboard, files)) {
			Cookie cookie = new Cookie("w", "t");
			cookie.setPath("/");
			cookie.setMaxAge(1);
			resp.addCookie(cookie);
		}

		else {
			Cookie cookie = new Cookie("w", "f");
			cookie.setPath("/");
			cookie.setMaxAge(1);
			resp.addCookie(cookie);
		}
		return "redirect:/pboard/list";
	}
}
