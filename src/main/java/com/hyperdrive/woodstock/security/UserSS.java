package com.hyperdrive.woodstock.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hyperdrive.woodstock.entities.enums.UserStatus;
import com.hyperdrive.woodstock.entities.enums.UserType;

public class UserSS implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String password;
	
	private String email;
	
	private UserStatus status;
	
	private Collection<? extends GrantedAuthority> authorities;	
	
	public UserSS(Long id, String password, String email, UserStatus status, UserType role) {
		super();
		this.id = id;
		this.password = password;
		this.email = email;
		this.status = status;
		
		List<UserType> auths = new ArrayList<>();
		auths.add(role);
		
		this.authorities = auths.stream()
				.map(x -> new SimpleGrantedAuthority(x.getDescription()))
				.collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
	public String getUsername() {
		
		return email;
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
		if(status == UserStatus.ATIVADO) {
			return true;
		} else {
			return false;
		}		
	}
	
	public boolean hasRole(UserType admin) {
		return authorities.contains(new SimpleGrantedAuthority(admin.getDescription()));
	}
}
