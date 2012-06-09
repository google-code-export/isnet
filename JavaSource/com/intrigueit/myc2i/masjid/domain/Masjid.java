package com.intrigueit.myc2i.masjid.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "masjid")
public class Masjid implements Serializable {

	@Id
	@Column(name = "masjid_id")
	@GeneratedValue(strategy=GenerationType.AUTO) 
	private Long masjidId;
	
	@Column(name = "masjid_name", length = 255)
	private String masjidName;
	
	@Column(name = "masjid_code", length = 255)
	private String masjidCode;
	
	@Column(name = "street_address", length = 255)
	private String streetAddress;
	
	@Column(name = "city", length = 100)
	private String city;
	
	@Column(name = "state", length = 100)
	private String state;
	
	@Column(name = "zip", length = 10)
	private String zipCode;
	
	@Column(name = "phone", length = 20)
	private String phone;
	
	@Column(name = "email", length = 255)
	private String email;
	
	@Column(name = "web_address", length = 255)
	private String webAddress;
	
	@Column(name = "imam_name", length = 255)
	private String imaamName;
	
	@Column(name = "imam_mobile", length = 20)
	private String imamMobile;
	
	@Column(name = "imam_email", length = 255)
	private String imamEmail;
	
	@Column(name = "chairman_name", length = 255)
	private String chairmanName;
	
	@Column(name = "chairman_mobile", length = 255)
	private String chairmanMobile;
	
	@Column(name = "chairman_email", length = 255)
	private String chairmanEmail;

	public Long getMasjidId() {
		return masjidId;
	}

	public void setMasjidId(Long masjidId) {
		this.masjidId = masjidId;
	}

	public String getMasjidName() {
		return masjidName;
	}

	public void setMasjidName(String masjidName) {
		this.masjidName = masjidName;
	}

	public String getMasjidCode() {
		return masjidCode;
	}

	public void setMasjidCode(String masjidCode) {
		this.masjidCode = masjidCode;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public String getImaamName() {
		return imaamName;
	}

	public void setImaamName(String imaamName) {
		this.imaamName = imaamName;
	}

	public String getImamMobile() {
		return imamMobile;
	}

	public void setImamMobile(String imamMobile) {
		this.imamMobile = imamMobile;
	}

	public String getImamEmail() {
		return imamEmail;
	}

	public void setImamEmail(String imamEmail) {
		this.imamEmail = imamEmail;
	}

	public String getChairmanName() {
		return chairmanName;
	}

	public void setChairmanName(String chairmanName) {
		this.chairmanName = chairmanName;
	}

	public String getChairmanMobile() {
		return chairmanMobile;
	}

	public void setChairmanMobile(String chairmanMobile) {
		this.chairmanMobile = chairmanMobile;
	}

	public String getChairmanEmail() {
		return chairmanEmail;
	}

	public void setChairmanEmail(String chairmanEmail) {
		this.chairmanEmail = chairmanEmail;
	}
		
		
		
	 
}
