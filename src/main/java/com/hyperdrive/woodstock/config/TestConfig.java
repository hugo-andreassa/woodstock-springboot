package com.hyperdrive.woodstock.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.hyperdrive.woodstock.entities.Address;
import com.hyperdrive.woodstock.entities.Budget;
import com.hyperdrive.woodstock.entities.BudgetItem;
import com.hyperdrive.woodstock.entities.Client;
import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.entities.CuttingPlan;
import com.hyperdrive.woodstock.entities.Material;
import com.hyperdrive.woodstock.entities.OperatingExpense;
import com.hyperdrive.woodstock.entities.Project;
import com.hyperdrive.woodstock.entities.User;
import com.hyperdrive.woodstock.entities.enums.BudgetItemStatus;
import com.hyperdrive.woodstock.entities.enums.BudgetStatus;
import com.hyperdrive.woodstock.entities.enums.OperatingExpenseType;
import com.hyperdrive.woodstock.entities.enums.StockUnit;
import com.hyperdrive.woodstock.entities.enums.UserStatus;
import com.hyperdrive.woodstock.entities.enums.UserType;
import com.hyperdrive.woodstock.repositories.AddressRepository;
import com.hyperdrive.woodstock.repositories.BudgetItemRepository;
import com.hyperdrive.woodstock.repositories.BudgetRepository;
import com.hyperdrive.woodstock.repositories.ClientRepository;
import com.hyperdrive.woodstock.repositories.CompanyRepository;
import com.hyperdrive.woodstock.repositories.CuttingPlanRepository;
import com.hyperdrive.woodstock.repositories.MaterialRepository;
import com.hyperdrive.woodstock.repositories.OperatingExpenseRepository;
import com.hyperdrive.woodstock.repositories.ProjectRepository;
import com.hyperdrive.woodstock.repositories.UserRepository;

/** Classe para o perfil de teste
 * 
 * @author Hugo A.
 */
@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private AddressRepository addressRespository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private BudgetRepository budgetRepository;
	
	@Autowired
	private BudgetItemRepository budgetItemRepository;
	
	@Autowired
	private CuttingPlanRepository cuttingPlanRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private OperatingExpenseRepository expenseRepository;
	
	@Autowired
	private MaterialRepository materialRepository;
	
	@Override
	public void run(String... args) throws Exception {
		Address adrs = new Address(null, "Avenida Brasil", "Mauá", "SP", "95", "", "09320-720");
		addressRespository.save(adrs);
		adrs = addressRespository.findById(1l).get();
		
		Company comp = new Company(null, "Berlatto Moveis Planejados", "12944621000102", 
				"(11) 3484-0227", "(11) 940308371", "berlattomoveis@hotmail.com", 
				"https://berlattomoveis.com.br", 
				"https://woodstock-springboot.s3-sa-east-1.amazonaws.com/comp1.jpg", adrs);
		companyRepository.save(comp);
		
		User u1 = new User(null, "Hugo A.", "123456", "hugo.andreassa@gmail.com", "11956492900", UserStatus.ENABLED, UserType.STOCKIST, comp);
		User u2 = new User(null, "Rafael M.", "123456", "berlattomoveis@hotmail.com", "11956492430", UserStatus.ENABLED, UserType.ADMINISTRATOR, comp);
		User u3 = new User(null, "Wesley Fernando", "123456", "wesley@gmail.com", "11956492430", UserStatus.ENABLED, UserType.WOODWORKER, comp);
		userRepository.saveAll(Arrays.asList(u1, u2, u3));
		
		Address ad1 = new Address(null, "Rua Dublin", "Santo André", "SP", "253", "apto23", "09220810");
		Address ad2 = new Address(null, "Rua Bell Aliance", "São Caetano do Sul", "SP", "225", "", "09581-680");
		Address ad3 = new Address(null, "Av. Governador Mário Covas", "Mauá", "SP", "287", "", "09390-040");
		addressRespository.saveAll(Arrays.asList(ad1, ad2, ad3));
		ad1 = addressRespository.findById(2L).get();
		ad2 = addressRespository.findById(3L).get();
		ad3 = addressRespository.findById(4L).get();
		
		Client c1 = new Client(null, "Felipe Giglio", "", "", "00000000001", comp, ad1);
		Client c2 = new Client(null, "Eduardo Monção", "", "", "00000000002", comp, ad3);
		Client c3 = new Client(null, "Alexandre Pássaro", "", "", "00000000003", comp, ad2);		
		clientRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Budget b1 = new Budget(null, 50, null, Instant.now(), 
				"Entrada de 30% e restante em até 15x no cartão", 
				BudgetStatus.COMPLETED, c1, null);
		Budget b2 = new Budget(null, 50, null, Instant.now(), 
				"Entrada de 20% e restante em até 10x no cartão", 
				BudgetStatus.COMPLETED, c2, null);
		Budget b3 = new Budget(null, 50, null, Instant.now(), 
				"Entrada de 40% e restante em até 20x no cartão", 
				BudgetStatus.COMPLETED, c3, null);
		budgetRepository.saveAll(Arrays.asList(b1, b2, b3));
		
		BudgetItem bi1 = new BudgetItem(null, "Escrivaninha", 500.0, 1, "Quarto", BudgetItemStatus.WAITING, b1);
		BudgetItem bi2 = new BudgetItem(null, "Criado", 100.0, 2, "Quarto", BudgetItemStatus.FINISHED, b1);
		BudgetItem bi3 = new BudgetItem(null, "Torre Quente", 1250.0, 1, "Cozinha", BudgetItemStatus.PRODUCING, b1);
		BudgetItem bi4 = new BudgetItem(null, "Armário", 900.0, 1, "Cozinha", BudgetItemStatus.WAITING, b1);
		budgetItemRepository.saveAll(Arrays.asList(bi1, bi2, bi3, bi4));		
		
		CuttingPlan cp1 = new CuttingPlan(null, 2.40, 1.40, 3, "", bi1);
		CuttingPlan cp2 = new CuttingPlan(null, 1.40, 2.40, 1, "", bi1);
		CuttingPlan cp3 = new CuttingPlan(null, 3.00, 0.50, 2, "", bi1);
		CuttingPlan cp4 = new CuttingPlan(null, 2.50, 1.00, 2, "", bi1);
		cuttingPlanRepository.saveAll(Arrays.asList(cp1, cp2, cp3, cp4));
		
		Project p1 = new Project(null, "", "", bi1);
		Project p2 = new Project(null, "", "", bi1);
		projectRepository.saveAll(Arrays.asList(p1, p2));
		
		OperatingExpense oe1 = new OperatingExpense(null, "Galão de Água", "", 30.0, 
				Instant.now(), OperatingExpenseType.SUPPLIES, comp);
		OperatingExpense oe2 = new OperatingExpense(null, "Canetas", "", 30.0, 
				Instant.now(), OperatingExpenseType.OFFICE_EXPENSES, comp);
		expenseRepository.saveAll(Arrays.asList(oe1, oe2));
		
		Material m1 = new Material(null, "Corrediça 25mm", "", 3, StockUnit.PR, comp);
		Material m2 = new Material(null, "Corrediça 35mm", "", 3, StockUnit.PR, comp);
		Material m3 = new Material(null, "Corrediça 45mm", "", 3, StockUnit.PR, comp);
		Material m4 = new Material(null, "Dobradiça Reta", "", 3, StockUnit.UN, comp);
		Material m5 = new Material(null, "Dobradiça Curva", "", 3, StockUnit.UN, comp);
		materialRepository.saveAll(Arrays.asList(m1, m2, m3, m4, m5));
		
	}
}









