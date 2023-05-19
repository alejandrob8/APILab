package com.manage.apilab.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manage.apilab.exceptions.NotFoundException;
import com.manage.apilab.repository.TestRepository;
import com.manage.apilab.service.TestService;
import com.manage.apilab.service.dto.TestDTO;
import com.manage.apilab.service.dto.mapper.TestDTOMap;

@WebMvcTest(TestController.class)
class TestControllerTest {

	@MockBean
	private TestService testService;

	@MockBean
	private TestRepository testRepo;
	
	@MockBean
	private TestDTOMap mapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void data() {
		System.out.println("********* TEST *********");
	}

	@Test
	void getlistTest() throws Exception {
		List<com.manage.apilab.model.Test> listTest = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			listTest.add(new com.manage.apilab.model.Test(i, "Test " + i, "Prueba test " + i));
		}

		when(testService.getlist()).thenReturn(listTest);

		mockMvc.perform(get("/api/controller/tests").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print()).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[1].name", is("Test 2")))
				.andExpect(jsonPath("$[2].description", is("Prueba test 3")))
				.andExpect(jsonPath("$.size()", is(listTest.size())));
	}

	@Test
	void getbyidTest() throws Exception {
		com.manage.apilab.model.Test test = new com.manage.apilab.model.Test();

		test.setId(1);
		test.setName("Test 1");
		test.setDescription("Prueba test 1");

		Integer id = test.getId();

		when(testService.getbyid(id)).thenReturn(test);

		mockMvc.perform(get("/api/controller/tests/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("Test 1")))
				.andExpect(jsonPath("$.description", is("Prueba test 1")));
	}

	@Test
	void getbyid_nonexistentTest() throws Exception {

		com.manage.apilab.model.Test test = new com.manage.apilab.model.Test();

		test.setId(1);
		test.setName("Test 1");
		test.setDescription("Prueba test 1");

		Integer id = test.getId();

		when(testService.getbyid(id)).thenThrow(new NotFoundException(null));

		mockMvc.perform(get("/api/controller/tests/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	void postTest() throws Exception {

		TestDTO testDTO = new TestDTO();
		testDTO.setName("Test 1");
		testDTO.setDescription("Prueba test 1");

		com.manage.apilab.model.Test postTest = mapper.map(testDTO);

		when(testService.post(testDTO)).thenReturn(postTest);

		mockMvc.perform(post("/api/controller/tests").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(testDTO)))
				.andExpect(status().isCreated()).andDo(print())
				.andExpect(jsonPath("$.statusCode", is(201)))
				.andExpect(jsonPath("$.message", is("Insertion successful")));
	}

	@Test
	void PutTest() throws Exception {

		com.manage.apilab.model.Test putTest = new com.manage.apilab.model.Test();

		putTest.setId(1);
		putTest.setName("Test 1");
		putTest.setDescription("Prueba test 1");

		com.manage.apilab.model.Test test = new com.manage.apilab.model.Test();

		test.setId(1);
		test.setName("Test 1");
		test.setDescription("Prueba test 1");

		Integer id = test.getId();

		when(testRepo.findById(id)).thenReturn(Optional.ofNullable(putTest));
		when(testService.put(test)).thenReturn(test);

		mockMvc.perform(put("/api/controller/tests/", id).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(test)))
				.andExpect(status().isCreated()).andDo(print())
				.andExpect(jsonPath("$.statusCode", is(201)))
				.andExpect(jsonPath("$.message", is("Update successful")));
	}

	@Test
	void deleteTest() throws Exception {

		com.manage.apilab.model.Test test = new com.manage.apilab.model.Test();

		test.setId(1);
		test.setName("Test 1");
		test.setDescription("Prueba test 1");

		Integer id = test.getId();

		when(testRepo.findById(id)).thenReturn(Optional.of(test));

		mockMvc.perform(delete("/api/controller/tests/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.statusCode", is(200)))
				.andExpect(jsonPath("$.message", is("Test id [" + id + "] removed")));
	}

}
