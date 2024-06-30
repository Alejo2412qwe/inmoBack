package com.inmo.cadastro.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Value("${spring.mail.username}")
    private String destinatario;

    @PostMapping("/enviar")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            emailService.enviarEmail(destinatario, "Pagamento De Aluguel.", emailRequest.getMensagem());
            return "Email enviado con éxito";
        } catch (Exception e) {
            return "Error al enviar el email: " + e.getMessage();
        }
    }
}
