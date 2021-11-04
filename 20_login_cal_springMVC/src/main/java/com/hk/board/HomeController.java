package com.hk.board;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hk.board.dtos.calDto;
import com.hk.board.dtos.loginDto;
import com.hk.board.service.Interface_calService;
import com.hk.board.service.Interface_loginService;
import com.hk.utils.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private Interface_loginService loginservice;
	@Autowired
	private Interface_calService calservice;
	
	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/error404.do", method = RequestMethod.GET)
	public String error404(Locale locale, Model model) {
		logger.info("404 오류{}.", locale);
		model.addAttribute("code", "404오류");
		return "error/404";
	}
	
	@RequestMapping(value = "/error500.do", method = RequestMethod.GET)
	public String error500(Locale locale, Model model) {
		logger.info("500 오류{}.", locale);
		model.addAttribute("code", "500오류");
		return "error/500";
	}
	
	@RequestMapping(value = "/login.do", method = {RequestMethod.POST, RequestMethod.GET})
	   public String getlogin(HttpServletRequest request ,Locale locale, Model model, String id, String password) {
	      logger.info("로그인 {}.", locale);
	      loginDto ldto = loginservice.getLogin(id, password);
	      String s = null;
	      if(ldto==null||ldto.getId()==null) {
	    	  System.out.println("ㅎㅇㅎㅇ");
	    	  s= "redirect:home.do";
	      }else {
	    	  request.getSession().setAttribute("ldto", ldto);
	    	  if(ldto.getRole().toUpperCase().equals("ADMIN")) {
	    		  s= "admin_main";
	    	  }else{
	    		  ldto.getRole().toUpperCase().equals("USER"); 
	    		  s= "user_main";
	    	  }
	      }
	     return s;
	}
	
	@RequestMapping(value = "/registform.do", method = RequestMethod.GET)
	public String registform(Locale locale, Model model) {
		logger.info("회원가입 폼으로 이동 {}.", locale);
		
		return "registform";
	}
	
	@RequestMapping(value = "/after_registform.do", method =  {RequestMethod.POST, RequestMethod.GET})
	public String afterregistform(@RequestParam Map<String,String>map ,Locale locale, Model model) {
		logger.info("회원가입 하기 {}.", locale);
		//String a= null;
		System.out.println(map.get("id"));
		System.out.println(map.get("email"));
		boolean isS = loginservice.insertUser(new loginDto(map.get("id"),map.get("password"),map.get("name"),map.get("address"),map.get("phone"),map.get("email")));
		if(isS) {
			return "redirect:index.jsp";
		}else {
			model.addAttribute("msg", "글추가 실패");
			return "error";
		}
	}
	
	@RequestMapping(value ="/idchk.do", method = RequestMethod.GET)
	public String idchk(HttpServletRequest request,String id,Locale locale, Model model) {
		logger.info("idchk{}.", locale);
		String a = loginservice.idChk(id);
		System.out.println(a);
		request.setAttribute("id", a);
	//	model.addAttribute("id", a);
		return "idchk";
	}
	
	@RequestMapping(value = "/cal.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String cal(HttpSession session,HttpServletRequest request,HttpServletResponse response,String id, String yyyyMM, Locale locale, Model model) {
		logger.info("캘린더 보기{}.", locale);
		if(session.getAttribute("ldto")==null) {
			return "redirect:index.jsp";
		}else {
			loginDto dto = (loginDto)session.getAttribute("ldto");
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			if(year == null) {
				Calendar cal = Calendar.getInstance();
				year = cal.get(Calendar.YEAR)+"";
				month = cal.get(Calendar.MONTH)+1+"";
			}
			String a = dto.getId();
			String b= year+Util.isTwo(month);
			
			List<calDto> list = calservice.calBoardListView(a, b);
			request.setAttribute("list", list);
		}
		return "calendar";
	}
	
	@RequestMapping(value = "/calinsertform.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String calinsertform(HttpSession session,Locale locale, Model model) {
		logger.info("캘린더 추가 폼으로 이동{}.", locale);
		if(session.getAttribute("ldto")==null) {
			return "redirect:index.jsp";
		}
		return "calinsert";
	}
	
	@RequestMapping(value = "/calinsert.do", method =  {RequestMethod.POST, RequestMethod.GET})
	public String calinsert(calDto dto ,Locale locale, Model model) {
		logger.info("일정 추가하기 {}.", locale);
		
		boolean isS = calservice.calInsert(dto);
		if(isS) {
			return "redirect:cal.do";
		}else {
			model.addAttribute("msg", "글추가 실패");
			return "error";
		}
	}
}
