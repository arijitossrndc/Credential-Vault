package com.epam.pwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.epam.pwt.bean.UserBean;
import com.epam.pwt.bean.UserDetailsBean;
import com.epam.pwt.dao.UserDaoImp;

@Service("userService")
public class UserServiceImp implements UserService{
	
	
	@Autowired
	private UserDaoImp userDao;
	
	public void addUser(UserBean userBean) {
		
		userDao.addUser(userBean);	
	}
	
	public UserBean getDetailsById(int id) {
			
		return userDao.getDetailsById(id);
	}
	
	public UserBean updateUserDetails(final UserBean userBean, int userId) {
			
		return userDao.updateUserDetails(userBean, userId);
	}
		
	public void deleteUserDetails(int userId) {
			
			userDao.deleteUserDetails(userId);
	}
	
	public UserBean getDetailsByName(String name) {
		
		return userDao.getDetailsByName(name);
	}
	
	public int countByUsername(String username) {
		
		return userDao.countByUsername(username);
	}
	
	public int countByEmail(String email) {
		
		return userDao.countByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 UserBean userBean = getDetailsByName(username);
		 
	     return new UserDetailsBean(userBean);
	       
	}

	@Override
	public UserBean getUserBean() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return getDetailsByName(authentication.getName());	}

	@Override
	public List<UserBean> getAllUsers() {
		
		
		return userDao.getAllUsers();
	}

}
