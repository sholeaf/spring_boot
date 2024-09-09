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
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@GetMapping("list")
	public void list(Criteria cri, HttpServletRequest req, Model model) {
		List<BoardDTO> list = service.getList(cri);
		model.addAttribute("list",list);
		model.addAttribute("pageMaker", new PageDTO(service.getTotal(cri), cri));

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
			long boardnum = service.getLastNum(board.getUserid());
			return "redirect:/board/get?boardnum="+boardnum;
		}
		else {
			return "redirect:/board/list";
		}
	}
	
	@GetMapping("get")
	public String get(Criteria cri, long boardnum, HttpServletRequest req, HttpServletResponse resp, Model model) {
		//list 에서 보던 곳으로 돌아가기 위한 cri 세팅
		model.addAttribute("cri",cri);
		HttpSession session = req.getSession();
		//현재 로그인 된 사람의 아이디
		String loginUser = (String)session.getAttribute("loginUser");
		//넘겨진 boardnum에 해당하는 게시글 데이터 검색
		BoardDTO board = service.getDetail(boardnum);
		
		Cookie[] cookies = req.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				//get으로 넘어온 때가 작성 완료 후 넘어왔다면, 알럿을 띄워주기 위한 w 쿠키 검사 후 모델에 추가
				if(cookie.getName().equals("w")) {
					model.addAttribute("w",cookie.getValue());
					break;
				}
			}
		}
		//내 게시글이 아닌 다른 사람의 게시글을 봤을 때
		if(!board.getUserid().equals(loginUser)) {
			Cookie read_board = null;
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					//ex) 1번 게시글을 조회하고자 클릭했을 때에는 "read_board1" 쿠키를 찾음
					if(cookie.getName().equals("read_board"+boardnum)) {
						read_board = cookie;
						break;
					}
				}
			}
			
			//read_board가 null이라는 뜻은 위에서 쿠키를 찾았을 때 존재하지 않았다는 뜻
			//첫 조회거나 조회한지 1시간이 지난 후
			if(read_board == null) {
				service.increaseReadCount(boardnum);
				//read_board1 이름의 쿠키(유효기간:3600초)를 생성해서 클라이언트에 저장
				Cookie cookie = new Cookie("read_board"+boardnum, "r");
				cookie.setPath("/");
				cookie.setMaxAge(3600);
				resp.addCookie(cookie);
				board.setReadcount(board.getReadcount()+1);
			}
		}
		model.addAttribute("board",board);
		return "/board/get";
	}
	
	@GetMapping("modify")
	public void modify(Criteria cri, long boardnum, Model model) {
		model.addAttribute("board",service.getDetail(boardnum));
		model.addAttribute("cri",cri);
	}
	
	@PostMapping("modify")
	public String modify(BoardDTO board,Criteria cri) {
		if(service.modify(board)) {
			
		}
		else {
			
		}
		return "redirect:/board/get"+cri.getListLink()+"&boardnum="+board.getBoardnum();
	}
	
	@GetMapping("remove")
	public String remove(Criteria cri, long boardnum,HttpServletRequest req) {
		String loginUser = (String)req.getSession().getAttribute("loginUser");
		BoardDTO board = service.getDetail(boardnum);
		if(board.getUserid().equals(loginUser)) {
			if(service.remove(boardnum)) {
				return "redirect:/board/list"+cri.getListLink();
			}
		}
		return "redirect:/board/get"+cri.getListLink()+"&boardnum="+boardnum;
	}
}














