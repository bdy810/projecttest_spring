<%@page import="com.hk.board.dtos.addroomDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<%
	addroomDto dto=(addroomDto)request.getAttribute("dto");
%>
<body>
<h1>방 정보 상세보기</h1>
<table border = "1">
	<tr>
		<th>글번호</th>
		<td><%=dto.getSeq() %></td>
	</tr>
	<tr>
		<th>건물명</th>
		<td><%=dto.getName() %></td>
	</tr>
	<tr>
		<th>가격</th>
		<td><%=dto.getPrice()%></td>
	</tr>
	<tr>
		<th>위치</th>
		<td><%=dto.getPlace() %>
	</tr>
	<tr>
		<th>작성자</th>
		<td><%=dto.getWriter()%></td>
	</tr>
	<tr>
		<th>작성시간</th>
		<td><%=dto.getRegdate()%></td>
	</tr>
	<tr>
		<td colspan="2">
			<button onclick="updateBoard()">수정</button>
			<button onclick="delBoard()">삭제</button>
			<button onclick="boardList()">목록</button>
		</td>
	</tr>
</table>
<script type="text/javascript">
	function updateBoard(){
		location.href="updateroomform.do?seq=<%=dto.getSeq()%>";
	}
	//글삭제하기
	function delBoard(){
		location.href="muldelroom.do?chk=<%=dto.getSeq()%>";
	}
	
	function boardList(){
		location.href="roomlist.do";
	}
</script>
</body>
</html>