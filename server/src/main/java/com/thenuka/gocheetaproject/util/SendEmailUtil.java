package com.thenuka.gocheetaproject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class SendEmailUtil implements ISendEmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String toEmail, String subject, String mailBody) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("thenukadev@gmail.com");
        msg.setTo(toEmail);

        msg.setSubject(subject);
        msg.setText(mailBody);

        javaMailSender.send(msg);
    }

    @Override
    public void sendEmailWithAttachment(String toEmail, String subject, String mailBody) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom("thenukadev@gmail.com");
        helper.setTo(toEmail);

        helper.setSubject(subject);

        helper.setText(mailBody, true);

        helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);

    }
}
