package com.epam.pwt.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id",nullable=false)
	private int id;
	
	@Column(name="username",nullable=false)
	private String username;
	
	@Column(name="email",nullable=false)
	private String email;
	
	@Column(name="password",nullable=false)
	private String password;
	
	@Transient
	private String confirmPassword;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="user")
	private List<Group> groups = new ArrayList<Group>();


	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
}