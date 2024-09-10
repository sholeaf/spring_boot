package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Criteria;
import com.example.demo.domain.ReplyDTO;
import com.example.demo.domain.ReplyPageDTO;
import com.example.demo.mapper.ReplyMapper;

@Service
public class ReplyServiceImpl implements ReplyService{
	@Autowired
	private ReplyMapper rmapper;
	
	@Override
	public ReplyDTO regist(ReplyDTO reply) {
		if(rmapper.insertReply(reply) == 1) {
			return rmapper.getLastReply(reply.getUserid());
		}
		return null;
	}
	
	@Override
	public ReplyPageDTO getList(Criteria cri, long boardnum) {
//		return new ReplyPageDTO(rmapper.getTotal(boardnum), rmapper.getList(cri,boardnum));
		
		long totalCnt = rmapper.getTotal(boardnum);
		List<ReplyDTO> list = rmapper.getList(cri,boardnum);
		
		ReplyPageDTO dto = new ReplyPageDTO(totalCnt, list);
		
		return dto;
	}
}







