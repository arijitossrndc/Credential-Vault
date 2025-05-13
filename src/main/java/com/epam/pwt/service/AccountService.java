package com.epam.pwt.service;

import java.util.List;

import com.epam.pwt.bean.AccountBean;
import com.epam.pwt.bean.GroupBean;


public interface AccountService {
	
public void addAccountByGroup(final AccountBean accountBean);
	
	public List<AccountBean> getAllAccountsByGroup(final GroupBean groupBean);
	
	public List<AccountBean> getAllAccounts();
	
	public void deleteAccount(final AccountBean accountBean);
	
	public AccountBean updateAccount(final AccountBean accountBean,int accountId);
	
	public boolean duplicateAccountExistInGroup(String accountName,int groupId);
	
	public AccountBean getById(int accountId);
	

}
