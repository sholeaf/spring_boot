package com.example.demo.service;

import java.util.ArrayList;

import com.example.demo.model.PBoardDTO;

public interface PFileService {

	ArrayList<String> getFilesByBoardnum(Long boardnum);

	ArrayList<String> getFileByBoardnum(ArrayList<PBoardDTO> list);
}
