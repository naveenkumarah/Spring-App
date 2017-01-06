package com.naveen.demo.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.naveen.demo.domain.Role;

@Repository
@Qualifier(value = "roleRepository")
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
