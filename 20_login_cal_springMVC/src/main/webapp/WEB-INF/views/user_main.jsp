<%@page import="com.hk.board.dtos.loginDto"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.board.daos.loginDao"%>
<%@include file="header.jsp" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<style type="text/css">
.box {
	margin: 2px 2px;
	width: 100%;
	heigjt: 50%;
	padding: 5px;
	background-color: lightgray;
}

.btnbox {
	/* /*    버튼박스  */ */ object align :"left";
	width: 100%;
	margin: 1px solid black;
	padding: 10px;
	text-align: center;
}

.button {
	height: 80px;
	width: 200px;
	font-size: 16px;
	background-color: white;
	margin: 3px;
}

h1 {
	text-align: center;
	font-size: 35px;
	margin: 40px;
	font-weight: bold;
}

.admin {
	text-align: center;
	background-color: white;
	font-size: 15px;
	heght: 8%;
	margin: 20px;
}

.user {
	text-align: center;
	background-color: white;
	font-size: 15px;
	margin: 10px;
	color: blue;
}

/*   로그아웃 */
a {
	color: red;
	position: fixed;
	right: 70px;
	font-size: 15px;
}

body {
	background-color: white;
}

#absolute {
	position: absolute;
	left: 230px;
	bottom: -150px;
}

#parent {
	position: relative;
	width: 100px;
	height: 100px;
	background: skyblue;
}

#child {
	position: absolute;
	right: 0;
	left: 650px;
	font-size: 10px;
	bottom: -35px;
}

h3 {
	font-size: 20px;
}
</style>
</head>
<%
	loginDto ldto=(loginDto)session.getAttribute("ldto"); 
	if(ldto==null){
		pageContext.forward("index.jsp");
	}
%>
<body>

	<a href="index.jsp">로그아웃</a>
	<h1>환영합니다</h1>
	<div class="admin">
		<span class="user"><%=ldto.getId() %></span>님 반갑습니다.
		(등급:<%=ldto.getRole().equals("USER")?"일반회원":"준회원"%>)
	</div>

	<div class="box">
		<nav class="navbar navbar-light bg-light">
			<form class="btnbox">
				<button class="button" type="button" onclick="location.href='userinfo.do?id=<%=ldto.getId() %>'">나의정보</button>
				<button class="button" type="button" onclick="location.href='cal.do'">일정관리</button>
				<button class="button" type="button" onclick="location.href='noticeboard.do'">공지사항 및 자유게시판</button>
			</form>
		</nav>
		<div>
		</div>
	</div>
</body>
</html> 