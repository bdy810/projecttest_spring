<%@page import="com.hk.board.daos.loginDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>
<%@include file="header.jsp" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script type="text/javascript">
	function confirm(isS){
		if(isS=="n"){
			//opener--> registform.jsp
			var inputId=opener.document.getElementsByName("id")[0];//id 입력박스 객체 구함
			inputId.value="";//회원가입폼에 아이디 입력박스를 ""으로 초기화한다.
			inputId.focus();//사용자가 바로 입력할 수 있게 커서를 넣어준다.
		}else{
			opener.document.getElementsByName("id")[0].title="y";//중복체크완료여부
			opener.document.getElementsByName("pw")[0].focus();
		}
		self.close();//자신의 창을 닫는다.
	}
</script>
</head>
<%
	String id=request.getParameter("id");
	loginDao dao=new loginDao();
	String resultId=dao.idChk(id);// 중복된 아이디 확인: 결과값이 있으면 사용X
	
	String isS="y";//사용여부를 나타내는 isS 정의(y는 사용가능, n은 사용못함)
	if(resultId!=null){
		isS="n";
	}
%>
<body>
<table class="table table-hover">
	<tr>
		<th>아이디</th>
		<td><%=id%></td>
	</tr>
	<tr>
		<th>사용가능여부</th>
		<td><%=isS.equals("y")?"사용 가능합니다.":"중복된 아이디입니다" %></td>
	</tr>
	<tr>
		<td colspan="2">
			<button onclick="confirm('<%=isS%>')">확인</button>
		</td>
	</tr>
</table>
</body>
</html>