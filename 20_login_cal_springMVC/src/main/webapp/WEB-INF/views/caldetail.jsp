<%@page import="com.hk.board.dtos.loginDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<%
	loginDto ldto=(loginDto)session.getAttribute("ldto"); 
	if(ldto==null){
		pageContext.forward("index.jsp");
	}
%>
<body>
<h1>일정상세보기</h1>
<table border="1">
	<tr>
		<th>이름</th>
 		<td><%=ldto.getId() %></td> 
	</tr>	
	<tr>
		<th>일정</th>
		<td>
			<jsp:useBean id="util" class="com.hk.utils.Util"/>
			<jsp:setProperty property="toDates" name="util" value="${dto.mdate}"/>
			<jsp:getProperty property="toDates" name="util"/>
		</td>
	</tr>
	<tr>
		<th>제목</th>
		<td>${dto.caltitle}</td>
	</tr>
	<tr>
		<th>내용</th>
		<td><textarea  rows="10" cols="60" readonly="readonly">${dto.calcontent}</textarea></td>
	</tr>
	<tr>
		<th>작성일</th>
		<td><fmt:formatDate value="${dto.calregdate}" pattern="yyyy-MM-dd"/> </td>
	</tr>
	<tr>
		<td colspan="3">
			<input type="button" value="수정" onclick="calUpdate()"/>
			<input type="button" value="삭제" onclick="mulDel(${dto.cseq})"/>
			<input type="button" value="목록" onclick="calList()"/>
		</td>
	</tr>
</table>
<script type="text/javascript">
	function calUpdate(){
		location.href="calupdateform.do?cseq=${dto.cseq}&year=${param.year}&month=${param.month}&date=${param.date}";
	}
	function calList(){
		location.href="callist.do?year=${param.year}&month=${param.month}&date=${param.date}";
	}

	function mulDel(cseq){
		location.href="muldel.do?year=${param.year}&month=${param.month}&date=${param.date}&cseq="+cseq;
	}
</script>
</body>
</html>






