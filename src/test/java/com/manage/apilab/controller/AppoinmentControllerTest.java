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

import java.time.LocalDate;
import java.time.LocalTime;
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
import com.manage.apilab.model.Affiliate;
import com.manage.apilab.model.Appoinment;
import com.manage.apilab.repository.AppoinmentRepository;
import com.manage.apilab.service.AppoinmentService;
import com.manage.apilab.service.dto.AppoinmentDTO;
import com.manage.apilab.service.dto.mapper.AppoinmentDTOMap;

@WebMvcTest(AppoinmentController.class)
class AppoinmentControllerTest {

	@MockBean
	private AppoinmentService appoinmentService;
	
	@MockBean
	private AppoinmentRepository appoinmentRepo;
	
	@MockBean
	private AppoinmentDTOMap mapper;

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
		Affiliate affiliate = new Affiliate();
		
		affiliate.setId(1);
		affiliate.setName("Affiliate 1");
		affiliate.setAge(1);
		affiliate.setMail("affiliate1@prueba.com");
		
		com.manage.apilab.model.Test test = new com.manage.apilab.model.Test();

		test.setId(1);
		test.setName("Test 1");
		test.setDescription("Prueba test 1");
		
		List<Appoinment> listAppoinments = new ArrayList<>();
		
		for (int i = 1; i <= 2; i++) {
			listAppoinments.add(new Appoinment(i, LocalDate.parse("2001-01-01"), LocalTime.parse("08:18"), test, affiliate));
			
		}

		when(appoinmentService.getlist()).thenReturn(listAppoinments);

		mockMvc.perform(get("/api/controller/appoinments").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.size()", is(listAppoinments.size())));
	}
	
	@Test
	void getbyidTest() throws Exception {
		Affiliate affiliate = new Affiliate();
		
		affiliate.setId(1);
		affiliate.setName("Affiliate 1");
		affiliate.setAge(1);
		affiliate.setMail("affiliate1@prueba.com");
		
		com.manage.apilab.model.Test test = new com.manage.apilab.model.Test();

		test.setId(1);
		test.setName("Test 1");
		test.setDescription("Prueba test 1");
		
		Appoinment appoinment = new Appoinment(1, LocalDate.parse("2001-01-01"), LocalTime.parse("08:18"), test, affiliate);

		Integer id = affiliate.getId();

		when(appoinmentService.getbyid(id)).thenReturn(appoinment);

		mockMvc.perform(get("/api/controller/appoinments/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.id_test.id", is(1)))
				.andExpect(jsonPath("$.id_test.name", is("Test 1")))
				.andExpect(jsonPath("$.id_affiliate.id", is(1)))
				.andExpect(jsonPath("$.id_affiliate.mail", is("affiliate1@prueba.com")));
	}
	
	@Test
	void getbyidNonexistentTest() throws Exception {

		Integer id = 2;

		when(appoinmentService.getbyid(id)).thenThrow(new NotFoundException(null));

		mockMvc.perform(get("/api/controller/appoinments/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andDo(print())
				.andExpect(jsonPath("$.statusCode", is(404)))
				.andExpect(jsonPath("$.message", is("Appoinment id ["+id+"] not found")));
	}
	
	@Test
	void postTest() throws Exception {

		Affiliate affiliate = new Affiliate();
		
		affiliate.setId(1);
		affiliate.setName("Affiliate 1");
		affiliate.setAge(1);
		affiliate.setMail("affiliate1@prueba.com");
		
		com.manage.apilab.model.Test test = new com.manage.apilab.model.Test();

		test.setId(1);
		test.setName("Test 1");
		test.setDescription("Prueba test 1");
		
		AppoinmentDTO appoinmentDTO = new AppoinmentDTO(LocalDate.parse("2001-01-01"), LocalTime.parse("08:18"), test, affiliate);
		
		Appoinment postAppoinment = mapper.map(appoinmentDTO);

		when(appoinmentService.post(appoinmentDTO)).thenReturn(postAppoinment);

		mockMvc.perform(post("/api/controller/appoinments").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(appoinmentDTO)))
				.andExpect(status().isCreated())
				.andDo(print())
				.andExpect(jsonPath("$.statusCode", is(201)))
				.andExpect(jsonPath("$.message", is("Insertion successful")));

	}
	
	@Test
	void putTest() throws Exception {
		Affiliate affiliate = new Affiliate();
		
		affiliate.setId(1);
		affiliate.setName("Affiliate 1");
		affiliate.setAge(1);
		affiliate.setMail("affiliate1@prueba.com");
		
		com.manage.apilab.model.Test test = new com.manage.apilab.model.Test();

		test.setId(1);
		test.setName("Test 1");
		test.setDescription("Prueba test 1");
		
		Appoinment appoinment = new Appoinment(1, LocalDate.parse("2001-01-01"), LocalTime.parse("08:18"), test, affiliate);

		Integer id = appoinment.getId();

		when(appoinmentRepo.findById(id)).thenReturn(Optional.of(appoinment));

		mockMvc.perform(put("/api/controller/appoinments").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(appoinment)))
				.andExpect(status().isCreated()).andDo(print())
				.andExpect(jsonPath("$.statusCode", is(201)))
				.andExpect(jsonPath("$.message", is("Update successful")));
	}
	
