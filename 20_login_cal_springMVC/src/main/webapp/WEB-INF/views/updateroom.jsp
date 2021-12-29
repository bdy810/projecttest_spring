<%@page import="com.hk.board.dtos.addroomDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>
<%@include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정하기</title>
<script type="text/javascript">
function boardList(){
	//JS: BOM ---> 위치와 관련있는 객체 --> 
	location.href="roomlist.do";
}
</script>
</head>
<%
	addroomDto dto = (addroomDto)request.getAttribute("dto");
%>
<body>
<h1>게시글 수정하기</h1>
<form action="updateroom.do" method="post">
<input type="hidden" name="seq" value="<%=dto.getSeq() %>"/>
<table border = "1">
	<tr>
		<th>글번호</th>
		<td><%=dto.getSeq() %></td>
	</tr>
	<tr>
		<th>건물명</th>
		<td><input type ="text" name="name" value="<%=dto.getName()%>"/></td>
	</tr>
	<tr>
		<th>방가격</th>
		<td><input type ="text" name="price" value="<%=dto.getPrice()%>"/></td>
	</tr>
	<tr>
		<th>방위치</th>
		<td><%=dto.getPlace() %></td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><%=dto.getWriter() %></td>
	</tr>
	<tr>
		<th>작성시간</th>
		<td><%=dto.getRegdate() %></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value = "수정"/>
			<button type="button" onclick="location.href='roomlist.do'">방 목록보기</button>
		</td>
	</tr>
</table>
</form>
</body>
</html>