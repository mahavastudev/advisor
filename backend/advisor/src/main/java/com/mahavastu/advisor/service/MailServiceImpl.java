package com.mahavastu.advisor.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahavastu.advisor.repository.AdvisorAppMetadataRepositroy;

@Service
public class MailServiceImpl implements MailService
{
    @Autowired
    private AdvisorAppMetadataRepositroy advisorAppMetadataRepositroy;

    private static final String FROM = "developer@mahavastu.com";
    private static final String USERNAME = "developer@mahavastu.com";
    private static final String PASSWORD = "Webhost@999";

    private static final String HOST = "smtp.gmail.com";

    @Override
    public void sendQueryRegisteredMail(String subject, String content)
    {
        if (!Boolean.valueOf(advisorAppMetadataRepositroy.findByPropertyKey("SEND_EMAIL_ENABLE").getPropertyValue()))
        {
            return;
        }

        String to = advisorAppMetadataRepositroy.findByPropertyKey("QUERY_REGISTERED_SUBSCRIBERS").getPropertyValue();
        Session session = getSession();

        MimeMessage message = new MimeMessage(session);

        try
        {

            message.setFrom(new InternetAddress(FROM));

            // To get the array of addresses
            message.addRecipients(Message.RecipientType.TO, to);

            message.setSubject(subject);
            message.setText(content);

            Transport transport = session.getTransport("smtp");
            transport.connect(HOST, FROM, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("Mail sent");

        }
        catch (AddressException ae)
        {
            ae.printStackTrace();
        }
        catch (MessagingException me)
        {
            me.printStackTrace();
        }

    }

    private Session getSession()
    {

        Properties props = System.getProperties();
        String host = "smtp.gmail.com";

        props.put("mail.smtp.starttls.enable", "true");

        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.user", USERNAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        return Session.getDefaultInstance(props);
    }

}
