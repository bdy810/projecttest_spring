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
		logger.info("404 ����{}.", locale);
		model.addAttribute("code", "404����");
		return "error/404";
	}
	
	@RequestMapping(value = "/error500.do", method = RequestMethod.GET)
	public String error500(Locale locale, Model model) {
		logger.info("500 ����{}.", locale);
		model.addAttribute("code", "500����");
		return "error/500";
	}
	
	@RequestMapping(value = "/login.do", method = {RequestMethod.POST, RequestMethod.GET})
	   public String getlogin(HttpSession session ,HttpServletRequest request ,Locale locale, Model model, String id, String password) {
	      logger.info("�α��� {}.", locale);
	      loginDto ldto = loginservice.getLogin(id, password);
	    //  request.getSession().setAttribute("ldto", ldto);
	      session.setAttribute("ldto", ldto);
	      String s = null;
	      System.out.println(ldto.getId());
	      if(ldto==null||ldto.getId()==null) {
	    	  System.out.println("��������");
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
		logger.info("ȸ������ ������ �̵� {}.", locale);
		
		return "registform";
	}
	
	@RequestMapping(value = "/after_registform.do", method =  {RequestMethod.POST, RequestMethod.GET})
	public String afterregistform(@RequestParam Map<String,String>map ,Locale locale, Model model) {
		logger.info("ȸ������ �ϱ� {}.", locale);
		boolean isS = loginservice.insertUser(new loginDto(map.get("id"),map.get("name"),map.get("password"),map.get("address"),map.get("phone"),map.get("email")));
		if(isS) {
			return "redirect:index.jsp";
		}else {
			model.addAttribute("msg", "���߰� ����");
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
		logger.info("Ķ���� ����{}.", locale);
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
		logger.info("Ķ���� �߰� ������ �̵�{}.", locale);
		if(session.getAttribute("ldto")==null) {
			return "redirect:index.jsp";
		}
		return "calinsert";
	}
	
	@RequestMapping(value = "/calinsert.do", method =  {RequestMethod.POST, RequestMethod.GET})
	public String calinsert(calDto dto, HttpServletRequest request, Locale locale, Model model) {
		logger.info("���� �߰��ϱ� {}.", locale);
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
			model.addAttribute("msg", "���߰� ����");
			return "error";
		}
	}
	
	@RequestMapping(value = "/callist.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String callist(HttpSession session,HttpServletRequest request, HttpServletResponse response ,Locale locale, Model model) throws UnsupportedEncodingException, IOException {
		String s = null;
		if(session.getAttribute("ldto")==null) {
			response.sendRedirect("index.jsp&msg="+URLEncoder.encode("�α����� �ʿ��մϴ�","utf-8"));
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
		logger.info("Ķ���� �߰� ������ �̵�{}.", locale);
		String [] cseq = request.getParameterValues("cseq");
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		String date=request.getParameter("date");
		
		boolean isS = calservice.mulDel(cseq);
		if(isS) {
			return "redirect:callist.do?year="+year+"&month="+month+"&date="+date;
		}else {
			model.addAttribute("msg", "�ۻ��� ����");
			return "error";
		}
	}
	
	@RequestMapping(value = "/calupdateform.do", method = RequestMethod.GET)
	public String calupdateform(HttpServletRequest request,Locale locale, Model model) {
		logger.info("Ķ���� ������Ʈ ������ �̵�{}.", locale);
		int cseq = Integer.parseInt(request.getParameter("cseq"));
		calDto dto = calservice.calDetail(cseq);
		request.setAttribute("dto", dto);
		return "calupdate";
	}
	
	@RequestMapping(value = "/calupdate.do", method =  {RequestMethod.POST, RequestMethod.GET})
	public String calupdate(calDto dto, HttpServletRequest request, Locale locale, Model model) {
		logger.info("���� �����ϱ� {}.", locale);
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
			model.addAttribute("msg", "���߰� ����");
			return "error";
		}
	}
	
	@RequestMapping(value = "/userinfo.do", method = RequestMethod.GET)
	public String userinfo(String id,loginDto ldto,HttpSession session,HttpServletRequest request,Locale locale, Model model) {
		logger.info("���� ���� ��ȸ�� �̵�{}.", locale);
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
		logger.info("���� ��ü ��ȸ�� �̵�{}.", locale);
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
		logger.info("�������� �̵�{}.", locale);
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
	} //�ȵǴºκ�
	
	@RequestMapping(value = "/noticeboard.do", method = RequestMethod.GET)
	public String boardList(loginDto ldto, HttpSession session,HttpServletRequest request,Locale locale, Model model) {
		logger.info("������� ��ȸ�ϱ� {}.", locale);
		
		List<noticeDto> list = noticeservice.getBoardList();
		request.setAttribute("list", list);
		session.setAttribute("ldto", ldto);
		
		return "notice";
	}
	
	//�� �󼼺���
		@RequestMapping(value = "/detailboard.do", method = RequestMethod.GET)
		public String detailboard(int seq, HttpServletRequest request,Locale locale, Model model) {
			logger.info("�ۻ� ��ȸ�ϱ� {}.", locale);
//			int seq=Integer.parseInt(request.getParameter("seq"));// ������ �ϴ� ����̴�. spring���� �ʿ������ ���� �Ķ���Ϳ� HttpServletRequest request ��� ��, �� ����� ������ �Ķ���Ͱ��� seq �����ʴ´�.
			noticeDto dto = noticeservice.getBoard(seq);
			model.addAttribute("dto", dto );
			
			return "detailboard";
		}
		
		//���̵�
		@RequestMapping(value = "/insertform.do", method = RequestMethod.GET)
		public String insertForm(Locale locale, Model model) {
			logger.info("�۾��� ������ �̵� {}.", locale);
			
			return "insertboard";
		}
		
		//�� �߰� ��� ����			   post���:����ڷκ��� ���� �Է¹޾� ��û�Ҷ�(�����۾�) ����: redirect
		//                         get���:����...��ȸ�Ҷ� (select�۾�) ����: forward
		//						   method = {RequestMethod.POST, RequestMethod.GET} :GET, POST��� ������̿��� ����
		@RequestMapping(value = "/insertboard.do", method = {RequestMethod.POST, RequestMethod.GET})
		public String insertBoard(noticeDto dto, Locale locale, Model model) {
								 //�Ķ���Ϳ� dto�� �����ϸ� ����ʵ�� ������ �̸��̸� ��� �޴´�.
								 //@RequestParam("seq")int sseq : seq�� ���޵� �Ķ���͸� sseq�� �����Ѵ�.
			logger.info("���߰��ϱ� {}.", locale);
			boolean isS = noticeservice.insertBoard(dto);
			if(isS) {
			//	response.sendRedirect("boardlist.do"); �����
				 return "redirect:noticeboard.do";  //���� ���� ���
				//return "��������" --> forward������� �����ϴ� �Ͱ� ����.
			}else {
				model.addAttribute("msg","���߰�����");
				return "error"; //return "��������" --> forward������� �����ϴ� �Ͱ� ����.
			}
		}
		
		
		@RequestMapping(value = "/updateform.do", method = RequestMethod.GET)
		public String updateForm(int seq, Locale locale, Model model) {
			logger.info("���������� �̵� {}.", locale);
			noticeDto dto= noticeservice.getBoard(seq);
			model.addAttribute("dto", dto);
			return "updateboard"; //forward ���
		}
		
		@RequestMapping(value = "/updateboard.do", method = RequestMethod.POST)
		public String updateBoard(noticeDto dto, Locale locale, Model model) {
			logger.info("�����ϱ� {}.", locale);
			boolean isS= noticeservice.updateBoard(dto);
			if(isS){
				logger.info("�������� {}.", locale);
				return "redirect:detailboard.do?seq="+dto.getSeq();
			}else {
				model.addAttribute("msg","�ۼ�������");
				return "error";
			}
		}
		
		@RequestMapping(value = "/muldelboard.do", method = {RequestMethod.POST, RequestMethod.GET})
		public String mulDelBoard(String[] chk,Locale locale, Model model) {
			//�Ķ���� �ޱ�: String[] chk --> name="chk" value="1,3,4,5,6" �迭�� �ö� �޴¹�
			logger.info("�����ϱ� {}.", locale);
			boolean isS= noticeservice.mulDel(chk);
			if(isS){
				logger.info("�������� {}.", locale);
				return "redirect:noticeboard.do";
			}else {
				model.addAttribute("msg","���߰�����");
				return "error";
			}
		}
		
		@RequestMapping(value = "/roomlist.do", method = RequestMethod.GET)
		public String roomList(loginDto ldto, HttpSession session,HttpServletRequest request,Locale locale, Model model) {
			logger.info("��ϵ� �� ��Ϻ��� {}.", locale);
			
			List<addroomDto> list = addroomservice.getRoomList();
			request.setAttribute("list", list);
			session.setAttribute("ldto", ldto);
			
			return "roomlist";	//��ϵ� �� ������� �̵�
		}
		
		//���̵�
		@RequestMapping(value = "/insertroomform.do", method = RequestMethod.GET)
		public String insertRoomForm(Locale locale, Model model) {
			logger.info("�۾��� ������ �̵� {}.", locale);
					
			return "insertroom";
		}
		
		@RequestMapping(value = "/insertroom.do", method = {RequestMethod.POST, RequestMethod.GET})
		public String insertRoom(addroomDto dto, HttpServletRequest request, Locale locale, Model model) {
								 //�Ķ���Ϳ� dto�� �����ϸ� ����ʵ�� ������ �̸��̸� ��� �޴´�.
								 //@RequestParam("seq")int sseq : seq�� ���޵� �Ķ���͸� sseq�� �����Ѵ�.
			logger.info("���߰��ϱ� {}.", locale);
			String name = request.getParameter("name");
			String place = request.getParameter("place");
			String price = request.getParameter("price");
			String writer = request.getParameter("writer");
//			boolean isS = addroomservice.insertRoom(new addroomDto(0,name,place,price,writer,null));
			boolean isS = addroomservice.insertRoom(new addroomDto(name,place,price,writer)); //dto�����ڿ� ���� �� ����� �� ����� �ִ�.
//			boolean isS = addroomservice.insertRoom(dto); ���� ���. �Ķ���͸� ���� �ʿ䰡 ����.
			if(isS) {
			//	response.sendRedirect("boardlist.do"); �����
				 return "redirect:roomlist.do";  //���� ���� ���
				//return "��������" --> forward������� �����ϴ� �Ͱ� ����.
			}else {
				model.addAttribute("msg","���߰�����");
				return "error"; //return "��������" --> forward������� �����ϴ� �Ͱ� ����.
			}
		}
	
		//�� �󼼺���
		@RequestMapping(value = "/detailroom.do", method = RequestMethod.GET)
		public String detailRoom(int seq, Locale locale, Model model) {
			
			logger.info("�ۻ� ��ȸ�ϱ� {}.", locale);
//			int seq=Integer.parseInt(request.getParameter("seq")); ������ �ϴ� ����̴�. spring���� �ʿ������ ���� �Ķ���Ϳ� HttpServletRequest request ��� ��
			addroomDto dto = addroomservice.detailRoom(seq);
			model.addAttribute("dto", dto );
					
			return "detailroom";
			}

		
		@RequestMapping(value = "/updateroomform.do", method = RequestMethod.GET)
		public String updateroomForm(int seq, Locale locale, Model model) {
			logger.info("����������� �̵� {}.", locale);
			addroomDto dto= addroomservice.detailRoom(seq);
			model.addAttribute("dto", dto);
			return "updateroom"; //forward ���
		}
		

		@RequestMapping(value = "/updateroom.do", method = RequestMethod.POST)
		public String updateRoom(addroomDto dto, HttpServletRequest request,Locale locale, Model model) {
			logger.info("�����ϱ� {}.", locale);
			int seq=Integer.parseInt(request.getParameter("seq"));
			String name = request.getParameter("name");
			String price = request.getParameter("price");
			boolean isS= addroomservice.updateRoom(new addroomDto(seq,name,price));
	//		boolean isS = addroomservice.insertRoom(new addroomDto(seq,name,null,price,null,null));
//			boolean isS=addroomservice.updateRoom(dto); �̹�������ϸ� �Ķ���͸� �����ʾƵ� dto��ü ã�Ƽ� �ٷΰ���.
			if(isS){
				logger.info("�������� {}.", locale);
				return "redirect:detailroom.do?seq="+dto.getSeq();
			}else {
				model.addAttribute("msg","�ۼ�������");
				return "error";
			}
		}
		
		@RequestMapping(value = "/muldelroom.do", method = {RequestMethod.POST, RequestMethod.GET})
		public String mulDelroom(String[] chk,Locale locale, Model model) {
			//�Ķ���� �ޱ�: String[] chk --> name="chk" value="1,3,4,5,6" �迭�� �ö� �޴¹�
			logger.info("�����ϱ� {}.", locale);
			boolean isS= addroomservice.mulDel(chk);
			if(isS){
				logger.info("�������� {}.", locale);
				return "redirect:roomlist.do";
			}else {
				model.addAttribute("msg","�ۻ�������");
				return "error";
			}
		}
}
