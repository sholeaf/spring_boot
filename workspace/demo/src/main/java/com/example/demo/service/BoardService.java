package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.BoardDTO;

public interface BoardService {
	List<BoardDTO> getList();
	long getTotal();
}
