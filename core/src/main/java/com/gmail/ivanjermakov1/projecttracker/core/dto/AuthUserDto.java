package com.gmail.ivanjermakov1.projecttracker.core.dto;

import javax.validation.constraints.NotNull;

public class AuthUserDto {
	
	@NotNull
	public String login;
	
	@NotNull
	public String password;
	
	public AuthUserDto() {
	}
	
	public AuthUserDto(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
}
