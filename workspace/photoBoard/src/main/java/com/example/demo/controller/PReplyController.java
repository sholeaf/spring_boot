package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.PReplyDTO;
import com.example.demo.service.PReplyService;

@Controller
@RequestMapping("/preply/*")
public class PReplyController {
	
	@Autowired
	private PReplyService prservice;
	
	@GetMapping("loadMore")
	@ResponseBody
	public List<PReplyDTO> loadMoreReply(@RequestParam(required = false) Long lastBoardnum,
										@RequestParam(required = false) Long replynum){
		System.out.println(lastBoardnum+" : "+replynum);
		List<PReplyDTO> list = prservice.getMoreReply(lastBoardnum, replynum);
		System.out.println("댓글 list : "+list);
		return list;
	}
	
	@PostMapping("regist")
	public ResponseEntity<Map<String, PReplyDTO>> regist(@RequestParam String replycontent,
														 @RequestParam Long boardnum,
														 @RequestParam String replyuserid){
		
	    
		Map<String, PReplyDTO> registReply = new HashMap<>();
		System.out.println(replycontent+" "+ boardnum+" "+ replyuserid);
		if(prservice.registReply(replycontent, boardnum, replyuserid)) {
			PReplyDTO replydto = prservice.getFirstReply(boardnum,replyuserid);
			if(replydto != null ) {				
				registReply.put("reply", replydto);
				return ResponseEntity.ok(registReply);
			}
		}
		return ResponseEntity.ok(Map.of("message", null));
		
		//여유 될때 DTO로 넘겨서 되는지 수정
	}
	
	@PostMapping("modify")
	public ResponseEntity<Map<String, String>> modify(@RequestBody PReplyDTO replydto){
		System.out.println(replydto.getReplycontent());
		Map<String, String> updateReply = new HashMap<>();
		if(prservice.modifyReply(replydto)) {
			updateReply.put("replycontent", replydto.getReplycontent());
			return ResponseEntity.ok(updateReply);
		}
		return ResponseEntity.ok(Map.of("message", "수정 실패"));
	}
	
	@PostMapping("remove")
	public ResponseEntity<Long> remove(@RequestParam Long replynum){
		if(prservice.removeReply(replynum)) {
			return  ResponseEntity.ok(replynum);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
