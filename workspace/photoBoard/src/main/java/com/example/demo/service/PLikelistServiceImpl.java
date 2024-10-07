package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.PLikelistMapper;
import com.example.demo.model.PBoardDTO;
import com.example.demo.model.PLikelistDTO;

@Service
public class PLikelistServiceImpl implements PLikelistService {

	@Autowired
	private PLikelistMapper plmapper;
	
	@Override
	public ArrayList<PLikelistDTO> getLikelist(String loginUser, ArrayList<PBoardDTO> list) {
		ArrayList<PLikelistDTO> llist = new ArrayList<>();
		for(int i = 0; i < list.size(); i++) {
			llist.add(plmapper.getLikelist(loginUser, list.get(i).getBoardnum()));
		}
		return llist;
	}

	@Override
	public PLikelistDTO checklike(Long boardnum, String loginUser) {
		return plmapper.getLikelist(loginUser, boardnum);
	}

	@Override
	public boolean insertLike(Long boardnum, String loginUser) {
		return plmapper.insertLike(boardnum, loginUser);
	}

	@Override
	public boolean deleteLike(Long boardnum, String loginUser) {
		return plmapper.deleteLike(boardnum, loginUser);
	}

	@Override
	public PLikelistDTO getLike(String loginUser, Long boardnum) {
		return plmapper.getLikelist(loginUser, boardnum);
	}

}
