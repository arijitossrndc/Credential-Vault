package com.epam.pwt.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.epam.pwt.bean.UserBean;
import com.epam.pwt.entity.User;

import com.epam.pwt.repository.UserRepository;

@Component("userDto")
public class UserDaoImp {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private GroupDaoImp groupDto;
	
	public void addUser(UserBean userBean) {
		
		userBean.setId(0);
		
		userBean.setPassword(passwordEncoder.encode(userBean.getPassword()));
		User user = modelMapper.map(userBean, User.class);
		
		userRepository.save(user);	
	}
	
	public UserBean getDetailsById(int id) {
			
		UserBean userBean = null;
		Optional<User> userOptional = userRepository.findById(id);
			
			if(userOptional.isPresent()) {
				User user = userOptional.get();
				userBean = modelMapper.map(user,UserBean.class);
				userBean.setGroupBeans(groupDto.getAllGroupDetailsByUser(userBean));
			}
			
			return userBean;
	}
	
	public UserBean updateUserDetails(final UserBean userBean, int userId) {
			
			User userToUpdate = userRepository.getById(userId);
			
			userToUpdate.setUsername(userBean.getUsername());
			userToUpdate.setEmail(userBean.getEmail());
			userToUpdate.setPassword(userBean.getPassword());
			userRepository.save(userToUpdate);
			UserBean userBeanUpdated = modelMapper.map(userToUpdate,UserBean.class);
			return userBeanUpdated;
	}
		
	public void deleteUserDetails(int userId) {
			
			userRepository.delete(userRepository.getById(userId));
	}
	
	public UserBean getDetailsByName(String name) {
		
		UserBean userBean = null;
		Optional<User> userOptional = userRepository.findByUsername(name);
			
			if(userOptional.isPresent()) {
				User user = userOptional.get();
				userBean = modelMapper.map(user,UserBean.class);
			}
			
			return userBean;
	}
	
	public int countByUsername(String username) {
		
		return userRepository.countByUsername(username);
	}
	
	public int countByEmail(String email) {
		
		return userRepository.countByEmail(email);
	}
	
	public List<UserBean> getAllUsers(){
		List<User> userList= userRepository.findAll();
		
		List<UserBean> beans=userList.stream().map(user->modelMapper.map(user, UserBean.class)).collect(Collectors.toList());
		
		return beans;
		
	}
	
	
	
	
	
	

}
