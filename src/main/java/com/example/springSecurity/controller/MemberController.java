package com.example.springSecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springSecurity.entity.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그 사용하기
@Controller
@RequestMapping("/member")
public class MemberController {

	@ResponseBody
	@GetMapping("/detail/{mid}")
	public String detail(@PathVariable int mid) {
		@SuppressWarnings("unused")
		Member member = new Member();
		log.info("detail"); // 로그 띄우기
		
		return "";
	}
	
	@ResponseBody
	@GetMapping("/insert")
	public String insert() {
		Member m1 = new Member();
		m1.setName("James");
		m1.setEmail("James@gmail.com");
		log.info(m1.toString());
		
		// Builder Pattern
		// set을 쓰지 않고 builder를 이용해서 객체를 생성하는 법
		Member m2 = Member.builder()
					.name("maria").email("maria@naver.com")
					.build();
		return m1.toString() + "<br>" + m2.toString();
	}
	
	@ResponseBody
	@GetMapping("/update")
	public String update() {
		Member member = Member.builder()
				.mid(1).name("Brian").email("brian@human.com")
				.build();
		log.info(member.toString());
		
		return member.toString();
	}
}