package com.example.demo.service;

import java.util.ArrayList;

import com.example.demo.model.PBoardDTO;
import com.example.demo.model.PLikelistDTO;

public interface PLikelistService {

	ArrayList<PLikelistDTO> getLikelist(String loginUser, ArrayList<PBoardDTO> list);

	PLikelistDTO checklike(Long boardnum, String loginUser);

	boolean insertLike(Long boardnum, String loginUser);

	boolean deleteLike(Long boardnum, String loginUser);

	PLikelistDTO getLike(String loginUser, Long boardnum);

}
