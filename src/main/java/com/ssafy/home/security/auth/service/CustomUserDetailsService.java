package com.ssafy.home.security.auth.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.home.repository.UserRepository;
import com.ssafy.home.security.auth.dto.CustomUserDetails;
import com.ssafy.home.security.auth.dto.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserDto> userOptional = userRepository.findByEmail(email);
		return userOptional.map(CustomUserDetails::new).orElse(null);
	}
}
