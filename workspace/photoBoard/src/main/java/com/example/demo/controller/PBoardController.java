package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.PBoardDTO;
import com.example.demo.service.PBoardService;
import com.example.demo.service.PFileService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/pboard/*")
public class PBoardController {

	@Autowired
	private PBoardService pbservice;
	
	@Autowired
	private PFileService pfservice;

	@GetMapping("list")
	public void getPBoard(HttpServletRequest req, Model model) {
		Long boardnum = pbservice.getStartnum();
		ArrayList<PBoardDTO> list = pbservice.getList(boardnum, 12);
		Long lastBoardnum = list.get(list.size() - 1).getBoardnum();
		
//		model.addAttribute("img",pfservice.getFileByBoardnum(list));
		
		
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
	
	@GetMapping("get")
	public String getBoard(@RequestParam("boardnum") Long boardnum, Model model) {
		System.out.println(pfservice.getFilesByBoardnum(boardnum));
		System.out.println(pbservice.getBoardByBoardnum(boardnum));
		model.addAttribute("flist",pfservice.getFilesByBoardnum(boardnum));
		model.addAttribute("board",pbservice.getBoardByBoardnum(boardnum));
        //일단 보드넘을 사용해서 file의 정보와 pboard의 정보를 가져와야함.
		//나중에 reply의 정보도 가져와야함.
		return "pboard/viewPost"; // viewPost.html 템플릿 반환
    }
	
}
