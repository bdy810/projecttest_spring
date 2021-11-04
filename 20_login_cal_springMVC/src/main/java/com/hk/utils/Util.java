package com.hk.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import com.hk.board.dtos.calDto;

public class Util {

	private String toDates;
	
	public void setToDates(String mDate) {
		
		//mDate를 날짜형식으로 편집한다. yyyy-MM-dd hh:mm:ss
		String m=mDate.substring(0, 4)+"-"   //year-
				+mDate.substring(4, 6)+"-"   //year-month-
				+mDate.substring(6, 8)+" "   //"year-month-date "
				+mDate.substring(8,10)+":"   //"year-month-date hh:"
				+mDate.substring(10)+":00";  //"year-month-date hh:mm:00"
		
		//문자열 ---> date타입으로 변환
		Timestamp tm=Timestamp.valueOf(m);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy년MM월dd일 HH:mm");
		this.toDates=sdf.format(tm);
	}

	public String getToDates() {
		return toDates;
	}
	
	//한자릿수를 두자릿수로 변환하는 메서드
	public static String isTwo(String s) {
		return s.length()<2?"0"+s:s;
	}
	
	 //자바 메서드 선언부 메소드 선언할때 쓴다.
	//list에 mdate에서 일과 달력에서 i값과 비교해서 일치하면 제목출력
	   public static String getCalView(List<calDto>list,int i){ 
	      //i가 int형 +""를 하면서 string 변환  5일 --> 5 --> "05" 정수형의 숫자를 두자리 문자열로 변환   
	      String d=Util.isTwo(i+"");
	      String titleList="";//출력해 줄 p태그 제목 "<p>title</p><p>title</p>"
	      
	      for(calDto dto:list){
	         //"202109281431" -> 28
	         if(dto.getMdate().substring(6, 8).equals(d)){
	            titleList+="<p class='ctitle'>"
	                     +(dto.getCaltitle())
	                     +"</p>"; 
	         }
	      }
	   
	      return titleList;
	   }
	

	  //요일별 날짜 색깔 적용하는 메서드 
		public static String fontColor(int dayOfWeek, int i){
			String color="";
			if((i+dayOfWeek-1)%7==0){
				color="blue";
		}else if((i+dayOfWeek-1)%7==1){
				color="red";
		}else {
			color="black";
		}
			return color;
	}
	
}