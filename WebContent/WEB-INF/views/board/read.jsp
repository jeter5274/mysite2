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
	
			<!-- include/header.jsp -->
			<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
			
			<!-- include/boardAside.jsp -->
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
					<div id="read">
						<form action="#" method="get">
							<!-- 작성자 -->
							<div class="form-group">
								<span class="form-text">작성자</span>
								<span class="form-value">${post.writer}</span> <%-- ${post.name} = ${requestScope.post.name} --%>
							</div>
							
							<!-- 조회수 -->
							<div class="form-group">
								<span class="form-text">조회수</span>
								<span class="form-value">${post.hit}</span>
							</div>
							
							<!-- 작성일 -->
							<div class="form-group">
								<span class="form-text">작성일</span>
								<span class="form-value">${post.regDate}</span>
							</div>
							
							<!-- 제목 -->
							<div class="form-group">
								<span class="form-text">제 목</span>
								<span class="form-value">${post.title}</span>
							</div>
						
							<!-- 내용 -->
							<div id="txt-content">
								<span class="form-value" >
									${post.content}
								</span>
							</div>
							
							<c:if test="${post.userNo == authUser.no}">
								<a id="btn_modify" href="/mysite2/board?action=mForm&no=${post.no}&hit=no">수정</a>
							</c:if>
							<a id="btn_modify" href="/mysite2/board">목록</a>
							
						</form>
		                <!-- //form -->
					</div>
					<!-- //read -->
				</div>
				<!-- //board -->
			</div>
			<!-- //content  -->
			<div class="clear"></div>
	
			<!-- include/footer.jsp -->
			<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
	
		</div>
		<!-- //wrap -->
	
	</body>

</html>