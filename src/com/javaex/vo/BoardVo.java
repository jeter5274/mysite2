package com.javaex.vo;

public class BoardVo {

	//필드
	private int no;
	private String title, content;
	private int hit;
	private String regDate;
	private int userNo;
	private String writer;
    
    //생성자
    public BoardVo() {}
    
    
    //글수정
  	public BoardVo(int no, String title, String content) {
  		this.no = no;
  		this.title = title;
  		this.content = content;
  	}
  	
    //글등록
  	public BoardVo(String title, String content, int user_no) {
  		this.title = title;
  		this.content = content;
  		this.userNo = user_no;
  	}
  	
    //글목록
	public BoardVo(int no, String title, int hit, String reg_date, int user_no, String writer) {
		this.no = no;
		this.title = title;
		this.hit = hit;
		this.regDate = reg_date;
		this.userNo = user_no;
		this.writer = writer;
	}

	//글읽기
	public BoardVo(int no, String title, String content, int hit, String reg_date, int user_no, String writer) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = reg_date;
		this.userNo = user_no;
		this.writer = writer;
	}

	//메소드 getter/setter
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", regDate="
				+ regDate + ", userNo=" + userNo + ", writer=" + writer + "]";
	}

	
	
    
}
