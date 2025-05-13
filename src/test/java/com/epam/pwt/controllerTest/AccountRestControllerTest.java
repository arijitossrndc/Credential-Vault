package com.epam.pwt.controllerTest;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.pwt.bean.AccountBean;
import com.epam.pwt.bean.GroupBean;
import com.epam.pwt.exceptions.AccountNotFoundException;
import com.epam.pwt.exceptions.DuplicateAccountException;
import com.epam.pwt.restapi.AccountRestController;
import com.epam.pwt.service.AccountServiceImp;
import com.epam.pwt.service.GroupServiceImp;
import com.epam.pwt.service.UserServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.test.context.support.WithMockUser;
@RunWith(SpringRunner.class)
@WebMvcTest(value=AccountRestController.class)
@WithMockUser
public class AccountRestControllerTest {
	
	@MockBean
	AccountServiceImp accountService;
	
	
	@MockBean
	GroupServiceImp groupService;
	
	@MockBean
	UserServiceImp userService;
	
	@Autowired
    MockMvc mockMvc;
	
	@Mock
	AccountBean accountBean;
	
	@Mock
	GroupBean groupBean;
	
	@Before
	public void setUp() {
		
		accountBean = new AccountBean("","","","","");
		groupBean = new GroupBean(2,"");
		accountBean.setGroupId(0);
		
		accountBean.setGroupBean(groupBean);
		
	}
	
	@Test
	@DisplayName("Get all accounts for a group if exist")
	
	public void returnListOfAccountsForGroupIfExist() throws Exception {
		accountBean.setAccountId(1);
		when(groupService.getById(anyInt())).thenReturn(groupBean);
		when(accountService.getAllAccountsByGroup(groupBean)).thenReturn(List.of(accountBean));
		mockMvc.perform(MockMvcRequestBuilders.get("/AccountApi/1/getAllAccounts")
				.contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
	}
	@Test
	@DisplayName("if account exist with given id then status equal to OK.")
	
	public void returnStatusOkIfAccountByIdExist() throws Exception {
		
		when(accountService.getById(anyInt())).thenReturn(accountBean);
		mockMvc.perform(MockMvcRequestBuilders.get("/AccountApi/getAccount/18")
				.contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
	}
	
	@Test
    @DisplayName("if account not exist with given id then status equal to NOT_FOUND and throw AccountNotFoundException.")
    public void getAccountByIdShouldGiveNotFoundStatusIfAccountNotExist() throws Exception {
        doThrow(AccountNotFoundException.class).when(accountService).getById(anyInt());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/AccountApi/getAccount/18")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
	
	@Test
    @DisplayName("if account not exist then status be NOT_FOUND and method return exception")
    public void deleteAccountShouldGiveNotFoundStatusIfAccountNotExist() throws Exception {
        doThrow(AccountNotFoundException.class).when(accountService).deleteAccount(accountBean);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/AccountApi/deleteAccount/18")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
	
	@Test
    @DisplayName("saveAccount Should Give Bad Request Status If Account is invalid")
    public void saveAccountShouldGiveValidationMessagesWithConflictCode() throws Exception {
		
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/AccountApi/addAccount")
                        .content(asJsonString(accountBean))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }


	@Test
    @DisplayName("saveAccount Should Give Ok Status If Account saved")
    public void saveAccountShouldSaveAccountWithAcceptedStatus() throws Exception {
        AccountBean accountBean = new AccountBean("gmail_account","Sagar_7299","Sagar@8820k","http://www.as.com","etc");
        accountBean.setGroupBean(groupBean);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/AccountApi/addAccount")
                        .content(asJsonString(accountBean))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

	
	@Test
    @DisplayName("updateAccount Should Give conflict Status If Account is duplicate")
    public void updateAccountShouldGiveConflictStatusCodeForDuplicateAccount() throws Exception {
		AccountBean accountBean = new AccountBean("gmail_account","Sagar_7299","Sagar@8820k","http://www.as.com","etc");
		accountBean.setAccountId(1);
		accountBean.setGroupBean(groupBean);
		when(accountService.getById(1)).thenReturn(accountBean);
        doThrow(DuplicateAccountException.class).when(accountService).updateAccount(any(), anyInt());
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/AccountApi/updateAccount")
                        .content(asJsonString(accountBean))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }


	
	
	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	
}
