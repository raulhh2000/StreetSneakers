package urjc.dad.service;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import urjc.dad.models.DataPurchase;
import urjc.dad.models.LineItem;

@Service
public class PDFService {
	public String createPdf (DataPurchase dataPurchase) {
		String namePDF = dataPurchase.getDate().replace(" ", "").replace(":", "_") + ".pdf";
		try {
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(namePDF));
			
			Font fontBlackBI = new Font(Font.HELVETICA, 16, Font.BOLDITALIC, Color.BLACK);
			Font fontBlackI = new Font(Font.HELVETICA, 16, Font.ITALIC, Color.BLACK);
			Font fontGrayI = new Font(Font.HELVETICA, 16, Font.ITALIC, new Color(230,88,61));
			Font fontWhiteBI = new Font(Font.HELVETICA, 16, Font.BOLDITALIC, Color.WHITE);
			
			document.open();
			
			PdfPTable header = new PdfPTable(1); 
			PdfPCell cell = new PdfPCell(new Phrase("FACTURA SIMPLIFICADA", fontWhiteBI));
		    cell.setVerticalAlignment(Element.ALIGN_CENTER);
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell.setBackgroundColor(new Color(45,62,80));
		    header.addCell(cell);
			
			Paragraph paragraphHeaderDataClient = new Paragraph("DATOS DEL CLIENTE: ", fontBlackBI);
			Paragraph lineBreak = new Paragraph(" ", fontBlackBI);
			
			Paragraph paragraphName = new Paragraph();
			paragraphName.add(new Phrase("Nombre: ",fontBlackBI));
			paragraphName.add(new Phrase(dataPurchase.getName(), fontBlackI));
			
			Paragraph paragraphLastName = new Paragraph();
			paragraphLastName.add(new Phrase("Apellido: " ,fontBlackBI));
			paragraphLastName.add(new Phrase(dataPurchase.getLastName(), fontBlackI));
			
			Paragraph paragraphEmail = new Paragraph();
			paragraphEmail.add(new Phrase("Correo electronico: ",fontBlackBI));
			paragraphEmail.add(new Phrase(dataPurchase.getEmail(), fontBlackI));
			
			Paragraph paragraphPhone = new Paragraph();
			paragraphPhone.add(new Phrase("Telefono: ",fontBlackBI));
			paragraphPhone.add(new Phrase(dataPurchase.getPhone(), fontBlackI));
			
			Paragraph paragraphAddress = new Paragraph();
			paragraphAddress.add(new Phrase("Dirección: ",fontBlackBI));
			paragraphAddress.add(new Phrase(dataPurchase.getAddress(), fontBlackI));
			
			Paragraph paragraphBankAccount = new Paragraph();
			paragraphBankAccount.add(new Phrase("Cuenta Bancaria: ",fontBlackBI));
			paragraphBankAccount.add(new Phrase(dataPurchase.getBankAccount(), fontBlackI));
			
			Paragraph paragraphDate = new Paragraph();
			paragraphDate.add(new Phrase("Fecha del pedido: ",fontBlackBI));
			paragraphDate.add(new Phrase(dataPurchase.getDate(), fontBlackI));
			
			Paragraph paragraphHeaderProducts = new Paragraph("PRODUCTOS: ", fontBlackBI);
			PdfPTable tablaLineItem = new PdfPTable(4);
			tablaLineItem.addCell(new Phrase("Nombre", fontBlackBI));
			tablaLineItem.addCell(new Phrase("Marca", fontBlackBI));
			tablaLineItem.addCell(new Phrase("Talla", fontBlackBI));          
			tablaLineItem.addCell(new Phrase("Precio", fontBlackBI));
		    for(LineItem item : dataPurchase.getLineItems()) {
		    	tablaLineItem.addCell(item.getName());
		    	tablaLineItem.addCell(item.getBrand());
		    	tablaLineItem.addCell(Double.toString(item.getSize()));
		    	tablaLineItem.addCell(Double.toString(item.getPrice())+"€");
		    }
		    
		    Paragraph paragraphTotalPrice = new Paragraph();
		    paragraphTotalPrice.add(new Phrase("Precio total: ",fontBlackBI));
		    paragraphTotalPrice.add(new Phrase(Double.toString(dataPurchase.getTotalPrice())+"€", fontGrayI));
			
			Paragraph paragraphNumProducts = new Paragraph();
			paragraphNumProducts.add(new Phrase("Número de productos: ",fontBlackBI));
			paragraphNumProducts.add(new Phrase(Integer.toString(dataPurchase.getNumProducts()), fontBlackI));
			
		    document.add(header);
		    document.add(lineBreak);
		    document.add(paragraphHeaderDataClient);
		    document.add(lineBreak);
			document.add(paragraphName);
			document.add(paragraphLastName);
			document.add(paragraphEmail);
			document.add(paragraphPhone);
			document.add(paragraphAddress);
			document.add(paragraphBankAccount);
			document.add(paragraphDate);
			document.add(lineBreak);
			document.add(paragraphHeaderProducts);
			document.add(lineBreak);
			document.add(tablaLineItem);
			document.add(lineBreak);
			document.add(paragraphTotalPrice);
			document.add(paragraphNumProducts);
			document.close();
			writer.close();
		} catch (DocumentException | IOException de) {
	      System.err.println(de.getMessage());
	    }
		return namePDF;
	}
}
