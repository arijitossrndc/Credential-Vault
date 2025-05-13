package com.epam.pwt.service;

import java.util.List;

import com.epam.pwt.bean.GroupBean;
import com.epam.pwt.bean.UserBean;

public interface GroupService {

	public void addGroup(final GroupBean groupBean, final UserBean userBean);
	
	public List<GroupBean> getAllGroupDetailsByUser(final UserBean userBean);
	
	public GroupBean updateGroupDetails(final GroupBean groupBean);
	
	public void deleteGroup(int groupId);
	
	public boolean duplicateGroupExistInUser(String groupName,int userId);
	
	public GroupBean getById(int groupId);
}
