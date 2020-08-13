package com.hyperdrive.woodstock.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.hyperdrive.woodstock.entities.Budget;
import com.hyperdrive.woodstock.entities.BudgetItem;
import com.hyperdrive.woodstock.entities.Client;
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

@Service
public class PdfService {

	@Autowired
	private ClientService service;

	private Budget budget;
	private Client client;

	final static Font FONT_BOLD = new Font(FontFamily.HELVETICA, 9f, Font.BOLD);
	final static Font FONT_NORMAL = new Font(FontFamily.HELVETICA, 9f, Font.NORMAL);
	
	DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            .withZone(ZoneId.systemDefault());

	public String generatePdfFromBudget(Long companyId, Long clientId, Long budgetId) {
		Client client = service.findById(clientId);
		Budget budget = client.getBudgets().stream().filter(x -> x.getId() == budgetId).findFirst().get();

		this.client = client;
		this.budget = budget;


		Document document = new Document();
		
		try {
			
			PdfWriter.getInstance(document, new FileOutputStream("C:\\Temp\\PDF.pdf"));
			document.open();

			// adicionando um parágrafo ao documento
			// document.add(new Paragraph("Gerando PDF em Java - metadados"));
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
			
			File file = new File("C:\\Temp\\PDF.pdf");
			Path path = Paths.get(file.getAbsolutePath());
	        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

			return "C:\\Temp\\PDF.pdf";
		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} finally {
			document.close();
		}
		
		return null;
	}

	private void createFirstTableBudget(Document document)
			throws DocumentException, MalformedURLException, IOException {

		// Cria a tabela
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 10, 8, 5 });

		// Cria a celula
		PdfPCell cell = null;

		// Recupera o logo e adiciona ele na classe Image
		Image img = Image.getInstance("C:\\Temp\\logo.jpg");
		img.setAlignment(Element.ALIGN_CENTER);
		img.scaleToFit(200, 200);

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
		prgphSecondCell.add("(11) 3484-0227 \n");

		prgphSecondCell.setFont(FONT_BOLD);
		prgphSecondCell.add("WhatsApp: ");
		prgphSecondCell.setFont(FONT_NORMAL);
		prgphSecondCell.add("(11) 3484-0227 \n\n");

		prgphSecondCell.setFont(FONT_NORMAL);
		prgphSecondCell.add("Avenida Brasil, Nº95 - Parque das Flores, \n São Mateus - SP CEP: 09320-720 \n\n");

		prgphSecondCell.setFont(FONT_BOLD);
		prgphSecondCell.add("E-mail.: ");
		prgphSecondCell.setFont(FONT_NORMAL);
		prgphSecondCell.add("berlattomoveis@hotmail.com \n");

		prgphSecondCell.setFont(FONT_BOLD);
		prgphSecondCell.add("Site: ");
		prgphSecondCell.setFont(FONT_NORMAL);
		prgphSecondCell.add("www.berlattomoveis.com.br");

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
		prgphThirdCell.add("Criado em \n " + DATE_TIME_FORMATTER.format(budget.getCreationDate()));

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
		p.add(client.getName());
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
		p.add(client.getAddress().toString());
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

		for(BudgetItem x : budget.getItems()) {
			// Configura o paragrafo
			p.clear();
			p.add(x.getQuantity().toString());
			// Adiciona o paragrafo na celula 1 e coluna 1
			cell = new PdfPCell();
			cell.setBorderWidth(0.5f);
			cell.setBorderColor(BaseColor.GRAY);
			cell.addElement(p);
			// Adiciona a celula na tabela
			table.addCell(cell);

			// Configura o paragrafo
			p.clear();
			p.add(x.getDescription());
			// Adiciona o paragrafo na celula 2 e coluna 2
			cell = new PdfPCell();
			cell.setBorderWidth(0.5f);
			cell.setBorderColor(BaseColor.GRAY);
			cell.addElement(p);
			// Adiciona a celula na tabela
			table.addCell(cell);

			// Configura o paragrafo
			p.clear();
			p.add(String.valueOf(x.getEnvironment()));
			// Adiciona o paragrafo na celula 3 e coluna 3
			cell = new PdfPCell();
			cell.setBorderWidth(0.5f);
			cell.setBorderColor(BaseColor.GRAY);
			cell.addElement(p);
			// Adiciona a celula na tabela
			table.addCell(cell);

			// Configura o parágrafo
			p.clear();
			p.add(x.getPrice().toString());
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
		p.add(String.valueOf(budget.getTotal()));
		// Adiciona o parágrafo na tabela
		cell.setBorderWidth(0.5f);
		cell.setBorderColor(BaseColor.GRAY);
		cell.addElement(p);
		// Adiciona na tabela
		table.addCell(cell);

		document.add(table);
	}

	public void createFourthTableBudget(Document document) throws DocumentException {
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
		p.add(String.valueOf(budget.getDeadline()));
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
		p.add(budget.getPaymentMethod());
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
