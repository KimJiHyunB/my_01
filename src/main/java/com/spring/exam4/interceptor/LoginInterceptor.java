package com.spring.exam4.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		logger.debug("LoginInterceptor 실행");
		
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("logid");
		
		if (loginId == null) {
			response.sendRedirect(request.getContextPath() + "/customer/loginForm");
			return false;
		}
		return super.preHandle(request, response, handler);
	}

}
