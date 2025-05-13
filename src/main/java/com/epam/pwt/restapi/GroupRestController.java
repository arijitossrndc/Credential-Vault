package com.epam.pwt.restapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.epam.pwt.bean.GroupBean;
import com.epam.pwt.bean.UserBean;
import com.epam.pwt.exceptions.DuplicateGroupException;
import com.epam.pwt.exceptions.GroupNotFoundException;
import com.epam.pwt.exceptions.NonEmptyGroupExceptions;
import com.epam.pwt.exceptions.UserNotFoundException;
import com.epam.pwt.service.AccountService;
import com.epam.pwt.service.GroupService;
import com.epam.pwt.service.UserService;

@RequestMapping("/GroupApi")
@RestController
public class GroupRestController {
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	AccountService accountService;
	
	
	@GetMapping(value="/{id}/getAllGroups",produces=MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<GroupBean> getAllGroupsForUser(@PathVariable("id") int userId) throws Exception{
		
		UserBean userbean = userService.getDetailsById(userId);
		
		if(userbean==null)
			throw new UserNotFoundException("user is not available with this id "+userId);
		
		List<GroupBean> beans = groupService.getAllGroupDetailsByUser(userbean);
		
		for(final GroupBean groupBean : beans) {
			groupBean.setUserBean(userbean);
			Link selfLink = linkTo(methodOn(GroupRestController.class).getGroupDetailsById(groupBean.getGroupId()))
					.withSelfRel();
			
			groupBean.add(selfLink);
			
		}
		
		Link link = linkTo(methodOn(GroupRestController.class)
			      .getAllGroupsForUser(userId)).withSelfRel();
			    CollectionModel<GroupBean> result = CollectionModel.of(beans, link);
			    return result;
		
	}
	
	@GetMapping(value = "/getGroup/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public EntityModel<GroupBean> getGroupDetailsById(@PathVariable("id") int myId)throws Exception{
		
			GroupBean groupBean= groupService.getById(myId);
			
			if(groupBean==null)
				throw new GroupNotFoundException("With id : "+myId+" group not available");
			
			if(!groupBean.getGroupName().equalsIgnoreCase("general")){
				Link updateLink = linkTo(methodOn(GroupRestController.class).updateGroupDetails(groupBean))
						.withRel("UpdateLink");
				
				Link deleteLink = linkTo(methodOn(GroupRestController.class).deleteGroupDetails(myId))
						.withRel("DeleteLink");
				
				groupBean.add(updateLink);
				groupBean.add(deleteLink);
		    }
			Link accountLink = linkTo(methodOn(AccountRestController.class).getAllAccountsForGroup(myId))
					.withRel("ViewAllAccountsLink");
			
			Link selfLink = linkTo(methodOn(GroupRestController.class).getGroupDetailsById(myId))
					.withSelfRel();
			groupBean.add(accountLink);
			groupBean.add(selfLink);
			
			return EntityModel.of(groupBean);
	
	}
	
	@PostMapping(value = "/addGroup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> addGroupDetails(@Valid @RequestBody GroupBean groupBean) throws Exception{

		if(groupService.duplicateGroupExistInUser(groupBean.getGroupName(), groupBean.getUserBean().getId())) {
			throw new DuplicateGroupException("Group already present with this name",null,null);
		}
		groupService.addGroup(groupBean, groupBean.getUserBean());

		return new ResponseEntity<String>("Group added successfully ", HttpStatus.ACCEPTED);
	}
	
	
	@PutMapping(value="/updateGroup",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	
	public ResponseEntity<GroupBean> updateGroupDetails(@Valid @RequestBody GroupBean groupBean) throws Exception{
		
		 GroupBean groupBeanOld = groupService.getById(groupBean.getGroupId()); 
		if(groupBeanOld==null || groupBeanOld.getGroupName().equalsIgnoreCase("general")) {
			throw new GroupNotFoundException("Sorry Can't Update this group With id : "+groupBean.getGroupId());
		}
		if(groupService.duplicateGroupExistInUser(groupBean.getGroupName(), groupBeanOld.getUserBean().getId())) {
			throw new DuplicateGroupException("Group already present with this name",null,null);
		}
		
		GroupBean updatedGroupBean = groupService.updateGroupDetails(groupBean);
		updatedGroupBean.setUserBean(groupBeanOld.getUserBean());
		
		return new ResponseEntity<GroupBean>(updatedGroupBean,HttpStatus.OK);
	}
	
	
@DeleteMapping(value="/deleteGroup/{id}",produces=MediaType.TEXT_HTML_VALUE)
	
	public ResponseEntity<String> deleteGroupDetails(@PathVariable("id") int myId) throws Exception{
		
		GroupBean groupBean = groupService.getById(myId);
		if(groupBean==null || groupBean.getGroupName().equalsIgnoreCase("general")){
			throw new GroupNotFoundException("Sorry Can't delete this group With id : "+myId);
		}
		if(accountService.getAllAccountsByGroup(groupBean).size()>0) {
			throw new NonEmptyGroupExceptions("this group contains accounts so can't be deleted");
		}
		groupService.deleteGroup(myId);
		return new ResponseEntity<String>("",HttpStatus.NO_CONTENT);
	}

	
	

}
