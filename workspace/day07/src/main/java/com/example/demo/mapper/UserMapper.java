package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.UserDTO;

@Mapper
public interface UserMapper {
	String getUsernameByUserid(String userid);
	UserDTO getUserByUserid(String userid);
}
