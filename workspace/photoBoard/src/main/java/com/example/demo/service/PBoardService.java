package com.example.demo.service;

import java.util.List;

import com.example.demo.model.PBoardDTO;

public interface PBoardService {
	List<PBoardDTO> getList(long lastBoardnum, int limit);
}
