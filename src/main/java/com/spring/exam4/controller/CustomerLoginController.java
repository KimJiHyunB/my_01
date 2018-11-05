package com.spring.exam4.controller;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.exam4.dao.CustomerDAO;
import com.spring.exam4.vo.Customer;


/**
 * login, logout controller
 */
@RequestMapping("customer")
@Controller
public class CustomerLoginController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerLoginController.class);

	// 会員関連データ処理オブジェクト
	@Autowired
	CustomerDAO dao;

	
	/**
	 * login Form
	 */
	@RequestMapping (value="loginForm", method=RequestMethod.GET)
	public String loginForm() {
		return "customer/loginForm";
	}

	/**
	 * login
	 * @param custid ユーザーが入力したID
	 * @param password ユーザーが入力したパスワード
	 * @param model Model客体
	 * @param session HtpSession 客体
	 */
	@RequestMapping (value="login", method=RequestMethod.POST)
	public String login(
			String custid, String password,Model model, HttpSession session) {
		
		Customer customer = dao.get(custid);		
			
		if (customer != null && customer.getPassword().equals(password)) {
			session.setAttribute("logid", customer.getCustid());
			
			
			return "redirect:/";
		}
		else {
			model.addAttribute("errorMsg", "ID or PASSWORD check");
			return "customer/loginForm";
		}
	}
	
	/**
	 * logout
	 * @param session HttpSession객체
	 */
	@RequestMapping (value="logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
