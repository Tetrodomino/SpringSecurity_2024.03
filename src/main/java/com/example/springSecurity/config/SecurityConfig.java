package com.example.springSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(auth -> auth.disable()) // 괄호 안에 람다 함수 사용
			.headers(x -> x.frameOptions(y -> y.disable())); // CK Editor로 이미지 업로드 할 때 사용
		
		http.build();
		
		return null;
	}
}
