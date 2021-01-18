package com.javaex.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.vo.UserVo;

public class WebUtil {

	//필드
	
	//생성자
	//메소드 - getter/setter
	//메소드 - 일반
	
	
	//포워드
	public static void forword(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(path);	//jsp파일 위치
		rd.forward(request, response);
			
	}
	//리다이렉트
	public static void redirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		response.sendRedirect(url);
		//request를 사용하지는 않지만 사용포맷을 통일화 하기 위해 쓰는 것
	}
	
	//로그인이 되어있으면 경로대로 이동하고 안되어있으면 게시판 리스트를 출력
	public static void chkLogin(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser"); 
		
		if(authUser == null) {
			WebUtil.redirect(request, response, "/mysite2/board");
		}else {
			WebUtil.forword(request, response, path);
		}
	}
	
	//jdbcConnect -> Dao가 많아 질 수록 DB의 설정 수정시 번거로움이 생길 것 같아 WebUtil에서 사용
	public static Connection jdbcConnect() {
		
		Connection conn = null;
		
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "webdb", "webdb");

		} catch (ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
		
		return conn;
	}
	//jdbcClose
}
