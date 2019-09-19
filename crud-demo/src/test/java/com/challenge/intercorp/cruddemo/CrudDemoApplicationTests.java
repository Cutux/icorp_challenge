package com.challenge.intercorp.cruddemo;

import com.challenge.intercorp.cruddemo.controller.PersonController;
import com.challenge.intercorp.cruddemo.model.Person;
import com.challenge.intercorp.cruddemo.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrudDemoApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(CrudDemoApplicationTests.class);

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	ObjectMapper objectMapper;

	@Value("${spring.data.rest.base-path}")
	private String apiBasePath;

	@InjectMocks
	private PersonController personService;

	@Mock
	private PersonService personRepository;


	private Person person1, person2, person3;

	@Test
	public void testGetPersonById() {
		String response = null;

		try {
			when(personRepository.createPerson(person1.getFirstName(), person1.getLastName(), person1.getDni(), person1.getBirthDate())).thenReturn(person1);

			response = mockMvc.perform(post(apiBasePath + "/create")
					.content(objectMapper.writeValueAsString(person1))
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.firstName").value(person1.getFirstName())).andReturn().getResponse().getContentAsString();

			logger.info("- RESPONSE: "+ response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		try {
			person1 = new Person("Javier", "Landa", "48997217", new SimpleDateFormat("yyyy-MM-dd").parse("1991-12-10"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
