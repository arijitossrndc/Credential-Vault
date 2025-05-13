package com.epam.pwt.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.pwt.bean.GroupBean;
import com.epam.pwt.bean.UserBean;
import com.epam.pwt.exceptions.DuplicateUserAccountException;
import com.epam.pwt.service.GroupService;
import com.epam.pwt.service.UserService;

@Controller
public class UserController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		binder.registerCustomEditor(String.class,stringTrimmerEditor);
	}
	
	@Autowired
	UserService userService;
	
	@Autowired
	GroupService groupService;
	
	
	@GetMapping("register")
	public ModelAndView getRegisterPage() {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("type","Register");
		mv.addObject("userBean",new UserBean());
		mv.setViewName("register");
		return mv;
	}
	
	@PostMapping("register")
	public String saveUser(@Valid @ModelAttribute("userBean") UserBean userBean , BindingResult result,
			ModelMap theModel) throws DuplicateUserAccountException  {
		
		if(result.hasErrors()) {
			theModel.put("type", "Register");
			return "register";
		}
		
		checkForDuplicateUsernameOrEmail(userBean);
		userService.addUser(userBean);
		addDefaultGroupForUser(userBean);
		return "redirect:login";
	}
	

	@GetMapping("login")
	public String getLoginPage() {
	  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/group/viewGroupDetails";
	}
	
	public void checkForDuplicateUsernameOrEmail(UserBean userBean) throws DuplicateUserAccountException {
		String usernameException=null;
		String emailException=null;
		if(userService.countByUsername(userBean.getUsername())>0) {
			usernameException="Username already taken";
		}
		if(userService.countByEmail(userBean.getEmail())>0) {
			emailException="Email already taken";
		}
		if(usernameException!=null || emailException!=null) {
			
			throw new DuplicateUserAccountException(usernameException,emailException,userBean);
		}
	}
	
	public void addDefaultGroupForUser(final UserBean userBean) {
		GroupBean groupBean = new GroupBean(0,"General");
		
		UserBean userBeanAdded = userService.getDetailsByName(userBean.getUsername());
		groupService.addGroup(groupBean, userBeanAdded);
		
	}
	
	@ExceptionHandler(DuplicateUserAccountException.class)
	public ModelAndView DuplicateAccountHandler(DuplicateUserAccountException duplicateUserAccountException) {
		
		ModelAndView themodel = new ModelAndView();
		 themodel.addObject("usernameError",duplicateUserAccountException.getUsernameException());
		themodel.addObject("emailError", duplicateUserAccountException.getEmailException());
		themodel.addObject("userBean",duplicateUserAccountException.getUserbean());
		themodel.addObject("type","Register");
		
		themodel.setViewName("register");
		return themodel;
	}
	
	

}
