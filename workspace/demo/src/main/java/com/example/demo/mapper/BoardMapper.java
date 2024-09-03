package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.BoardDTO;

@Mapper
public interface BoardMapper {
	List<BoardDTO> getList();
	long getTotal();
}
