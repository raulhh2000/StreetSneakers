package urjc.dad.service;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
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
			Font font = new Font(Font.HELVETICA, 16, Font.BOLDITALIC, Color.BLACK);
			document.open();
			Paragraph paragraph0 = new Paragraph("DATOS DEL PEDIDO: ", font);
			Paragraph paragraph12 = new Paragraph(" ", font);
			Paragraph paragraph = new Paragraph("Nombre: " + dataPurchase.getName(), font);
			Paragraph paragraph1 = new Paragraph("Apellido: " + dataPurchase.getLastName(), font);
			Paragraph paragraph2 = new Paragraph("Correo electronico: " + dataPurchase.getEmail(), font);
			Paragraph paragraph3 = new Paragraph("Telefono: " + dataPurchase.getPhone(), font);
			Paragraph paragraph4 = new Paragraph("Dirección: " + dataPurchase.getAddress(), font);
			Paragraph paragraph5 = new Paragraph("Cuenta Bancaria: " + dataPurchase.getBankAccount(), font);
			Paragraph paragraph6 = new Paragraph("Fecha del pedido: " + dataPurchase.getDate(), font);
			Paragraph paragraph7 = new Paragraph("Precio total: " + dataPurchase.getTotalPrice(), font);
			Paragraph paragraph8 = new Paragraph("Número de productos: " + dataPurchase.getNumProducts(), font);
			Paragraph paragraph9 = new Paragraph(" ", font);
			Paragraph paragraph10 = new Paragraph("PRODUCTOS: ", font);
			Paragraph paragraph11 = new Paragraph(" ", font);
			PdfPTable tablaLineItem = new PdfPTable(4);
			tablaLineItem.addCell(new Phrase("Nombre", font));
			tablaLineItem.addCell(new Phrase("Marca", font));
			tablaLineItem.addCell(new Phrase("Talla", font));          
			tablaLineItem.addCell(new Phrase("Precio", font));
		      for(LineItem item : dataPurchase.getLineItems()) {
		    	tablaLineItem.addCell(item.getName());
		    	tablaLineItem.addCell(item.getBrand());
		    	tablaLineItem.addCell(Double.toString(item.getSize()));
		    	tablaLineItem.addCell(Double.toString(item.getPrice()));
		      }
		    document.add(paragraph0);
		    document.add(paragraph12);
			document.add(paragraph);
			document.add(paragraph1);
			document.add(paragraph2);
			document.add(paragraph3);
			document.add(paragraph4);
			document.add(paragraph5);
			document.add(paragraph6);
			document.add(paragraph7);
			document.add(paragraph8);
			document.add(paragraph9);
			document.add(paragraph10);
			document.add(paragraph11);
			document.add(tablaLineItem);
			document.close();
			writer.close();
		} catch (DocumentException | IOException de) {
	      System.err.println(de.getMessage());
	    }
		return namePDF;
	}
}
