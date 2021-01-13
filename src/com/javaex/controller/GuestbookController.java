package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;

@WebServlet("/guestbook")
public class GuestbookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		GuestbookDao gbDao = new GuestbookDao();
		
		if("add".equals(action)) {
			System.out.println("방명록 등록");
			
			//파라미터로 GuestbookVo 생성
			GuestbookVo gbVo = new GuestbookVo(request.getParameter("name"), request.getParameter("pass"), request.getParameter("content"));
			
			//Dao로 DB에 insert
			gbDao.insert(gbVo);
			
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite2/guestbook");
			
		}else if ("dform".equals(action)) {
			System.out.println("삭제 폼");
			
			//포워드
			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			
		}else if ("delete".equals(action)) {
			System.out.println("삭제");
			
			//파라미터 값으로 삭제하고 삭제결과를 리턴받음
			int result = gbDao.delete(Integer.parseInt(request.getParameter("no")), request.getParameter("pass"));
			
			if(result == 0) {	
				//삭제가 실패할 경우 파라미터에 결과를 저장하여 리다이렉트
				WebUtil.redirect(request, response, "/mysite2/guestbook?action=dform&result=fail&no="+request.getParameter("no"));
		
			}else {
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite2/guestbook");
			}
			
		}else {	//기본페이지로 설정
			System.out.println("입력 및 방명록 목록");
			
			//방명록을 DB로 부터 불러옴
			List<GuestbookVo> gbList = gbDao.getList();
			
			//어트리뷰트에 방명록List 저장
			request.setAttribute("GuestbookList", gbList);
			
			//포워드
			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
