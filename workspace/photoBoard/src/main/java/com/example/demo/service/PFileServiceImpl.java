package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.PFileMapper;
import com.example.demo.model.PBoardDTO;

@Service
public class PFileServiceImpl implements PFileService {
	
	@Autowired
	private PFileMapper pfmapper;
	
	@Override
	public ArrayList<String> getFilesByBoardnum(Long boardnum) {
		return pfmapper.getFilesByBoardnum(boardnum);
	}

	@Override
	public ArrayList<String> getFileByBoardnum(ArrayList<PBoardDTO> list) {
		return pfmapper.getFileByBoardnum(list);
	}
	
}
