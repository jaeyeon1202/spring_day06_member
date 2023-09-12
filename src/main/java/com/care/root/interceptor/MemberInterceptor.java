package com.care.root.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.care.root.common.LoginSession;

public class MemberInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		if(session.getAttribute(LoginSession.LOGIN)==null) { //로그인이 되어 있지 않다.
			//response.sendRedirect("login");
			String msg = "<script> alert('로그인 먼저 하세요.');";
			msg += "location.href='/root/member/login';</script>";
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter(); 
			out.print( msg );
			return false; //리턴값에 의해서 ture:사용자가 요청한 컨트롤러, false: 사용자가 요청한 페이지로 연결x
		}
		
		System.out.println("list 컨트롤러 전 실행");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("===list 컨트롤러 후 실행");
	}

}
