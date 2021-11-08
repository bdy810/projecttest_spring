package com.hk.board;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
	   public String getlogin(HttpSession session ,HttpServletRequest request ,Locale locale, Model model, String id, String password) {
	      logger.info("로그인 {}.", locale);
	      loginDto ldto = loginservice.getLogin(id, password);
	    //  request.getSession().setAttribute("ldto", ldto);
	      session.setAttribute("ldto", ldto);
	      String s = null;
	      System.out.println(ldto.getId());
	      if(ldto==null||ldto.getId()==null) {
	    	  System.out.println("ㅎㅇㅎㅇ");
	    	  s= "redirect:home.do";
	      }else {
	    //	  request.getSession().setAttribute("ldto", ldto);
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
		boolean isS = loginservice.insertUser(new loginDto(map.get("id"),map.get("name"),map.get("password"),map.get("address"),map.get("phone"),map.get("email")));
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
//	String id, String yyyyMM,
	@RequestMapping(value = "/cal.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String cal(HttpSession session,HttpServletRequest request,HttpServletResponse response, Locale locale, Model model) {
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
			String id = dto.getId();
			String yyyyMM= year+Util.isTwo(month);
			
			List<calDto> list = calservice.calBoardListView(id, yyyyMM);
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
	public String calinsert(calDto dto, HttpServletRequest request, Locale locale, Model model) {
		logger.info("일정 추가하기 {}.", locale);
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String date = request.getParameter("date");
		String hour = request.getParameter("hour");
		String min = request.getParameter("min");
		String mdate = year + Util.isTwo(month)+Util.isTwo(date)+Util.isTwo(hour)+Util.isTwo(min);
		String id = request.getParameter("id");
		String caltitle = request.getParameter("caltitle");
		String calcontent = request.getParameter("calcontent");
		
		boolean isS = calservice.calInsert(new calDto(0,id,caltitle,calcontent,mdate,null));
		if(isS) {
			return "redirect:cal.do";
		}else {
			model.addAttribute("msg", "글추가 실패");
			return "error";
		}
	}
	
	@RequestMapping(value = "/callist.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String callist(HttpSession session,HttpServletRequest request, HttpServletResponse response ,Locale locale, Model model) throws UnsupportedEncodingException, IOException {
		String s = null;
		if(session.getAttribute("ldto")==null) {
			response.sendRedirect("index.jsp&msg="+URLEncoder.encode("로그인이 필요합니다","utf-8"));
		}else {
			loginDto ldto = (loginDto) session.getAttribute("ldto");
			
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			String date = request.getParameter("date");
			String id = ldto.getId();
			String ymd=year+Util.isTwo(month)+Util.isTwo(date);
			
			List<calDto>list = calservice.getCalBoardList(id, ymd);
			
			request.setAttribute("list", list);
			s="calboardlist";
		}
		return s;
	}
	
	@RequestMapping(value = "/caldetail.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String caldetail(loginDto ldto, HttpServletRequest request,Locale locale, Model model) {
		int cseq=Integer.parseInt(request.getParameter("cseq"));
		calDto dto = calservice.calDetail(cseq);
		
		request.setAttribute("dto", dto);
		request.setAttribute("ldto", ldto);
		return "caldetail";
	}
	
	@RequestMapping(value = "/muldel.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String muldel(HttpServletRequest request,Locale locale, Model model) {
		logger.info("캘린더 추가 폼으로 이동{}.", locale);
		String [] cseq = request.getParameterValues("cseq");
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		String date=request.getParameter("date");
		
		boolean isS = calservice.mulDel(cseq);
		if(isS) {
			return "redirect:callist.do?year="+year+"&month="+month+"&date="+date;
		}else {
			model.addAttribute("msg", "글삭제 실패");
			return "error";
		}
	}
	
	@RequestMapping(value = "/calupdateform.do", method = RequestMethod.GET)
	public String calupdateform(HttpServletRequest request,Locale locale, Model model) {
		logger.info("캘린더 업데이트 폼으로 이동{}.", locale);
		int cseq = Integer.parseInt(request.getParameter("cseq"));
		calDto dto = calservice.calDetail(cseq);
		request.setAttribute("dto", dto);
		return "calupdate";
	}
	
	@RequestMapping(value = "/calupdate.do", method =  {RequestMethod.POST, RequestMethod.GET})
	public String calupdate(calDto dto, HttpServletRequest request, Locale locale, Model model) {
		logger.info("일정 수정하기 {}.", locale);
		int cseq = Integer.parseInt(request.getParameter("cseq"));
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String date = request.getParameter("date");
		String hour = request.getParameter("hour");
		String min = request.getParameter("min");
		String mdate = year + Util.isTwo(month)+Util.isTwo(date)+Util.isTwo(hour)+Util.isTwo(min);
		String caltitle = request.getParameter("caltitle");
		String calcontent = request.getParameter("calcontent");
		
		boolean isS = calservice.calUpdate(new calDto(cseq,null,caltitle,calcontent,mdate,null));
		if(isS) {
			return "redirect:caldetail.do?year="+year+"&month="+month+"&date="+date+"&cseq="+cseq;
		}else {
			model.addAttribute("msg", "글추가 실패");
			return "error";
		}
	}
	
	@RequestMapping(value = "/userinfo.do", method = RequestMethod.GET)
	public String userinfo(String id,loginDto ldto,HttpSession session,HttpServletRequest request,Locale locale, Model model) {
		logger.info("유저 정보 조회로 이동{}.", locale);
//		loginDto ldto = (loginDto) session.getAttribute("ldto");
//		if(ldto == null) {
//			return "redirect:index.jsp";
//		}
		System.out.println(id);
		loginDto dto = loginservice.getUser(id);
		request.setAttribute("dto", dto);
		session.setAttribute("ldto", ldto);
		return "userinfo";
	}
	
	@RequestMapping(value = "/alluserlist.do", method = RequestMethod.GET)
	public String userlist(loginDto ldto,HttpSession session,HttpServletRequest request,Locale locale, Model model) {
		logger.info("유저 전체 조회로 이동{}.", locale);
//		loginDto ldto = (loginDto) session.getAttribute("ldto");
//		if(ldto == null) {
//			return "redirect:index.jsp";
//		}
		List<loginDto> list = loginservice.getUserList();
		request.setAttribute("list", list);
		session.setAttribute("ldto", ldto);
		return "userlist";
	}
}
