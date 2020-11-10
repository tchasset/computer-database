package com.excilys.tchasset.dto;

import java.util.Objects;

public class UserDTO {
	private String username;
	private String password;
	private String role;
	private boolean enabled=true;

	public static class Builder {
		private String username;
		private String password;
		private String role;
		private boolean enabled=true;

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

		public Builder setEnabled(boolean enabled) {
			this.enabled = enabled;
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
		this.setEnabled(builder.enabled);
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDTO userDTO = (UserDTO) o;
		return enabled == userDTO.enabled &&
				username.equals(userDTO.username) &&
				password.equals(userDTO.password) &&
				role.equals(userDTO.role);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password, role, enabled);
	}
}
