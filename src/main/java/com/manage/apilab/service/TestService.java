package com.manage.apilab.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.apilab.model.Test;
import com.manage.apilab.repository.TestRepository;
import com.manage.apilab.service.dto.TestDTO;
import com.manage.apilab.service.dto.mapper.TestDTOMap;

@Service
public class TestService {

	@Autowired
	private TestRepository testRepo;

	@Autowired
	private TestDTOMap mapper;

	public List<Test> getlist() throws Exception {
		List<Test> listTests = testRepo.findAll();
		if (listTests.isEmpty()) {
			throw new Exception();
		}
		return listTests;
	}

	public Test getbyid(Integer id) throws Exception {
		Optional<Test> testId = testRepo.findById(id);
		if (testId.isEmpty()) {
			throw new Exception();
		}
		return testRepo.findById(id).get();
	}

	public Test post(TestDTO testDTO) throws Exception {
		Test postTest = mapper.map(testDTO);

		if (testDTO.getName().isBlank()) {
			throw new Exception();
		}
		return testRepo.save(postTest);
	}

	public Test put(Test test) throws Exception {
		Optional<Test> putTest = testRepo.findById(test.getId());
		if (putTest.isPresent()) {
			return testRepo.save(test);
		} else {
			throw new Exception();
		}
	}

	public void delete(Integer id) throws Exception {
		Optional<Test> deleteTest = testRepo.findById(id);
		if (deleteTest.isPresent()) {
			testRepo.deleteById(id);
		} else {
			throw new Exception();
		}
	}

}
