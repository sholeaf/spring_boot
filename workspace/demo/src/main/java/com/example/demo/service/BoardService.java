package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.Criteria;
import com.example.demo.domain.FileDTO;

public interface BoardService {
	List<BoardDTO> getList(Criteria cri);
	long getTotal(Criteria cri);
	boolean regist(BoardDTO board, MultipartFile[] files) throws Exception;
	BoardDTO getDetail(long boardnum);
	long getLastNum(String userid);
	void increaseReadCount(long boardnum);
	boolean modify(BoardDTO board);
	boolean remove(long boardnum);
	List<FileDTO> getFiles(long boardnum);
}
