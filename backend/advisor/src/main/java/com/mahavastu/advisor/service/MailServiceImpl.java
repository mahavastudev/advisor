package com.mahavastu.advisor.service;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahavastu.advisor.repository.AdvisorAppMetadataRepositroy;
import com.mahavastu.advisor.repository.AdvisorRepository;

@Service
public class MailServiceImpl implements MailService
{
    @Autowired
    private AdvisorAppMetadataRepositroy advisorAppMetadataRepositroy;
    
    @Autowired
    private AdvisorRepository advisorRepository;

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
    
    @Override
    public void sendAdviceWithPdfMail(String subject, String content, String filePath, String to)
    {
        Session session = getSession();

        MimeMessage message = new MimeMessage(session);

        try
        {

            message.setFrom(new InternetAddress(FROM));

            message.addRecipients(Message.RecipientType.TO, to);

            message.setSubject(subject);
            message.setText(content);
            
            BodyPart messageBodyPart = new MimeBodyPart(); 
            messageBodyPart.setText(content);
            
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(filePath));
            
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);
            
            message.setContent(multipart);
            
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
        catch (IOException e)
        {
            e.printStackTrace();
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
