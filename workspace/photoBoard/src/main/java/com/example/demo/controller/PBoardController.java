package com.example.demo.controller;

import java.util.ArrayList;
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
	public String replace(Model model) {
		model.addAttribute("list", new ArrayList<PBoardDTO>());
		return "list";
	}
	
	@GetMapping("loadMore")
	@ResponseBody
	public List<PBoardDTO> loadMorePBoard(@RequestParam(required = false) long lastBoardnum){
		return pbservice.getList(lastBoardnum != null ? lastBoardnum : 0l, 10);
	}
}
