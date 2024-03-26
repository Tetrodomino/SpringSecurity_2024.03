package com.example.springSecurity.service;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springSecurity.dao.SecurityUserDao;
import com.example.springSecurity.entity.SecurityUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityUserServiceImpl implements SecurityUserService {
	
	// SecurityDao를 선언하는 방법 1 (권장)
//	private SecurityUserDao securityDao;
//	@Autowired 
//	public SecurityUserServiceImpl(SecurityUserDao securityDao) {
//		this.securityDao = securityDao;
//	}
	
	// SecurityDao를 선언하는 방법 2 (RequiredArgsConstructor 이용)
	private final SecurityUserDao securityDao;
	
	@Override
	public SecurityUser getUserByUid(String uid) {
		return securityDao.getUserBuUid(uid);
	}

	@Override
	public List<SecurityUser> getSecurityUserList(int page) {
		int count = COUNT_PER_PAGE;
		int offset = (page - 1) * COUNT_PER_PAGE;
		return securityDao.getSecurityUserList(count, offset);
	}

	@Override
	public int getSecurityUserCount() {
		return securityDao.getSecurityUserCount();
	}

	@Override
	public void insertSecurityUser(SecurityUser securityUser) {
		securityDao.insertSecurityUser(securityUser);
	}

	@Override
	public void updateSecurityUser(SecurityUser securityUser) {
		securityDao.updateSecurityUser(securityUser);
	}

	@Override
	public void deleteSecurityUser(String uid) {
		securityDao.deleteSecurityUser(uid);
	}

}
