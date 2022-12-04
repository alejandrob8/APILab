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
import com.manage.apilab.model.Affiliate;
import com.manage.apilab.repository.AffiliateRepository;
import com.manage.apilab.service.AffiliateService;
import com.manage.apilab.service.dto.AffiliateDTO;
import com.manage.apilab.service.dto.mapper.AffiliateDTOMap;

@WebMvcTest(AffiliateController.class)
class AffiliateControllerTest {

	@MockBean
	private AffiliateService affiliateService;

	@MockBean
	private AffiliateRepository affiliateRepo;

	@MockBean
	private AffiliateDTOMap mapper;

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
		List<Affiliate> listAffiliates = new ArrayList<>();
		for (int i = 1; i <= 3; i++) {
			listAffiliates.add(new Affiliate(i, "Affiliate " + i, i, "affiliate" + i + "@prueba.com"));
		}

		when(affiliateService.getlist()).thenReturn(listAffiliates);

		mockMvc.perform(get("/api/controller/affiliates").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[1].name", is("Affiliate 2")))
				.andExpect(jsonPath("$[2].age", is(3)))
				.andExpect(jsonPath("$[2].mail", is("affiliate3@prueba.com")))
				.andExpect(jsonPath("$.size()", is(listAffiliates.size())));
	}

	@Test
	void getbyidTest() throws Exception {
		Affiliate affiliate = new Affiliate();

		affiliate.setId(1);
		affiliate.setName("Affiliate 1");
		affiliate.setAge(1);
		affiliate.setMail("affiliate1@prueba.com");

		Integer id = affiliate.getId();

		when(affiliateService.getbyid(id)).thenReturn(affiliate);

		mockMvc.perform(get("/api/controller/affiliates/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("Affiliate 1")))
				.andExpect(jsonPath("$.age", is(1)))
				.andExpect(jsonPath("$.mail", is("affiliate1@prueba.com")));
	}

	@Test
	void getbyidNonexistentTest() throws Exception {
		Affiliate affiliate = new Affiliate();

		affiliate.setId(1);
		affiliate.setName("Affiliate 1");
		affiliate.setAge(1);
		affiliate.setMail("affiliate1@prueba.com");

		Integer id = affiliate.getId();

		when(affiliateService.getbyid(id)).thenThrow(new NotFoundException(null));

		mockMvc.perform(get("/api/controller/affiliates/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	void postTest() throws Exception {

		AffiliateDTO affiliateDTO = new AffiliateDTO();
		affiliateDTO.setName("Affiliate 1");
		affiliateDTO.setAge(1);
		affiliateDTO.setMail("affiliate1@prueba.com");

		Affiliate postAffiliate = mapper.map(affiliateDTO);

		when(affiliateService.post(affiliateDTO)).thenReturn(postAffiliate);

		mockMvc.perform(post("/api/controller/affiliates").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(affiliateDTO)))
				.andExpect(status().isCreated()).andDo(print())
				.andExpect(jsonPath("$.statusCode", is(201)))
				.andExpect(jsonPath("$.message", is("Insertion sussccesful")));
	}

	@Test
	void putTest() throws Exception {
		Affiliate putAffiliate = new Affiliate();

		putAffiliate.setId(1);
		putAffiliate.setName("Affiliate 1");
		putAffiliate.setAge(1);
		putAffiliate.setMail("affiliate1@prueba.com");

		Affiliate affiliate = new Affiliate();

		affiliate.setId(1);
		affiliate.setName("Affiliate 1");
		affiliate.setAge(1);
		affiliate.setMail("affiliate1@prueba.com");

		Integer id = affiliate.getId();

		when(affiliateRepo.findById(id)).thenReturn(Optional.ofNullable(putAffiliate));
		when(affiliateService.put(affiliate)).thenReturn(affiliate);

		mockMvc.perform(put("/api/controller/affiliates/", id).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(affiliate)))
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

		Integer id = affiliate.getId();

		when(affiliateRepo.findById(id)).thenReturn(Optional.of(affiliate));

		mockMvc.perform(delete("/api/controller/affiliates/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.statusCode", is(200)))
				.andExpect(jsonPath("$.message", is("Affiliate id [" + id + "] removed")));
	}

}
