package com.hyperdrive.woodstock.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyperdrive.woodstock.entities.Budget;
import com.hyperdrive.woodstock.entities.BudgetItem;
import com.hyperdrive.woodstock.entities.Client;
import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.services.exceptions.FileException;
import com.hyperdrive.woodstock.services.exceptions.ResourceNotFoundException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/** Serviço responsável por gerar um PDF a partir dos dados da Empresa, Cliente e Orçamento
 * 
 * @author Hugo Andreassa Amaral
 */
@Service
public class PdfService {

	@Autowired
	private CompanyService companyService;
	@Autowired
	private BudgetService budgetService;
	@Autowired
	private ClientService clientService;
	
	private Company company;
	private Budget budget;
	private Client client;

	private final String PATH = "auto-generated-budget.pdf";
	
	private final Font FONT_BOLD = new Font(FontFamily.HELVETICA, 9f, Font.BOLD);
	private final Font FONT_NORMAL = new Font(FontFamily.HELVETICA, 9f, Font.NORMAL);

	public File generatePdfFromBudget(Long companyId, Long clientId, Long budgetId) {
		
		Company company = companyService.findById(companyId);
		Client client = clientService.findById(clientId);
		Budget budget = budgetService.findById(budgetId);	
		
		if(company == null) {
			throw new ResourceNotFoundException(companyId);
		} else if(client == null) {
			throw new ResourceNotFoundException(clientId);
		} else if (budget == null) {
			throw new ResourceNotFoundException(budgetId);
		}		

		this.client = client;
		this.budget = budget;
		this.company = company;
		
		return generateBudgetPdf();
	}
	
	private File generateBudgetPdf() {
		
		Document document = new Document();
		
		try {
			
			PdfWriter.getInstance(document, new FileOutputStream(PATH));
			document.open();

			// Adicionando dados de criação ao pdf
			document.addSubject("Gerando PDF em Java");
			document.addKeywords("Woodstock auto genarated budget");
			document.addCreator("iText");
			document.addAuthor("Hugo Andreassa Amaral");

			// Primeira tabela ---------------------------------------------------
			createFirstTableBudget(document);
			// -----------------------------------------------------------
			
			document.add(new Paragraph("\n"));
			
			// Segunda tabela ------------------------------------------------
			createSecondTableBudget(document);
			// --------------------------------------------------------------
			
			document.add(new Paragraph("\n"));
			
			// Terceira tabela -----------------------------------------------
			createThirdTableBudget(document);
			// --------------------------------------------------------------
			
			document.add(new Paragraph("\n"));
			
			// Quarta tabela -----------------------------------------------------
			createFourthTableBudget(document);
			// -----------------------------------------------------------------
	        
			File file = new File(PATH);
			
			return file;
		} catch (DocumentException e) {
			throw new FileException("An error occurred while trying to open the file.");
		} catch (IOException e) {
			throw new FileException("An error occurred while trying to open the file.");
		} finally {
			document.close();
		}
	}

