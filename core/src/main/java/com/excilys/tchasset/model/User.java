package com.excilys.tchasset.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
	private boolean enabled=true;
	@Nonnull
	private String role;

	public static class Builder {
		private String username;
		private String password;
		private boolean enabled=true;
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

		public Builder setRole(String role) {
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
		this.enabled = builder.enabled;
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

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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
		return this.enabled;
	}

	@Override
	public String toString() {
		return "User{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", enabled=" + enabled +
				", role='" + role + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return username.equals(user.username) &&
				password.equals(user.password) &&
				(enabled == user.enabled) &&
				role.equals(user.role);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password, enabled, role);
	}
}
