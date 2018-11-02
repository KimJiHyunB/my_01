package com.spring.exam4.vo;

public class Customer {
	
	String custid;			
	String password;			
	String name;			
	String email;			 		
	int p;
	
	public Customer() {
	}

	public Customer(String custid, String password, String name, String email, int p) {
		super();
		this.custid = custid;
		this.password = password;
		this.name = name;
		this.email = email;
		this.p = p;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	@Override
	public String toString() {
		return "Customer [custid=" + custid + ", password=" + password + ", name=" + name + ", email=" + email + ", p="
				+ p + "]";
	}		
	
	

}
