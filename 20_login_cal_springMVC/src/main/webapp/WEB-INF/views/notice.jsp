<%@page import="com.hk.board.dtos.noticeDto"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.board.dtos.loginDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>
<%@include file="header.jsp" %> 
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
	
	List<noticeDto>list=(List<noticeDto>)request.getAttribute("list");
%>
<body>
<h1>Administrator Page</h1>
<h2>공지사항 및 자유게시판</h2>
<form action="muldelboard.do" method="post" onsubmit="return isChecked()">
<table class="table table-hover">
   <tr>
      <th><input type="checkbox" name = "all" onclick="allSel(this.checked)"/></th>
      <th>번호</th>
      <th>아이디</th>
      <th>제목</th>
   </tr>
 <%
		for(int i=0;i<list.size();i++){
			noticeDto dto=list.get(i);//list[dto,dto,dto...]--> 순차적으로 하나씩 꺼낸다(dto)
	%>  	
	<tr>
		<td><input type="checkbox" name="chk" value="<%=dto.getSeq()%>" /></td>
		<td><%=dto.getSeq()%></td>
		<td><%=dto.getId()%></td>
		<td><a href="detailboard.do?seq=<%=dto.getSeq()%>"><%=dto.getTitle()%></td>	
	</tr>
	<%
		}
	%>
	<tr>
		<td colspan="6">
			<button class="btn btn-primary" type="button" onclick="location.href='insertform.do'">등록</button>
			<input class="btn btn-danger" type="submit" value="삭제" />
			<button class="btn btn-secondary" type="button" onclick="location.href='index.jsp'">메인</button>
		</td>
	</tr>
</table>
</form>
</body>
</html>