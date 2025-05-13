package com.epam.pwt.restapi;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
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
import com.epam.pwt.exceptions.DuplicateUserAccountException;
import com.epam.pwt.exceptions.UserNotFoundException;
import com.epam.pwt.service.GroupService;
import com.epam.pwt.service.UserService;

@RequestMapping("/UserApi")
@RestController
public class UserRestController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	
	
	@GetMapping(value="/getAllUsers",produces = {MediaType.APPLICATION_JSON_VALUE})
	public CollectionModel<UserBean>getAllUserDetails() throws Exception {

		List<UserBean> users = userService.getAllUsers();

		for(UserBean userBean:users) {
			
			int userID = userBean.getId();
			
			 Link selfLink = linkTo(methodOn(UserRestController.class).getUserDetailById(userID)).withSelfRel();
		        userBean.add(selfLink);
		        
		     if(groupService.getAllGroupDetailsByUser(userBean) != null) {
		    	 
		    	 Link ordersLink = linkTo(methodOn(GroupRestController.class)
		    			 .getAllGroupsForUser(userID)).withRel("allGroups");
		    	 userBean.add(ordersLink);
		     }
		}
		Link link = linkTo(methodOn(UserRestController.class).getAllUserDetails()).withSelfRel();
	    CollectionModel<UserBean> result = CollectionModel.of(users, link);
	    return result;
	}
	
	@GetMapping(value = "/getUser/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public EntityModel<UserBean> getUserDetailById(@PathVariable("id") int myId) throws Exception {

		  	UserBean userBean = userService.getDetailsById(myId);
		
	        if(userBean==null)
	        	throw new UserNotFoundException("With id : "+myId+" user not available");
	        
	        Link updateLink =linkTo(methodOn(UserRestController.class).updateUserDetails(userBean,null))
	        		.withRel("UpdateLink");
	        userBean.add(updateLink);
	        
	        Link deleteLink = linkTo(methodOn(UserRestController.class).deleteUserDetails(myId)).withRel("DeleteLink");
	        userBean.add(deleteLink);
	        
	        Link viewAllGroups = linkTo(methodOn(GroupRestController.class).getAllGroupsForUser(myId))
	        		.withRel("View All Groups");
	        userBean.add(viewAllGroups);
	        Link selfLink = linkTo(methodOn(UserRestController.class).getUserDetailById(myId)).withSelfRel();
	        userBean.add(selfLink);
	        
	        return EntityModel.of(userBean);
	      
	}
	
	
	@PostMapping(value = "/addUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> addUserDetails(@Valid @RequestBody UserBean bean ) {

		if(userService.countByUsername(bean.getUsername())>0 || userService.countByEmail(bean.getEmail())>0) {
			throw new DuplicateUserAccountException("","",null);
		}
		userService.addUser(bean);

		return new ResponseEntity<String>("User added successfully ", HttpStatus.ACCEPTED);
	}
	
	@PutMapping(value="/updateUser",consumes = MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	
	public ResponseEntity<String> updateUserDetails(@RequestBody @Valid UserBean userBean, Errors errors){
		
		if(userService.countByUsername(userBean.getUsername())>0 || userService.countByEmail(userBean.getEmail())>0) {
			throw new DuplicateUserAccountException("","",null);
		}
		
		UserBean UpdatedUserBean = userService.updateUserDetails(userBean, userBean.getId());
		
		return new ResponseEntity<String>(UpdatedUserBean.toString(),HttpStatus.OK);
	}
	
	@DeleteMapping(value="/deleteUser/{id}",produces=MediaType.TEXT_HTML_VALUE)
	
	public ResponseEntity<String> deleteUserDetails(@PathVariable("id") int myId) throws Exception{
		
		if(userService.getDetailsById(myId)==null) {
			throw new UserNotFoundException("User is not available with given " + myId);
		}
		userService.deleteUserDetails(myId);
		return new ResponseEntity<String>("",HttpStatus.NO_CONTENT);
	}
	
	

}
