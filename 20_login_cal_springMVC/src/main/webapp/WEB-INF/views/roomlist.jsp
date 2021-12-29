<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%@page import="com.hk.board.dtos.addroomDto" %>
<%@page import="java.util.List"%>
<%@page import="com.hk.board.dtos.loginDto"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<style type="text/css">
	h1 {  text-align : center;
          font-size : 35px; 
          margin:40px;  
          font-weight:bold;
       }
    
    h2 {  
    
    text-align: center;       
        background-color : white;  
       font-size : 15px;           
        heght : 8%;
        margin : 20px;
     }
     
     tr{
     border: 1px solid
     text-align: center;
     font-size : 20px;
      margin:40px;  
      font-weight:bold;
     }
     
     #zz{
     text-align: right;
     }
</style>
<script type="text/javascript">
	function allSel(bool){
		var chks=document.getElementsByName("chk");
		for(var i = 0; i< chks.length; i++){
			chks[i].checked=bool;
		}
	}
	
	onload = function(){
		var chks = document.getElementsByName("chk");
		
		for(var i = 0; i < chks.length; i++){
			chks[i].onclick=function(){
				var count = 0;
				for(var i =0; i < chks.length; i++){
					if(chks[i].checked){
						count++;
					}
				}
				if(count == chks.length){
					a = document.getElementsByName("all")[0];
		 			a.checked = true;
				}else{		
					b = document.getElementsByName("all")[0];
		 			b.checked = false;
				}	
			}
		}
	}
	
	function isChecked(){
		var count=0;
		var chks=document.getElementsByName("chk");
		for(var i =0; i < chks.length; i++){
		   if(chks[i].checked){
		      count++;
		   }
		}
		if(count == 0){
		   alert("최소 하나 선택하세요");
		   return false;
		}else{
			alert("삭제 되었습니다.");
		   return true;
		}
	}
	
	

</script>
</head>
<%
	loginDto ldto = (loginDto)session.getAttribute("ldto");
	if(ldto==null){
		pageContext.forward("index.jsp");
	}
	
	List<addroomDto>list=(List<addroomDto>)request.getAttribute("list");
%>
<body>
<h1>Roomlist Page</h1>
<h2>등록된 방 목록보기</h2>
<form action="muldelroom.do" method="post" onsubmit="return isChecked()">
<table class="table table-hover">
   <tr>
      <th><input type="checkbox" name = "all" onclick="allSel(this.checked)"/></th>
      <th>번호</th>
      <th>건물명</th>
      <th>위치</th>
      <th>가격</th>
      <th>작성자</th>
   </tr>
 <%
		for(int i=0;i<list.size();i++){
			addroomDto dto=list.get(i);//list[dto,dto,dto...]--> 순차적으로 하나씩 꺼낸다(dto)
	%>  	
	<tr>
		<td><input type="checkbox" name="chk" value="<%=dto.getSeq()%>" /></td>
		<td><%=dto.getSeq()%></td>
		<td><a href="detailroom.do?seq=<%=dto.getSeq()%>"><%=dto.getName()%></td>
		<td><%=dto.getPlace()%></td>
		<td><%=dto.getPrice()%></td>
		<td><%=dto.getWriter()%></td>
	</tr>
	<%
		}
	%>
	<tr>
		<td id="zz" colspan="6">
			<input class="btn btn-danger" type="submit" value="삭제" />
			<button class="btn btn-secondary" type="button" onclick="location.href='index.jsp'">메인</button>
		</td>
	</tr>
</table>
</form>
</body>
</html>