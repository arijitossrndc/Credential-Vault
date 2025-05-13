package com.epam.pwt.exceptions;

import com.epam.pwt.bean.GroupBean;

public class DuplicateGroupException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private GroupBean groupBean;
	
	
	public DuplicateGroupException(String message,String type, GroupBean groupBean) {
		super(message);
		this.type=type;
		this.groupBean=groupBean;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public GroupBean getGroupBean() {
		return groupBean;
	}


	public void setGroupBean(GroupBean groupBean) {
		this.groupBean = groupBean;
	}

}
