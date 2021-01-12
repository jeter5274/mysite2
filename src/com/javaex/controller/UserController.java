package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		System.out.println("UserController");
		
		String action = request.getParameter("action");
		System.out.println("action="+action);
		
		UserDao uDao = new UserDao();
		
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
			uDao.insert(uVo);
			
			//포워드 -> joinOk.jsp
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinOk.jsp");
		
		}else if("loginForm".equals(action)) {
			System.out.println("로그인 폼");
			WebUtil.forword(request, response, "/WEB-INF/views/user/loginForm.jsp");

		}else if("login".equals(action)) {
			System.out.println("로그인");
			
			//파라미터 값으로 유저1명의 no/name을 불러옴
			UserVo authVo = uDao.getUser(request.getParameter("id"), request.getParameter("pw"));
			System.out.println(authVo);
			
			if(authVo == null) {
				System.out.println("로그인 실패");
			
				WebUtil.redirect(request, response, "/mysite2/user?action=loginForm");
				
			}else {
				System.out.println("로그인 성공");
				
				//세션영역에 필요한 값(authVo)를 할당한다
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authVo);
			
				WebUtil.redirect(request, response, "/mysite2/main");
			}
			
		}else if("logout".equals(action)) {
			System.out.println("로그아웃");
			
			//세션영역에 있는 값(authVo)을 삭제해야함
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite2/main");
			
		}else if("modifyForm".equals(action)) {
			System.out.println("수정 폼");
			
			//세션으로부터 로그인된 유저의 정보를 불러옴
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			//로그인된 유저의 no를 활용하여 유저정보를 불러옴
			UserVo modiUser = uDao.getUser(authUser.getNo());
			
			//request의 어트리뷰트에 정보를 할당함
			request.setAttribute("modiUser", modiUser);
			
			//포워드 -> modifyForm.jsp
			WebUtil.forword(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		
		}else if("modify".equals(action)) {
			System.out.println("수정");
		
			//세션으로부터 로그인된 유저의 정보를 불러옴
			HttpSession session = request.getSession();
			UserVo authVo = (UserVo)session.getAttribute("authUser");

			//파라미터 값(newPw/newName/gender)과 세션에 있는 값(no)으로 DB수정
			uDao.update(authVo.getNo(), request.getParameter("newPw"), request.getParameter("newName"), request.getParameter("gender"));
			
			//로그인된 유저의 no를 활용하여 업데이트 된 유저정보를 불러옴
			UserVo modiUser = uDao.getUser(authVo.getNo());
			
			//id와 변경할 pw를 통해 유저의 로그인 정보를 다시불러옴
			UserVo updateVo = uDao.getUser(modiUser.getId(), modiUser.getPassword());
			System.out.println(updateVo);
			
			//세션의 정보를 다시 저장함
			session.setAttribute("authUser", updateVo);
			
			//메인으로 돌아가!
			WebUtil.redirect(request, response, "/mysite2/main");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
