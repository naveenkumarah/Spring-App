package com.naveen.demo.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tbl_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long userId;
	
	@Column(name = "username", unique = true, length = 64)
	private String username;

	@Column(name = "password", length = 256)
	private String password;
	
	@Column(name = "first_name", length = 128)
	private String firstName;

	@Column(name = "last_name", length = 128)
	private String lastName;
	
	
	@OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	private Set<UserRole> userRoles;

	public User(){
		
	}
	
	public User(User user) {
		super();
		this.userId = user.userId;
		this.username = user.username;
		this.password = user.password;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * @return the userRoles
	 */
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	/**
	 * @param userRoles the userRoles to set
	 */
	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username
				+ ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName+"]";
	}
	
}
