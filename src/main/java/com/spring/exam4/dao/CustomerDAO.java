package com.spring.exam4.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.exam4.vo.Customer;


/**
 * 会員関連DAO
 */
@Repository
public class CustomerDAO {
	@Autowired
	SqlSession sqlSession;
	
	/**
	 * 会員加入処理
	 * @param customer 使用者が入力した加入情報
	 */
	public int insert(Customer customer) {
		CustomerMapper mapper = sqlSession.getMapper(CustomerMapper.class);
		
		int result = 0;
		try {
			result = mapper.insertCustomer(customer);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * IDで会員情報検索
	 * @param custid 検索するID
	 * @return 検索された会員情報。なければ null。
	 */
	public Customer get(String custid) {
		CustomerMapper mapper = sqlSession.getMapper(CustomerMapper.class);
		Customer cust = mapper.selectCustomer(custid);
		return cust;
	}
	
	/**
	 * user modify
	 * @param customer 使用者が入力した修正情報
	 */
	public int update(Customer customer) {
		CustomerMapper mapper = sqlSession.getMapper(CustomerMapper.class);
		
		int result = 0;
		try {
			result = mapper.updateCustomer(customer);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
