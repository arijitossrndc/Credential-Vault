package com.epam.pwt.bean;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsBean implements UserDetails {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private final UserBean userBean;

    public UserDetailsBean(UserBean userBean) {
        this.userBean = userBean;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("User"));
    }

    @Override
    public String getPassword() {
        return userBean.getPassword();
    }

    @Override
    public String getUsername() {
        return userBean.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
