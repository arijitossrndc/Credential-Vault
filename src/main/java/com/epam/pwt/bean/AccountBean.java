package com.epam.pwt.bean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import com.epam.pwt.Config.RegexConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AccountBean extends RepresentationModel<AccountBean>{
	
	
	
	private Integer accountId;
	
	
	@NotNull(message = "Account Name is Required")
	@Size(min = 5 , max = 20 , message = "Account name should have min 5 chars and max 20 chars")
	@Pattern(regexp = RegexConstants.ACCOUNT_NAME_PATTERN, message = "Account Name should be lower case alphabets"
			+ " and can have underscore(_)")
	private String accountName;
	
	
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
	
	@Pattern(regexp=RegexConstants.URL_PATTERN, message="Url should be in proper format")
	private String url;
	
	private String comments;

	public AccountBean(String accountName, String username, String password,String url,String comments) {
		this.accountName = accountName;
		this.username = username;
		this.url=url;
		this.password = password;
		this.comments=comments;
	}

	@JsonIgnore
	private Integer groupId;
	
	private GroupBean groupBean;
	
	@JsonIgnore
	private LinkedHashMap<Integer,String> groupOptions;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountBean bean = (AccountBean) o;
        return accountId == bean.accountId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
	@Override
	public String toString() {
		return "AccountBean [accountId=" + accountId + ", accountName=" + accountName + ", username=" + username
				+ ", password=" + password + ", url=" + url + ", comments=" + comments + "]";
	}
	    
	    
	

}
