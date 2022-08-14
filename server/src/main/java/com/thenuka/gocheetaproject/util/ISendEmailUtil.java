package com.thenuka.gocheetaproject.util;

import javax.mail.MessagingException;
import java.io.IOException;

public interface ISendEmailUtil {
    void sendEmail(String toEmail, String subject, String mailBody);

    void sendEmailWithAttachment(String toEmail, String subject, String mailBody) throws MessagingException, IOException;
}
