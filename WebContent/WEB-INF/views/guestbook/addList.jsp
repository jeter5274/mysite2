<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import = "java.util.List" %>
<%@ page import = "com.javaex.vo.GuestbookVo" %>
<%
	//어트리뷰트에서 방명록 리스트를 불러옴
	List<GuestbookVo> gbList = (List<GuestbookVo>)request.getAttribute("GuestbookList");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
		<link href="/mysite2/assets/css/guestbook.css" rel="stylesheet" type="text/css">
		
	</head>
	
	<body>
		<div id="wrap">
	
			<!--  header & navi 공통으로 옮겼음 -->
			<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	
			<!--  guestbookAside 공통으로 옮겼음 -->
			<jsp:include page="/WEB-INF/views/include/guestbookAside.jsp"></jsp:include>
	
			<div id="content">
				
				<div id="content-head">
	            	<h3>일반방명록</h3>
	            	<div id="location">
	            		<ul>
	            			<li>홈</li>
	            			<li>방명록</li>
	            			<li class="last">일반방명록</li>
	            		</ul>
	            	</div>
	                <div class="clear"></div>
	            </div>
	            <!-- //content-head -->
	
				<div id="guestbook">
					<form action="/mysite2/guestbook" method="post">
						<table id="guestAdd">
							<colgroup>
								<col style="width: 70px;">
								<col>
								<col style="width: 70px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th><label class="form-text" for="input-uname">이름</label></th>
									<td><input id="input-uname" type="text" name="name"></td>
									<th><label class="form-text" for="input-pass">패스워드</label></th>
									<td><input id="input-pass"type="password" name="pass"></td>
								</tr>
								<tr>
									<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
								</tr>
								<tr class="button-area">
									<td colspan="4">
									<button type="submit">등록</button>
									</td>
								</tr>
							</tbody>
							
						</table>
						<!-- //guestWrite -->
						<input type="hidden" name="action" value="add">
						
					</form>	
					<%for(int i=0;i<gbList.size();i++){ %>
						<table class="guestRead">
							<colgroup>
								<col style="width: 10%;">
								<col style="width: 40%;">
								<col style="width: 40%;">
								<col style="width: 10%;">
							</colgroup>
							<tr>
								<td><%=gbList.get(i).getNo() %></td>
								<td><%=gbList.get(i).getName() %></td>
								<td><%=gbList.get(i).getReg_date() %></td>
								<td><a href="/mysite2/guestbook?action=dform&no=<%=gbList.get(i).getNo() %>">[삭제]</a></td>
							</tr>
							<tr>
								<td colspan=4 class="text-left"><%=gbList.get(i).getContent() %></td>
							</tr>
						</table>
					<%} %>
					<!-- //guestRead -->
					
				</div>
				<!-- //guestbook -->
			</div>
			<!-- //content  -->
			<div class="clear"></div>
			
			<!--  footer 공통으로 옮겼음 -->
			<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
	
		</div>
		<!-- //wrap -->
	
	</body>

</html>