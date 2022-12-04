package com.manage.apilab.service.dto.mapper;

import org.springframework.stereotype.Component;
import com.manage.apilab.model.Test;
import com.manage.apilab.service.dto.TestDTO;

@Component
public class TestDTOMap implements Mapper<TestDTO, Test> {

	@Override
	public Test map(TestDTO input) {
		Test test = new Test();
		test.setName(input.getName());
		test.setDescription(input.getDescription());
		return test;
	}

}
