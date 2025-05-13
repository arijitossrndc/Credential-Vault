package com.epam.pwt.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.epam.pwt.bean.UserBean;



public interface UserService extends UserDetailsService{
	
	public UserBean getDetailsById(int id);
	
	public void addUser(UserBean userBean);
	
	public UserBean updateUserDetails(final UserBean userBean, int userId) ;
	
	public void deleteUserDetails(int userId);
	
	public UserBean getDetailsByName(String name);
	
	public int countByUsername(String username);
	
	public int countByEmail(String email);
	
	public UserBean getUserBean();
	
	public List<UserBean> getAllUsers();
	
	

}
