package com.shixin.chat.modules.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@DynamicInsert 
@DynamicUpdate
@Table(name = "stu_user")
public class MyUser {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String id;
	

	@Column(name = "token")
	private String token;
	
	@NotEmpty
	@Column(name = "pwd")
	private String pwd;

	@Column(name = "imgAvatar",length=18)
	private String imgAvatar;
	
	@NotEmpty
	@Column(name = "name")
	private String name;
	
	
	@Email
	@Column(name = "email")
	private String email;
	

	@Column(name = "gender")
	private Integer gender;
	

	@Column(name = "stature")
	private String stature;
	
	@Column(name = "bloodType")
	private String bloodType;
	
	@Column(name = "telephone")
	private String telephone;
	
	
	@Column(name = "idCard")
	private String idCard;
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name = "birthday")
	private Date birthday;
	
	@Column(name = "age")
	private Integer age;
	
	@Column(name = "maritalStatus")
	private String maritalStatus;
	
	@Column(name = "province")
	private String province;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "districtOrCounty")
	private String districtOrCounty;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "community")
	private String community;
	
	@Column(name = "location")
	private String location;

	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getToken() {
		return token;
	}

	
	
	
	public void setToken(String token) {
		this.token = token;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	
	public String getImgAvatar() {
		return imgAvatar;
	}

	public void setImgAvatar(String imgAvatar) {
		this.imgAvatar = imgAvatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getStature() {
		return stature;
	}

	public void setStature(String stature) {
		this.stature = stature;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrictOrCounty() {
		return districtOrCounty;
	}

	public void setDistrictOrCounty(String districtOrCounty) {
		this.districtOrCounty = districtOrCounty;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "User [imgAvatar=" + imgAvatar + ", name=" + name + ", gender=" + gender + ", stature=" + stature
				+ ", bloodType=" + bloodType + ", telephone=" + telephone + ", idCard=" + idCard + ", birthday="
				+ birthday + ", age=" + age + ", maritalStatus=" + maritalStatus + ", province=" + province + ", city="
				+ city + ", districtOrCounty=" + districtOrCounty + ", street=" + street + ", community=" + community
				+ ", location=" + location + "]";
	}

}
