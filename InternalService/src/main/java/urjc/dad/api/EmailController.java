package urjc.dad.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import urjc.dad.service.EmailService;

@RestController
public class EmailController {
	@Autowired
	private EmailService emailService;

	@PostMapping("/email/send")
	public ResponseEntity<String> send(@RequestBody(required=true)String to, @RequestBody(required=true)String subject, @RequestBody(required=true)String content) {
		emailService.sendEmail(to, subject, content);
		return ResponseEntity.ok("Se ha enviado el correo electronico");
	}
}
