package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.PBoardDTO;

public interface PBoardService {
	ArrayList<PBoardDTO> getList(Long lastBoardnum, int limit);
	Long getStartnum();
	boolean regist(PBoardDTO pboard,MultipartFile[] files) throws Exception;
	PBoardDTO getBoardByBoardnum(Long boardnum);
	boolean modify(PBoardDTO updateBoard);
	Long getNextBoardnum(Long lastBoardnum);
}
