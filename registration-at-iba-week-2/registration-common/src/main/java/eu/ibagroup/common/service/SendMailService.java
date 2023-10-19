package eu.ibagroup.common.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class SendMailService {

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${registration.web.url}")
    String url;

    @Async
    public void sendNotification(@NonNull String uuid, String email) throws MailException, MessagingException {
        MimeMessage message = createConfirmationMessage(uuid, email);
        javaMailSender.send(message);
    }

    private MimeMessage createConfirmationMessage(String uuid, String email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        String link = url + uuid;
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("iba.registration.bot@ibagroup.eu");
        helper.setTo(email);
        helper.setSubject("Please confirm your email for IBA Registration Bot");
        helper.setText("""
        <html>
        Please click the link below to confirm your email for IBA Registration Bot
        <br />       
        <a href="%s">%s</a>
        <br />
        Note, if you not requested this email, simply ignore.
        </html>
        """.formatted(link, link), true);
        return message;
    }

    @Async
    public void sendNotificationSuccess(String email) throws MailException, MessagingException {
        MimeMessage message = createSuccessfulConfirmationMessage(email);
        javaMailSender.send(message);
    }

    private MimeMessage createSuccessfulConfirmationMessage(String email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("iba.registration.bot@ibagroup.eu");
        helper.setTo(email);
        helper.setSubject("You confirmed your email for IBA Registration Bot");
        helper.setText("""
        <html>
        You successfully confirmed your email for IBA Registration Bot
        </html>
        """, true);
        return message;
    }
}
