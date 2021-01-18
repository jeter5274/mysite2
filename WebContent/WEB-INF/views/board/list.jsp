<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
		<link href="/mysite2/assets/css/board.css" rel="stylesheet" type="text/css">
		
	</head>
	
	
	<body>
		<div id="wrap">
	
			<!-- include/header.jsp  -->
			<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
				
	
			<!-- include/boardAside.
			jsp  -->
			<c:import url="/WEB-INF/views/include/boardAside.jsp"></c:import>
	
			<div id="content">
	
				<div id="content-head">
					<h3>게시판</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>게시판</li>
							<li class="last">일반게시판</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->
	
				<div id="board">
					<div id="list">
						<form action="/mysite2/board" method="">
							<div class="form-group text-right">
								<input type="text" name="keyword" value="">
								<button type="submit" id=btn_search>검색</button>
								<input type="hidden" name="action" value="search">
							</div>
						</form>
						<table >
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>글쓴이</th>
									<th>조회수</th>
									<th>작성일</th>
									<th>관리</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${boardList}" var="bVo">
								<tr>
									<td>${num = num+1}</td>	<!-- 게시글 번호에 빈 번호가 없도록 출력 -->
									<td class="text-left"><a href="/mysite2/board?action=read&no=${bVo.no}">${bVo.title}</a></td>
									<td>${bVo.writer}</td>
									<td>${bVo.hit}</td>
									<td>${bVo.regDate} </td>
									<td>
										<c:if test="${bVo.userNo == authUser.no}">
											<a href="/mysite2/board?action=delete&no=${bVo.no}">[삭제]</a>
										</c:if>
									</td>
										 
								</tr>
								</c:forEach>
							</tbody>
						</table>
			
						<div id="paging">
							<ul>
								<li><a href="">◀</a></li>
								<li><a href="">1</a></li>
								<li><a href="">2</a></li>
								<li><a href="">3</a></li>
								<li><a href="">4</a></li>
								<li class="active"><a href="">5</a></li>
								<li><a href="">6</a></li>
								<li><a href="">7</a></li>
								<li><a href="">8</a></li>
								<li><a href="">9</a></li>
								<li><a href="">10</a></li>
								<li><a href="">▶</a></li>
							</ul>
							
							
							<div class="clear"></div>
						</div>
						
						<c:if test="${authUser.no != null}">
							<a id="btn_write" href="/mysite2/board?action=wForm">글쓰기</a>
						</c:if>
					
					</div>
					<!-- //list -->
				</div>
				<!-- //board -->
			</div>
			<!-- //content  -->
			<div class="clear"></div>
	
			<!-- include/footer.jsp  -->
			<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>

		</div>
		<!-- //wrap -->
	
	</body>

</html>
