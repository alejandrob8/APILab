package com.manage.apilab.service.dto.mapper;

public interface Mapper <In, Out>{
	public Out map(In input);
}
