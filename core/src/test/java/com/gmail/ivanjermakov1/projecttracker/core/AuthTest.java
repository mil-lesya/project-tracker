package com.gmail.ivanjermakov1.projecttracker.core;

import com.gmail.ivanjermakov1.projecttracker.core.controller.AuthController;
import com.gmail.ivanjermakov1.projecttracker.core.controller.RegisterController;
import com.gmail.ivanjermakov1.projecttracker.core.dto.AuthUserDto;
import com.gmail.ivanjermakov1.projecttracker.core.dto.RegisterUserDto;
import com.gmail.ivanjermakov1.projecttracker.core.dto.UserDto;
import com.gmail.ivanjermakov1.projecttracker.core.exeption.AuthenticationException;
import com.gmail.ivanjermakov1.projecttracker.core.exeption.NoSuchEntityException;
import com.gmail.ivanjermakov1.projecttracker.core.exeption.RegistrationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class AuthTest {
	
	@Autowired
	private RegisterController registerController;
	
	@Autowired
	private AuthController authController;
	
	private RegisterUserDto registerUserDto;
	
	@Before
	public void registerUser() throws RegistrationException, AuthenticationException, NoSuchEntityException {
		registerUserDto = new RegisterUserDto("login", "password");
		
		registerController.register(registerUserDto);
	}
	
	@Test
	public void shouldAuthAndValidateUser() throws RegistrationException, AuthenticationException, NoSuchEntityException {
		String token = authController.authenticate(new AuthUserDto(registerUserDto.login, registerUserDto.password));
		Assert.assertNotNull(token);
		
		UserDto user = authController.validate(token);
		Assert.assertNotNull(user);
		Assert.assertEquals(user.userCredentials.login, registerUserDto.login);
	}
	
}
