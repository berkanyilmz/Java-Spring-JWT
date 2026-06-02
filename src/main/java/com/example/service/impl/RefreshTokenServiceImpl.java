package com.example.service.impl;

import com.example.jwt.AuthResponse;
import com.example.jwt.JwtService;
import com.example.jwt.RefreshTokenRequest;
import com.example.models.RefreshToken;
import com.example.models.User;
import com.example.repository.IRefreshTokenRepository;
import com.example.service.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements IRefreshTokenService {

    @Autowired
    private IRefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtService jwtService;

    public boolean isTokenExpired(Date expireDate) {
        return new Date().before(expireDate);
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(new Date((System.currentTimeMillis() + 1000 * 60 * 60 * 48)));
        refreshToken.setUser(user);

        return refreshToken;
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshToken) {
        Optional<RefreshToken> optional = refreshTokenRepository.findByRefreshToken(refreshToken.getRefreshToken());
        if (optional.isEmpty()) {
            System.out.println("Refresh token not found: " + refreshToken.getRefreshToken());
        }
        RefreshToken refreshTokenOptional = optional.get();

        if (!isTokenExpired(refreshTokenOptional.getExpireDate())) {
            System.out.println("Refresh token is expired: " + refreshToken.getRefreshToken());
        }

        String accessToken = jwtService.generateToken(refreshTokenOptional.getUser());
        RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(refreshTokenOptional.getUser()));

        return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
    }
}
