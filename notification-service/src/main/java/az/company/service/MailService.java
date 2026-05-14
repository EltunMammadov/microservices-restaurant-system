package az.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    public void sendMail(String text) {
        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("mmmdoveltun19@gmail.com");
        simpleMailMessage.setTo("mmmdoveltun19@gmail.com");
        simpleMailMessage.setSubject("Order Notification");
        simpleMailMessage.setText(text);

        mailSender.send(simpleMailMessage);


    }
}
