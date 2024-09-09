package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.Criteria;

public interface BoardService {
	List<BoardDTO> getList(Criteria cri);
	long getTotal(Criteria cri);
	boolean regist(BoardDTO board);
	BoardDTO getDetail(long boardnum);
	long getLastNum(String userid);
	void increaseReadCount(long boardnum);
	boolean modify(BoardDTO board);
	boolean remove(long boardnum);
}
