package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.FileDTO;

@Mapper
public interface FileMapper {
	int insertFile(FileDTO file);
	
	FileDTO getFileBySystemname(String systemname);
	List<FileDTO> getFiles(long boardnum);
	
	int deleteFileBySystemname(String systemname);
	int deleteFilesByBoardnum(long boardnum);
}





