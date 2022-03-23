package urjc.dad.service;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.alignment.VerticalAlignment;
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
			Font font2 = new Font(Font.HELVETICA, 16, Font.ITALIC, Color.BLACK);
		
			Font font3 = new Font(Font.HELVETICA, 16, Font.ITALIC, new Color(230,88,61));
			Font font4 = new Font(Font.HELVETICA, 16, Font.BOLDITALIC, Color.WHITE);
			document.open();
			
			PdfPTable table = new PdfPTable(1); 
			
			PdfPCell cell = new PdfPCell(new Phrase("FACTURA SIMPLIFICADA", font4));
		    cell.setVerticalAlignment(Element.ALIGN_CENTER);
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell.setBackgroundColor(new Color(45,62,80));
			
		    table.addCell(cell);
			
			Paragraph paragraph0 = new Paragraph("DATOS DEL CLIENTE: ", font);
			Paragraph paragraph12 = new Paragraph(" ", font);
			
			Paragraph paragraph = new Paragraph();
			paragraph.add(new Phrase("Nombre: ",font));
			paragraph.add(new Phrase(dataPurchase.getName(), font2));
			
			
			Paragraph paragraph1 = new Paragraph();
			paragraph1.add(new Phrase("Apellido: " ,font));
			paragraph1.add(new Phrase(dataPurchase.getLastName(), font2));
			
			Paragraph paragraph2 = new Paragraph();
			paragraph2.add(new Phrase("Correo electronico: ",font));
			paragraph2.add(new Phrase(dataPurchase.getEmail(), font2));
			
			Paragraph paragraph3 = new Paragraph();
			paragraph3.add(new Phrase("Telefono: ",font));
			paragraph3.add(new Phrase(dataPurchase.getPhone(), font2));
			
			Paragraph paragraph4 = new Paragraph();
			paragraph4.add(new Phrase("Dirección: ",font));
			paragraph4.add(new Phrase(dataPurchase.getAddress(), font2));
			
			Paragraph paragraph5 = new Paragraph();
			paragraph5.add(new Phrase("Cuenta Bancaria: ",font));
			paragraph5.add(new Phrase(dataPurchase.getBankAccount(), font2));
			
			Paragraph paragraph6 = new Paragraph();
			paragraph6.add(new Phrase("Fecha del pedido: ",font));
			paragraph6.add(new Phrase(dataPurchase.getDate(), font2));
			
			Paragraph paragraph7 = new Paragraph();
			paragraph7.add(new Phrase("Precio total: ",font));
			paragraph7.add(new Phrase(Double.toString(dataPurchase.getTotalPrice())+"€", font3));
			
			
			Paragraph paragraph8 = new Paragraph();
			paragraph8.add(new Phrase("Número de productos: ",font));
			paragraph8.add(new Phrase(Integer.toString(dataPurchase.getNumProducts()), font2));
			
			
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
		    	tablaLineItem.addCell(Double.toString(item.getPrice())+"€");
		      }
		    document.add(table);
		    document.add(paragraph12);
		    document.add(paragraph0);
		    document.add(paragraph12);
			document.add(paragraph);
			document.add(paragraph1);
			document.add(paragraph2);
			document.add(paragraph3);
			document.add(paragraph4);
			document.add(paragraph5);
			document.add(paragraph6);
			document.add(paragraph9);
			document.add(paragraph10);
			document.add(paragraph11);
			document.add(tablaLineItem);
			document.add(paragraph12);
			document.add(paragraph8);
			document.add(paragraph7);
			document.close();
			writer.close();
		} catch (DocumentException | IOException de) {
	      System.err.println(de.getMessage());
	    }
		return namePDF;
	}
}
