<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<%
   //현재시간 구하기
   Calendar cal=Calendar.getInstance();
   int hour=cal.get(Calendar.HOUR_OF_DAY);
   int min=cal.get(Calendar.MINUTE);

   //EL에서 꺼내 쓰기 위해 pageScope에 저장하기
   pageContext.setAttribute("hour", hour);//java코드의 페이지스코프에다가 담에서 EL로 출력할 수 있다.   
   pageContext.setAttribute("min", min);
%>
<body>
<h1>일정 추가하기</h1>
<form action="calinsert.do" method="post">
   <input type="hidden" name="id" value="${sessionScope.ldto.id}">
   <table border="1">
      <tr>
         <th>아이디</th>  <!-- 세션에서 아이디를 가져와서 넣어준다 -->
         <td>${sessionScope.ldto.id}</td>
      </tr>
      <tr>
         <th>일정</th>  
         <td>
<%--          	<c:set var = "num" value="0" /> --%>
<!--              <input type="date" name="ymd" >  -->
            <select name="year">
               <c:forEach var="i" begin="${param.year-5}" end="${param.year+5}" step="1">
                  <option ${param.year==i?"selected":""} value="${i}">${i}</option>
               </c:forEach>
            </select>년
            <select name="month">
               <c:forEach var="i" begin="1" end="12" step="1">
                  <option ${param.month==i?"selected":""} value="${i}">${i}</option>            
               </c:forEach>
            </select>월
            <select name="date">
               <c:forEach var="i" begin="1" end="31" step="1">
                  <option ${param.date==i?"selected":""} value="${i}">${i}</option>            
               </c:forEach>
            </select>일
            <select name="hour">
               <c:forEach var="i" begin="0" end="23" step="1">
                  <option ${hour==i?"selected":""} value="${i}">${i}</option>            
               </c:forEach>
            </select>시
            <select name="min">
               <c:forEach var="i" begin="0" end="59" step="1">
                  <option ${min==i?"selected":""} value="${i}">${i}</option>            
               </c:forEach>
            </select>분
         </td>
      </tr>
      <tr>
         <th>제목</th>
         <td><input type="text" name="caltitle"></td>
      </tr>
      <tr>
         <th>내용</th>
         <td><textarea rows="10" cols="60" name="calcontent"></textarea></td>
      </tr>
      <tr>
         <td colspan="2">
            <input type="submit" value="일정추가">
            <input type="button" value="달력" onclick="location.href='cal.do'" />
         </td>
      </tr>
   </table>
</form>
</body>
</html> 




