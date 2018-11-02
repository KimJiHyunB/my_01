package com.spring.exam4.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.exam4.vo.Customer;


/**
 * customer DAO
 */
@Repository
public class CustomerDAO {
	@Autowired
	SqlSession sqlSession;
	
	/**
	 * join
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
	 * select 
	 */
	public Customer get(String custid) {
		CustomerMapper mapper = sqlSession.getMapper(CustomerMapper.class);
		Customer cust = mapper.selectCustomer(custid);
		return cust;
	}
	
	/**
	 * user modify
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
