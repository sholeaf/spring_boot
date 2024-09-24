package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.PBoardDTO;
import com.example.demo.service.PBoardService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pboard/*")
public class PBoardController {
	
	@Autowired
	private PBoardService pbservice;
	
	@GetMapping("list")
	public String getPBoard(Model model) {
		Long boardnum = pbservice.getStatnum();
		System.out.println(boardnum);
		model.addAttribute("list", pbservice.getList(boardnum, 10));
		System.out.println(pbservice.getList(boardnum, 10));//데이터 제대로 담겨있음
		return "pboard/list";
	}
	
	@GetMapping("loadMore")
	@ResponseBody
	public List<PBoardDTO> loadMorePBoard(@RequestParam(required = false) Long lastBoardnum){
		List<PBoardDTO> list = pbservice.getList(lastBoardnum != null ? lastBoardnum : 0, 10);
		System.out.println("loadmore 에서 마지막 보드넘"+lastBoardnum);
		System.out.println(list);
		return list;
	}
}
