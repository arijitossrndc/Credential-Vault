package com.epam.pwt.controller;

import java.util.LinkedHashMap;
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

import com.epam.pwt.bean.AccountBean;
import com.epam.pwt.bean.GroupBean;
import com.epam.pwt.exceptions.DuplicateAccountException;

import com.epam.pwt.service.AccountService;
import com.epam.pwt.service.GroupService;
import com.epam.pwt.service.UserService;

@Controller
@RequestMapping("account")
public class AccountController {
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		binder.registerCustomEditor(String.class,stringTrimmerEditor);
	}
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("addAccountDetails")
	public ModelAndView accountHomePage() {
	
		AccountBean accountBean = new AccountBean();
		accountBean.setGroupOptions(generateGroupOptions());
		ModelAndView mv = new ModelAndView();
		mv.addObject("type","Add Account");
		mv.addObject("accountBean",accountBean);
		mv.setViewName("createAccount");
		return mv;
	}

	
	@PostMapping("addAccountDetails")
	public String addAccountDetails(@Valid @ModelAttribute("accountBean") AccountBean accountBean,
			BindingResult theBindingResult,ModelMap theModel) throws DuplicateAccountException{
		
		GroupBean groupBean = groupService.getById(accountBean.getGroupId());
		accountBean.setGroupBean(groupBean);
		accountBean.setGroupOptions(generateGroupOptions());
		theModel.put("accountBean", accountBean);
		String type = "Update Account";
		
		if(checkForNewAccount(accountBean))
			type = "Add Account";
		
		if(theBindingResult.hasErrors()) {
			theModel.put("type", type);
			return "createAccount";
		}
		if(checkForNewAccount(accountBean)) {
			checkForDuplicateAccount(accountBean);
			accountService.addAccountByGroup(accountBean);
		}
		else {
			accountService.updateAccount(accountBean, accountBean.getAccountId());
		}
		return "redirect:/group/viewGroupDetails";
	}
	
	@PostMapping("viewAccountDetails")
	public ModelAndView viewAccountsHome(@ModelAttribute("tempGroup") GroupBean groupBean) {
		
		GroupBean groupBeanForAccount = groupService.getById(groupBean.getGroupId());
		ModelAndView mv = getViewForAccounts(groupBeanForAccount);
		return mv;
	}
	
	
	@PostMapping("updateAccountDetails")
	public ModelAndView updateAccountDetails(@ModelAttribute("tempAccount") AccountBean bean) {
		
		AccountBean accountBean = accountService.getById(bean.getAccountId());
		accountBean.setGroupOptions(generateGroupOptions());
		ModelAndView mv = new ModelAndView();
		mv.addObject("type","Update Account");
		mv.addObject("accountBean",accountBean);
		System.out.println(accountBean.getPassword());
		mv.setViewName("createAccount");
		return mv;
		
	}
	
	
	@PostMapping("deleteAccountDetails")
	public ModelAndView deleteAccountDetails(@ModelAttribute("tempAccount") AccountBean bean) {
		
		AccountBean accountBean = accountService.getById(bean.getAccountId());
		GroupBean groupBeanForAccount = groupService.getById(accountBean.getGroupId());
		accountService.deleteAccount(accountBean);
		ModelAndView mv = getViewForAccounts(groupBeanForAccount);
		return mv;
		
	}
	
	public ModelAndView getViewForAccounts(GroupBean groupBean) {
		List<AccountBean> accountList = accountService.getAllAccountsByGroup(groupBean);
		ModelAndView mv = new ModelAndView();
		AccountBean accountBean = new AccountBean();
		accountBean.setGroupBean(groupBean);
		mv.addObject("accountBean",accountBean);
		mv.addObject("accountsList",accountList);
		mv.setViewName("viewAccounts");
		return mv;
	}
	private LinkedHashMap<Integer, String> generateGroupOptions() {
		List<GroupBean> grouplist = groupService.getAllGroupDetailsByUser(userService.getUserBean());
		LinkedHashMap<Integer,String> groupOptions = new LinkedHashMap<>();
		for (GroupBean group : grouplist) {
			groupOptions.put(group.getGroupId(),group.getGroupName());
		}
		return groupOptions;
	}
	public boolean checkForNewAccount(AccountBean accountBean) {
		
		boolean flag = true;
		if(accountBean.getAccountId()!=null)
			flag=false;
		return flag;
			
	}
	public void checkForDuplicateAccount(AccountBean accountBean) throws DuplicateAccountException {
		if(accountService.duplicateAccountExistInGroup(accountBean.getAccountName(),accountBean.getGroupId())) {
			
			throw new DuplicateAccountException("Account already exists with given accountName","Add Account",accountBean);
		}
	}
	 @ExceptionHandler(DuplicateAccountException.class)
	 public ModelAndView duplicateAccountHandler(DuplicateAccountException duplicateAccountException){
		 	ModelAndView model = new ModelAndView();
		 	AccountBean accountBean = duplicateAccountException.getAccountBean();
		 	accountBean.setGroupOptions(generateGroupOptions());
	        model.addObject("errorMessage" , duplicateAccountException.getMessage());
	        model.addObject("type",duplicateAccountException.getType());
	        model.addObject("accountBean",accountBean);
	        model.setViewName("createAccount");
	        return model;
	    }
	 
	
	
	
}
