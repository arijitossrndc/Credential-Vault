package com.epam.pwt.serviceTest;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;

import com.epam.pwt.bean.UserBean;
import com.epam.pwt.dao.UserDaoImp;
import com.epam.pwt.exceptions.DuplicateUserAccountException;
import com.epam.pwt.service.UserServiceImp;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImpTest {
	
	
	@InjectMocks
	UserServiceImp userService;
	
	@Mock
	UserDaoImp userDto;
	
	@Mock
	UserBean userBean;
	
	@Test
	@DisplayName("Add user with no exceptions")
	
	public void addUserwithNoException() {
		
		Assertions.assertDoesNotThrow(()->userService.addUser(userBean));
	}
	
	@Test
	@DisplayName("Get User By Id If Exist")
	
	public void getUserByIdIfNoExceptionOccur() {
		
		when(userDto.getDetailsById(anyInt())).thenReturn(userBean);
		Assertions.assertEquals(userBean, userService.getDetailsById(anyInt()));
	}
	
	@Test
	@DisplayName("Get All User Accounts")
	
	public void getAllUsers() {
		
		when(userDto.getAllUsers()).thenReturn(List.of(userBean));
		Assertions.assertEquals(List.of(userBean),userService.getAllUsers());
	}
	
	@Test
	@DisplayName("Get User By Username If Exist")
	
	public void getUserByNameIfExist() {
		
		when(userDto.getDetailsByName(anyString())).thenReturn(userBean);
		Assertions.assertEquals(userBean, userService.getDetailsByName(anyString()));
	}
	
	
	
	@Test
	@DisplayName("load user by username if user exist")
	
	public void loadUserByUserName() {
		
		Assertions.assertDoesNotThrow(()->userService.loadUserByUsername(anyString()));
	}
	
	@Test
	@DisplayName("get update userDetails")
	public void returnUpdatedUserDetails() {
		
		when(userDto.updateUserDetails(any(), anyInt())).thenReturn(userBean);
		Assertions.assertEquals(userBean, userService.updateUserDetails(any(), anyInt()));
	}
	
	@Test
	@DisplayName("get count by username")
	public void returnCountByUsername() {
		
		when(userDto.countByUsername(anyString())).thenReturn(1);
		Assertions.assertDoesNotThrow(()->userService.countByUsername(anyString()));
	}
	
	@Test
	@DisplayName("get count by email")
	public void returnCountByEmail() {
		
		when(userDto.countByEmail(anyString())).thenReturn(1);
		Assertions.assertDoesNotThrow(()->userService.countByEmail(anyString()));
	}
	
	@Test
	@DisplayName("delete user if exist")
	public void deleteUserIfExist() {
		
		Assertions.assertDoesNotThrow(()->userService.deleteUserDetails(anyInt()));
		
		
	}
	@Test
	@DisplayName("throw exception if user already exist while saving")
	
	public void getUserBean() {
		
		doThrow(DuplicateUserAccountException.class).when(userDto).addUser(userBean);
		Assertions.assertThrows(DuplicateUserAccountException.class,()-> userService.addUser(userBean));
	}
	

}
