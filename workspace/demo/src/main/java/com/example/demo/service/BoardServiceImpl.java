package com.example.demo.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.Criteria;
import com.example.demo.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardMapper bmapper;
	
	@Override
	public List<BoardDTO> getList(Criteria cri) {
		List<BoardDTO> list = bmapper.getList(cri);
		//현재 시간 정보
		LocalDateTime now = LocalDateTime.now();
		//시간 형태의 문자열을 해석하는 해석기
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//게시글 하나씩 꺼내오면서
		for(BoardDTO board : list) {
			//게시글의 등록 시간 정보						해석	   게시글의 등록시간 문자열 을	dtf 형태에 맞추어서
			LocalDateTime regdate = LocalDateTime.parse(board.getRegdate(),dtf);
			//두 시간 사이의 걸린 시간 정보 
			Duration duration = Duration.between(regdate, now);
			long elapsedHours = duration.toHours();
			if(elapsedHours < 2) {
				board.setNew(true);
			}
		}
		return list;
	}

	@Override
	public long getTotal(Criteria cri) {
		return bmapper.getTotal(cri);
	}

	@Override
	public boolean regist(BoardDTO board) {
		return bmapper.insertBoard(board) == 1;
	}
	
	@Override
	public BoardDTO getDetail(long boardnum) {
		return bmapper.getBoardByBoardnum(boardnum);
	}
	
	@Override
	public long getLastNum(String userid) {
		return bmapper.getLastNum(userid);
	}
	
	@Override
	public void increaseReadCount(long boardnum) {
		BoardDTO board = bmapper.getBoardByBoardnum(boardnum);
		//bmapper.updateReadCount(boardnum,board.getReadcount()+1) : 기존 게시글의 readcount 보다 1 증가시켜서 업데이트
		//bmapper.updateReadCount(boardnum,board.getReadcount()-1) : 기존 게시글의 readcount 보다 1 감소시켜서 업데이트
		//bmapper.updateReadCount(10,7) : 10번 게시글의 readcount를 7로 업데이트
		bmapper.updateReadCount(boardnum,board.getReadcount()+1);
	}

	@Override
	public boolean modify(BoardDTO board) {
		return bmapper.updateBoard(board) == 1;
	}
	
	@Override
	public boolean remove(long boardnum) {
		return bmapper.deleteBoard(boardnum) == 1;
	}
	
}










