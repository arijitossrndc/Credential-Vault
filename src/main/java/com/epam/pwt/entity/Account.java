package com.epam.pwt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter
@Setter
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="account_id",nullable=false)
	private Integer accountId;
	
	
	@Column(name="account_name")
	private String accountName;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="url")
	private String url;
	
	
	@Column(name="comments")
	private String comments;
	
	
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;
	
	 public Account(String accountName, String userName, String password, String url,String comment) {
	        this.accountName = accountName;
	        this.userName = userName;
	        this.password = password;
	        this.url = url;
	        this.comments = comment;
	 }
	    
    @Override
    public String toString()
    {
    	return "Account{" +
                "accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", groupid='" + group.getGroupId() + '\'' +
                ", comments=" + comments + '\'' +
                '}';

    }


}
