package com.naveen.demo.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.naveen.demo.domain.UserRole;

@Repository
@Qualifier(value = "userRoleRepository")
public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {

}
