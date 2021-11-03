package com.hk.board;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hk.board.dtos.loginDto;
import com.hk.board.service.Interface_loginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private Interface_loginService loginservice;
	
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
	
	@RequestMapping(value = "/after_registform.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String atterregistform(loginDto dto,Locale locale, Model model) {
		logger.info("회원가입 하기 {}.", locale);
		boolean isS = loginservice.insertUser(dto);
		if(isS) {
			return "index";
		}else {
			return "home";
		}
	}
}
