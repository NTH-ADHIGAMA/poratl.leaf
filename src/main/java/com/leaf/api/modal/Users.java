package com.leaf.api.modal;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "users")
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;
	@Column(name = "phone")
	private String phone;
	@Column(name = "user_type")
	private String type;
	@Column(name = "role")
	private String role;
	@Column(name = "status")
	private String status;

	@Column(name = "record_Status")
	private boolean recordStatus;
	@Column(name = "reset_token")
	private String resetToken;
	@Column(name = "first_login")
	private boolean firstLogin;
	@Column(name = "first_login_date")
	private LocalDateTime firstLoginDate;
	@Column(name = "last_login_date")
	private LocalDateTime lastLoginDate;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "last_modified_date")
	private LocalDateTime lastModifiedDate;

	@Transient
	private boolean userNotFound;
	@Transient
	private boolean inCorrectPassword;
	@Transient
	private boolean unAuthorized;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	public LocalDateTime getFirstLoginDate() {
		return firstLoginDate;
	}

	public void setFirstLoginDate(LocalDateTime firstLoginDate) {
		this.firstLoginDate = firstLoginDate;
	}

	public LocalDateTime getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(LocalDateTime lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}


	public boolean isUserNotFound() {
		return userNotFound;
	}

	public void setUserNotFound(boolean userNotFound) {
		this.userNotFound = userNotFound;
	}

	public boolean isInCorrectPassword() {
		return inCorrectPassword;
	}

	public void setInCorrectPassword(boolean inCorrectPassword) {
		this.inCorrectPassword = inCorrectPassword;
	}

	public boolean isUnAuthorized() {
		return unAuthorized;
	}

	public void setUnAuthorized(boolean unAuthorized) {
		this.unAuthorized = unAuthorized;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(boolean recordStatus) {
		this.recordStatus = recordStatus;
	}



}
