package com.naveen.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naveen.demo.domain.UserRole;
import com.naveen.demo.repository.UserRepository;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		com.naveen.demo.domain.User user = userRepository.findByUsername(username);
		if(user==null){
			throw new UsernameNotFoundException("Username/password wrong");
		}
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRoles());
		
		return buildUserForAuthentication(user, authorities);
	}
	
	private User buildUserForAuthentication(com.naveen.demo.domain.User user,
			List<GrantedAuthority> authorities) {
		
		return new User(user.getUsername(), user.getPassword(),
				true, true, true, true, authorities);
	}
	
	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(0);
		for (UserRole userRole : userRoles) {
			authorities.add(new SimpleGrantedAuthority(userRole.getRole().getRoleName()));
		}
		
		return authorities;
	}
}
	