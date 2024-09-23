package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.PBoardMapper;
import com.example.demo.model.PBoardDTO;

@Service
public class PBoardServiceImpl implements PBoardService {
	
	@Autowired
	private PBoardMapper pbmapper;
	
	@Override
	public List<PBoardDTO> getList(long lastBoardnum, int limit) {
		List<PBoardDTO> list = pbmapper.getList(lastBoardnum, limit);
		return list;
	}
	
}
