package com.epam.pwt.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.pwt.bean.AccountBean;
import com.epam.pwt.bean.GroupBean;

import com.epam.pwt.entity.Account;
import com.epam.pwt.entity.Group;
import com.epam.pwt.repository.AccountRepository;


@Component("accountDto")
public class AccountDaoImp {
	
	
	@Autowired
	private AccountRepository accountRepository;
	
	
	@Autowired
	private ModelMapper modelMapper;


	public void addAccountByGroup(final AccountBean accountBean) {
		
		accountBean.setAccountId(0);
		Group group  = modelMapper.map(accountBean.getGroupBean(), Group.class);
		Account account = modelMapper.map(accountBean , Account.class);
		account.setGroup(group);
		account.setComments(accountBean.getComments());
		accountRepository.save(account);
		
	}


	public List<AccountBean> getAllAccountsByGroup(final GroupBean groupBean) {
		
		Group group = modelMapper.map(groupBean, Group.class);
		List<Account> list = accountRepository.findByGroup(group);
		
		List<AccountBean> accountBeans = list.stream().map(account->modelMapper.map(account, AccountBean.class))
				.collect(Collectors.toList());
		
		return accountBeans;
	}


	public List<AccountBean> getAllAccounts() {
		
		List<Account> accounts = accountRepository.findAll();
		
		List<AccountBean> accountBeans = accounts.stream().map(account -> modelMapper.map(account, AccountBean.class))
				.collect(Collectors.toList());
		return accountBeans;
	}


	public void deleteAccount(AccountBean accountBean) {
		
		Account account = modelMapper.map(accountBean, Account.class);
		accountRepository.delete(account);
		
	}


	public AccountBean updateAccount(AccountBean accountBean, int accountId) {
		Account accountToUpdate = accountRepository.getById(accountId);
		
		accountToUpdate.setAccountName(accountBean.getAccountName());
		accountToUpdate.setUserName(accountBean.getUsername());
		accountToUpdate.setPassword(accountBean.getPassword());
		accountToUpdate.setUrl(accountBean.getUrl());
		accountToUpdate.setComments(accountBean.getComments());
		Group group = modelMapper.map(accountBean.getGroupBean(), Group.class);
		accountToUpdate.setGroup(group);
		accountRepository.save(accountToUpdate);
		
		AccountBean accountBeanUpdated = modelMapper.map(accountToUpdate, AccountBean.class);
		return accountBeanUpdated;
	}


	public boolean duplicateAccountExistInGroup(String accountName,int groupId) {
		boolean IsDuplicate=false;
		if(accountRepository.existsAccountWithAccountNameInGroup(accountName,groupId)){
			IsDuplicate=true;
		}
		return IsDuplicate;
	}


	public AccountBean getById(int accountId) {
		AccountBean accountBean = null;
		Optional<Account> accountOptional = accountRepository.findById(accountId);
			
			if(accountOptional.isPresent()) {
				Account account = accountOptional.get();
				accountBean = modelMapper.map(account,AccountBean.class);
				GroupBean groupBean = modelMapper.map(account.getGroup(), GroupBean.class);
				accountBean.setGroupBean(groupBean);
			}
			
			return accountBean;
	}


	
	

}
