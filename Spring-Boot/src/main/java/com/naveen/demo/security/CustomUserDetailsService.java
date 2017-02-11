package com.naveen.demo.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naveen.demo.domain.User;
import com.naveen.demo.domain.UserRole;
import com.naveen.demo.repository.UserRepository;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;

	
	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(0);
		for (UserRole userRole : userRoles) {
			authorities.add(new SimpleGrantedAuthority(userRole.getRole().getRoleName()));
		}
		
		return authorities;
	}
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new BadCredentialsException("Username not found.");
		}
		
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRoles());
		
		return new CustomUserDetails(user,authorities);
	}

	private final static class CustomUserDetails extends User implements UserDetails {
		private List<GrantedAuthority> authorities;
		
		private CustomUserDetails(User user,List<GrantedAuthority> authorities) {
			super(user);
			this.authorities=authorities;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return this.authorities;
		}
		
		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		private static final long serialVersionUID = 5639683223516504866L;
	}
}
	