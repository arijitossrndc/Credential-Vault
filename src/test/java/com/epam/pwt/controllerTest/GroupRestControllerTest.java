package com.epam.pwt.controllerTest;

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
import com.epam.pwt.exceptions.DuplicateGroupException;
import com.epam.pwt.exceptions.GroupNotFoundException;
import com.epam.pwt.restapi.GroupRestController;
import com.epam.pwt.service.AccountServiceImp;
import com.epam.pwt.service.GroupServiceImp;
import com.epam.pwt.service.UserServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value=GroupRestController.class)
@WithMockUser
public class GroupRestControllerTest {
	
	@MockBean
	GroupServiceImp groupService;
	
	@MockBean
	UserServiceImp userService;
	
	@MockBean
	AccountServiceImp accountService;
	
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
	@DisplayName("Get all groups for a user if exist")
	
	public void returnListOfGroupsForUserIfExist() throws Exception {
		when(userService.getDetailsById(anyInt())).thenReturn(userBean);
		when(groupService.getAllGroupDetailsByUser(userBean)).thenReturn(List.of(groupBean));
		mockMvc.perform(MockMvcRequestBuilders.get("/GroupApi/1/getAllGroups")
				.contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
		
	}
	
	@Test
	@DisplayName("return group from id if exist and status be OK")
	
	public void returnStatusOkIfGroupExist() throws Exception {
		when(groupService.getById(anyInt())).thenReturn(groupBean);
		mockMvc.perform(MockMvcRequestBuilders.get("/GroupApi/getGroup/1")
				.contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
	}
	
	@Test
    @DisplayName("if group not exist with given id then status equal to NOT_FOUND and throw GroupNotFoundException.")
    public void getGroupByIdShouldGiveNotFoundStatusIfGroupNotExist() throws Exception {
        doThrow(GroupNotFoundException.class).when(groupService).getById(anyInt());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/GroupApi/getGroup/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
	
	@Test
    @DisplayName("if account not exist then status be NOT_FOUND and method return exception")
    public void deleteGroupShouldGiveNotFoundStatusIfGroupNotExist() throws Exception {
        doThrow(GroupNotFoundException.class).when(groupService).deleteGroup(anyInt());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/GroupApi/deleteGroup/18")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
	
	@Test
    @DisplayName("saveGroup Should Give Bad Request Status If Group is invalid")
    public void saveAccountShouldGiveValidationMessagesWithConflictCode() throws Exception {
		GroupBean groupBean = new GroupBean(1,"");
		groupBean.setUserBean(userBean);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/GroupApi/addGroup")
                        .content(asJsonString(groupBean))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }
	
	@Test
    @DisplayName("saveGroup Should Give Created Status If Group is valid")
    public void saveAccountShouldGiveCreatedStatus() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/GroupApi/addGroup")
                        .content(asJsonString(groupBean))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
	
	@Test
    @DisplayName("updateGroup Should Give conflict Status If group is duplicate")
    public void updateGroupShouldGiveConflictStatusCodeForDuplicateGroup() throws Exception {

		when(groupService.getById(1)).thenReturn(groupBean);
        doThrow(DuplicateGroupException.class).when(groupService).updateGroupDetails(groupBean);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/GroupApi/updateGroup")
                        .content(asJsonString(groupBean))
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
