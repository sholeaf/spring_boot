package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.BoardDTO;
import com.example.demo.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardMapper bmapper;
	
	@Override
	public List<BoardDTO> getList() {
		return bmapper.getList();
	}

	@Override
	public long getTotal() {
		return bmapper.getTotal();
	}

}










