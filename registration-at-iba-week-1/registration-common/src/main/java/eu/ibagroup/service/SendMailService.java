package eu.ibagroup.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Service to send email notifications
 *
 * @author Mikalai Zaikin (mzaikin@ibagroup.eu)
 * @since 4Q2022
 */
@Service
public class SendMailService {

    @Resource
    JavaMailSender javaMailSender;

    @Value("${registration.web.url}")
    String url;

    public void sendNotification(String uuid, String email) throws MailException, MessagingException {
        String link = url + uuid;
        MimeMessage message = createEmailMessage(email
                , "Please confirm your email for IBA Registration Bot"
                , """
                        <html>
                        Please click the link below to confirm your email for IBA Registration Bot
                        <br />
                        <a href="%s">%s</a>
                        <br />
                        Note, if you not requested this email, simply ignore.
                        </html>
                        """.formatted(link, link)
        );
        javaMailSender.send(message);
    }

    public void sendNotificationSuccess(String email) throws MailException, MessagingException {
        MimeMessage message = createEmailMessage(email
                , "You confirmed your email for IBA Registration Bot"
                , """
                        <html>
                        You successfully confirmed your email for IBA Registration Bot
                        </html>
                        """
        );
        javaMailSender.send(message);
    }

    private MimeMessage createEmailMessage(String email, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("iba.registration.bot@ibagroup.eu");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(body, true);
        return message;
    }

}
