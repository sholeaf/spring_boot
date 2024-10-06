package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.PReplyMapper;
import com.example.demo.model.PReplyDTO;

@Service
public class PReplyServiceImpl implements PReplyService {

	@Autowired
	private PReplyMapper rmapper;

	@Override
	public ArrayList<PReplyDTO> getReply(Long boardnum) {
		return rmapper.getReply(boardnum);
	}

	@Override
	public ArrayList<PReplyDTO> getMoreReply(Long boardnum, Long replynum) {
		return rmapper.getMoreReply(boardnum, replynum);
	}

	@Override
	public boolean modifyReply(PReplyDTO replydto) {
		return rmapper.modifyReply(replydto);
	}

	@Override
	public boolean removeReply(Long replynum) {
		return rmapper.removeReply(replynum);
	}

	
	@Override
	public boolean registReply(String replycontent, Long boardnum, String replyuserid) {
		// TODO Auto-generated method stub
		return rmapper.registReply(replycontent,boardnum,replyuserid);
	}
	
	@Override
	public PReplyDTO getFirstReply(Long boardnum, String replyuserid) {
		return rmapper.getFirstReply(boardnum, replyuserid);
	}

	

}