	private void createFirstTableBudget(Document document) throws DocumentException, IOException {
		
		final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy")
	            .withZone(ZoneId.systemDefault());
		
		// Dados utilizados na primeira tabela
		String logo = company.getLogo();
		String phone = company.getPhone();
		String whatsapp = company.getWhatsapp();
		String address = company.getAddress().toString();
		String email = company.getEmail();
		String site = company.getSite();
		String creationDate = DATE_TIME_FORMATTER.format(budget.getCreationDate());
		
		// Cria a tabela
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 10, 8, 5 });

		// Cria a celula
		PdfPCell cell = null;

		// Recupera o logo e adiciona ele na classe Image
		Image img = Image.getInstance(new URL(logo));
		img.setAlignment(Element.ALIGN_CENTER);
		img.scaleToFit(200, 200);
		System.out.println(img.toString());

		// Adiciona o logo na celula
		cell = new PdfPCell();
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(img);
		cell.setRowspan(2);

		// Adiciona a celula na tabela
		table.addCell(cell);

		// Cria o paragrafo e configura o mesmo
		Paragraph prgphSecondCell = new Paragraph();
		prgphSecondCell.setAlignment(Element.ALIGN_CENTER);
		prgphSecondCell.setLeading(11f);

		// Adiciona os textos ao paragrafo e troca o espaçamento
		prgphSecondCell.setFont(FONT_BOLD);
		prgphSecondCell.add("Tel.: ");
		prgphSecondCell.setFont(FONT_NORMAL);
		prgphSecondCell.add(phone + " \n");
		
		prgphSecondCell.setFont(FONT_BOLD);
		prgphSecondCell.add("WhatsApp: ");
		prgphSecondCell.setFont(FONT_NORMAL);
		prgphSecondCell.add(whatsapp + " \n\n");

		prgphSecondCell.setFont(FONT_NORMAL);
		prgphSecondCell.add(address + "\n\n");

		prgphSecondCell.setFont(FONT_BOLD);
		prgphSecondCell.add("E-mail.: ");
		prgphSecondCell.setFont(FONT_NORMAL);
		prgphSecondCell.add(email + " \n");

		prgphSecondCell.setFont(FONT_BOLD);
		prgphSecondCell.add("Site: ");
		prgphSecondCell.setFont(FONT_NORMAL);
		prgphSecondCell.add(site);

		// Adiciona o paragrafo na celula
		cell = new PdfPCell();
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(prgphSecondCell);
		cell.setRowspan(2);

		// Adiciona a celula na tabela
		table.addCell(cell);

		// Cria o paragrafo e configura o mesmo
		Paragraph prgphThirdCell = new Paragraph();
		prgphThirdCell.setAlignment(Element.ALIGN_CENTER);

		// Configura o texto do paragrafo da 3 celula
		prgphThirdCell.clear();
		prgphThirdCell.setFont(FONT_BOLD);
		prgphThirdCell.setAlignment(Element.ALIGN_CENTER);
		prgphThirdCell.add("Criado em \n " + creationDate);

		// Coloca o paragrafo dentro da celula
		cell = new PdfPCell();
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(prgphThirdCell);
		cell.setFixedHeight(50);

		// Adiciona a celula na tabela
		table.addCell(cell);

		// Configura o texto do paragrafo da 3 celula e 2 linha
		prgphThirdCell.clear();
		prgphThirdCell.setFont(FONT_BOLD);
		prgphThirdCell.add("Agradecemos a preferência!");

		// Coloca o paragrafo dentro da celula
		cell = new PdfPCell();
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(prgphThirdCell);
		cell.setFixedHeight(50);

		// Adiciona a celula na tabela
		table.addCell(cell);

		document.add(table);
	}

	private void createSecondTableBudget(Document document) throws DocumentException {
		
		// Dados utilizados na criação da tabela
		String name = client.getName();
		String address = client.getAddress().toString();
		
		
		// Cria a tabela com 2 colunas
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 1, 10 });

		// Cria a celula
		PdfPCell cell = null;

		// Cria o paragrafo e configura
		Paragraph p = new Paragraph();
		p.setAlignment(Element.ALIGN_LEFT);

		// Configura o paragrafo
		p.clear();
		p.setFont(FONT_BOLD);
		p.add("Cliente");
		// Adiciona o paragrafo na celula 1
		cell = new PdfPCell();
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(p);
		// Adiciona a celula na tabela
		table.addCell(cell);

		// Configura o paragrafo
		p.clear();
		p.setFont(FONT_NORMAL);
		p.add(name);
		// Adiciona o paragrafo na celula 2
		cell = new PdfPCell();
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(p);
		// Adiciona a celula na tabela
		table.addCell(cell);

		// Configura o paragrafo
		p.clear();
		p.setFont(FONT_BOLD);
		p.add("Endereço");
		// Adiciona o paragrafo na celula 3
		cell = new PdfPCell();
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(p);
		// Adiciona a celula na tabela
		table.addCell(cell);

		// Configura o paragrafo
		p.clear();
		p.setFont(FONT_NORMAL);
		p.add(address);
		// Adiciona o paragrafo na celula 4
		cell = new PdfPCell();
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(p);
		// Adiciona a celula na tabela
		table.addCell(cell);

		document.add(table);
	}

	public void createThirdTableBudget(Document document) throws DocumentException {
		
		// Cria a tabela com 4 colunas
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 0.5f, 5f, 1.5f, 1f });

		// Cria a celula
		PdfPCell cell = null;

		// Cria o paragrafo e configura
		Paragraph p = new Paragraph();
		p.setAlignment(Element.ALIGN_CENTER);

		// Deixa a fonte em negrito
		p.setFont(FONT_BOLD);

		// Configura o paragrafo
		p.clear();
		p.add("Qtd.");
		// Adiciona o paragrafo na celula 1
		cell = new PdfPCell();
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(p);
		// Adiciona a celula na tabela
		table.addCell(cell);

		// Configura o paragrafo
		p.clear();
		p.add("Descrição");
		// Adiciona o paragrafo na celula 2
		cell = new PdfPCell();
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(p);
		// Adiciona a celula na tabela
		table.addCell(cell);

		// Configura o paragrafo
		p.clear();
		p.add("Ambiente");
		// Adiciona o paragrafo na celula 3
		cell = new PdfPCell();
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(p);
		// Adiciona a celula na tabela
		table.addCell(cell);

		// Configura o paragrafo
		p.clear();
		p.add("Total");
		// Adiciona o paragrafo na celula 4
		cell = new PdfPCell();
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(p);
		// Adiciona a celula na tabela
		table.addCell(cell);

		// Deixa a fonte normal
		p.setFont(FONT_NORMAL);

		// Dados utilizados na criação da tabela
		Locale locale = new Locale("pt", "BR");
		String quantity = "";
		String description = "";
		String environment = "";
		String sum = "";
		String total = NumberFormat.getCurrencyInstance(locale).format(budget.getTotal());
		
		for(BudgetItem x : budget.getItems()) {
			quantity = x.getQuantity().toString();
			description = x.getDescription();
			environment = String.valueOf(x.getEnvironment());
			sum = NumberFormat.getCurrencyInstance(locale).format(x.subTotal());
			
			// Configura o paragrafo
			p.clear();
			p.add(quantity);
			// Adiciona o paragrafo na celula 1 e coluna 1
			cell = new PdfPCell();
			cell.setBorderWidth(0.5f);
			cell.setBorderColor(BaseColor.GRAY);
			cell.addElement(p);
			// Adiciona a celula na tabela
			table.addCell(cell);

			// Configura o paragrafo
			p.clear();
			p.add(description);
			// Adiciona o paragrafo na celula 2 e coluna 2
			cell = new PdfPCell();
			cell.setBorderWidth(0.5f);
			cell.setBorderColor(BaseColor.GRAY);
			cell.addElement(p);
			// Adiciona a celula na tabela
			table.addCell(cell);

			// Configura o paragrafo
			p.clear();
			p.add(environment);
			// Adiciona o paragrafo na celula 3 e coluna 3
			cell = new PdfPCell();
			cell.setBorderWidth(0.5f);
			cell.setBorderColor(BaseColor.GRAY);
			cell.addElement(p);
			// Adiciona a celula na tabela
			table.addCell(cell);

			// Configura o parágrafo
			p.clear();
			p.add(sum);
			// Adiciona o parágrafo na célula 4 e coluna 4
			cell = new PdfPCell();
			cell.setBorderWidth(0.5f);
			cell.setBorderColor(BaseColor.GRAY);
			cell.addElement(p);
			// Adiciona a célula na tabela
			table.addCell(cell);
		}

		// Adiciona células sem borda a tabela
		cell = new PdfPCell();
		cell.setBorderWidth(0);
		table.addCell(cell);
		table.addCell(cell);
		table.addCell(cell);

		// Configura o parágrafo
		p.clear();
		p.setFont(FONT_BOLD);
		p.add(total);
		// Adiciona o parágrafo na tabela
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(p);
		// Adiciona na tabela
		table.addCell(cell);

		document.add(table);
	}

	public void createFourthTableBudget(Document document) throws DocumentException {

		// Dados utilizados na criação da tabela
		String deadline = budget.getDeadline().toString() + " dias.";
		String paymentMethod = budget.getPaymentMethod();

		
		// Cria a tabela com 2 colunas
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 1, 4 });

		// Cria a celula
		PdfPCell cell = null;

		// Cria o paragrafo e configura
		Paragraph p = new Paragraph();
		p.setAlignment(Element.ALIGN_LEFT);

		// Configura o paragrafo
		p.clear();
		p.setFont(FONT_BOLD);
		p.add("Prazo de entrega");
		// Adiciona o paragrafo na celula 1
		cell = new PdfPCell();
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(p);
		// Adiciona a celula na tabela
		table.addCell(cell);

		// Configura o paragrafo
		p.clear();
		p.setFont(FONT_NORMAL);
		p.add(deadline);
		// Adiciona o paragrafo na celula 2
		cell = new PdfPCell();
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(p);
		// Adiciona a celula na tabela
		table.addCell(cell);

		// Configura o paragrafo
		p.clear();
		p.setFont(FONT_BOLD);
		p.add("Forma de pagamento");
		// Adiciona o paragrafo na celula 3
		cell = new PdfPCell();
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(p);
		// Adiciona a celula na tabela
		table.addCell(cell);

		// Configura o paragrafo
		p.clear();
		p.setFont(FONT_NORMAL);
		p.add(paymentMethod);
		// Adiciona o paragrafo na celula 4
		cell = new PdfPCell();
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(p);
		// Adiciona a celula na tabela
		table.addCell(cell);

		document.add(table);
	}

}
