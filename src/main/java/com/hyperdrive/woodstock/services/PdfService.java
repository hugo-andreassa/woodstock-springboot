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

	private final Font FONT_BOLD = new Font(FontFamily.HELVETICA, 9f, Font.BOLD);
	private final Font FONT_NORMAL = new Font(FontFamily.HELVETICA, 9f, Font.NORMAL);

	public File generateBudgetPdf(Long companyId, Long clientId, Long budgetId) {
		
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
		
		return generateBudgetPdf(company, client, budget);
	}
	
	private File generateBudgetPdf(Company company, Client client, Budget budget) {
		final String PATH = "Budget.pdf";
		
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
			createFirstTableBudget(company, budget, document);
			document.add(new Paragraph("\n"));
			
			// -----------------------------------------------------------
			
			// Segunda tabela ------------------------------------------------
			createSecondTableBudget(client, document);
			document.add(new Paragraph("\n"));
			
			// --------------------------------------------------------------
			
			// Terceira tabela -----------------------------------------------
			createThirdTableBudget(budget, document);
			document.add(new Paragraph("\n"));
			// --------------------------------------------------------------
			
			// Quarta tabela -----------------------------------------------------
			createFourthTableBudget(budget, document);
			
			// -----------------------------------------------------------------
	        
			File file = new File(PATH);
			file.delete();
			
			return file;
		} catch (DocumentException e) {
			throw new FileException("An error occurred while trying to open the file.");
		} catch (IOException e) {
			throw new FileException("An error occurred while trying to open the file.");
		} finally {
			document.close();
		}
	}

	private void createFirstTableBudget(Company company, Budget budget, Document document) throws DocumentException, IOException {
		
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
		PdfPTable table = createTable(3, new int[] { 10, 8, 5 });

		// Recupera o logo e adiciona ele na classe Image
		Image img = Image.getInstance(new URL(logo));
		img.setAlignment(Element.ALIGN_CENTER);
		img.scaleToFit(200, 200);
		System.out.println(img.toString());
		
		// Cria a celula
		PdfPCell cell = null;
		
		// Adiciona o logo na celula
		cell = createCellTableWithElement(0.5f, img);
		cell.setRowspan(2);
		// Adiciona a celula na tabela
		table.addCell(cell);

		// Cria o paragrafo e configura o mesmo
		Paragraph p = createParagraph(Element.ALIGN_CENTER, FONT_BOLD, 10.5f);

		// Adiciona os textos ao paragrafo
		p.setFont(FONT_BOLD);
		p.add("Tel.: ");
		p.setFont(FONT_NORMAL);
		p.add(phone + " \n");
		
		p.setFont(FONT_BOLD);
		p.add("WhatsApp: ");
		p.setFont(FONT_NORMAL);
		p.add(whatsapp + " \n\n");

		p.setFont(FONT_NORMAL);
		p.add(address + "\n\n");

		p.setFont(FONT_BOLD);
		p.add("E-mail.: ");
		p.setFont(FONT_NORMAL);
		p.add(email + " \n");

		p.setFont(FONT_BOLD);
		p.add("Site: ");
		p.setFont(FONT_NORMAL);
		p.add(site);

		// Adiciona o paragrafo na celula
		cell = createCellTableWithElement(0.5f, p);
		cell.setRowspan(2);
		// Adiciona a celula na tabela
		table.addCell(cell);

		// Cria o paragrafo e configura o mesmo
		p = createParagraph(Element.ALIGN_CENTER, FONT_BOLD);
		// Limpa o paragrafo e adiciona o texto
		clearParagraph(p, "Criado em \n " + creationDate);

		// Coloca o paragrafo dentro da celula
		cell = createCellTableWithElement(0.5f, p);	
		cell.setFixedHeight(50);
		// Adiciona a celula na tabela
		table.addCell(cell);

		// Configura o texto do paragrafo da 3 celula e 2 linha
		clearParagraph(p, "Agradecemos a preferência!");

		// Coloca o paragrafo dentro da celula
		cell = createCellTableWithElement(0.5f, p);
		cell.setFixedHeight(50);
		// Adiciona a celula na tabela
		table.addCell(cell);

		document.add(table);
	}

	private void createSecondTableBudget(Client client, Document document) throws DocumentException {
		
		// Dados utilizados na criação da tabela
		String name = client.getName();
		String address = client.getAddress().toString();
		
		// Cria a tabela com 2 colunas
		PdfPTable table = createTable(2, new int[] { 1, 10 });

		// Cria o parágrafo e configura
		Paragraph p = createParagraph(Element.ALIGN_LEFT, FONT_BOLD);
		p.add("Cliente");
		createCellTableWithElement(0.5f, p, table);

		clearParagraph(p, name, FONT_NORMAL);
		createCellTableWithElement(0.5f, p, table);

		clearParagraph(p, "Endereço", FONT_BOLD);
		createCellTableWithElement(0.5f, p, table);

		clearParagraph(p, address, FONT_NORMAL);
		createCellTableWithElement(0.5f, p, table);
		
		document.add(table);
	}

	public void createThirdTableBudget(Budget budget, Document document) throws DocumentException {
		
		// Cria a tabela com 4 colunas
		PdfPTable table = createTable(4, new float[] { 0.5f, 5f, 1.5f, 1f });

		// Cria o paragrafo e configura
		Paragraph p = createParagraph(Element.ALIGN_CENTER, FONT_BOLD);
		
		p.add("Qtd.");
		createCellTableWithElement(0.5f, p, table);
		
		clearParagraph(p, "Descrição");
		createCellTableWithElement(0.5f, p, table);

		clearParagraph(p, "Ambiente");
		createCellTableWithElement(0.5f, p, table);
		
		clearParagraph(p, "Total");
		createCellTableWithElement(0.5f, p, table);
		
		// Dados utilizados na criação da tabela
		Locale locale = new Locale("pt", "BR");
		String quantity = "";
		String description = "";
		String environment = "";
		String sum = "";
		String total = NumberFormat.getCurrencyInstance(locale).format(budget.getTotal());
		
		for(BudgetItem x : budget.getItems()) {
			sum = "";
			quantity = x.getQuantity().toString();
			description = x.getDescription();
			environment = String.valueOf(x.getEnvironment());
			sum = NumberFormat.getCurrencyInstance(locale).format(x.subTotal());
			
			clearParagraph(p, quantity, FONT_NORMAL);
			createCellTableWithElement(0.5f, p, table);

			clearParagraph(p, description, FONT_NORMAL);
			createCellTableWithElement(0.5f, p, table);

			clearParagraph(p, environment, FONT_NORMAL);
			createCellTableWithElement(0.5f, p, table);
			
			clearParagraph(p, sum, FONT_NORMAL);
			createCellTableWithElement(0.5f, p, table);
		}

		// Adiciona células sem borda a tabela
		table.addCell(createCellTable(0));
		table.addCell(createCellTable(0));
		table.addCell(createCellTable(0));

		clearParagraph(p, total, FONT_BOLD);
		createCellTableWithElement(0.5f, p, table);

		document.add(table);
	}

	public void createFourthTableBudget(Budget budget, Document document) throws DocumentException {

		// Dados utilizados na criação da tabela
		String deadline = budget.getDeadline().toString() + " dias.";
		String paymentMethod = budget.getPaymentMethod();

		
		// Cria a tabela com 2 colunas
		PdfPTable table = createTable(2, new int[] { 1, 4 });

		// Cria o paragrafo e configura
		Paragraph p = createParagraph(Element.ALIGN_LEFT, FONT_BOLD);

		p.add("Prazo de entrega");
		createCellTableWithElement(0.5f, p, table);

		clearParagraph(p, deadline, FONT_NORMAL);
		createCellTableWithElement(0.5f, p, table);

		clearParagraph(p, "Forma de pagamento", FONT_BOLD);
		createCellTableWithElement(0.5f, p, table);
		
		clearParagraph(p, paymentMethod, FONT_NORMAL);
		createCellTableWithElement(0.5f, p, table);

		document.add(table);
	}
	
	private PdfPTable createTable(int columns, int[] widths) throws DocumentException {
		PdfPTable table = new PdfPTable(columns);
		table.setWidthPercentage(100);
		table.setWidths(widths);
		
		return table;
	}
	
	private PdfPTable createTable(int columns, float[] widths) throws DocumentException {
		PdfPTable table = new PdfPTable(columns);
		table.setWidthPercentage(100);
		table.setWidths(widths);
		
		return table;
	}
	
	private PdfPCell createCellTable(float borderWidth) {
		PdfPCell cell = new PdfPCell();
		cell.setBorderWidth(borderWidth);
		cell.setBorderColor(BaseColor.GRAY);
		
		return cell;
	}
	
	private PdfPCell createCellTableWithElement(float borderWidth, Element element) {
		PdfPCell cell = new PdfPCell();
		cell.setBorderWidth(borderWidth);
		cell.setBorderColor(BaseColor.GRAY);
		
		cell.addElement(element);
		return cell;
	}
	
	private void createCellTableWithElement(float borderWidth, Element element, PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBorderWidth(borderWidth);
		cell.setBorderColor(BaseColor.GRAY);
		
		cell.addElement(element);
		table.addCell(cell);
	}
	
	private Paragraph createParagraph(int alignment, Font font) {
		Paragraph p = new Paragraph();
		p.setFont(font);
		p.setAlignment(alignment);
		
		return p;
	}
	
	private Paragraph createParagraph(int alignment, Font font, float fixedLeading) {
		Paragraph p = new Paragraph();
		p.setAlignment(alignment);
		p.setFont(font);
		p.setLeading(fixedLeading);
		
		return p;
	}
	
	private void clearParagraph(Paragraph p, String newContent) {
		p.clear();
		p.add(newContent);
	}
	
	private void clearParagraph(Paragraph p, String newContent, Font font) {
		p.clear();
		p.setFont(font);
		p.add(newContent);
	}
}
