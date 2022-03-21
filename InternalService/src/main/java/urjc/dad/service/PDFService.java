package urjc.dad.service;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import urjc.dad.models.DataPurchase;

@Service
public class PDFService {
	public String createPdf (DataPurchase dataPurchase) {
		String namePDF = dataPurchase.getDate().replace(" ", "").replace(":", "_") + ".pdf";
		try {
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(namePDF));
			Font font = new Font(Font.HELVETICA, 16, Font.BOLDITALIC, Color.RED);
			document.open();
			Paragraph paragraph = new Paragraph(dataPurchase.toString(), font);
			document.add(paragraph);
			document.close();
			writer.close();
		} catch (DocumentException | IOException de) {
	      System.err.println(de.getMessage());
	    }
		return namePDF;
	}
}
