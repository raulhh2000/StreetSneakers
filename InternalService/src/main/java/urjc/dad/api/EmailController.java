package urjc.dad.api;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import urjc.dad.models.Email;
import urjc.dad.service.EmailService;
import urjc.dad.service.PDFService;

@RestController
public class EmailController {
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PDFService pdfService;

	@PostMapping("/email/send")
	public ResponseEntity<String> send(@RequestBody(required=true)Email email) {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(() -> emailService.sendEmail(email.getTo(), email.getSubject(), email.getContent()));
		executor.shutdown();
		return ResponseEntity.ok("Se ha enviado el correo electronico");
	}
	
	@PostMapping("/email/sendPDF")
	public ResponseEntity<String> sendPDF(@RequestBody(required=true)Email email){
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(() -> {
			String namePDF = pdfService.createPdf(email.getDataPurchase());
			emailService.sendEmail(email.getTo(), email.getSubject(), email.getContent(), namePDF);
		});
		executor.shutdown();
		return ResponseEntity.ok("Se ha enviado el correo electronico");
	}
}
