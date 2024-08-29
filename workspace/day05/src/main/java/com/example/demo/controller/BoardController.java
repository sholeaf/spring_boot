package com.example.demo.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.dto.BoardDTO;
import com.example.demo.model.dto.UserDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	// /views/user/joinview.jsp
	// 여러 요청 주소를 하나의 메소드에 매핑하는 방법
	@GetMapping(value = { "writeview" })
	public void replace() {
	}

	// 데이터 수집 -> 처리 -> 응답 생성 및 응답하기
	@PostMapping("writeOk")
	public String writeOk(BoardDTO board, HttpServletResponse resp) throws Exception {
		// DB처리
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/gb";
		String user = "root";
		String password = "1234";

		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println(board);

		String sql = "insert into test_board (boardtitle, boardcontents, userid) values(?,?,?)";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, board.getBoardtitle());
		ps.setString(2, board.getBoardcontents());
		ps.setString(3, board.getUserid());

		int result = ps.executeUpdate();

		if (result == 1) {
			Cookie cookie = new Cookie("w", "t");
			cookie.setMaxAge(1);
			cookie.setPath("/");
			resp.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("w", "f");
			cookie.setMaxAge(1);
			cookie.setPath("/");
			resp.addCookie(cookie);
		}
		// forward(길 찾는 사람 손 잡고 함께 가기)
		// ex) return "/user/main";
		// /user/main.jsp 를 해석해서 바로 응답

		// redirect(길 찾는 사람에게 가는 방법 알려주기)
		// ex) return "redirect:/user/main";
		// /user/main 으로 재요청 유도
		return "redirect:/user/main";
	}

	// 서버사이드 랜더링
	@GetMapping("getview")
	public void getview(int boardnum, Model model) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/gb";
		String user = "root";
		String password = "1234";

		Connection conn = DriverManager.getConnection(url, user, password);

		String sql = "select * from test_board where boardnum = " + boardnum;

		PreparedStatement ps = conn.prepareStatement(sql);

		ResultSet rs = ps.executeQuery(sql);
		if (rs.next()) {
			BoardDTO board = new BoardDTO();
			board.setBoardnum(rs.getInt("boardnum"));
			board.setBoardtitle(rs.getString("boardtitle"));
			board.setBoardcontents(rs.getString("boardcontents"));
			board.setRegdate(rs.getString("regdate"));
			board.setUserid(rs.getString("userid"));

			model.addAttribute("board", board);
		}

	}

	// 클라이언트 사이드 랜더링
	@GetMapping("getDetail")
	@ResponseBody
	public BoardDTO getDetail(int boardnum) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/gb";
		String user = "root";
		String password = "1234";

		Connection conn = DriverManager.getConnection(url, user, password);

		String sql = "select * from test_board where boardnum = " + boardnum;

		PreparedStatement ps = conn.prepareStatement(sql);

		ResultSet rs = ps.executeQuery(sql);
		if (rs.next()) {
			BoardDTO board = new BoardDTO();
			board.setBoardnum(rs.getInt("boardnum"));
			board.setBoardtitle(rs.getString("boardtitle"));
			board.setBoardcontents(rs.getString("boardcontents"));
			board.setRegdate(rs.getString("regdate"));
			board.setUserid(rs.getString("userid"));

			return board;
		}
		return null;
	}

	@GetMapping("update")
	public void update(int boardnum, Model model) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/gb";
		String user = "root";
		String password = "1234";

		Connection conn = DriverManager.getConnection(url, user, password);

		String sql = "select * from test_board where boardnum = " + boardnum;

		PreparedStatement ps = conn.prepareStatement(sql);

		ResultSet rs = ps.executeQuery(sql);
		if (rs.next()) {
			BoardDTO board = new BoardDTO();
			board.setBoardnum(rs.getInt("boardnum"));
			board.setBoardtitle(rs.getString("boardtitle"));
			board.setBoardcontents(rs.getString("boardcontents"));
			board.setRegdate(rs.getString("regdate"));
			board.setUserid(rs.getString("userid"));

			model.addAttribute("board", board);
			System.out.println(board);
		}
	}

	@PostMapping("updateOk")
	public String updateOk(BoardDTO board,Model model) throws Exception {
		System.out.println(board);

		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/gb";
		String user = "root";
		String password = "1234";

		Connection conn = DriverManager.getConnection(url, user, password);

		String sql = "update test_board set boardtitle=?,boardcontents=? where boardnum=?";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, board.getBoardtitle());
		ps.setString(2, board.getBoardcontents());
		ps.setInt(3, board.getBoardnum());

		ps.executeUpdate();
		
		model.addAttribute("board", board);
		return "redirect:/user/main";

	}
}
