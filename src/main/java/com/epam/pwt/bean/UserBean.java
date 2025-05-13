package com.epam.pwt.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import org.springframework.hateoas.RepresentationModel;

import com.epam.pwt.Config.RegexConstants;
import com.epam.pwt.entity.Account;
import com.epam.pwt.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class UserBean extends RepresentationModel<UserBean>{
	
	private int id;
	
	@NotNull(message = "Username is Required")
	@Size(min = 5 , max = 20 , message = "Username should have min 5 chars and max 20 chars")
	@Pattern(regexp = RegexConstants.USERNAME_PATTERN, message = " Username should contain alphabets,numbers"
			+ " and can have specific(_.-) symbols only")
	private String username;
	
	
	@NotNull(message = "Password is Required")
	@Size(min = 5 , max = 20 , message = "Password should have min 8 chars and max 20 chars")
	@Pattern(regexp = RegexConstants.PASSWORD_PATTERN, message = "Password should contain atleast One UpperCase, One lowercase,"
			+ "One digit and one specific symbol(_.-$#@)")
	private String password;
	
	@JsonIgnore
	private String confirmPassword;
	
	@NotNull(message = "Email is Required")
	@Email(message="Email should be in proper format")
	private String email;
	
	@JsonIgnore
	private List<GroupBean> groupBeans = new ArrayList<GroupBean>();

	public UserBean(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBean userBean = (UserBean) o;
        return id == userBean.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
	public String toString() {
		return "UserBean [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + "]";
	}


}
