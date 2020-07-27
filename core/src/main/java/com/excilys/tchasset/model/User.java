package com.excilys.tchasset.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name="users")
public class User implements UserDetails{
	private static final long serialVersionUID = -2968690769265534724L;
	
	@Id @Nonnull
	private String username;
	@Nonnull 
	private String password;
	@Nonnull
	private Boolean enabled;
	@Nonnull
	private String role;
	
	public static class Builder {
		private String username;
		private String password;
		private Boolean enabled;
		private String role;
		
		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}
		
		public Builder setEnabled(Boolean bool) {
			this.enabled = bool;
			return this;
		}

		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}

		public Builder serRole(String role) {
			this.role = role;
			return this;
		}
		
		public User build() {
			return new User(this);
		}
	}
	
	public User() {}
	
	public User(Builder builder) {
		this.username = builder.username;
		this.password = builder.password;
		this.enabled = builder.enabled == null ? false : builder.enabled;
		this.role = builder.role;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + this.role);
		grantList.add(authority);
		
		return grantList;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
}
