<%@page import="com.hk.board.dtos.noticeDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>
<%@include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정하기</title>
</head>
<%
	noticeDto dto = (noticeDto)request.getAttribute("dto");
%>
<body>
<h1>게시글 수정하기</h1>
<form action="updateboard.do" method="post">
<input type="hidden" name="seq" value="<%=dto.getSeq() %>"/>
<table border = "1">
	<tr>
		<th>글번호</th>
		<td><%=dto.getSeq() %></td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><%=dto.getId() %></td>
	</tr><tr>
		<th>제목</th>
		<td><input type ="text" name="title" value="<%=dto.getTitle()%>"/></td>
	</tr>
	<tr>
		<th>내용</th>
		<td><textarea rows="10" cols="60" name="content"><%=dto.getContent()%></textarea></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value = "수정"/>
			<button type="button" onclick="boardList()">목록</button>
		</td>
	</tr>
</table>
</form>
</body>
</html>