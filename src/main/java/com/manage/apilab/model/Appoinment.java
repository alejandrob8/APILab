package com.manage.apilab.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "appoinments")
public class Appoinment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "date")
	@JsonFormat(pattern = "dd/MM/yyyy", shape = Shape.STRING)
	private LocalDate date;

	@Column(name = "hour")
	@JsonFormat(pattern = "HH:mm", shape = Shape.STRING)
	private LocalTime hour;

	@ManyToOne
	@JsonProperty(value = "id_test")
	@JoinColumn(name = "id_test")
	private Test idTest;

	@ManyToOne
	@JsonProperty(value = "id_affiliate")
	@JoinColumn(name = "id_affiliate")
	private Affiliate idAffiliatte;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getHour() {
		return hour;
	}

	public void setHour(LocalTime hour) {
		this.hour = hour;
	}

	public Test getIdTest() {
		return idTest;
	}

	public void setIdTest(Test idTest) {
		this.idTest = idTest;
	}

	public Affiliate getIdAffiliatte() {
		return idAffiliatte;
	}

	public void setIdAffiliatte(Affiliate idAffiliatte) {
		this.idAffiliatte = idAffiliatte;
	}

	public Appoinment() {
		super();
	}

	public Appoinment(Integer id, LocalDate date, LocalTime hour, Test idTest, Affiliate idAffiliatte) {
		super();
		this.id = id;
		this.date = date;
		this.hour = hour;
		this.idTest = idTest;
		this.idAffiliatte = idAffiliatte;
	}

}
