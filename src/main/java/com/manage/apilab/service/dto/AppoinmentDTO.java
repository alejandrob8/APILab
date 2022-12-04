package com.manage.apilab.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.manage.apilab.model.Affiliate;
import com.manage.apilab.model.Test;

public class AppoinmentDTO {

	@JsonFormat(pattern = "dd/MM/yyyy", shape = Shape.STRING)
	private LocalDate date;

	private String description;

	@JsonFormat(pattern = "HH:mm", shape = Shape.STRING)
	private LocalTime hour;

	@JsonProperty(value = "id_test")
	private Test idTest;

	@JsonProperty(value = "id_affiliate")
	private Affiliate idAffiliate;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Affiliate getIdAffiliate() {
		return idAffiliate;
	}

	public void setIdAffiliate(Affiliate idAffiliate) {
		this.idAffiliate = idAffiliate;
	}

	public AppoinmentDTO(LocalDate date, String description, LocalTime hour, Test idTest, Affiliate idAffiliate) {
		super();
		this.date = date;
		this.description = description;
		this.hour = hour;
		this.idTest = idTest;
		this.idAffiliate = idAffiliate;
	}

	public AppoinmentDTO() {
		super();
	}

}
