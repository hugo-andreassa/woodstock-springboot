package com.hyperdrive.woodstock.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.hyperdrive.woodstock.entities.Adress;
import com.hyperdrive.woodstock.entities.Client;
import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.entities.User;
import com.hyperdrive.woodstock.entities.enums.UserStatus;
import com.hyperdrive.woodstock.entities.enums.UserType;
import com.hyperdrive.woodstock.repositories.AdressRepository;
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
	private AdressRepository adressRespository;
	@Autowired
	private ClientRepository clientRepository;
	
	@Override
	public void run(String... args) throws Exception {
		Company comp = new Company(null, "Berlatto Moveis Planejados", "12944621000102", "11940308371", "berlattomoveis@hotmail.com", "https://berlattomoveis.com.br", "");
		
		User u1 = new User(null, "Hugo A.", "123456", "hugo.andreassa@gmail.com", "11956492900", UserStatus.ENABLED, UserType.STOCKIST, comp);
		User u2 = new User(null, "Rafael M.", "123456", "berlattomoveis@hotmail.com", "11956492430", UserStatus.ENABLED, UserType.ADMINISTRATOR, comp);
		User u3 = new User(null, "Wesley Fernando", "123456", "wesley@gmail.com", "11956492430", UserStatus.ENABLED, UserType.WOODWORKER, comp);
		
		Adress ad1 = new Adress(null, "street", "city", "state", "number", "comp", "cep");
		Adress ad2 = new Adress(null, "street", "city", "state", "number", "comp", "cep");
		Adress ad3 = new Adress(null, "street", "city", "state", "number", "comp", "cep");
		
		Client c1 = new Client(null, "Felipe Giglio", "", "", "00000000001", comp, ad1);
		Client c2 = new Client(null, "Eduardo Monção", "", "", "00000000002", comp, ad3);
		Client c3 = new Client(null, "Alexandre Pássaro", "", "", "00000000003", comp, ad2);		
		
		companyRepository.save(comp);
		userRepository.saveAll(Arrays.asList(u1, u2, u3));
		adressRespository.saveAll(Arrays.asList(ad1, ad2, ad3));
		clientRepository.saveAll(Arrays.asList(c1, c2, c3));
		
	}
}
