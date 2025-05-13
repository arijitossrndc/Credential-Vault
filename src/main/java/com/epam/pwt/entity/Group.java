package com.epam.pwt.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "groups")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="group_id",nullable=false)
	private Integer groupId;
	
	
	@Column(name="group_name",nullable=false)
	private String groupName;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="group",fetch = FetchType.LAZY)
	private List<Account> accounts = new ArrayList<Account>();
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Override
	public String toString()
	{
		return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                '}';

	}
}
