package com.inmo.cadastro.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("$spring.mail.username")
    private String remetente;

    public void enviarEmail(String destinatario, String asunto, String mensagem) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(remetente);
            simpleMailMessage.setTo(destinatario);
            simpleMailMessage.setSubject(asunto);
            simpleMailMessage.setText(mensagem);
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
