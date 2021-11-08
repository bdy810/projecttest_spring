<%@page import="com.hk.utils.Util"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script type="text/javascript">
	function allSel(bool){
		var chks=document.getElementsByName("cseq");
		for (var i = 0; i < chks.length; i++) {
			chks[i].checked=bool;
		}
	}
	
	function calendar(year,month){
		location.href=
		"cal.do?year="+year+"&month="+month;
	}
	function chkConfirm(){
		var chks=document.getElementsByName("cseq");
		var count=0;
		for (var i = 0; i < chks.length; i++) {
			if(chks[i].checked){
				count++;
			}
		}
		if(count<=0){
			alert("최소 하나 이상 체크하세요")
			return false;
		}else{
			return true;
		}
	}
</script>
</head>
<body>
<h1>일정목록(<small>${param.year}년 ${param.month}월 ${param.date}일</small>)</h1>
<form action="muldel.do" method="post" onsubmit="return chkConfirm()">
	<input type="hidden" name="year" value="${param.year}"/>
	<input type="hidden" name="month" value="${param.month}"/>
	<input type="hidden" name="date" value="${param.date}"/>
	<table border="1">
		<col width="5px">
		<col width="50px">
		<col width="200px">
		<col width="300px">
		<col width="100px">
		<tr>
			<th><input type="checkbox" name="all" onclick="allSel(this.checked)"/></th>
			<th>번호</th>		
			<th>일정</th>
			<th>제목</th>
			<th>작성일</th>
		</tr>
		<c:choose>
			<c:when test="${empty list}">
				<tr><td colspan="5">---- 작성된 일정이 없습니다. ----</td></tr>
			</c:when>
			<c:otherwise>
				<c:set var="num" value="0" />
				<jsp:useBean id="util" class="com.hk.utils.Util" />
				<c:forEach var="dto" items="${list}">
					<tr>
						<td><input type="checkbox" name="cseq" value="${dto.cseq}"/></td>
						<td>${num+=1}</td>
						<td>
<!-- 						jstl을 이용해서 날짜 형식 표현하기 -->
	<%-- 					<fmt:parseDate var="parseMdate" value="${dto.mdate}" pattern="yyyyMMddHHmm"/> --%>
	<%-- 					<fmt:formatDate value="${parseMdate}" pattern="yyyy년MM월dd일 HH시mm분"/> --%>
	
<!-- 						Action Tag를 이용해서 날짜 형식 표현하기 -->
<!-- 						jsp에서 자바와 jstl을 같이 쓸때 셋프로퍼티 겟프로퍼티를 쓴다. util이라는 java파일과 연결-->			
						 	<jsp:setProperty property="toDates" name="util" value="${dto.mdate}"/>
						 	<jsp:getProperty property="toDates" name="util"/>
						 </td>
						<td><a href="caldetail.do?cseq=${dto.cseq}&year=${param.year}&month=${param.month}&date=${param.date}">${dto.caltitle}</a></td>
						<td><fmt:formatDate value="${dto.calregdate}" pattern="yyyy-MM-dd"/> </td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		<tr>
			<td colspan="5">
				<input type="button" value="달력보기" onclick="calendar(${param.year},${param.month})"/>
				<input type="submit" value="삭제"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>












