package com.care.root.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.care.root.common.LoginSession;
import com.care.root.dto.MemberDTO;
import com.care.root.service.MemberService;

@Controller
@RequestMapping("member")
public class MemberController implements LoginSession {
	
	@Autowired MemberService ms;
	
	@GetMapping("login")
	public String login() {
		System.out.println("login ����");
		return "member/login";
	}
	
	@PostMapping("logChk")
	public String logChk(@RequestParam String id, @RequestParam String pw, 
			@RequestParam(required = false, defaultValue = "off") String autoLogin, HttpSession session, RedirectAttributes rs) {
		System.out.println("logChk ����");
		
		int result = ms.logChk(id, pw);
		if(result == 0) {
			session.setAttribute(LOGIN, id);
			rs.addAttribute("id", id);
			rs.addAttribute("autoLogin", autoLogin);
			return "redirect:successLogin";
		}
		return "redirect:login";
	}
	
	@GetMapping("successLogin")
	public String successLogin(@RequestParam String id, HttpSession session
								,@RequestParam String autoLogin, HttpServletResponse res) {
		System.out.println("successLogin ����");
		System.out.println("autoLogin : "+autoLogin);
		if(autoLogin.equals("on")) {
			int limitTime = 60*60*24*90; //90�� ����
			Cookie loginCookie = new Cookie("loginCookie", session.getId());
			loginCookie.setPath("/");
			loginCookie.setMaxAge(limitTime);
			res.addCookie(loginCookie);
			
			ms.keepLogin(session.getId(), id);
		}
		
		session.setAttribute(LoginSession.LOGIN, id);
		return "member/successLogin";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session,
				@CookieValue(value="loginCookie", required = false) Cookie cookie,
				HttpServletResponse res) {
		if(cookie != null) {
			cookie.setMaxAge(0); //��������״� ��Ű�� �����
			ms.keepLogin("nan", (String)session.getAttribute(LOGIN));
		}
		System.out.println("logout ����");
		session.invalidate();
		return "redirect:login";
	}
	
	@GetMapping("list")
	public String list(Model model) {
		System.out.println("list ����");
		model.addAttribute("list", ms.getList());
		return "member/list";
	}
	
	@GetMapping("register_view")
	public String register_view() {
		System.out.println("register_view ����");
		
		return "member/register_view";
	}
	
	@PostMapping("register")
	public String register(HttpServletRequest req, MemberDTO dto) {
		System.out.println("register����");
		String[] addr = req.getParameterValues("addr");
		String ad = "";
		for(String a: addr) {
			System.out.println(a);
			ad += a +"/" ;
		}
		System.out.println(ad);
		String[] addr02 = ad.split("/");
		for(String a1 : addr02) {
			System.out.println(a1);
		}
		System.out.println("====dto=======");
		System.out.println(dto.getId());
		System.out.println(dto.getPw());
		System.out.println(dto.getAddr());
		
		ms.register(dto, req.getParameterValues("addr"));
		
		return "redirect:login";
	}
	
	@GetMapping("info")
	public String info(@RequestParam String id, Model model) {
		model.addAttribute("mem", ms.getMember(id));
		return "member/info";
	}
}
