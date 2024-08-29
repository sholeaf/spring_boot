package com.example.demo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.dto.BoardDTO;

@Mapper
public interface BoardMapper {
	int insertBoard(BoardDTO board);
	BoardDTO getBoardByNum(int boardnum);
	int updateBoard(BoardDTO board);
	int deleteBoardByNum(int boardnum);
	List<BoardDTO> getList();
}
