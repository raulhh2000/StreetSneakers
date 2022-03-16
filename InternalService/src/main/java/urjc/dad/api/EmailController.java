package urjc.dad.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import urjc.dad.models.Email;
import urjc.dad.service.EmailService;

@RestController
public class EmailController {
	@Autowired
	private EmailService emailService;

	@PostMapping("/email/send")
	public ResponseEntity<String> send(@RequestBody(required=true)Email email) {
		emailService.sendEmail(email.getTo(), email.getSubject(), email.getContent());
		return ResponseEntity.ok("Se ha enviado el correo electronico");
	}
}
