<%@page import="com.hk.board.dtos.loginDto"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.board.daos.loginDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>
<% 	//캐시처리
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보목록조회</title>
<script type="text/javascript">
	function updateRole(userId){
		location.href="updateroleform.jsp?zid="+userId;
	}
</script>
</head>
<%
	List<loginDto>list=(List<loginDto>)request.getAttribute("list");

	loginDto ldto=(loginDto)session.getAttribute("ldto"); //로그인 정보가 담긴 객체이다.scope객체에 저장하면 모두 Object타입이기때문에 형변환해줘야한다.

	//로그인 정보가 없으면 index페이지로 이동
	if(ldto==null){ 
	pageContext.forward("index.jsp");
}
%>
<body>
<h1>회원정보목록조회</h1>
<table border="1">
	<tr>
		<th>아이디</th>
		<th>이름</th>
		<th>등급</th>
		<th>등급변경</th>
	</tr>
	<%
		if(list==null||list.size()==0){
			%>
			<tr>
				<td colspan="4">----가입한 회원이 없습니다.----</td>
			</tr>
			<%
		}else{
			for(int i=0; i<list.size(); i++){
				loginDto dto = list.get(i);
			%>
			<tr>
				<td><%=i+1 %></td>
				<td><%=dto.getId() %></td>
				<td><%=dto.getName() %></td>
				<td><%=getRoleName(dto.getRole()) %></td>
				<td><button onclick="updateRole('<%=dto.getId()%>')">변경</button></td>
			</tr>
			<%
			}
		}
	%>
	<tr>
		<td colspan="5">
			<button onclick="location.href='admin_main.jsp'">메인</button>
		</td>
	</tr>
</table>
<%! //자바 선언무 == 메서드를 선언코드 작성
	public String getRoleName(String rName){
	String s= "";
	switch(rName){
		case "ADMIN": s="관리자"; break;
		case "USER" : s ="일반회원"; break;
		case "MANAGER" : s = "정회원"; break;
		default : s="미정"; break;
	}
	return s;
}
%>
</body>
</html>