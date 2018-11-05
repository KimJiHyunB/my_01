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
 * モデルに"customer"という名前の値が保存されるとき,セッションにも保存
 * 加入段階の開始から最後の段階までセッションの値維持
 * 
 */
@Controller
@RequestMapping("customer")
@SessionAttributes("customer")
public class CustomerJoinController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerJoinController.class);
	
	// 会員関連データ処理オブジェクト
	@Autowired
	CustomerDAO dao;		
		
	/**
	 * join Form
	 */
	@RequestMapping (value="joinForm", method=RequestMethod.GET)
	public String joinForm(Model model) {
		// VO客体を作ってセッションに保存
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		return "customer/joinForm";
	}

	/**
	 * join
	 * @param customer joinForm()에서 생성한 VO객체에 사용자가 입력한 가입 정보가 추가된 객체.
	 * 세션에 해당 값이 없으면 에러.
	 */
	@RequestMapping (value="join", method=RequestMethod.POST)
	public String join(@ModelAttribute("customer") Customer customer,Model model) {
		
		int result = dao.insert(customer);
		if (result != 1) {
			// DB保存に失敗した場合はalert()出力用のメッセージをモデルに保存
			model.addAttribute("errorMsg", "fail");
			return "customer/joinForm";
		}
		
		return "redirect:joinComplete";
	}

	
	/**
	 * IDの重複確認
	 * @param custid 利用者が入力したID
	 * @return リターン結果
	 */
	@ResponseBody
	@RequestMapping(value="idcheck", method=RequestMethod.POST)
	public String idcheck(String custid, Model model) {
		logger.info("idcheck 1");
		// ID検索
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
	 * 会員加入処理完了
	 * @param customer DBに最終保存された情報
	 * @param session Status @SessionAttributesで指定した名前をセッションで削除するために使用
	 */
	@RequestMapping(value="joinComplete", method=RequestMethod.GET)
	public String joinComplete(@ModelAttribute("customer") Customer customer, SessionStatus sessionStatus,Model model) {
		// 加入処理されたIDをモデルに保存
		model.addAttribute("id", customer.getCustid());
		sessionStatus.setComplete();
		
		return "customer/joinComplete";
	}

	
}
