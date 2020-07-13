package com.excilys.tchasset.model;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="users")
public class User {
	@Id @Nonnull
	private String username;
	@Nonnull 
	private String password;
	@Nonnull
	private Role role;
	
	public enum Role{ADMIN, USER}
	
	public static class Builder {
		private String username;
		private String password;
		private Role role;
		
		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}

		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}

		public Builder serRole(Role role) {
			this.role = role;
			return this;
		}
		
		public User build() {
			return new User(this);
		}
	}
	
	public User(Builder builder) {
		this.username = builder.username;
		this.password = builder.password;
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
