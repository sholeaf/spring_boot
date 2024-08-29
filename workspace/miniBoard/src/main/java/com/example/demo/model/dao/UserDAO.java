package com.example.demo.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.demo.model.dto.UserDTO;

public class UserDAO {
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;

	public UserDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/gb";
			String user = "root";
			String password = "1234";

			Connection conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 오버로딩 실패");
		} catch (SQLException e) {
			System.out.println("DB연결 실패");
		}
	}

	public int insertUser(UserDTO user) {
		String sql = "insert into test_user values(?,?,?)";
		int result = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, user.getUserid());
			ps.setString(2, user.getUserpw());
			ps.setString(3, user.getUsername());

			result = ps.executeUpdate();

		} catch (SQLException e) {

		}
		return result;
	}
	
	public UserDTO getUserById(String userid) {
		String sql = "select * from test_user where userid=?";
		UserDTO result = null;
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, userid);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				result = new UserDTO();
				result.setUserid(rs.getString("userid"));
				result.setUserpw(rs.getString("userpw"));
				result.setUsername(rs.getString("username"));
			}
		} catch (SQLException e) {
			System.out.println("getUserById : "+e);
		}
		
		return result;
	}
}
