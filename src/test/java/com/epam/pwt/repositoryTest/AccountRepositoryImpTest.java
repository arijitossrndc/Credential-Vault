package com.epam.pwt.repositoryTest;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import static org.mockito.Mockito.when;

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
import com.epam.pwt.repository.AccountRepository;
import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountRepositoryImpTest {
	
	@InjectMocks
	AccountDaoImp accountDtoImp;

	@Mock
	AccountRepository accountRepository;
	
	 @Mock
	 ModelMapper modelMapper;
	 AccountBean accountBean;
	 Account account;
	 UserBean userBean;
	 GroupBean groupBean;
	 Group group;
	 User user;
	 Optional<Account> accountOptional;
	@Before
    public void setUp(){
	
		
		groupBean = new GroupBean();
        accountBean = new AccountBean("","" , "" , "" ,"");
        accountBean.setGroupBean(groupBean);
        account = new Account("","","","", "");
        accountOptional=Optional.ofNullable(account);
        when(modelMapper.map(account, AccountBean.class)).thenReturn(accountBean);
        when(modelMapper.map(accountBean, Account.class)).thenReturn(account);
    }
	 
	 
 	@Test
 	@DisplayName("Return true for duplicate account")
    public void returnTrueForDuplicateAccount(){
        
 		when(accountRepository.existsAccountWithAccountNameInGroup(anyString(), anyInt())).thenReturn(true);
 		Assertions.assertEquals(true, accountDtoImp.duplicateAccountExistInGroup("general_account",18));
    }
 	
 	
 	@Test
 	@DisplayName("Return False for duplicate account")
    public void returnFalseForUniqueAccount(){
        
 		when(accountRepository.existsAccountWithAccountNameInGroup(anyString(), anyInt())).thenReturn(false);
 		Assertions.assertEquals(false, accountDtoImp.duplicateAccountExistInGroup("general_account",18));
    }
 	
 	@Test
 	@DisplayName("Save account by group with no exceptions")
 	public void saveAccountByGroup() {
 		when(accountRepository.save(account)).thenReturn(account);
 		Assertions.assertDoesNotThrow(()->accountDtoImp.addAccountByGroup(accountBean));
 	}



 	 @Test
     @DisplayName("Get All Accounts By group")
     public void getAllAccountByGroup(){
         when(accountRepository.findByGroup(group)).thenReturn(List.of(account));
         Assertions.assertEquals(List.of(accountBean), accountDtoImp.getAllAccountsByGroup(groupBean));
     }
 	 
 	 @Test
 	 @DisplayName("Get All Accounts")
 	 public void getAllAccounts() {
 		 when(accountRepository.findAll()).thenReturn(List.of(account));
 		Assertions.assertEquals(List.of(accountBean), accountDtoImp.getAllAccounts());
 	 }
 	 
 	 @Test
 	 @DisplayName("Get Account By Id with no exceptions")
 	 public void getAccountById() {
 		 when(accountRepository.findById(anyInt())).thenReturn(accountOptional);
 		 Assertions.assertEquals(accountBean, accountDtoImp.getById(1));
 	 }
 	 
 	 @Test
 	 @DisplayName("Update Account with Given Details with no exceptions")
 	 public void updateAccount() {
 		 when(accountRepository.getById(1)).thenReturn(account);
 		 when(accountRepository.save(account)).thenReturn(account);
 		 Assertions.assertEquals(accountBean, accountDtoImp.updateAccount(accountBean, 1));
 		 
 	 }
 	 
 	 @Test
 	 @DisplayName("Delete Account with no exceptions")
 	 public void deleteAccount() {
 		 
 		 Assertions.assertDoesNotThrow(()->accountDtoImp.deleteAccount(accountBean));
 		 
 	 }
 	

}
