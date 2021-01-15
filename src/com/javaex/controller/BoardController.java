package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardController");
		
		request.setCharacterEncoding("utf-8");
		
		BoardDao bDao = new BoardDao();
		
		String action = request.getParameter("action");
		System.out.println("action :" +action);
		
		if("read".equals(action)) {
			System.out.println("게시글 읽기");
						
			HttpSession session = request.getSession();
			System.out.println(session.getId());
			UserVo authUser = (UserVo)session.getAttribute("authUser");
				
			//본인의 글을 읽으면 조회수가 늘어나지 않음
			//로그인이 안되어있거나, 작성자가 아니면 조회수 +1
			if(authUser == null || bDao.getPost(Integer.parseInt(request.getParameter("no"))).getUserNo() != authUser.getNo()) {
				bDao.Update(Integer.parseInt(request.getParameter("no"))); //조회수 +1	
			}
						
			BoardVo post = bDao.getPost(Integer.parseInt(request.getParameter("no")), action);
			
			request.setAttribute("post", post);
			
			WebUtil.forword(request, response, "/WEB-INF/views/board/read.jsp");
			
		}else if("delete".equals(action)) {
			System.out.println("내 게시글 삭제");
			
			bDao.delete(Integer.parseInt(request.getParameter("no")));

			WebUtil.redirect(request, response, "/mysite2/board");
			
		}else if("wForm".equals(action)) {
			System.out.println("글 작성");
			
			//비로그인 상태의 접속시도 차단
			WebUtil.chkLogin(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
		}else if("mForm".equals(action)) {
			
			System.out.println("게시글 수정폼");
			
			BoardVo post = bDao.getPost(Integer.parseInt(request.getParameter("no")));
			
			request.setAttribute("post", post);
			
			//비로그인 상태의 접속시도 차단
			WebUtil.chkLogin(request, response, "/WEB-INF/views/board/modifyForm.jsp");
			
		}else if("insert".equals(action)) {

			System.out.println("글 등록");
			
			HttpSession session = request.getSession();
		
			BoardVo insertPost = new BoardVo(request.getParameter("title"), request.getParameter("content"), ((UserVo)session.getAttribute("authUser")).getNo());
			
			bDao.insert(insertPost);
			
			WebUtil.redirect(request, response, "/mysite2/board");
			
		}else if("modify".equals(action)) {
			System.out.println("게시글 수정");
			
			BoardVo modiPost = new BoardVo(Integer.parseInt(request.getParameter("no")), request.getParameter("title"), request.getParameter("content"));
			
			bDao.update(modiPost);

			WebUtil.redirect(request, response, "/mysite2/board");
			
		}else {
			System.out.println("게시판 목록");
			
			List<BoardVo> bList;
			
			if("search".equals(action)) {
				bList = bDao.getlist(request.getParameter("keyword"));
			}else {
				bList = bDao.getlist();
			}
			
			request.setAttribute("boardList", bList);
			
			WebUtil.forword(request, response, "/WEB-INF/views/board/list.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
