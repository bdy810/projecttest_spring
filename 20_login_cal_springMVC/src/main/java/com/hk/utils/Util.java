package com.hk.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import com.hk.board.dtos.calDto;

public class Util {

	private String toDates;
	
	public void setToDates(String mDate) {
		
		//mDate�� ��¥�������� �����Ѵ�. yyyy-MM-dd hh:mm:ss
		String m=mDate.substring(0, 4)+"-"   //year-
				+mDate.substring(4, 6)+"-"   //year-month-
				+mDate.substring(6, 8)+" "   //"year-month-date "
				+mDate.substring(8,10)+":"   //"year-month-date hh:"
				+mDate.substring(10)+":00";  //"year-month-date hh:mm:00"
		
		//���ڿ� ---> dateŸ������ ��ȯ
		Timestamp tm=Timestamp.valueOf(m);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy��MM��dd�� HH:mm");
		this.toDates=sdf.format(tm);
	}

	public String getToDates() {
		return toDates;
	}
	
	//���ڸ����� ���ڸ����� ��ȯ�ϴ� �޼���
	public static String isTwo(String s) {
		return s.length()<2?"0"+s:s;
	}
	
	 //�ڹ� �޼��� ����� �޼ҵ� �����Ҷ� ����.
	//list�� mdate���� �ϰ� �޷¿��� i���� ���ؼ� ��ġ�ϸ� �������
	   public static String getCalView(List<calDto>list,int i){ 
	      //i�� int�� +""�� �ϸ鼭 string ��ȯ  5�� --> 5 --> "05" �������� ���ڸ� ���ڸ� ���ڿ��� ��ȯ   
	      String d=Util.isTwo(i+"");
	      String titleList="";//����� �� p�±� ���� "<p>title</p><p>title</p>"
	      
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
	

	  //���Ϻ� ��¥ ���� �����ϴ� �޼��� 
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