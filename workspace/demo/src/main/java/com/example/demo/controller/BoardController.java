package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.PageDTO;
import com.example.demo.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@GetMapping("list")
	public void list(Model model) {
		List<BoardDTO> list = service.getList();
		model.addAttribute("list",list);
		model.addAttribute("pageMaker", new PageDTO(service.getTotal(), 1));
	}
}














