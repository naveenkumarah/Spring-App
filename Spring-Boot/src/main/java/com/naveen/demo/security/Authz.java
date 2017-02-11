package com.naveen.demo.security;

import org.springframework.stereotype.Component;

import com.naveen.demo.domain.User;

@Component
public class Authz {

	public boolean check(Long userId, User user) {
		return userId.equals(user.getUserId());
	}

	public boolean check(User user) {
		return true;
	}
}
