package com.example.demo.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.PBoardDTO;
import com.example.demo.model.PFileDTO;

@Mapper
public interface PFileMapper {
	int insertFile(PFileDTO pfdto);

	ArrayList<String> getFilesByBoardnum(Long boardnum);

	ArrayList<String> getFileByBoardnum(ArrayList<PBoardDTO> list);

	boolean deleteFile(Long boardnum, String systemname);

	String getImg(long boardnum);
}
