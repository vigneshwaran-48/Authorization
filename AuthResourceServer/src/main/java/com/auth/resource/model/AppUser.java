package com.auth.resource.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.UuidGenerator;

import com.auth.library.model.CommonUserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "user")
public class AppUser {

	@Id
	@Column(name = "user_id")
	private String id;

	@Column(name = "user_name", nullable = false)
	private String userName;

	@Column(nullable = false, unique = true)
	private String email;

	private int age = -1;

	private LocalDate dob;

	private String mobile;

	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "profile_image")
	private String profileImage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	@Override
	public String toString() {
		return "AppUserTemp [userId=" + id + ", userName=" + userName + ", email=" + email + ", age=" + age
				+ ", dob=" + dob + ", mobile=" + mobile + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", profileImage=" + profileImage + "]";
	}

	//
//	public AppUser(String id, String username, String password, String firstName, String lastName,
//			Set<Authority> authorities, Date dob, String mobile, String email, Boolean accountNonExpired,
//			Boolean accountNonLocked, Boolean credentialsNonExpired, Boolean enabled) {
//		super();
//		this.id = id;
//		this.username = username;
//		this.password = password;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.authorities = authorities;
//		this.dob = dob;
//		this.mobile = mobile;
//		this.email = email;
//		this.accountNonExpired = accountNonExpired;
//		this.accountNonLocked = accountNonLocked;
//		this.credentialsNonExpired = credentialsNonExpired;
//		this.enabled = enabled;
//	}
//	public AppUser() {}
//
	public static AppUser toAppUser(CommonUserDetails commonUserDetails) {
		AppUser appUser = new AppUser();
		appUser.setId(commonUserDetails.getId());
		appUser.setUserName(commonUserDetails.getUserName());
		appUser.setPassword(commonUserDetails.getPassword());
//		appUser.setAuthorities(null);
//		appUser.setAccountNonExpired(true);
//		appUser.setAccountNonLocked(true);
//		appUser.setCredentialsNonExpired(true);
//		appUser.setEnabled(true);
		appUser.setDob(commonUserDetails.getDob());
		appUser.setEmail(commonUserDetails.getEmail());
		appUser.setFirstName(commonUserDetails.getFirstName());
		appUser.setLastName(commonUserDetails.getLastName());
		appUser.setMobile(commonUserDetails.getMobile());
		appUser.setProfileImage(commonUserDetails.getProfileImage());

		return appUser;
	}
	public static CommonUserDetails toCommonUserDetails(AppUser appUser) {
		CommonUserDetails commonUserDetails = new CommonUserDetails();
		commonUserDetails.setId(appUser.getId());
		commonUserDetails.setUserName(appUser.getUserName());
		commonUserDetails.setPassword(appUser.getPassword());
		commonUserDetails.setDob(appUser.getDob());
		commonUserDetails.setFirstName(appUser.getFirstName());
		commonUserDetails.setLastName(appUser.getLastName());
		commonUserDetails.setEmail(appUser.getEmail());
		commonUserDetails.setMobile(appUser.getMobile());
		commonUserDetails.setProfileImage(appUser.getProfileImage());

		return commonUserDetails;
	}
}
