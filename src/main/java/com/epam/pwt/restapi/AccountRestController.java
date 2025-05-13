package com.epam.pwt.restapi;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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


import com.epam.pwt.bean.AccountBean;
import com.epam.pwt.bean.GroupBean;
import com.epam.pwt.exceptions.AccountNotFoundException;
import com.epam.pwt.exceptions.DuplicateAccountException;
import com.epam.pwt.exceptions.GroupNotFoundException;
import com.epam.pwt.service.AccountService;
import com.epam.pwt.service.GroupService;

@RestController
@RequestMapping("/AccountApi")
public class AccountRestController {
	

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private GroupService groupService;
	
	
	@GetMapping(value="/{id}/getAllAccounts",produces=MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<AccountBean> getAllAccountsForGroup(@PathVariable("id") int groupId) throws Exception{
		
		GroupBean groupBean = groupService.getById(groupId);
		if(groupBean==null) {
			throw new GroupNotFoundException("Group with this id "+ groupId +" is not available");
		}
		List<AccountBean> beans = accountService.getAllAccountsByGroup(groupBean);
		
		for(final AccountBean accountBean : beans) {
			Link selfLink = linkTo(methodOn(AccountRestController.class).getAccountDetailsById(accountBean.getAccountId()))
					.withSelfRel();
			accountBean.setGroupBean(groupBean);
			accountBean.add(selfLink);
			
		}
		
		Link link = linkTo(methodOn(AccountRestController.class)
			      .getAllAccountsForGroup(groupId)).withSelfRel();
			    CollectionModel<AccountBean> result = CollectionModel.of(beans, link);
			    return result;
		
	}
	
	@GetMapping(value = "/getAccount/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<AccountBean> getAccountDetailsById(@PathVariable("id") int myId) throws Exception {

		AccountBean accountBean = accountService.getById(myId);
		
		if(accountBean==null)
			throw new AccountNotFoundException("Account with this Id "+ myId+" not available");
		
		GroupBean groupBean = groupService.getById(accountBean.getGroupId());
		accountBean.setGroupBean(groupBean);
		Link updateLink = linkTo(methodOn(AccountRestController.class).updateAccountDetails(accountBean))
				.withRel("UpdateLink");
		
		Link deleteLink = linkTo(methodOn(AccountRestController.class).deleteAccountDetails(myId))
				.withRel("DeleteLink");
		
		Link selfLink = linkTo(methodOn(AccountRestController.class).getAccountDetailsById(myId))
				.withSelfRel();
		
		Link groupLink  = linkTo(methodOn(GroupRestController.class).getGroupDetailsById(accountBean.getGroupId()))
				.withRel("GroupLink");
		accountBean.add(updateLink);
		accountBean.add(deleteLink);
		accountBean.add(selfLink);
		accountBean.add(groupLink);
		return EntityModel.of(accountBean);

	}
	
	@PostMapping(value="/addAccount",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.TEXT_HTML_VALUE)
	public  ResponseEntity<String>addAccountDetails(@Valid @RequestBody AccountBean accountBean)throws Exception {
			if(accountService.duplicateAccountExistInGroup(accountBean.getAccountName(),
					accountBean.getGroupBean().getGroupId())) {
				throw new DuplicateAccountException("Duplicate Account Name Found", null, null);
			}
				
			accountService.addAccountByGroup(accountBean);
			return new ResponseEntity<String>("Account Created Succesfully",HttpStatus.ACCEPTED);
			
	}
	
	@PutMapping(value="/updateAccount",consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateAccountDetails(@Valid @RequestBody AccountBean accountBean)throws Exception{
		
		if(accountService.duplicateAccountExistInGroup(accountBean.getAccountName(),
				accountBean.getGroupBean().getGroupId())) {
			throw new DuplicateAccountException("Duplicate Account Name Found", null, null);
		}
		if(accountService.getById(accountBean.getAccountId())==null)
			throw new AccountNotFoundException("Can't be updated this Account as it is not available");
		
		AccountBean accountBean1 = accountService.updateAccount(accountBean, accountBean.getAccountId());
		
		return new ResponseEntity<String>(accountBean1.toString(),HttpStatus.OK);
	}
	
	
	@DeleteMapping(value="/deleteAccount/{id}",produces=MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> deleteAccountDetails(@PathVariable("id") int myId) throws Exception{
		
		if(accountService.getById(myId)==null) {
			throw new AccountNotFoundException("Account is not available with given " + myId);
		}
		
		accountService.deleteAccount(accountService.getById(myId));
		return new ResponseEntity<String>("",HttpStatus.NO_CONTENT);
	}
	
	

}
