package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UserController");
		
		String action = request.getParameter("action");
		System.out.println("action="+action);
		
		if("joinForm".equals(action)) {
			System.out.println("회원가입 폼");
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinForm.jsp");
			
		}else if("join".equals(action)){
			System.out.println("회원가입");
			
			//파라미터 값으로 UserVo생성
			UserVo uVo = new UserVo(request.getParameter("uid"), request.getParameter("pw"), request.getParameter("uname"), request.getParameter("gender"));
			System.out.println(uVo.toString());
			
			//dao -> insert() 데이터 저장
			//	dao클래스, insert()메소드 생성 및 테스트
			UserDao uDao = new UserDao();
			uDao.insert(uVo);
			
			//포워드 -> joinOk.jsp
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinOk.jsp");
		
		}else if("loginForm".equals(action)) {
			System.out.println("로그인 폼");
			WebUtil.forword(request, response, "/WEB-INF/views/user/loginForm.jsp");

		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
