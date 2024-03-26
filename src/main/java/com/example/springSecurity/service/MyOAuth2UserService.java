package com.example.springSecurity.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.springSecurity.entity.MyUserDetails;
import com.example.springSecurity.entity.SecurityUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyOAuth2UserService extends DefaultOAuth2UserService {
	private final SecurityUserService securityService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	// provider(구글, 깃허브 등)로부터 받은 userRequest 데이터에 대해 후처리하는 메소드
	@SuppressWarnings("unused")
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		String uid, email, uname, picture;
		String hashedPwd = bCryptPasswordEncoder.encode("Social Login");
		SecurityUser securityUser = null;
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.info("Attributes(): " + oAuth2User.getAttributes());
		
		String provider = userRequest.getClientRegistration().getRegistrationId();
		
		switch (provider) {
		case "google":
			String providerId = oAuth2User.getAttribute("sub");
			uid = provider + "_" + providerId;
			
			// 가입이 되어있지 않으면 가입 진행
			if (securityUser == null) {
				uname = oAuth2User.getAttribute("name");
				uname = (uname == null) ? "google_user" : uname;
				email = oAuth2User.getAttribute("email");
				picture = oAuth2User.getAttribute("picture");
				securityUser = SecurityUser.builder()
						.uid(uid).pwd(hashedPwd).uname(uname).email(email)
						.picture(picture).provider(provider)
						.build();
				securityService.insertSecurityUser(securityUser);
				securityUser = securityService.getUserByUid(uid);
				log.info("구글 계정을 통해 회원가입이 되었습니다: " + securityUser);
			}
				
			break;
		case "github":
			int id = oAuth2User.getAttribute("id");
			uid = provider + "_" + id;
			
			// 가입이 되어있지 않으면 가입 진행
			if (securityUser == null) {
				uname = oAuth2User.getAttribute("name");
				uname = (uname == null) ? "github_user" : uname;
				email = oAuth2User.getAttribute("email");
				picture = oAuth2User.getAttribute("avatar_url");
				securityUser = SecurityUser.builder()
						.uid(uid).pwd(hashedPwd).uname(uname).email(email)
						.picture(picture).provider(provider)
						.build();
				securityService.insertSecurityUser(securityUser);
				securityUser = securityService.getUserByUid(uid);
				log.info("깃허브 계정을 통해 회원가입이 되었습니다: " + securityUser);
			}
			
			break;
		case "naver":
			break;
		case "kakao":
			break;
		}
		
		return new MyUserDetails(securityUser, oAuth2User.getAttributes());
	}
}
