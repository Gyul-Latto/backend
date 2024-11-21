package com.ssafy.home.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {
	private final SecretKey secretKey;
	private final long expiredMs;

	// 생성자로 토큰 생성
	public JWTUtil(@Value("${jwt.secret}") String secret,
		@Value("${jwt.accessToken.expiration}") long expiredMs) {
		this.expiredMs = expiredMs;
		secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
	}

	// 토큰에서 userId 추출
	public Long getUserId(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("userId", Long.class);
	}

	// 토큰 만료 확인
	public void isExpired(String token) {
		Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getExpiration();
	}

	// 토큰 생성
	public String createJwt(long userId) {
		return Jwts.builder()
			.claim("userId", userId)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + expiredMs))
			.signWith(secretKey)
			.compact();
	}
}
