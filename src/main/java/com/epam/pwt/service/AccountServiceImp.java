package com.epam.pwt.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.pwt.bean.AccountBean;
import com.epam.pwt.bean.GroupBean;
import com.epam.pwt.dao.AccountDaoImp;

@Service("accountService")
public class AccountServiceImp implements AccountService{
	
	@Autowired
	AccountDaoImp accountDao;
	
	public AccountServiceImp() {
		
	}

	public void addAccountByGroup(AccountBean accountBean) {
		
		accountDao.addAccountByGroup(accountBean);
	}

	
	public List<AccountBean> getAllAccountsByGroup(GroupBean groupBean) {
		
		return accountDao.getAllAccountsByGroup(groupBean);
	}

	public List<AccountBean> getAllAccounts() {
		
		return accountDao.getAllAccounts();
	}

	
	public void deleteAccount(AccountBean accountBean) {
		
		accountDao.deleteAccount(accountBean);
	}


	public AccountBean updateAccount(AccountBean accountBean, int accountId) {
		
		return accountDao.updateAccount(accountBean, accountId);
	}


	public boolean duplicateAccountExistInGroup(String accountName,int groupId) {
		
		return accountDao.duplicateAccountExistInGroup(accountName,groupId);
	}

	
	public AccountBean getById(int accountId) {
		
		return accountDao.getById(accountId);
	}
	
	

}
