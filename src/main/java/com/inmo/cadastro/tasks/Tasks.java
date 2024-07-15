package com.inmo.cadastro.tasks;

import com.inmo.cadastro.email.EmailService;
import com.inmo.cadastro.models.Aluguel;
import com.inmo.cadastro.models.Comprovante;
import com.inmo.cadastro.repository.AluguelRepository;
import com.inmo.cadastro.repository.ComprovanteRepository;
import com.inmo.cadastro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Tasks {

    @Autowired
    private AluguelRepository aluguelRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ComprovanteRepository comprovanteRepository;
    @Autowired
    private EmailService emailService;


    @Scheduled(fixedRate = 432000000)
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
            if (aluguel.getAluEstado() == 1) {
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
    }

    private boolean isWithinOneMonth(Date date, Date currentDate, Date oneMonthLater) {
        return date.after(currentDate) && date.before(oneMonthLater);
    }

    //@Scheduled(fixedDelay = 120000)
    @Scheduled(cron = "0 0 21 * * ?")
    public void checkPaymentDueDates() {
        List<Aluguel> aluguels = aluguelRepository.findAll();
        Date currentDate = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DAY_OF_MONTH, 5);
        Date fiveDaysLater = cal.getTime();

        Set<String> sentEmails = new HashSet<>();

        for (Aluguel aluguel : aluguels) {
            if (isWithinFiveDays(aluguel.getAluDiaPago(), currentDate, fiveDaysLater)) {
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

    private boolean isWithinFiveDays(int diaPago, Date currentDate, Date fiveDaysLater) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        int currentDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);

        cal.setTime(fiveDaysLater);
        int fiveDaysLaterMonth = cal.get(Calendar.MONTH);
        int fiveDaysLaterYear = cal.get(Calendar.YEAR);

        for (int i = 0; i < 5; i++) {
            cal.set(currentYear, currentMonth, currentDayOfMonth + i);
            if (cal.get(Calendar.DAY_OF_MONTH) == diaPago &&
                    cal.get(Calendar.MONTH) == currentMonth &&
                    cal.get(Calendar.YEAR) == currentYear) {
                return true;
            }
            if (cal.get(Calendar.DAY_OF_MONTH) == diaPago &&
                    cal.get(Calendar.MONTH) == fiveDaysLaterMonth &&
                    cal.get(Calendar.YEAR) == fiveDaysLaterYear) {
                return true;
            }
        }

        return false;
    }

    @Scheduled(fixedDelay = 120000)
    //@Scheduled(cron = "0 0 21 * * ?")
    public void checkOverduePayments() {
        List<String> mails = usuarioRepository.getMailsOfAdmins();
        List<Aluguel> aluguels = aluguelRepository.findAll();
        Date currentDate = new Date();

        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(currentDate);
        int currentDay = currentCal.get(Calendar.DAY_OF_MONTH);
        int currentMonth = currentCal.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
        int currentYear = currentCal.get(Calendar.YEAR);

        for (Aluguel aluguel : aluguels) {
            if (aluguel.getAluEstado() == 1) {
                int paymentDay = aluguel.getAluDiaPago();

                if (paymentDay != 0 && paymentDay < currentDay) {
                    List<Comprovante> comprovantes = comprovanteRepository.findComprovantesByAluguelAndMonth(aluguel.getAluId(), currentMonth, currentYear);

                    if (comprovantes.isEmpty()) {
                        int daysOverdue = currentDay - paymentDay;

                        for (String mail : mails) {
                            String tenantName = aluguel.getAluInquilino().getUsuPerId().getPerNombre() + " " + aluguel.getAluInquilino().getUsuPerId().getPerApellido();
                            String message = "Olá administrador, o inquilino " + tenantName + " está com um atraso de " + daysOverdue + " dias no pagamento do aluguel.";
                            emailService.enviarEmail(mail, "Atraso No Pagamento Do Aluguel", message);
                        }
                    }
                }
            }
        }
    }

}
