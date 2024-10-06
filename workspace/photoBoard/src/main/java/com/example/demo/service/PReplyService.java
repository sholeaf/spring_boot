package com.example.demo.service;

import java.util.ArrayList;

import com.example.demo.model.PReplyDTO;

public interface PReplyService {
	ArrayList<PReplyDTO> getReply(Long boardnum);

	ArrayList<PReplyDTO> getMoreReply(Long boardnum, Long replynum);

	boolean modifyReply(PReplyDTO replydto);

	boolean removeReply(Long replynum);

	boolean registReply(String replycontent, Long boardnum, String replyuserid);

	PReplyDTO getFirstReply(Long boardnum, String replyuserid);
}
