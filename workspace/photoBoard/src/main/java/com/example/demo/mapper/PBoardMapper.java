package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.PBoardDTO;

@Mapper
public interface PBoardMapper {
	ArrayList<PBoardDTO> getList(@Param("lastBoardnum") Long lastBoardnum, @Param("limit") int limit);
	Long getBoardnum();
	boolean insertBoard(PBoardDTO pboard);
	PBoardDTO getBoardByBoardnum(Long boardnum);
	boolean updateBoard(PBoardDTO updateBoard);
}
