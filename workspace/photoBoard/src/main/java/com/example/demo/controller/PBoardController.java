package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import jakarta.servlet.http.HttpServlet;
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
		// lastBoardnum -1 은 오류 발생할 수 있음 수정 해야함
		// 수정 한다면 lastBoardnum 다음 값 select문으로 Boardnum < lastBoardnum limit 1 이런식으로 하나
		// 가져와서 그값을 초기 보드넘으로
		// 수정완료 되면
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
		model.addAttribute("flist", pfservice.getFilesByBoardnum(boardnum));
		model.addAttribute("board", pbservice.getBoardByBoardnum(boardnum));
		// 일단 보드넘을 사용해서 file의 정보와 pboard의 정보를 가져와야함.
		// 나중에 reply의 정보도 가져와야함.
		return "pboard/viewPost"; // viewPost.html 템플릿 반환
	}

	@PostMapping("modify")
	public ResponseEntity<String> modifyView(@RequestParam("boardnum") Long boardnum, @RequestParam("boardtitle") String boardtitle,
																	  @RequestParam("boardcontents") String boardcontents,
																	  @RequestParam(value = "delete",  required = false) String[] delete,
																	  @RequestParam(value = "files",  required = false) MultipartFile[] files 
																	  ) throws Exception {
		System.out.println("modify 진입");
		System.out.println(files);
		if(files != null && files.length != 0) {
			for(int i = 0; i < files.length; i++) {
				System.out.println(files[i]);
			}
			System.out.println("files if문 진입");
			if(!pfservice.insert(boardnum, files)) {				
				System.out.println("pfservice.insert 실패");
				return ResponseEntity.ok("수정 실패."); 
			}
		}
		if(delete != null && delete.length != 0) {		
			System.out.println("delete진입");
			if(!pfservice.delete(boardnum,delete)) {				
				System.out.println("pfservice.delete 실패");
				return ResponseEntity.ok("수정 실패."); 
			}
		}
		
		PBoardDTO updateBoard = new PBoardDTO();
		updateBoard.setBoardnum(boardnum);
		updateBoard.setBoardtitle(boardtitle);
		updateBoard.setBoardcontents(boardcontents);
		
		// update service 코드
		if(pbservice.modify(updateBoard)) {
			
		}
		else {
			 return ResponseEntity.ok("수정 실패."); 
		}


		 return ResponseEntity.ok("수정이 완료되었습니다."); // 응답 메시지
	}
}
