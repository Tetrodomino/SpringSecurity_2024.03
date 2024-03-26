package com.example.springSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.springSecurity.service.MyOAuth2UserService;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired private MyOAuth2UserService myOAuth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(auth -> auth.disable()) // 괄호 안에 람다 함수 사용
			.headers(x -> x.frameOptions(y -> y.disable())) // CK Editor로 이미지 업로드 할 때 사용
			.authorizeHttpRequests(auth -> auth.dispatcherTypeMatchers(DispatcherType.FORWARD)
					.permitAll()
					.requestMatchers("/user/register"
							, "/img/**", "/css/**", "/js/**", "/error/**")
					.permitAll()
					.requestMatchers("/admin/**")
					.hasAuthority("ADMIN") // ADMIN 권한을 가진 유저만 접속 가능
					.anyRequest()
					.authenticated()
			)
			.formLogin(auth -> auth.loginPage("/user/login") // 로그인 폼
					.loginProcessingUrl("/user/login") // springSecurity가 낚아채서 만드는 것, UserDetailService에서 처리해야 함
					.usernameParameter("uid")
					.passwordParameter("pwd") // password parameter를 pwd로 사용
					.defaultSuccessUrl("/user/loginSuccess", true) // 로그인 후 할 것들(세션 세팅, 오늘의 메시지 등)을 실행
					.permitAll()
			)
			.oauth2Login(auth -> auth
					.loginPage("/user/login")
					/* 소셜 로그인이 완료된 후의 후처리
					 * 1. 코드받기(인증)
					 * 2. 액세스 토큰(권한)
					 * 3. 사용자 프로필 정보 가져오기
					 * 4. 3에서 받은 정보를 토대로 DB에 없으면 새로 DB에 추가
					 * 5. 프로바이더가 제공하는 정보 + 추가정보 수집 (주소 등)
					 */
					.userInfoEndpoint(user -> user.userService(myOAuth2UserService))
					.defaultSuccessUrl("/user/loginSuccess", true)
			)
			.logout(y -> y
					.logoutUrl("/user/logout")
					.invalidateHttpSession(true) // 로그아웃 시 세션 초기화
					.deleteCookies("JSESSIONID") // 로그아웃 시 쿠키 삭제
					.logoutSuccessUrl("/user/login")
			)
			;
		
		return http.build();
	}
}
