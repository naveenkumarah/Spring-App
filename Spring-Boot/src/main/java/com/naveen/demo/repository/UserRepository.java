package com.naveen.demo.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.naveen.demo.domain.User;

@Repository
@Qualifier(value = "userRepository")
public interface UserRepository extends CrudRepository<User, Long> {

	public User findByUsername(String username);
}
