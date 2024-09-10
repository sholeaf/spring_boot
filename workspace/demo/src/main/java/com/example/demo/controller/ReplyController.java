package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Criteria;
import com.example.demo.domain.ReplyDTO;
import com.example.demo.domain.ReplyPageDTO;
import com.example.demo.service.ReplyService;

@RestController
@RequestMapping("/reply/*")
public class ReplyController {
	
	@Autowired
	private ReplyService service;
	
	//ResponseEntity : 서버의 상태코드, 응답 메세지, 응답 데이터 등을 담을 수 있는 타입
	//@RequestBody : 넘겨지는 body의 내용을 해석해서 해당 파라미터에 채워넣기
	@PostMapping("regist")
	public ResponseEntity<String> regist(@RequestBody ReplyDTO reply) {
		//DB 처리
		//regist()를 boolean 타입의 return으로 설계하지 않은 이유는
		//ReplyDTO로 return 시 활용성이 늘어나기 때문이다.
		//ex) 방금 등록한 댓글의 번호도 알 수 있음
		ReplyDTO createdReply = service.regist(reply);
		if(createdReply != null) {
			return new ResponseEntity<>(reply.getReplynum()+"", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatusCode.valueOf(500));
		}
	}
	
	@GetMapping("/{boardnum}/{pagenum}")
	public ResponseEntity<ReplyPageDTO> list(
			@PathVariable("boardnum") long boardnum,
			@PathVariable("pagenum") int pagenum
	) {
		Criteria cri = new Criteria(pagenum,5);
		//{replyCnt:10, list:[]}
		//1. HashMap
		//	key				value
		//	replyCnt		10
		//	list			[]
		//2. DTO
		//	replyCnt
		//	list
		return new ResponseEntity<>(service.getList(cri,boardnum),HttpStatus.OK);
	}
	
}














