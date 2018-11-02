package com.spring.exam4.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.spring.exam4.dao.CustomerDAO;
import com.spring.exam4.vo.Customer;


/**
 * join controller
 */
@Controller
@RequestMapping("customer")
@SessionAttributes("customer")
public class CustomerJoinController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerJoinController.class);
	
	@Autowired
	CustomerDAO dao;		
		
	/**
	 * join Form
	 */
	@RequestMapping (value="joinForm", method=RequestMethod.GET)
	public String joinForm(Model model) {
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		return "customer/joinForm";
	}

	/**
	 * join
	 */
	@RequestMapping (value="join", method=RequestMethod.POST)
	public String join(@ModelAttribute("customer") Customer customer,Model model) {
		
		int result = dao.insert(customer);
		if (result != 1) {
			model.addAttribute("errorMsg", "가입 실패");
			return "customer/joinForm";
		}
		
		return "redirect:joinComplete";
	}

	
	/**
	 * id check
	 */
	@ResponseBody
	@RequestMapping(value="idcheck", method=RequestMethod.POST)
	public String idcheck(String custid, Model model) {
		logger.info("idcheck 1");
		Customer customer = dao.get(custid);
		
		if (customer == null) {
			
			logger.info("idcheck pos 2");
			return "Available ID";
		} else {
			logger.info("idcheck imp 2");
			return "Unavailable ID";
		}
				
		
	}
	
	/**
	 * join complete
	 */
	@RequestMapping(value="joinComplete", method=RequestMethod.GET)
	public String joinComplete(@ModelAttribute("customer") Customer customer, SessionStatus sessionStatus,Model model) {
		
		model.addAttribute("id", customer.getCustid());
		sessionStatus.setComplete();
		
		return "customer/joinComplete";
	}

	
}
