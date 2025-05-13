package com.epam.pwt.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.epam.pwt.bean.GroupBean;
import com.epam.pwt.bean.UserBean;
import com.epam.pwt.exceptions.DuplicateUserAccountException;
import com.epam.pwt.exceptions.UserNotFoundException;
import com.epam.pwt.restapi.UserRestController;
import com.epam.pwt.service.GroupServiceImp;
import com.epam.pwt.service.UserServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value=UserRestController.class)
@WithMockUser
public class UserRestControllerTest {
	
	
	@MockBean
	UserServiceImp userService;
	
	@MockBean
	GroupServiceImp groupService;
	
	@Autowired
	MockMvc mockMvc;
	
	@Mock
	GroupBean groupBean;
	UserBean userBean;
	
	@Before
	public void setUp() {
		groupBean = new GroupBean(1,"epamIndia");
		userBean = new UserBean("Sagar_729","Sst@20kss","mm@gm.com");
		userBean.setId(1);
		
		groupBean.setUserBean(userBean);

	}
	
	@Test
	@DisplayName("Get list of All users and return status OK")
	public void returnStatusOkAndReturnListOfUsers() throws Exception {
		
		when(userService.getAllUsers()).thenReturn(List.of(userBean));
		mockMvc.perform(MockMvcRequestBuilders.get("/UserApi/getAllUsers")
				.contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("return user from id if exist and status be OK")
	
	public void returnStatusOkIfUserExist() throws Exception {
		when(userService.getDetailsById(anyInt())).thenReturn(userBean);
		mockMvc.perform(MockMvcRequestBuilders.get("/UserApi/getUser/1")
				.contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
	}
	
	@Test
    @DisplayName("if user not exist with given id then status equal to NOT_FOUND and throw UserNotFoundException.")
    public void getUserByIdShouldGiveNotFoundStatusIfUserNotExist() throws Exception {
        doThrow(UserNotFoundException.class).when(userService).getDetailsById(anyInt());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/UserApi/getUser/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
	
	@Test
    @DisplayName("if user not exist then status be NOT_FOUND and method return exception")
    public void deleteUserShouldGiveNotFoundStatusIfGroupNotExist() throws Exception {
        doThrow(UserNotFoundException.class).when(userService).deleteUserDetails(anyInt());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/UserApi/deleteUser/18")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
	
	@Test
    @DisplayName("saveUser Should Give Bad Request Status If User is invalid")
    public void saveUseShouldGiveValidationMessagesWithConflictCode() throws Exception {
		UserBean userBean = new UserBean("","","");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/UserApi/addUser")
                        .content(asJsonString(userBean))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }
	
	@Test
    @DisplayName("saveUser Should Give Created Status If User is valid")
    public void saveUserShouldGiveCreatedStatus() throws Exception {
		UserBean userBean = new UserBean("Sagar_729","Sst@20kss","mm@gm.com");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/UserApi/addUser")
                        .content(asJsonString(userBean))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
	
	@Test
    @DisplayName("updateUser Should Give conflict Status If user is duplicate")
    public void updateUserShouldGiveConflictStatusCodeForDuplicateUser() throws Exception {

        doThrow(DuplicateUserAccountException.class).when(userService).updateUserDetails(any(), anyInt());
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/UserApi/updateUser")
                        .content(asJsonString(userBean))
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
