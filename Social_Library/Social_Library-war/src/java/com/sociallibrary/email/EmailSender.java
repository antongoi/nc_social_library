/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sociallibrary.email;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 *
 * @author Костя
 */

///Class for sending eny kind of email to group of users with static method sendEmail

public class EmailSender {

    public static void sendEmail(String emails [], String subject, String body) throws MessagingException
    {
        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.host", "smtp.gmail.com");
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.port", "465");
        mailProps.put("mail.smtp.socketFactory.port", "465");
        mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailProps.put("mail.smtp.socketFactory.fallback", "false");
        Session mailSession = Session.getInstance(mailProps, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return (new PasswordAuthentication("nc.social.library", "soclib2014"));
            }
        });

        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress("nc.social.library@gmail.com"));
        InternetAddress dests[] = new InternetAddress[emails.length];
        for (int i = 0; i < emails.length; i++) {
            dests[i] = new InternetAddress(emails[i].trim().toLowerCase());
        }
        message.setRecipients(Message.RecipientType.TO, dests);
        message.setSubject(subject, "KOI8-R");
        Multipart mp = new MimeMultipart();
        MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText(body, "KOI8-R");
        mp.addBodyPart(mbp1);
        message.setContent(mp);
        message.setSentDate(new java.util.Date());
        Transport.send(message);
    }
}
