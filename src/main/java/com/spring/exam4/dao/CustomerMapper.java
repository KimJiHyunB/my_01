package com.spring.exam4.dao;

import com.spring.exam4.vo.Customer;

/**
 * 会員関連Mybatis使用のメソッド
 */
public interface CustomerMapper {
	// 会員情報保存
	public int insertCustomer(Customer customer);
	// IDで該当会員情報検索
	public Customer selectCustomer(String custid);
	// 会員情報修正
	public int updateCustomer(Customer customer);

}
