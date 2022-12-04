package com.manage.apilab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manage.apilab.exceptions.CreatedException;
import com.manage.apilab.exceptions.NoContentException;
import com.manage.apilab.exceptions.NotFoundException;
import com.manage.apilab.exceptions.OKException;
import com.manage.apilab.model.Test;
import com.manage.apilab.service.TestService;
import com.manage.apilab.service.dto.TestDTO;

@RestController
@RequestMapping("/api/controller/tests")
public class TestController {

	@Autowired
	private TestService testService;

	@GetMapping
	public ResponseEntity<?> getlist() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(testService.getlist());
		} catch (Exception e) {
			throw new NoContentException();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getbyid(@PathVariable Integer id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(testService.getbyid(id));
		} catch (Exception e) {
			throw new NotFoundException("Test id " + "[" + id + "]" + " not found");
		}
	}

	@PostMapping
	public void post(@RequestBody TestDTO testDTO) {
		try {
			testService.post(testDTO);
		} catch (Exception e) {
			throw new NotFoundException("Insertion failed");
		}
		throw new CreatedException("Insertion sussccesful");
	}

	@PutMapping
	public void put(@RequestBody Test test) {
		try {
			testService.put(test);
		} catch (Exception e) {
			throw new NotFoundException("Update failed");
		}
		throw new CreatedException("Update successful");
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Integer id) {
		try {
			testService.delete(id);
		} catch (Exception e) {
			throw new NoContentException();
		}
		throw new OKException("Test id " + "[" + id + "]" + " removed");
	}
}
