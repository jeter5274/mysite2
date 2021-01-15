package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	
	//필드
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb" , pw = "webdb";
		
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//생성자 - 기본생성자 활용 (생략)
	
	//메소드 getter/setter 필요없음
	
	//메소드 일반		

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
	
	public List<BoardVo> getlist() {
		return getlist(" ");
	}
	
	public List<BoardVo> getlist(String keyword) {
		
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		BoardVo boardVo;
		
		connectDB();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query ="";
			query +=" select  bo.no,";
			query +="         bo.title,";
			query +="         bo.hit,";
			query +="         to_char(bo.reg_date, 'YYYY-MM-DD HH24:MI') reg_date,";
			query +="         bo.user_no,";
			query +="         us.name";
			query +=" from board bo left join users us";
			query +=" on bo.user_no = us.no";
			if(" ".equals(keyword)) {
				query +=" order by bo.no asc";
				pstmt = conn.prepareStatement(query);
			}else {
				keyword = "%" +keyword+ "%";
				
				query +=" where bo.title like ?";
				query +=" or us.name like ?";
				query +=" order by bo.no asc";
			
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
			}
			
			
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
				boardVo = new BoardVo(rs.getInt("no"), rs.getString("title"), rs.getInt("hit"), rs.getString("reg_date"), rs.getInt("user_no"), rs.getString("name"));
				boardList.add(boardVo);
			}
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		closeRs();
		
		return boardList;
	}
	
	public BoardVo getPost(int no) {
		
		return getPost(no, "");
	}
	
	public BoardVo getPost(int no, String action) {
		BoardVo post=null;
		String contentQuery;
		
		//게시글 읽을 시, 엔터 적용
		if("read".equals(action)) {
			contentQuery = " replace(bo.content, chr(10), '<br>') content,";
		}else {
			contentQuery = " bo.content,";
		}
		
		connectDB();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query ="";
			query +=" select  bo.no,";
			query +="         bo.title,";
			query += contentQuery;
			query +="         bo.hit,";
			query +="         to_char(bo.reg_date, 'YYYY-MM-DD HH24:MI') reg_date,";
			query +="         bo.user_no,";
			query +="         us.name";
			query +=" from board bo left join users us";
			query +=" on bo.user_no = us.no";
			query +=" where bo.no = ?";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
				post = new BoardVo(rs.getInt("no"), rs.getString("title"), rs.getString("content"), rs.getInt("hit"), rs.getString("reg_date"), rs.getInt("user_no"), rs.getString("name"));
			}
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		closeRs();
		
		return post;
		
	}
	
	public int Update(int no) {
		//조회수 +1

		int count = 0;

		connectDB();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query ="update board set hit = hit+1 where no=?";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();
			// 4.결과처리
			System.out.println("[BoardDAO] 조회수" +count+ "건 업데이트");			
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		closeRs();
		
		return count;
	}
	
	public int update(BoardVo bVo) {
		//게시글 수정
		
		int count = 0;
		
		connectDB();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query ="";
			query +=" update board";
			query +=" set title = ?,";
			query +="     content = ?";
			query +=" where no=?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getNo());			
			
			count = pstmt.executeUpdate();
			// 4.결과처리
			System.out.println("[BoardDAO] 게시글" +count+ "건 업데이트");
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		closeRs();
		
		return count;
	}
	
	public int delete(int no) {
		int count=0;
		
		connectDB();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query ="delete board where no=?";
			
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();
			// 4.결과처리
			System.out.println("[BoardDAO]" +count+ "건 삭제");
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		closeRs();
		
		return count;
	}
	
	public int insert(BoardVo bVo) {
		int count = 0;
		System.out.println(bVo);
		
		connectDB();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query ="";
			query +=" insert into board";
			query +=" values(seq_board_no.nextval, ?, ?, default, sysdate, ?)";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getUserNo());
			
			count = pstmt.executeUpdate();
			// 4.결과처리
			System.out.println("[BoardDAO]" +count+ "건 등록");
			
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
