package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

	//필드
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb" , pw = "webdb";
		
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//생성자
	
	//메소드 getter/setter
	
	//메소드 일반

	// 0. import java.sql.*;
	private void connectDB() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
	}
		
	private void closeRs() {
		// 5. 자원정리
		try {
		      if (rs != null) {
		       rs.close();
		     }            	
		      if (pstmt != null) {
		      pstmt.close();
		     }
		      if (conn != null) {
		      conn.close();
		     }
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	public int insert(UserVo uvo) {
		
		int count = 0;
		connectDB();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			String query ="insert into users values (seq_user_no.nextval, ?, ?, ?, ?)";
			//insert into users values (seq_user_id.nextval, id, password, name, gender);
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, uvo.getId());
			pstmt.setString(2, uvo.getPassword());
			pstmt.setString(3, uvo.getName());
			pstmt.setString(4, uvo.getGender());
			
			count = pstmt.executeUpdate();
			// 4.결과처리
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		closeRs();
		
		return count;
	}
	
	//로그인시 유저 정보를 불러옴
	public UserVo getUser(String id, String pw) {
		
		UserVo userVo = null;
		
		connectDB();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			String query ="";
			query += " select  no,";
			query += "         name";
			query += " from users";
			query += " where id = ?";
			query += " and password = ?";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
				
				userVo = new UserVo(rs.getInt("no"), rs.getString("name"));
				
			}
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		closeRs();
		
		return userVo;
	}
	
	//회원정소 수정시 유저 정보를 불러옴
	public UserVo getUser(int no) {
		
		UserVo userVo = null;
		
		connectDB();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			String query ="";
			query += " select  no,";
			query += "         id,";
			query += "         password,";
			query += "         name,";
			query += "        gender";
			query += " from users";
			query += " where no = ?";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
				
				userVo = new UserVo(rs.getInt("no"), rs.getString("id"), rs.getString("password"), rs.getString("name"), rs.getString("gender"));
				
			}
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		closeRs();
		
		return userVo;
	}
	
	public int update(UserVo userVo) {
		int count = 0;
		
		connectDB();
		
		try {
		
			// 3. SQL문 준비 / 바인딩 / 실행
			String query ="";
			query +=" update users";
			query +=" set password = ?,";
			query +="     name = ?,";
			query +="     gender = ?";
			query +=" where no = ?";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, userVo.getPassword());
			pstmt.setString(2, userVo.getName());
			pstmt.setString(3, userVo.getGender());
			pstmt.setInt(4, userVo.getNo());
			
			count = pstmt.executeUpdate();
			// 4.결과처리
			//System.out.println("userDao : "+count);
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		closeRs();
		
		return count;
	}
	/*
	connectDB();
	
	try {
	
		// 3. SQL문 준비 / 바인딩 / 실행
		String query ="";
		query +="";
		query +="";
		
		pstmt = conn.prepareStatement(query);

		// 4.결과처리

	} catch (SQLException e) {
	    System.out.println("error:" + e);
	}
	closeRs();
	*/

}
