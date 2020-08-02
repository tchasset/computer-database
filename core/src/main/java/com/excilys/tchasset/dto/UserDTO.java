package com.excilys.tchasset.dto;

public class UserDTO {
	private String username;
	private String password;
	private String role;

	public static class Builder {
		private String username;
		private String password;
		private String role;

		public Builder setUsername(String username) {
			this.username = username;
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

		public UserDTO build() {
			return new UserDTO(this);
		}
	}

	public UserDTO(Builder builder) {
		this.setUsername(builder.username);
		this.setPassword(builder.password);
		this.setRole(builder.role);
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
}
