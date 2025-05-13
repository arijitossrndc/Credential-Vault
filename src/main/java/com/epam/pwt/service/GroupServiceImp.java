package com.epam.pwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.pwt.bean.GroupBean;
import com.epam.pwt.bean.UserBean;
import com.epam.pwt.dao.GroupDaoImp;


@Service("groupService")
public class GroupServiceImp implements GroupService{
	

	@Autowired
	GroupDaoImp groupDao;
	
	public GroupServiceImp() {
		
	}
	
	
	public void addGroup(final GroupBean groupBean,final UserBean userBean) {
		
		groupDao.addGroup(groupBean,userBean);
	}
	
	public List<GroupBean> getAllGroupDetailsByUser(final UserBean userBean) {
		
		return groupDao.getAllGroupDetailsByUser(userBean);
	}
	
	
	public GroupBean updateGroupDetails(final GroupBean groupBean) {
		
		return groupDao.updateGroupDetails(groupBean);

	}
	
	public void deleteGroup(int groupId) {
		
		groupDao.deleteGroup(groupId);
	}

	
	public boolean duplicateGroupExistInUser(String groupName, int id) {
		
		return groupDao.duplicateGroupExistInUser(groupName, id);
	}



	public GroupBean getById(int groupId) {
		
		return groupDao.getById(groupId);
	}


}
