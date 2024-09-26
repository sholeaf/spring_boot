package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.PFileDTO;

@Mapper
public interface PFileMapper {
	int insertFile(PFileDTO pfdto);
}
