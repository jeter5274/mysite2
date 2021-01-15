package com.javaex.dao;

import com.javaex.vo.UserVo;

public class DaoTest {

	public static void main(String[] args) {

		//빨리 테스트 하는 용 - 지금은 필요 없어 보이나, 규모가 커지는 프로젝트시에는 용이함
		UserDao userDao = new UserDao();
		BoardDao bDao = new BoardDao();
		
		//int count = bDao.update(1);
		//System.out.println(count);
		//userDao.update(2, "1234", "관리자50", "female");
		
		
		/*
		UserVo userVo = userDao.getUser("sjh", "1234");
		
		System.out.println(userVo);
		*/
		
	}

}
