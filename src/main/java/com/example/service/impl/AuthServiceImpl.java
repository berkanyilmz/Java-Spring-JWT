package com.example.service.impl;

import com.example.jwt.AuthResponse;
import com.example.jwt.JwtService;
import com.example.models.RefreshToken;
import com.example.repository.IRefreshTokenRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.DtoUser;
import com.example.jwt.AuthRequest;
import com.example.models.User;
import com.example.repository.IUserRepository;
import com.example.service.IAuthService;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements IAuthService {

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private IRefreshTokenRepository refreshTokenRepository;

	private RefreshToken createRefreshToken(User user) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setRefreshToken(UUID.randomUUID().toString());
		refreshToken.setExpireDate(new Date((System.currentTimeMillis() + 1000 * 60 * 60 * 48)));
		refreshToken.setUser(user);

		return refreshToken;
	}
	
	@Override
	public DtoUser register(AuthRequest authRequest) {
		DtoUser dtoUser = new DtoUser();
		User user = new User();
		
		user.setUserName(authRequest.getUserName());
		user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
		
		User savedUser = userRepository.save(user);
		// BeanUtils.copyProperties may not map 'username' vs 'userName' correctly
		// so set fields explicitly to ensure DTO contains the proper values
		dtoUser.setUserName(savedUser.getUsername());
		dtoUser.setPassword(savedUser.getPassword());

		return dtoUser;
	}

	@Override
	public AuthResponse authenticate(AuthRequest authRequest) {
		try {
			UsernamePasswordAuthenticationToken authToken =
					new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword());
			authenticationProvider.authenticate(authToken);

			Optional<User> optionalUser = userRepository.findByuserName(authRequest.getUserName());
			String accessToken = jwtService.generateToken(optionalUser.get());
			RefreshToken refreshToken = createRefreshToken(optionalUser.get());
			refreshTokenRepository.save(refreshToken);

			return new AuthResponse(accessToken, refreshToken.getRefreshToken());
		} catch (Exception e) {
			System.out.println("Authentication failed: " + e.getLocalizedMessage());
		}
		return null;
	}


}
