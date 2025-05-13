package com.epam.pwt.serviceTest;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.epam.pwt.bean.AccountBean;
import com.epam.pwt.bean.GroupBean;
import com.epam.pwt.bean.UserBean;
import com.epam.pwt.dao.AccountDaoImp;
import com.epam.pwt.entity.Account;
import com.epam.pwt.entity.Group;
import com.epam.pwt.entity.User;
import com.epam.pwt.exceptions.DuplicateAccountException;
import com.epam.pwt.service.AccountServiceImp;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImpTest {
	
	@InjectMocks
	AccountServiceImp accountService;
	
	@Mock
	AccountDaoImp accountDto;
	
	
	@Mock
	AccountBean accountBean;
	Account account;
	UserBean userBean;
	GroupBean groupBean;
	Group group;
	User user;
	 
      @Before
       public void setUp(){
       
       accountBean = new AccountBean("","" , "" , "" ,"");
       accountBean.setAccountName("ddd");
       System.out.println(accountBean.toString());
       account = new Account(" "," "," "," ", " ");
       Group group1 = new Group();
       group1.setGroupId(1);
       group1.setGroupName("ddd");
       Account a = new Account();
       a.setAccountId(1);
       a.setAccountName("ss");
       a.setComments("dd");
       a.setGroup(group);
       a.setPassword("ddd");
       a.setUserName("dd");
       
   }
	
	 @Test
     @DisplayName("AccountService should get all Accounts By Group")
     public void getAllAccountByGroup(){
         when(accountDto.getAllAccountsByGroup(groupBean)).thenReturn(List.of(accountBean));
         Assertions.assertEquals(List.of(accountBean), accountService.getAllAccountsByGroup(groupBean));
     }
	 
	 @Test
	 	@DisplayName("Return true for duplicate account")
	    public void returnTrueForDuplicateAccount(){
	        
	 		when(accountDto.duplicateAccountExistInGroup(anyString(), anyInt())).thenReturn(true);
	 		Assertions.assertEquals(true, accountService.duplicateAccountExistInGroup("general_account",18));
	    }
	 	
	 	
	 	@Test
	 	@DisplayName("Return False for duplicate account")
	    public void returnFalseForUniqueAccount(){
	        
	 		when(accountDto.duplicateAccountExistInGroup(anyString(), anyInt())).thenReturn(false);
	 		Assertions.assertEquals(false, accountService.duplicateAccountExistInGroup("general_account",18));
	    }
	 	
	 	@Test
	 	@DisplayName("Save account by group with no exceptions")
	 	public void saveAccountByGroup() {
	 		Assertions.assertDoesNotThrow(()->accountService.addAccountByGroup(accountBean));
	 	}
	 	 
	 	 @Test
	 	 @DisplayName("Get All Accounts")
	 	 public void getAllAccounts() {
	 		 when(accountDto.getAllAccounts()).thenReturn(List.of(accountBean));
	 		Assertions.assertEquals(List.of(accountBean), accountService.getAllAccounts());
	 	 }
	 	 
	 	 @Test
	 	 @DisplayName("Get Account By Id with no exceptions")
	 	 public void getAccountById() {
	 		 when(accountDto.getById(1)).thenReturn(accountBean);
	 		 Assertions.assertEquals(accountBean, accountService.getById(1));
	 	 }
	 	 
	 	 @Test
	 	 @DisplayName("Update Account with Given Details with no exceptions")
	 	 public void updateAccount() {
	 		 when(accountDto.updateAccount(accountBean, 1)).thenReturn(accountBean);
	 		 Assertions.assertEquals(accountBean, accountService.updateAccount(accountBean, 1));
	 		 
	 	 }
	 	 
	 	 @Test
	 	 @DisplayName("Delete Account if no exceptions occured")
	 	 public void deleteAccount() {
	 		 
	 		 Assertions.assertDoesNotThrow(()->accountService.deleteAccount(accountBean));
	 	 }
	 	 
	 	 @Test
	 	 @DisplayName("throws Duplicate Account Exception if account already Exist")
	 	 
	 	 public void throwsDuplicateAccountExceptionIfAccountExist() {
	 		 
	 		 doThrow(DuplicateAccountException.class).when(accountDto).addAccountByGroup(accountBean);
	 		 Assertions.assertThrows(DuplicateAccountException.class, ()->accountService.addAccountByGroup(accountBean));
	 	 }
	 
	 
	
	

}
