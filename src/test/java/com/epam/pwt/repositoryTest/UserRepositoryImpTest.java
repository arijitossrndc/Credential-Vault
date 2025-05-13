package com.epam.pwt.repositoryTest;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.epam.pwt.bean.UserBean;
import com.epam.pwt.dao.GroupDaoImp;
import com.epam.pwt.dao.UserDaoImp;
import com.epam.pwt.entity.User;
import com.epam.pwt.exceptions.DuplicateUserAccountException;
import com.epam.pwt.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryImpTest {
	
	@InjectMocks
	UserDaoImp userDto;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	PasswordEncoder passwordEncoder;
	@Mock
	GroupDaoImp groupDto;
	
	
	@Mock
	ModelMapper modelMapper;
	UserBean userBean;
	Optional<User> userOptional;
	
	User user;
	
	@Before
	public void setUp() {
		user = new User("","","");
		userBean = new UserBean("","","");
		userOptional = Optional.ofNullable(user);
		when(modelMapper.map(user, UserBean.class)).thenReturn(userBean);
		when(userRepository.countByUsername(anyString())).thenReturn(1);
		when(userRepository.countByEmail(anyString())).thenReturn(1);
	}
	
	@Test
	@DisplayName("Add User if user not already exist")
	
	public void addUserIfUserNoExist() {
		
		Assertions.assertDoesNotThrow(()->userDto.addUser(userBean));
	}
	
	@Test
	@DisplayName("throw duplicate username Exception if username already exist")
	
	public void throwDuplicateUsernameExceptionIfUsernameAlreadyExist() {
		
		doThrow(DuplicateUserAccountException.class).when(userRepository).countByUsername(anyString());
		Assertions.assertThrows(DuplicateUserAccountException.class, ()->userDto.countByUsername(anyString()));
		
	}
	
	@Test
	@DisplayName("throw duplicate email Exception if email already exist")
	
	public void throwDuplicateEmailExceptionIfEmailAlreadyExist() {
		
		doThrow(DuplicateUserAccountException.class).when(userRepository).countByEmail(anyString());
		Assertions.assertThrows(DuplicateUserAccountException.class, ()->userDto.countByEmail(anyString()));
		
	}
	
	@Test
	@DisplayName("Get User by id if exist")
	
	public void getUserByIdIfExist() {
		
		when(userRepository.findById(anyInt())).thenReturn(userOptional);
		Assertions.assertEquals(userBean, userDto.getDetailsById(anyInt()));
	}
	
	@Test
	@DisplayName("Get UserDetails by username if exist")
	
	public void getUserByUsernameIfExist() {
		
		when(userRepository.findByUsername(anyString())).thenReturn(userOptional);
		Assertions.assertEquals(userBean, userDto.getDetailsByName(anyString()));
	}
	
	@Test
	@DisplayName("Get all users details")
	
	public void returnListOfUsers() {
		
		when(userRepository.findAll()).thenReturn(List.of(user));
		Assertions.assertEquals(List.of(userBean), userDto.getAllUsers());
	}
	
	@Test
	@DisplayName("update details of user if exist")
	
	public void updateUserDetailsById() {
		
		when(userRepository.getById(anyInt())).thenReturn(user);
		Assertions.assertEquals(userBean, userDto.updateUserDetails(userBean, anyInt()));
	}
	
	@Test
	@DisplayName("return count of user by username")
	
	public void returnCountByUsername() {
		
		Assertions.assertDoesNotThrow(()->userDto.countByUsername(anyString()));
	}
	
	@Test
	@DisplayName("return count of user by email")
	
	public void returnCountByEmail() {
		
		Assertions.assertDoesNotThrow(()->userDto.countByEmail(anyString()));
	}
	
	
	
	@Test
	@DisplayName("delete user from the table if exist")
	
	public void deleteUserDetailsById() {
		
		Assertions.assertDoesNotThrow(()->userDto.deleteUserDetails(anyInt()));
	}

}
