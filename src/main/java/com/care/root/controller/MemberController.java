package com.care.root.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		System.out.println("login 角青");
		return "member/login";
	}
	
	@PostMapping("logChk")
	public String logChk(@RequestParam String id, @RequestParam String pw, 
							HttpSession session, RedirectAttributes rs) {
		System.out.println("logChk 角求");
		
		int result = ms.logChk(id, pw);
		if(result == 0) {
			session.setAttribute(LOGIN, id);
			rs.addAttribute("id", id);
			return "redirect:successLogin";
		}
		return "redirect:login";
	}
	
	@GetMapping("successLogin")
	public String successLogin(@RequestParam String id, HttpSession session) {
		System.out.println("successLogin 角青");
		session.setAttribute(LoginSession.LOGIN, id);
		return "member/successLogin";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		System.out.println("logout 角青");
		session.invalidate();
		return "redirect:login";
	}
	
	@GetMapping("list")
	public String list(Model model) {
		System.out.println("list 角青");
		model.addAttribute("list", ms.getList());
		return "member/list";
	}
	
	@GetMapping("register_view")
	public String register_view() {
		System.out.println("register_view 角青");
		
		return "member/register_view";
	}
	
	@PostMapping("register")
	public String register(HttpServletRequest req, MemberDTO dto) {
		System.out.println("register角青");
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
