package com.inmo.cadastro.tasks;

import com.inmo.cadastro.email.EmailService;
import com.inmo.cadastro.repository.AluguelRepository;
import com.inmo.cadastro.repository.UsuarioRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CheckDates {

    private final AluguelRepository aluguelRepository;

    private final UsuarioRepository usuarioRepository;

    private final EmailService emailService;

    public CheckDates(AluguelRepository aluguelRepository, UsuarioRepository usuarioRepository, EmailService emailService) {
        this.aluguelRepository = aluguelRepository;
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
    }

    //@Scheduled(fixedDelay = 120000)
    //@Scheduled(cron = "0 0 21 * * ?")
    public void checkDates() {
        List<Date> dates = aluguelRepository.getDates();
        List<String> mails = usuarioRepository.getMailsOfAdmins();
        Date currentDate = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MONTH, 1);
        Date oneMonthLater = cal.getTime();

        Set<String> sentEmails = new HashSet<>();

        for (Date date : dates) {
            if (isWithinOneMonth(date, currentDate, oneMonthLater)) {
                for (String mail : mails) {
                    if (!sentEmails.contains(mail)) {
                        emailService.enviarEmail(mail, "AVISO", "Olá administrador, verifique os aluguéis, há um aluguel cujo contrato está prestes a expirar.");
                        sentEmails.add(mail);
                    }
                }
            }
        }
    }

    private boolean isWithinOneMonth(Date date, Date currentDate, Date oneMonthLater) {
        return date.after(currentDate) && date.before(oneMonthLater);
    }
}
