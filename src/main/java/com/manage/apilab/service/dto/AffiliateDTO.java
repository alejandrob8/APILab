package com.manage.apilab.service.dto;

public class AffiliateDTO {

	private String name;
	private Integer age;
	private String mail;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public AffiliateDTO(String name, Integer age, String mail) {
		super();
		this.name = name;
		this.age = age;
		this.mail = mail;
	}

	public AffiliateDTO() {
		super();
	}

}
