package com.epam.pwt.serviceTest;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.epam.pwt.bean.GroupBean;
import com.epam.pwt.bean.UserBean;
import com.epam.pwt.dao.GroupDaoImp;
import com.epam.pwt.entity.Group;
import com.epam.pwt.entity.User;
import com.epam.pwt.exceptions.NonEmptyGroupExceptions;
import com.epam.pwt.service.GroupServiceImp;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceImpTest {
	
	@InjectMocks
	GroupServiceImp groupService;
	
	@Mock
	GroupDaoImp groupDto;
	
	@Mock
	GroupBean groupBean;
	GroupBean groupBean2;
	UserBean userBean;
	Group group;
	User user;
	
	@Before
	public void setUp() {
		
		userBean = new UserBean("Sagar_7299","Sagar@8820k","mitt@gmail.com");
		groupBean = new GroupBean(1,"General");
	    groupBean2  = new GroupBean(2,"Google");
	    
	    Group g = new Group();
	    System.out.println(g.getGroupName());
	    System.out.println(g.getGroupId());
	    System.out.println(g.toString());
	    
	    
		
	}
	
	@Test
	@DisplayName("Return True For Duplicate Group For A User")
	
	public void returnTrueForDuplicateGroup() {
		when(groupDto.duplicateGroupExistInUser(anyString(), anyInt())).thenReturn(true);
		Assertions.assertEquals(true, groupService.duplicateGroupExistInUser("General", 1));
	}
	
	@Test
	@DisplayName("Get all groups for user")
	
	public void returnListOfGroupsForUser() {
		
		when(groupDto.getAllGroupDetailsByUser(userBean)).thenReturn(List.of(groupBean));
		Assertions.assertEquals(List.of(groupBean), groupService.getAllGroupDetailsByUser(userBean));
	}
	
	@Test
	@DisplayName("Add Group For User with no exceptions")
	
	public void saveGroupForUserWithNoExceptions() {
		
		Assertions.assertDoesNotThrow(()->groupService.addGroup(groupBean, userBean));
	}
	
	
	@Test
	@DisplayName("Return Group By Id If Exist")
	
	public void returnGroupByIdIfExist() {
		
		when(groupDto.getById(1)).thenReturn(groupBean);
		Assertions.assertEquals(groupBean, groupService.getById(1));
	}
	
	@Test
	@DisplayName("Update Group Details if Its Not A Default One")
	
	public void updateGroupDetailsIfNotDefaultGroup() {
		
		when(groupDto.updateGroupDetails(groupBean2)).thenReturn(groupBean2);
		Assertions.assertEquals(groupBean2, groupService.updateGroupDetails(groupBean2));
	}
	
	@Test
	@DisplayName("Delete Group By Id If Exist And Not the default one")
	
	public void deleteGroupByIdIfExist() {
		
		Assertions.assertDoesNotThrow(()->groupService.deleteGroup(2));
	}
	
	@Test
	@DisplayName("throws Non Empty Group Exception if group which has to be deleted has at least one account")
	
	public void throwNonEmptyGroupExceptionForNonEmtpyGroupDeletion() {
		
		doThrow(NonEmptyGroupExceptions.class).when(groupDto).deleteGroup(2);
		Assertions.assertThrows(NonEmptyGroupExceptions.class, ()->groupService.deleteGroup(2));
	}
	

}
