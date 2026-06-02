package com.example.controller;

import com.example.dto.DtoUser;
import com.example.jwt.AuthRequest;
import com.example.jwt.AuthResponse;
import com.example.jwt.RefreshTokenRequest;
import com.example.models.RefreshToken;

public interface IRestAuthController {

	public DtoUser register(AuthRequest authRequest);

	public AuthResponse authenticate(AuthRequest authRequest);

	public AuthResponse refreshToken(RefreshTokenRequest refreshToken);
}
