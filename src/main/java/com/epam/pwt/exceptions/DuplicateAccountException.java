package com.epam.pwt.exceptions;

import com.epam.pwt.bean.AccountBean;

public class DuplicateAccountException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private AccountBean accountBean;

	public DuplicateAccountException(String message,String type,AccountBean accountBean) {
		super(message);
		this.setType(type);
		this.setAccountBean(accountBean);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AccountBean getAccountBean() {
		return accountBean;
	}

	public void setAccountBean(AccountBean accountBean) {
		this.accountBean = accountBean;
	}

}
