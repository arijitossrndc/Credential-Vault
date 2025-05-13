package com.epam.pwt.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.pwt.bean.GroupBean;
import com.epam.pwt.bean.UserBean;
import com.epam.pwt.entity.Group;
import com.epam.pwt.entity.User;
import com.epam.pwt.repository.GroupRepository;


@Component("groupDto")
public class GroupDaoImp{
	
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AccountDaoImp accountDto;

	public void addGroup(GroupBean groupBean , UserBean userBean) {
		
		groupBean.setGroupId(0);
		Group group  = modelMapper.map(groupBean,Group.class);
		User user = modelMapper.map(userBean, User.class);
		group.setUser(user);
		groupRepository.save(group);
		
	}
	
	public List<GroupBean> getAllGroupDetailsByUser(UserBean userBean) {
		
		User user = modelMapper.map(userBean, User.class);
		List<Group> groups = groupRepository.findByUser(user);
		
		List<GroupBean> groupBeans = groups.stream().map(group -> modelMapper.map(group, GroupBean.class))
				.collect(Collectors.toList());
		return groupBeans;
	}


	public GroupBean updateGroupDetails(GroupBean groupBean) {
		
		Group groupToUpdate = groupRepository.getById(groupBean.getGroupId());
		groupToUpdate.setGroupName(groupBean.getGroupName());
		groupRepository.save(groupToUpdate);
		GroupBean groupBeanUpdated = modelMapper.map(groupToUpdate,GroupBean.class);
		return groupBeanUpdated;
		
	}



	public void deleteGroup(int groupId) {
		
		Group group = groupRepository.getById(groupId);
		groupRepository.delete(group);
		
		
	}


	
	public boolean duplicateGroupExistInUser(String groupName,int id) {
		
		boolean IsDuplicate=false;
		if(groupRepository.existsGroupWithGroupNameAndUserId(groupName,id)){
			IsDuplicate=true;
		}
		return IsDuplicate;
	}



	public GroupBean getById(int groupId) {
		
		GroupBean groupBean = null;
		Optional<Group> groupOptional = groupRepository.findById(groupId);
			
			if(groupOptional.isPresent()) {
				Group group = groupOptional.get();
				groupBean = modelMapper.map(group,GroupBean.class);
				UserBean userBean = modelMapper.map(group.getUser(), UserBean.class);
				groupBean.setUserBean(userBean);
				groupBean.setAccountBeans(accountDto.getAllAccountsByGroup(groupBean));
			}
			
			return groupBean;
	}
	

}
