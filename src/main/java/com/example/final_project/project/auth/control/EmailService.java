package com.example.final_project.project.auth.control;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
@Transactional
public class EmailService {
    JavaMailSender emailSender;
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendMailWithAttachment(String toEmail, String body, String subject, String attachment) {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        {
            try {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setFrom("from");
                mimeMessageHelper.setTo(toEmail);
                mimeMessageHelper.setText(body);
                mimeMessageHelper.setSubject(subject);
                FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));
                mimeMessageHelper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()), fileSystemResource);
                emailSender.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
//    public EmailService(JavaMailSender emailSender) {
//        this.emailSender = emailSender;
//    }
//    public void sendMessage(String to, String subject, String text) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("email");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        this.emailSender.send(message);
//    }
}
