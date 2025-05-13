package com.epam.pwt.repositoryTest;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import com.epam.pwt.bean.GroupBean;
import com.epam.pwt.bean.UserBean;
import com.epam.pwt.dao.AccountDaoImp;
import com.epam.pwt.dao.GroupDaoImp;
import com.epam.pwt.entity.Group;
import com.epam.pwt.entity.User;
import com.epam.pwt.exceptions.DuplicateGroupException;
import com.epam.pwt.repository.GroupRepository;

@RunWith(MockitoJUnitRunner.class)
public class GroupRepositoryImpTest {
	
	
	@InjectMocks
	GroupDaoImp groupDto;
	
	@Mock
	GroupRepository groupRepository;
	
	@Mock
	AccountDaoImp accountDto;
	
	@Mock
	ModelMapper modelMapper;
	Optional<Group> groupOptional;
	GroupBean groupBean;
	UserBean userBean;
	Group group;
	User user;
	
	
	@Before
	public void setUp() {
		
		userBean = new UserBean("","","");
		groupBean = new GroupBean(null,"");
		groupBean.setUserBean(userBean);
		groupBean.setGroupId(0);
		group = new Group();
		group.setGroupName("hhh");
		group.setGroupId(0);
		groupOptional = Optional.ofNullable(group);
		when(modelMapper.map(group, GroupBean.class)).thenReturn(groupBean);
		when(modelMapper.map(groupBean, Group.class)).thenReturn(group);
		
	}
	
	@Test
	@DisplayName("Add group for a user if group is not already exist")
	
	public void addGroupByUserIfNoExceptionOccur() {
		
		when(groupRepository.save(group)).thenReturn(group);
		Assertions.assertDoesNotThrow(()->groupDto.addGroup(groupBean, userBean));
	}
	
	@Test
	@DisplayName("update group details if groupname is unique and not default one")
	public void updateGroupDetails() {
		
		when(groupRepository.getById(anyInt())).thenReturn(group);
		when(groupRepository.save(group)).thenReturn(group);
		Assertions.assertEquals(groupBean,groupDto.updateGroupDetails(groupBean));
	}
	
	
	@Test
	@DisplayName("throw DuplicateGroupException while saving if group already exist")
	
	public void throwDuplicateGroupExceptionIfGroupExist() {
		doThrow(DuplicateGroupException.class).when(groupRepository).save(group);
		Assertions.assertThrows(DuplicateGroupException.class, ()->groupDto.addGroup(groupBean, userBean));
	}
	
	@Test
	@DisplayName("Get All groups For User")
	
	public void getAllGroupsListForUser() {
		when(groupRepository.findByUser(user)).thenReturn(List.of(group));
		Assertions.assertEquals(List.of(groupBean), groupDto.getAllGroupDetailsByUser(userBean));
	}
	
	@Test
	@DisplayName("throws DuplicateGroupException while checking for duplicate group")
	
	public void throwsDuplicateGroupExceptionWhileCheckingDuplicateGroup() {
		
		doThrow(DuplicateGroupException.class).
		when(groupRepository).existsGroupWithGroupNameAndUserId(anyString(), anyInt());
		Assertions.assertThrows(DuplicateGroupException.class, ()->groupDto.duplicateGroupExistInUser(anyString(), anyInt()));
		
	}
	
	@Test
	@DisplayName("delete group if exist")
	
	public void deleteGroupIfExist() {
		
		Assertions.assertDoesNotThrow(()->groupDto.deleteGroup(1));
	}
	
	@Test
	@DisplayName("check if duplicate group in user")
	
	public void returnTrueIfDuplicateGroupExist() {
		
		when(groupRepository.existsGroupWithGroupNameAndUserId(anyString(), anyInt())).thenReturn(true);
		Assertions.assertEquals(true, groupDto.duplicateGroupExistInUser(anyString(), anyInt()));
	}
	
	@Test
	@DisplayName("Get Group by id If exist")
	
	public void returnGroupByIdIfExist() {
		
		when(groupRepository.findById(anyInt())).thenReturn(groupOptional);
		Assertions.assertEquals(groupBean, groupDto.getById(anyInt()));
	}
	
	

}
