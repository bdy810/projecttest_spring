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

import com.hk.board.dtos.addroomDto;
import com.hk.board.dtos.calDto;
import com.hk.board.dtos.loginDto;
import com.hk.board.dtos.noticeDto;
import com.hk.board.service.Interface_addroomService;
import com.hk.board.service.Interface_calService;
import com.hk.board.service.Interface_loginService;
import com.hk.board.service.Interface_noticeService;
import com.hk.board.service.noticeService;
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
	@Autowired
	private Interface_noticeService noticeservice;
	@Autowired
	private Interface_addroomService addroomservice;
	
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
	
	@RequestMapping(value = "/main.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String main(loginDto ldto, HttpSession session,HttpServletRequest request,Locale locale, Model model) throws IOException {
		logger.info("메인으로 이동{}.", locale);
		session.setAttribute("ldto", ldto);
		String id=request.getParameter("id");
		String role = loginservice.getRole(id);
		String a = null;
		if(role.toUpperCase().equals("ADMIN")) {
			a = "admin_main";
		}else if(role.toUpperCase().equals("USER")){
			a = "user_main";
		}
		return "a";
	} //안되는부분
	
	@RequestMapping(value = "/noticeboard.do", method = RequestMethod.GET)
	public String boardList(loginDto ldto, HttpSession session,HttpServletRequest request,Locale locale, Model model) {
		logger.info("공지목록 조회하기 {}.", locale);
		
		List<noticeDto> list = noticeservice.getBoardList();
		request.setAttribute("list", list);
		session.setAttribute("ldto", ldto);
		
		return "notice";
	}
	
	//글 상세보기
		@RequestMapping(value = "/detailboard.do", method = RequestMethod.GET)
		public String detailboard(int seq, HttpServletRequest request,Locale locale, Model model) {
			logger.info("글상세 조회하기 {}.", locale);
//			int seq=Integer.parseInt(request.getParameter("seq"));// 예전에 하던 방식이다. spring에서 필요없어짐 또한 파라미터에 HttpServletRequest request 없어도 댐, 이 방법을 쓰려면 파라미터값에 seq 주지않는다.
			noticeDto dto = noticeservice.getBoard(seq);
			model.addAttribute("dto", dto );
			
			return "detailboard";
		}
		
		//폼이동
		@RequestMapping(value = "/insertform.do", method = RequestMethod.GET)
		public String insertForm(Locale locale, Model model) {
			logger.info("글쓰기 폼으로 이동 {}.", locale);
			
			return "insertboard";
		}
		
		//글 추가 기능 구현			   post방식:사용자로부터 값을 입력받아 요청할때(수정작업) 응답: redirect
		//                         get방식:보통...조회할때 (select작업) 응답: forward
		//						   method = {RequestMethod.POST, RequestMethod.GET} :GET, POST방식 어느것이오든 실행
		@RequestMapping(value = "/insertboard.do", method = {RequestMethod.POST, RequestMethod.GET})
		public String insertBoard(noticeDto dto, Locale locale, Model model) {
								 //파라미터에 dto를 선언하면 멤버필드와 동일한 이름이면 모두 받는다.
								 //@RequestParam("seq")int sseq : seq로 전달된 파라미터를 sseq에 저장한다.
			logger.info("글추가하기 {}.", locale);
			boolean isS = noticeservice.insertBoard(dto);
			if(isS) {
			//	response.sendRedirect("boardlist.do"); 옛방식
				 return "redirect:noticeboard.do";  //위와 같은 방식
				//return "페이지명" --> forward방식으로 응답하는 것과 같다.
			}else {
				model.addAttribute("msg","글추가실패");
				return "error"; //return "페이지명" --> forward방식으로 응답하는 것과 같다.
			}
		}
		
		
		@RequestMapping(value = "/updateform.do", method = RequestMethod.GET)
		public String updateForm(int seq, Locale locale, Model model) {
			logger.info("수정폼으로 이동 {}.", locale);
			noticeDto dto= noticeservice.getBoard(seq);
			model.addAttribute("dto", dto);
			return "updateboard"; //forward 방식
		}
		
		@RequestMapping(value = "/updateboard.do", method = RequestMethod.POST)
		public String updateBoard(noticeDto dto, Locale locale, Model model) {
			logger.info("수정하기 {}.", locale);
			boolean isS= noticeservice.updateBoard(dto);
			if(isS){
				logger.info("수정성공 {}.", locale);
				return "redirect:detailboard.do?seq="+dto.getSeq();
			}else {
				model.addAttribute("msg","글수정실패");
				return "error";
			}
		}
		
		@RequestMapping(value = "/muldelboard.do", method = {RequestMethod.POST, RequestMethod.GET})
		public String mulDelBoard(String[] chk,Locale locale, Model model) {
			//파라미터 받기: String[] chk --> name="chk" value="1,3,4,5,6" 배열이 올때 받는법
			logger.info("삭제하기 {}.", locale);
			boolean isS= noticeservice.mulDel(chk);
			if(isS){
				logger.info("삭제성공 {}.", locale);
				return "redirect:noticeboard.do";
			}else {
				model.addAttribute("msg","글추가실패");
				return "error";
			}
		}
		
		@RequestMapping(value = "/roomlist.do", method = RequestMethod.GET)
		public String roomList(loginDto ldto, HttpSession session,HttpServletRequest request,Locale locale, Model model) {
			logger.info("등록된 방 목록보기 {}.", locale);
			
			List<addroomDto> list = addroomservice.getRoomList();
			request.setAttribute("list", list);
			session.setAttribute("ldto", ldto);
			
			return "roomlist";	//등록된 방 목록으로 이동
		}
		
		//폼이동
		@RequestMapping(value = "/insertroomform.do", method = RequestMethod.GET)
		public String insertRoomForm(Locale locale, Model model) {
			logger.info("글쓰기 폼으로 이동 {}.", locale);
					
			return "insertroom";
		}
		
		@RequestMapping(value = "/insertroom.do", method = {RequestMethod.POST, RequestMethod.GET})
		public String insertRoom(addroomDto dto, HttpServletRequest request, Locale locale, Model model) {
								 //파라미터에 dto를 선언하면 멤버필드와 동일한 이름이면 모두 받는다.
								 //@RequestParam("seq")int sseq : seq로 전달된 파라미터를 sseq에 저장한다.
			logger.info("방추가하기 {}.", locale);
			String name = request.getParameter("name");
			String place = request.getParameter("place");
			String price = request.getParameter("price");
			String writer = request.getParameter("writer");
//			boolean isS = addroomservice.insertRoom(new addroomDto(0,name,place,price,writer,null));
			boolean isS = addroomservice.insertRoom(new addroomDto(name,place,price,writer)); //dto생성자에 따라 위 방법과 이 방법이 있다.
//			boolean isS = addroomservice.insertRoom(dto); 한줄 방식. 파라미터를 받을 필요가 없다.
			if(isS) {
			//	response.sendRedirect("boardlist.do"); 옛방식
				 return "redirect:roomlist.do";  //위와 같은 방식
				//return "페이지명" --> forward방식으로 응답하는 것과 같다.
			}else {
				model.addAttribute("msg","글추가실패");
				return "error"; //return "페이지명" --> forward방식으로 응답하는 것과 같다.
			}
		}
	
		//글 상세보기
		@RequestMapping(value = "/detailroom.do", method = RequestMethod.GET)
		public String detailRoom(int seq, Locale locale, Model model) {
			
			logger.info("글상세 조회하기 {}.", locale);
//			int seq=Integer.parseInt(request.getParameter("seq")); 예전에 하던 방식이다. spring에서 필요없어짐 또한 파라미터에 HttpServletRequest request 없어도 댐
			addroomDto dto = addroomservice.detailRoom(seq);
			model.addAttribute("dto", dto );
					
			return "detailroom";
			}

		
		@RequestMapping(value = "/updateroomform.do", method = RequestMethod.GET)
		public String updateroomForm(int seq, Locale locale, Model model) {
			logger.info("방수정폼으로 이동 {}.", locale);
			addroomDto dto= addroomservice.detailRoom(seq);
			model.addAttribute("dto", dto);
			return "updateroom"; //forward 방식
		}
		

		@RequestMapping(value = "/updateroom.do", method = RequestMethod.POST)
		public String updateRoom(addroomDto dto, HttpServletRequest request,Locale locale, Model model) {
			logger.info("수정하기 {}.", locale);
			int seq=Integer.parseInt(request.getParameter("seq"));
			String name = request.getParameter("name");
			String price = request.getParameter("price");
			boolean isS= addroomservice.updateRoom(new addroomDto(seq,name,price));
	//		boolean isS = addroomservice.insertRoom(new addroomDto(seq,name,null,price,null,null));
//			boolean isS=addroomservice.updateRoom(dto); 이방식으로하면 파라미터를 받지않아도 dto객체 찾아서 바로간다.
			if(isS){
				logger.info("수정성공 {}.", locale);
				return "redirect:detailroom.do?seq="+dto.getSeq();
			}else {
				model.addAttribute("msg","글수정실패");
				return "error";
			}
		}
		
		@RequestMapping(value = "/muldelroom.do", method = {RequestMethod.POST, RequestMethod.GET})
		public String mulDelroom(String[] chk,Locale locale, Model model) {
			//파라미터 받기: String[] chk --> name="chk" value="1,3,4,5,6" 배열이 올때 받는법
			logger.info("삭제하기 {}.", locale);
			boolean isS= addroomservice.mulDel(chk);
			if(isS){
				logger.info("삭제성공 {}.", locale);
				return "redirect:roomlist.do";
			}else {
				model.addAttribute("msg","글삭제실패");
				return "error";
			}
		}
}
