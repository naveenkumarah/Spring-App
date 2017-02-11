package com.naveen.demo.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.naveen.demo.domain.User;
import com.naveen.demo.domain.UserRole;
import com.naveen.demo.repository.UserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
 
	@Autowired
	private UserRepository userRepository;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        User user = userRepository.findByUsername(username);
        
        if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
            throw new BadCredentialsException("Username not found.");
        }
        
        if (!passwordEncoder().matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
        
       // List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRoles());
        
        return new UsernamePasswordAuthenticationToken(user, password,  new ArrayList<>());
        
    }
    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(0);
		for (UserRole userRole : userRoles) {
			authorities.add(new SimpleGrantedAuthority(userRole.getRole().getRoleName()));
		}
		
		return authorities;
	}
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
