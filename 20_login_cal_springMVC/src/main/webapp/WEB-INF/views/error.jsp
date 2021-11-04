<%@include file="header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
<!-- scope객체: ${requestScope.name}:줄인버전= ${list}, ${sessionScope.name} ${ldto} -->
<!-- script요소: request.getAttribute("list"), session.getAttribute("ldto") -->
<!-- parameter: ${param} -->
<!-- script 요소: request.getParameter("msg") -->
<h1>시스템 오류가 발생했습니다.(관리자에게 문의하세요: 042-1234-5678)</h1>
<h2>오류내용:${param.msg}</h2>
<h3><a href="index.jsp">메인</a></h3>
</body>
</html>