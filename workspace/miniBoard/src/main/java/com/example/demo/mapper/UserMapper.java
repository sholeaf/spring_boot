package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.dto.UserDTO;

@Mapper
public interface UserMapper {
	int insertUser(UserDTO user);
	UserDTO getUserByUserid(String userid);
}
