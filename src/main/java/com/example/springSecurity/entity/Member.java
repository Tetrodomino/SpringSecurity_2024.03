package com.example.springSecurity.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter				// Getter 기능 추가
@Setter				// Setter 기능 추가
@ToString			// ToString 기능 추가
@AllArgsConstructor // 전체 생성자 (설정 시 기본 생성자는 만들어야 활성화)
@NoArgsConstructor	// 기본 생성자
@Builder			// builder() 메소드 사용
public class Member {

	private int mid;
	private String name;
	private LocalDate regDate;
	private String email;
}
