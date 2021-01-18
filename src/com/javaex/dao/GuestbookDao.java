package com.javaex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;

public class GuestbookDao {

	/* jdbc 연결을 WebUitl로 사용
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb" , pw = "webdb";
	*/
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private int count = 0;
	//생성자 - 기본생성자 활용 (생략)
	
	//메소드 getter/setter 필요없음
	
	//메소드 일반		
	
	/* jdbc 연결을 WebUitl로 사용
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
	*/
	
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
	
	public List<GuestbookVo> getList() {
		
		List<GuestbookVo> GBList = new ArrayList<GuestbookVo>();
		
		conn = WebUtil.jdbcConnect();
		
		try {		
			// 3. SQL문 준비 / 바인딩 / 실행
			String query="";
			query +=" select	no,";
			query +="			name,";
			query +="			password,";
			query +="			content,";
			query +="			reg_date";
			query +=" from guestbook";
			
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while(rs.next()) {
								
				GuestbookVo gbVo = new GuestbookVo(rs.getInt("no"), rs.getString("name"), rs.getString("password"), rs.getString("content"), rs.getString("reg_date"));
				
				GBList.add(gbVo);
			}
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		closeRs();
		
		return GBList;
	}
	
	public int insert(GuestbookVo gbVo) {
		
		conn = WebUtil.jdbcConnect();
		
		try {		
			// 3. SQL문 준비 / 바인딩 / 실행
			String query="";
			query +=" insert into guestbook";
			query +=" values (seq_guestbook_no.nextval, ?, ?, ?, sysdate)";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, gbVo.getName());
			pstmt.setString(2, gbVo.getPassword());
			pstmt.setString(3, gbVo.getContent());
			
			count = pstmt.executeUpdate();
			
			// 4.결과처리
			//System.out.println("[DAO] : " +count+ "건 등록");
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		closeRs();
		
		return count;
	}
	
	public int delete(int no, String pwd) {
		
		conn = WebUtil.jdbcConnect();
		
		try {		
			// 3. SQL문 준비 / 바인딩 / 실행
			String query="";
			query +=" delete guestbook";
			query +=" where no = ?";
			query +=" and password = ?";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, no);
			pstmt.setString(2, pwd);
			
			count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println("[DAO] : " +count+ "건 삭제");
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		closeRs();
		
		return count;
		
	}
}