	@Test
	void deleteTest() throws Exception {
		Affiliate affiliate = new Affiliate();
		
		affiliate.setId(1);
		affiliate.setName("Affiliate 1");
		affiliate.setAge(1);
		affiliate.setMail("affiliate1@prueba.com");
		
		com.manage.apilab.model.Test test = new com.manage.apilab.model.Test();

		test.setId(1);
		test.setName("Test 1");
		test.setDescription("Prueba test 1");
		
		Appoinment appoinment = new Appoinment(1, LocalDate.parse("2001-01-01"), LocalTime.parse("08:18"), test, affiliate);

		Integer id = appoinment.getId();

		when(appoinmentRepo.findById(id)).thenReturn(Optional.of(appoinment));

		mockMvc.perform(delete("/api/controller/appoinments/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.statusCode", is(200)))
				.andExpect(jsonPath("$.message", is("Appoinment id ["+id+"] removed")));
	}
	
	@Test
	void getbydateTest() throws Exception {
		Affiliate affiliate = new Affiliate();
		
		affiliate.setId(1);
		affiliate.setName("Affiliate 1");
		affiliate.setAge(1);
		affiliate.setMail("affiliate1@prueba.com");
		
		com.manage.apilab.model.Test test = new com.manage.apilab.model.Test();

		test.setId(1);
		test.setName("Test 1");
		test.setDescription("Prueba test 1");
		
		List<Appoinment> listByDate = new ArrayList<>();
		
		for (int i = 1; i <= 2; i++) {
			listByDate.add(new Appoinment(i, LocalDate.parse("2001-01-01"), LocalTime.parse("08:18"), test, affiliate));
		}
		
		LocalDate date = LocalDate.parse("2001-01-01");
		String localDate = "01/01/2001";

		when(appoinmentService.getbydate(date)).thenReturn(listByDate);

		mockMvc.perform(get("/api/controller/appoinments/by").param("date",localDate)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(listByDate)))
				.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.size()", is(listByDate.size())));
	}
	
	@Test
	void getbyaffiliatesTest() throws Exception {
		Affiliate affiliate = new Affiliate();
		
		affiliate.setId(1);
		affiliate.setName("Affiliate 1");
		affiliate.setAge(1);
		affiliate.setMail("affiliate1@prueba.com");
		
		com.manage.apilab.model.Test test = new com.manage.apilab.model.Test();
		
		test.setId(1);
		test.setName("Test 1");
		test.setDescription("Prueba test 1");
		
		List<Appoinment> listByAffiliate = new ArrayList<>();
		
		for (int i = 1; i <= 2; i++) {
			listByAffiliate.add(new Appoinment(i, LocalDate.parse("2001-01-01"), LocalTime.parse("08:18"), test, affiliate));
		}

		Integer idAffiliatte = 1;

		when(appoinmentService.getbyaffiliates(idAffiliatte)).thenReturn(listByAffiliate);

		mockMvc.perform(get("/api/controller/appoinments/affiliate/{idAffiliatte}",idAffiliatte)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(listByAffiliate)))
				.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.size()", is(listByAffiliate.size())));
	}
}
