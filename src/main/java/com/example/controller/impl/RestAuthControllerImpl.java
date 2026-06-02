package com.example.controller.impl;

import com.example.jwt.AuthResponse;
import com.example.jwt.RefreshTokenRequest;
import com.example.service.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.controller.IRestAuthController;
import com.example.dto.DtoUser;
import com.example.jwt.AuthRequest;
import com.example.service.IAuthService;

import jakarta.validation.Valid;

@RestController
public class RestAuthControllerImpl implements IRestAuthController {
	
	@Autowired
	private IAuthService authService;

	@Autowired
	private IRefreshTokenService refreshTokenService;
	
	@PostMapping("/register")
	@Override
	public DtoUser register(@Valid @RequestBody AuthRequest authRequest) {
		return authService.register(authRequest);
	}

	@PostMapping("/authenticate")
	@Override
	public AuthResponse authenticate(@Valid @RequestBody AuthRequest authRequest) {
		return authService.authenticate(authRequest);
	}

	@PostMapping("/refreshToken")
	@Override
	public AuthResponse refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
		return refreshTokenService.refreshToken(refreshToken);
	}

}
