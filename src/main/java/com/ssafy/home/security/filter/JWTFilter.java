package com.ssafy.home.security.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.home.common.exception.exceptions.BadRequestException;
import com.ssafy.home.common.response.ErrorCode;
import com.ssafy.home.dto.user.UserDto;
import com.ssafy.home.repository.UserRepository;
import com.ssafy.home.security.auth.dto.CustomUserDetails;
import com.ssafy.home.security.jwt.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

	private final JWTUtil jwtUtil;
	private final UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			// 헤더에서 Authorization 에 담긴 토큰을 꺼냄
			String token = extractToken(request);

			// 토큰이 없을 경우
			if (token == null) {
				throw new BadRequestException(ErrorCode.NO_TOKEN);
			}

			// 토큰 검증
			validateToken(token);

			// 토큰 검증 완료 후 토큰에서 userId 추출
			Long userId = jwtUtil.getUserId(token);

			// 추출한 userId로 사용자 조회
			Optional<UserDto> byUserId = userRepository.findByUserId(userId);
			if (byUserId.isEmpty()) {
				throw new BadRequestException(ErrorCode.USER_NOT_FOUND);
			}

			// 사용자 정보를 SecurityContext에 저장
			setAuthentication(byUserId.get());

		} catch (BadRequestException ex) {
			request.setAttribute("exception", ex);
		}
		filterChain.doFilter(request, response);
	}

	private String extractToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		return null;
	}

	private void validateToken(String token) {
		try {
			jwtUtil.isExpired(token);
		} catch (ExpiredJwtException e) {
			throw new BadRequestException(ErrorCode.EXPIRED_TOKEN);
		} catch (JwtException e) {
			throw new BadRequestException(ErrorCode.INVALID_TOKEN);
		}
	}

	private static void setAuthentication(UserDto dto) {
		CustomUserDetails userDetails = new CustomUserDetails(dto);
		Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
			userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

}
