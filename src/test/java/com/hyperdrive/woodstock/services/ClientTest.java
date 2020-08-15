package com.hyperdrive.woodstock.services;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyperdrive.woodstock.entities.Address;
import com.hyperdrive.woodstock.entities.Client;
import com.hyperdrive.woodstock.entities.Company;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientTest {	
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMaper;
	
	@Test
	public void postCorreto() throws JsonProcessingException, Exception {
		
		// Cenario
		Company comp = new Company(1L, null, null, null, null, null, null, null, null);
		Address ad = new Address(null, "street", "city", "state", "number", "comp", "cep");
		Client c = new Client(null, "Felipe Giglio", "", "", "00000000005", comp, ad);

		System.out.println(c.getCpfOrCnpj());
		
		// Ações
		ResultActions response = mockMvc.perform(post("/clients")
	            .contentType("application/json")
	            .content(objectMaper.writeValueAsString(c)))
	            .andExpect(status().isCreated());
		
		// Verificações 
		System.out.println(response.andExpect(status().isCreated())
				 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
				 .andReturn());
	}
	
	@Test
	public void postSemIdEmpresa() throws JsonProcessingException, Exception {
		
		// Cenario
		Address ad = new Address(null, "street", "city", "state", "number", "comp", "cep");
		Client c = new Client(null, "Felipe Giglio", "", "", "00000000005", null, ad);

		System.out.println(c.getCpfOrCnpj());
		
		// Ações
		ResultActions response = mockMvc.perform(post("/clients")
	            .contentType("application/json")
	            .content(objectMaper.writeValueAsString(c)));
		
		// Verificações 
		System.out.println(response.andExpect(status().isBadRequest())
				 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
				 .andReturn());
	}
	
	@Test
	public void postSemCpf() throws JsonProcessingException, Exception {
		
		// Cenario
		Address ad = new Address(null, "street", "city", "state", "number", "comp", "cep");
		Client c = new Client(null, "Felipe Giglio", "", "", null, null, ad);

		System.out.println(c.getCpfOrCnpj());
		
		// Ações
		ResultActions response = mockMvc.perform(post("/clients")
	            .contentType("application/json")
	            .content(objectMaper.writeValueAsString(c)));
		
		// Verificações 
		System.out.println(response.andExpect(status().isBadRequest())
				 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
				 .andReturn());
	}
	
	@Test
	public void deleteCorreto() throws Exception {
		// Ações
		ResultActions response = mockMvc.perform(delete("/clients/1")
	            .contentType("application/json"));
		
		// Verificações 
		System.out.println(response.andExpect(status().is2xxSuccessful())
				 .andReturn());
	}
	
	@Test
	public void deleteErrado() throws Exception {
		// Ações
		ResultActions response = mockMvc.perform(delete("/clients/A"));
		
		// Verificações 
		System.out.println(response.andExpect(status().isNotFound())
				 .andReturn());
	}
	
	
	
}
