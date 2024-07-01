package com.inmo.cadastro.tasks;

import com.inmo.cadastro.email.EmailService;
import com.inmo.cadastro.models.Aluguel;
import com.inmo.cadastro.repository.AluguelRepository;
import com.inmo.cadastro.repository.UsuarioRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Tasks {

    private final AluguelRepository aluguelRepository;

    private final UsuarioRepository usuarioRepository;

    private final EmailService emailService;

    public Tasks(AluguelRepository aluguelRepository, UsuarioRepository usuarioRepository, EmailService emailService) {
        this.aluguelRepository = aluguelRepository;
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
    }

    @Scheduled(fixedDelay = 120000)
    //@Scheduled(cron = "0 0 21 * * ?")
    public void checkDates() {

        List<String> mails = usuarioRepository.getMailsOfAdmins();
        List<Aluguel> aluguels = aluguelRepository.findAll();
        Date currentDate = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MONTH, 1);
        Date oneMonthLater = cal.getTime();

        Set<String> sentEmails = new HashSet<>();

        for (Aluguel aluguel : aluguels) {
            if (isWithinOneMonth(aluguel.getAluExpiracao(), currentDate, oneMonthLater)) {
                for (String mail : mails) {
                    if (!sentEmails.contains(mail)) {
                        emailService.enviarEmail(mail, "Expiração Do Contrato", "Olá administrador, falta 1 mês para expirar o contrato do " + aluguel.getAluInquilino().getUsuPerId().getPerNombre() + " " + aluguel.getAluInquilino().getUsuPerId().getPerApellido());
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
