<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방 등록하기</title>
<script type="text/javascript">
	function roomList(){
		//JS: BOM ---> 위치와 관련있는 객체 --> 
		location.href="roomlist.do";
	}
</script>
</head>
<body>
<h1>방 등록하기</h1>
<form action="insertroom.do" method="post">

	<table border ="1">
	<tr>
		<th>건물명</th>
		<td><input type="text" name="name"/></td>
	</tr>
	<tr>
		<th>위치</th>
		<td><input type="text" name="place"/></td>
	</tr>
	<tr>
		<th>가격</th>
		<td><input type="text" name="price"></td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><input type="text" name="writer"></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="등록"/>
			<input type="button" value="목록" onclick="roomList()"/>
		</td>
	</tr>
	</table>
</form>
</body>
</html>