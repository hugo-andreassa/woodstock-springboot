package com.hyperdrive.woodstock.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.hyperdrive.woodstock.entities.Address;
import com.hyperdrive.woodstock.entities.Budget;
import com.hyperdrive.woodstock.entities.Client;
import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.entities.User;
import com.hyperdrive.woodstock.entities.enums.BudgetStatus;
import com.hyperdrive.woodstock.entities.enums.UserStatus;
import com.hyperdrive.woodstock.entities.enums.UserType;
import com.hyperdrive.woodstock.repositories.AddressRepository;
import com.hyperdrive.woodstock.repositories.BudgetRepository;
import com.hyperdrive.woodstock.repositories.ClientRepository;
import com.hyperdrive.woodstock.repositories.CompanyRepository;
import com.hyperdrive.woodstock.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private AddressRepository adressRespository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private BudgetRepository budgetRepository;
	
	@Override
	public void run(String... args) throws Exception {
		Company comp = new Company(null, "Berlatto Moveis Planejados", "12944621000102", "11940308371", "berlattomoveis@hotmail.com", "https://berlattomoveis.com.br", "");
		
		User u1 = new User(null, "Hugo A.", "123456", "hugo.andreassa@gmail.com", "11956492900", UserStatus.ENABLED, UserType.STOCKIST, comp);
		User u2 = new User(null, "Rafael M.", "123456", "berlattomoveis@hotmail.com", "11956492430", UserStatus.ENABLED, UserType.ADMINISTRATOR, comp);
		User u3 = new User(null, "Wesley Fernando", "123456", "wesley@gmail.com", "11956492430", UserStatus.ENABLED, UserType.WOODWORKER, comp);
		
		Address ad1 = new Address(null, "street", "city", "state", "number", "comp", "cep");
		Address ad2 = new Address(null, "street", "city", "state", "number", "comp", "cep");
		Address ad3 = new Address(null, "street", "city", "state", "number", "comp", "cep");
		
		Client c1 = new Client(null, "Felipe Giglio", "", "", "00000000001", comp, ad1);
		Client c2 = new Client(null, "Eduardo Monção", "", "", "00000000002", comp, ad3);
		Client c3 = new Client(null, "Alexandre Pássaro", "", "", "00000000003", comp, ad2);		
		
		Budget b1 = new Budget(null, 50, null, Instant.now(), 
				"Entrada de 30% e restante em até 10x no cartão", BudgetStatus.COMPLETED, c1, null);
		Budget b2 = new Budget(null, 50, null, Instant.now(), 
				"Entrada de 30% e restante em até 10x no cartão", BudgetStatus.COMPLETED, c2, null);
		Budget b3 = new Budget(null, 50, null, Instant.now(), 
				"Entrada de 30% e restante em até 10x no cartão", BudgetStatus.COMPLETED, c3, null);
		
		companyRepository.save(comp);
		userRepository.saveAll(Arrays.asList(u1, u2, u3));
		adressRespository.saveAll(Arrays.asList(ad1, ad2, ad3));
		clientRepository.saveAll(Arrays.asList(c1, c2, c3));
		budgetRepository.saveAll(Arrays.asList(b1, b2, b3));
		
	}
}
