<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<style type="text/css">
	.table, .tr, .td {border: 1px solid black;}
	.table{display: table;}
	.tr{display: table-row;}
	.td{display: table-cell; padding: 5px;}
	#container{width: 400px; margin: 200px auto;}
</style>
<script type="text/javascript">
function registform(){
	location.href="registform.do";
}
</script>
</head>
<body>
<a href="home.do">HOME</a>
<div id="container">
	<h1 style="text-align: center;">로그인</h1> 
	<form action="login.do" method="post">
		<div class="form-group"> 
			<input type="text" class="form-control" placeholder="아이디"  name="id" required="required">
		</div>
		<div class="form-group">
			<input type="password" class="form-control" placeholder="비밀번호" name="password" maxlength="20" required="required">
		</div>
		<input type="submit" class="btn btn-primary form-control" value="로그인" >
		<input type="button" class="btn btn-success form-control" value="회원가입" onclick="registform()">
	</form>
</div>
</body>
</html>