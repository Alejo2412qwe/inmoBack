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
    public void checkDatesExpiration() {

        List<String> mails = usuarioRepository.getMailsOfAdmins();
        List<Aluguel> aluguels = aluguelRepository.findAll();
        Date currentDate = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MONTH, 1);
        Date oneMonthLater = cal.getTime();

        Set<String> sentEmails = new HashSet<>();

        for (Aluguel aluguel : aluguels) {
            if (isWithinOneMonth(aluguel.getAluExpiracao(), currentDate, oneMonthLater) && aluguel.getAluEstado() == 1) {
                for (String mail : mails) {
                    if (!sentEmails.contains(mail)) {
                        emailService.enviarEmail(mail, "Expiração Do Contrato", "Olá administrador, falta 1 mês para expirar o contrato do " + aluguel.getAluInquilino().getUsuPerId().getPerNombre() + " " + aluguel.getAluInquilino().getUsuPerId().getPerApellido());
                        sentEmails.add(mail);
                    }
                }
            }
        }
    }

    @Scheduled(fixedDelay = 120000)
    //@Scheduled(cron = "0 0 21 * * ?")
    public void checkPaymentDueDates() {

        List<Aluguel> aluguels = aluguelRepository.findAll();
        Date currentDate = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DAY_OF_MONTH, 15);
        Date fifteenDaysLater = cal.getTime();

        Set<String> sentEmails = new HashSet<>();

        for (Aluguel aluguel : aluguels) {
            aluguel.setAluComprovante("");
            if (isWithinFifteenDays(aluguel.getAluDiaPago(), currentDate, fifteenDaysLater)) {
                String email = aluguel.getAluInquilino().getUsuCorreo();
                if (!sentEmails.contains(email)) {
                    emailService.enviarEmail(
                            email,
                            "Próximo Dia de Pagamento",
                            "Olá " + aluguel.getAluInquilino().getUsuPerId().getPerNombre() + " " + aluguel.getAluInquilino().getUsuPerId().getPerApellido() + ", está perto do seu dia de pagamento, lembre-se de enviar seu comprovante de pagamento."
                    );
                    sentEmails.add(email);
                }
            }
        }
    }


    private boolean isWithinFifteenDays(int diaPago, Date currentDate, Date fifteenDaysLater) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        int currentDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);

        cal.setTime(fifteenDaysLater);
        int fifteenDaysLaterMonth = cal.get(Calendar.MONTH);
        int fifteenDaysLaterYear = cal.get(Calendar.YEAR);

        for (int i = 0; i < 15; i++) {
            cal.set(currentYear, currentMonth, currentDayOfMonth + i);
            if (cal.get(Calendar.DAY_OF_MONTH) == diaPago &&
                    cal.get(Calendar.MONTH) == currentMonth &&
                    cal.get(Calendar.YEAR) == currentYear) {
                return true;
            }
            if (cal.get(Calendar.DAY_OF_MONTH) == diaPago &&
                    cal.get(Calendar.MONTH) == fifteenDaysLaterMonth &&
                    cal.get(Calendar.YEAR) == fifteenDaysLaterYear) {
                return true;
            }
        }

        return false;
    }

    private boolean isWithinOneMonth(Date date, Date currentDate, Date oneMonthLater) {
        return date.after(currentDate) && date.before(oneMonthLater);
    }
}
