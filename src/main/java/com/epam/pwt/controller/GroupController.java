package com.epam.pwt.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.epam.pwt.bean.GroupBean;
import com.epam.pwt.exceptions.DuplicateGroupException;
import com.epam.pwt.exceptions.NonEmptyGroupExceptions;
import com.epam.pwt.service.AccountService;
import com.epam.pwt.service.GroupService;
import com.epam.pwt.service.UserService;

@Controller()
@RequestMapping("group")
public class GroupController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		binder.registerCustomEditor(String.class,stringTrimmerEditor);
	}
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("addGroupDetails")
	public ModelAndView groupHomePage() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("type","Add Group");
		mv.addObject("groupBean",new GroupBean());
		mv.setViewName("createGroup");
		return mv;
	}
	@PostMapping("addGroupDetails")
	public String addGroupDetails(@Valid @ModelAttribute("groupBean") GroupBean groupBean,BindingResult theBindingResult,ModelMap theModel) {
		String type = "Update Group";
		
		if(checkForNewGroup(groupBean))
			type="Add Group";
		
		if(theBindingResult.hasErrors()) {
			theModel.put("type", type);
			return "createGroup";
		}
		if(checkForNewGroup(groupBean)) {
			checkForDuplicateGroup(groupBean,type);
			groupService.addGroup(groupBean,userService.getUserBean());	
		}
		else{
			checkForDuplicateGroup(groupBean, type);
			groupService.updateGroupDetails(groupBean);
		}
		return "redirect:/group/viewGroupDetails";
		
	}
	
	@GetMapping("viewGroupDetails")
	public ModelAndView viewGroupDetails() {
		
		List<GroupBean> groupList = groupService.getAllGroupDetailsByUser(userService.getUserBean());
		
		return new ModelAndView("viewGroups","groupList",groupList);
		
	}
	
	@PostMapping("updateGroupDetails")
	public ModelAndView updateGroupDetails(@ModelAttribute("tempGroup") GroupBean groupBean) {
		
		GroupBean groupBean1 = groupService.getById(groupBean.getGroupId());
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("type","Update Group");
		mv.addObject("groupBean",groupBean1);
		mv.setViewName("createGroup");
		
		return mv;
		
	}
	
	@PostMapping("deleteGroupDetails")
	public String deleteGroupDetails(@ModelAttribute("tempGroup") GroupBean groupBean) throws NonEmptyGroupExceptions{
		
		if(!accountService.getAllAccountsByGroup(groupBean).isEmpty()) {
			throw new NonEmptyGroupExceptions("Group with one or more accounts cannot be deleted");
		}
		groupService.deleteGroup(groupBean.getGroupId());
		return "redirect:/group/viewGroupDetails";
		
	}
	
	public boolean checkForNewGroup(@Valid GroupBean groupBean) {
		boolean flag = false;
		if(groupBean.getGroupId()==null)
			flag=true;
		return flag;
	}
	public void checkForDuplicateGroup(GroupBean groupBean,String type) throws DuplicateGroupException {
		if(groupService.duplicateGroupExistInUser(groupBean.getGroupName(),userService.getUserBean().getId())) {
			
			throw new DuplicateGroupException("Group already exists with given groupName",type,groupBean);
		}
	}
	
	 @ExceptionHandler(DuplicateGroupException.class)
	 public ModelAndView duplicateGroupHandler(DuplicateGroupException duplicateGroupException){
		 	ModelAndView model = new ModelAndView();
	        model.addObject("errorMessage" , duplicateGroupException.getMessage());
	        model.addObject("type",duplicateGroupException.getType());
	        model.addObject("groupBean",duplicateGroupException.getGroupBean());
	        model.setViewName("createGroup");
	        return model;
	    }
	 
	 @ExceptionHandler(NonEmptyGroupExceptions.class)
	 public ModelAndView nonEmptyGroupHandler(NonEmptyGroupExceptions nonEmptyGroupException){
		 	ModelAndView model = new ModelAndView();
		 	List<GroupBean> groupList = groupService.getAllGroupDetailsByUser(userService.getUserBean());
	        model.addObject("errorMessage" , nonEmptyGroupException.getMessage());
	        model.addObject("groupList",groupList);
	        model.setViewName("viewGroups");
	        return model;
	    }
}
