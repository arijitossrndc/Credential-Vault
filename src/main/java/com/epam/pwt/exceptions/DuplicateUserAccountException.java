package com.epam.pwt.exceptions;

import com.epam.pwt.bean.UserBean;

public class DuplicateUserAccountException extends RuntimeException{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String usernameException;
	
	private String emailException;
	
	private UserBean userbean;
	
	
	public UserBean getUserbean() {
		return userbean;
	}


	public void setUserbean(UserBean userbean) {
		this.userbean = userbean;
	}


	public DuplicateUserAccountException(String usernameException,String emailException,UserBean bean) {
		
		this.usernameException=usernameException;
		this.emailException=emailException;
		this.userbean= bean;
	}


	public String getUsernameException() {
		return usernameException;
	}


	public void setUsernameException(String usernameException) {
		this.usernameException = usernameException;
	}


	public String getEmailException() {
		return emailException;
	}


	public void setEmailException(String emailException) {
		this.emailException = emailException;
	}
	
	

}
