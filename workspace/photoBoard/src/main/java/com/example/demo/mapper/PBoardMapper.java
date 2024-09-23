package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.PBoardDTO;

@Mapper
public interface PBoardMapper {
	List<PBoardDTO> getList(@Param("lastBoardnum") long lastBoardnum, @Param("limit") int limit);
}
