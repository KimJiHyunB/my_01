package com.spring.exam4.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.spring.exam4.dao.CustomerDAO;
import com.spring.exam4.vo.Customer;

/**
 * user info modify
 */
@Controller
@RequestMapping("customer")
@SessionAttributes("customer")
public class CustomerUpdateController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerUpdateController.class);
	
	@Autowired
	CustomerDAO dao;		
	
	/**
	 * update Form
	 */
	@RequestMapping (value="update", method=RequestMethod.GET)
	public String updateForm(HttpSession session, Model model) {
		String id = (String) session.getAttribute("logid");
		Customer customer = dao.get(id);
	
		model.addAttribute("customer", customer);
		return "customer/updateForm";
	}

	/**
	 * update
	 */
	@RequestMapping (value="update", method=RequestMethod.POST)
	public String update(@ModelAttribute("customer") Customer customer,Model model) {
		
		int result = dao.update(customer);
		if (result != 1) {
			model.addAttribute("errorMsg", "수정 실패");
			return "customer/updateForm";
		}
		
		return "redirect:updateComplete";
	}

	/**
	 * update complete
	 */
	@RequestMapping(value="updateComplete", method=RequestMethod.GET)
	public String updateComplete(@ModelAttribute("customer") Customer customer, 
			SessionStatus sessionStatus,Model model) {

		model.addAttribute("result", customer);
		sessionStatus.setComplete();
		
		return "customer/updateComplete";
	}

}
