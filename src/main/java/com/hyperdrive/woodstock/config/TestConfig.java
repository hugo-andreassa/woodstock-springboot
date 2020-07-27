package com.hyperdrive.woodstock.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.entities.User;
import com.hyperdrive.woodstock.entities.enums.UserStatus;
import com.hyperdrive.woodstock.entities.enums.UserType;
import com.hyperdrive.woodstock.repositories.CompanyRepository;
import com.hyperdrive.woodstock.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CompanyRepository companyRepository;
	
	@Override
	public void run(String... args) throws Exception {
		Company comp = new Company(null, "Berlatto Moveis Planejados", "12944621000102", "11940308371", "berlattomoveis@hotmail.com", "https://berlattomoveis.com.br", "");
		
		User u1 = new User(null, "Hugo A.", "123456", "hugo.andreassa@gmail.com", "11956492900", UserStatus.ENABLED, UserType.STOCKIST, comp);
		User u2 = new User(null, "Rafael M.", "123456", "berlattomoveis@hotmail.com", "11956492430", UserStatus.ENABLED, UserType.ADMINISTRATOR, comp);
		
		companyRepository.save(comp);
		userRepository.saveAll(Arrays.asList(u1, u2));
	}
}
