package com.ssafy.home.security.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.home.common.response.ErrorCode;
import com.ssafy.home.common.response.ErrorResponse;
import com.ssafy.home.common.response.SuccessResponse;
import com.ssafy.home.security.auth.dto.CustomUserDetails;
import com.ssafy.home.security.jwt.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	private final JWTUtil jwtUtil;

	// public LoginFilter() {
	// 	// 로그인 엔드포인트를 "/api/v1/login"으로 설정
	// 	this.setFilterProcessesUrl("/api/v1/login");
	// }

	// 로그인 요청시 실행되는 메소드
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {

		// 로그인 요청에서 username, password 추출
		String username = obtainUsername(request);
		String password = obtainPassword(request);

		log.info("attemptAuthentication - email: {}", username);

		// username 과 password 를 검증하기 위해 token 에 담아서 사용 (스프링 시큐리티에서)
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		return authenticationManager.authenticate(authToken);
	}

	//로그인 성공시 실행하는 메소드 (JWT 발급)
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authentication) {

		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		Long userId = userDetails.getUserId();

		String token = jwtUtil.createJwt(userId);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			String jsonResponse = new ObjectMapper().writeValueAsString(SuccessResponse.of(token));
			response.getWriter().write(jsonResponse);
		} catch (IOException e) {
			log.error("Error writing JSON response", e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

	}

	//로그인 실패시 실행하는 메소드
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) {

		// request.setAttribute("exception", new BadRequestException(ErrorCode.INVALID_CREDENTIALS));
		// throw new BadRequestException(ErrorCode.INVALID_CREDENTIALS);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			String jsonResponse = new ObjectMapper().writeValueAsString(
				ErrorResponse.of(ErrorCode.INVALID_CREDENTIALS));
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			response.getWriter().write(jsonResponse);
		} catch (IOException e) {
			log.error("Error writing JSON response", e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

	}
}
