package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.example.demo.model.PReplyDTO;
import com.example.demo.service.PBoardService;
import com.example.demo.service.PFileService;
import com.example.demo.service.PReplyService;

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
	
	@Autowired
	private PReplyService prservice;
	

	@GetMapping("list")
	public void getPBoard(HttpServletRequest req, Model model) {
		Long boardnum = pbservice.getStartnum();
		if(boardnum == null || boardnum == 0) {
			model.addAttribute("lastBoardnum", null);
			return;
		}
		ArrayList<PBoardDTO> list = pbservice.getList(boardnum, 12);
		Long lastBoardnum = list.get(list.size() - 1).getBoardnum();

		model.addAttribute("lastBoardnum", lastBoardnum);
		model.addAttribute("list", list);

		System.out.println("초기 BOARDNUM : " + boardnum);
		System.out.println(pbservice.getList(boardnum, 12));// 데이터 제대로 담겨있음
	}

	@GetMapping("loadMore")
	@ResponseBody
	public List<PBoardDTO> loadMorePBoard(@RequestParam(required = false) Long lastBoardnum) {
		Long startBoardnum = pbservice.getNextBoardnum(lastBoardnum);
		List<PBoardDTO> list = pbservice.getList(lastBoardnum != null ? startBoardnum : 0L, 12);
		System.out.println("loadmore 에서 초기 보드넘" + startBoardnum);
		System.out.println(list);
		return list;
	}

	@PostMapping("write")
	public String write(PBoardDTO pboard, MultipartFile[] files) throws Exception {
		pbservice.regist(pboard, files);
		return "redirect:/pboard/list";
	}

	@GetMapping("get")
	public String getBoard(@RequestParam("boardnum") Long boardnum, Model model) {
		System.out.println(pfservice.getFilesByBoardnum(boardnum));
		System.out.println(pbservice.getBoardByBoardnum(boardnum));
		
		List<PReplyDTO> replies = prservice.getReply(boardnum);
		
		model.addAttribute("flist", pfservice.getFilesByBoardnum(boardnum));
		model.addAttribute("board", pbservice.getBoardByBoardnum(boardnum));
		model.addAttribute("rlist", replies);
		
		Long lastReplynum = replies.isEmpty() ? null : replies.get(replies.size() - 1).getReplynum();
		model.addAttribute("lastReplynum", lastReplynum);
		
		// 나중에 reply의 정보도 가져와야함.
		
		return "pboard/viewPost"; // viewPost.html 템플릿 반환
	}

	@PostMapping("modify")
	public ResponseEntity<Map<String, Object>> modifyView(@RequestParam("boardnum") Long boardnum, @RequestParam("boardtitle") String boardtitle,
																	  @RequestParam("boardcontents") String boardcontents,
																	  @RequestParam(value = "delete",  required = false) String delete,
																	  @RequestParam(value = "files",  required = false) MultipartFile[] files 
																	  ) throws Exception {
		if(files != null && files.length != 0) {
			if(!pfservice.insert(boardnum, files)) {				
				System.out.println("pfservice.insert 실패");
				return ResponseEntity.ok(Map.of("message", "수정 실패 : 추가 파일 오류."));
			}
		}
		if(delete != null && delete.length() != 0) {
			String[] deleteArr = delete.split(",");
			if(!pfservice.delete(boardnum,deleteArr)) {				
				System.out.println("pfservice.delete 실패");
				 return ResponseEntity.ok(Map.of("message", "수정 실패 : 기존 파일 삭제 오류."));  
			}
		}
		//넘겨줄 데이터 포장
		PBoardDTO updateBoard = new PBoardDTO();
		updateBoard.setBoardnum(boardnum);
		updateBoard.setBoardtitle(boardtitle);
		updateBoard.setBoardcontents(boardcontents);
		
		Map<String, Object> updatedBoard = new HashMap<>();
		if(pbservice.modify(updateBoard)) {
			updatedBoard.put("boardtitle", boardtitle);
			updatedBoard.put("boardcontetns", boardcontents);
			updatedBoard.put("images", pfservice.getFilesByBoardnum(boardnum));
			
			Map<String, Object> response = new HashMap<>();
		    response.put("updatedBoard", updatedBoard);
		    return ResponseEntity.ok(response); 
		}
		else {
			return ResponseEntity.ok(Map.of("message", "수정 실패"));  
		}
	}
	
	@PostMapping("remove")
	public ResponseEntity<String> remove(@RequestParam("boardnum") Long boardnum, @RequestParam("files") String[] files) throws Exception {
		if(!pbservice.remove(boardnum, files)) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시물 삭제에 실패했습니다.");
		}
		 // 삭제 성공 시 200 상태 코드와 메시지 반환
	    return ResponseEntity.ok("게시물이 삭제되었습니다.");
	}
}
