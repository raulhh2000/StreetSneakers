package urjc.dad.service;

import java.awt.Color;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;

import urjc.dad.models.DataPurchase;

@Service
public class PDFService {
	public Document createPdf (DataPurchase dataPurchase) {
		Document document = new Document();
		Font font = new Font(Font.HELVETICA, 16, Font.BOLDITALIC, Color.RED);
		document.open();
		Paragraph paragraph = new Paragraph(dataPurchase.toString(), font);
		document.add(paragraph);
		document.close();
		return document;
	}
}
