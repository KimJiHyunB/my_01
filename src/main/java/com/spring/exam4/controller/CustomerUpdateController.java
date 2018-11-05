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
 *     モデルに"customer"という名前の値が保存されるとき,セッションにも保存
 *    修正段階のスタートから最後の段階までセッションの値維持
 */
@Controller
@RequestMapping("customer")
@SessionAttributes("customer")
public class CustomerUpdateController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerUpdateController.class);
	
	@Autowired
	CustomerDAO dao;		
	
	/**
	 *  会員情報修正フォーム
	 */
	@RequestMapping (value="update", method=RequestMethod.GET)
	public String updateForm(HttpSession session, Model model) {
		String id = (String) session.getAttribute("logid");
		Customer customer = dao.get(id);
	
		model.addAttribute("customer", customer);
		return "customer/updateForm";
	}

	/**
	 *     会員 情報修正処理
	 * @param customer updateFormで生成した  VO客体にユーザが入力した修正情報が追加されたオブジェクト。
	 *  			    セッションに当該値がなければエラー。
	 */
	@RequestMapping (value="update", method=RequestMethod.POST)
	public String update(@ModelAttribute("customer") Customer customer,Model model) {
		
		int result = dao.update(customer);
		if (result != 1) {
			// DB updateに失敗した場合,alert()出力用のメッセージをモデルに保存
			model.addAttribute("errorMsg", "受精失敗");
			return "customer/updateForm";
		}
		
		return "redirect:updateComplete";
	}

	/**
	 * update complete
	 * @param customer DBに最終保存された情報
	 */
	@RequestMapping(value="updateComplete", method=RequestMethod.GET)
	public String updateComplete(@ModelAttribute("customer") Customer customer, 
			SessionStatus sessionStatus,Model model) {
		
		// 修正処理された情報をモデルに保存
		model.addAttribute("result", customer);
		sessionStatus.setComplete();
		
		return "customer/updateComplete";
	}

}
