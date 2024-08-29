package com.example.demo.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.demo.model.dto.BoardDTO;
import com.example.demo.model.dto.UserDTO;

public class BoardDAO {
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;

	public BoardDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/gb";
			String user = "root";
			String password = "1234";
			
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<BoardDTO> getList() {
		ArrayList<BoardDTO> list = new ArrayList<>();
		String sql = "select * from test_board order by boardnum desc";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setBoardnum(rs.getInt("boardnum"));
				board.setBoardtitle(rs.getString("boardtitle"));
				board.setBoardcontents(rs.getString("boardcontents"));
				board.setRegdate(rs.getString("regdate"));
				board.setUserid(rs.getString("userid"));

				list.add(board);
			}
		} catch (SQLException e) {
			System.out.println("getList() : " + e);
		}
		return list;
	}

	public int insertBoard(BoardDTO board) {
		String sql = "insert into test_board (boardtitle, boardcontents, userid) values(?,?,?)";
		int result = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, board.getBoardtitle());
			ps.setString(2, board.getBoardcontents());
			ps.setString(3, board.getUserid());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("insertBoard() : " + e);
		}
		return result;
	}

	public BoardDTO getBoardByNum(int boardnum) {
		BoardDTO board = null;
		String sql = "select * from test_board where boardnum = " + boardnum;

		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);
			if (rs.next()) {
				board = new BoardDTO();
				board.setBoardnum(rs.getInt("boardnum"));
				board.setBoardtitle(rs.getString("boardtitle"));
				board.setBoardcontents(rs.getString("boardcontents"));
				board.setRegdate(rs.getString("regdate"));
				board.setUserid(rs.getString("userid"));
			}

		} catch (SQLException e) {
			System.out.println("getBoardByNum : " + e);
		}
		return board;
	}

	public int updateBoard(BoardDTO board) {
		String sql = "update test_board set boardtitle=?,boardcontents=? where boardnum=?";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, board.getBoardtitle());
			ps.setString(2, board.getBoardcontents());
			ps.setInt(3, board.getBoardnum());

			return ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("updateBoard : " + e);
		}
		return 0;
	}

	public int deleteBoardByNum(int boardnum) {
		String sql = "delete from test_board where boardnum=?";
		int result = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,boardnum);
			
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
