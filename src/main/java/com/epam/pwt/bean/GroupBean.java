package com.epam.pwt.bean;

import java.util.ArrayList;
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
public class GroupBean extends RepresentationModel<UserBean>{


	private Integer groupId;
	
	@NotNull(message = "Group Name is Required")
	@Size(min = 5 , max = 20 , message = "Group name Should have min 5 chars and max 20 chars")
	@Pattern(regexp = RegexConstants.GROUP_NAME_PATTERN, message = "Group Name should contain only Alphabets")
	private String groupName;
	
	@JsonIgnore
	private List<AccountBean> accountBeans = new ArrayList<AccountBean>();

	private UserBean userBean;
	

	public GroupBean(Integer groupId, String groupName) {
		this.groupId = groupId;
		this.groupName = groupName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupBean groupBean = (GroupBean) o;
        return groupId == groupBean.groupId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId);
    }
    
    @Override
	public String toString() {
		return "GroupBean [groupId=" + groupId + ", groupName=" + groupName + "]";
	}
}
