package util;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import error_handling.ErrorHandler;

public class Email
{
  // for example, smtp.mailgun.org
  private final String SMTP_SERVER = "smtp.gmail.com";
  private final String USERNAME = "maxc.esg@gmail.com";
  private final String PASSWORD = "Zulu9495";
  private final String EMAIL_FROM = "maxc.esg@gmail.com";
  private final String EMAIL_TO_CC = "";
  private Message msg;
  private Session session;

  public Email(String EMAIL_TO, String EMAIL_SUBJECT, String EMAIL_TEXT)
  {
    Properties prop = new Properties();
    prop.put("mail.smtp.host", SMTP_SERVER);
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        session = Session.getInstance(prop,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            });
    msg = new MimeMessage(session);

    try
    {

      // from
      msg.setFrom(new InternetAddress(EMAIL_FROM));

      // to
      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO, false));

      // cc
      msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(EMAIL_TO_CC, false));

      // subject
      msg.setSubject(EMAIL_SUBJECT);

      // content
      msg.setText(EMAIL_TEXT);

      msg.setSentDate(new Date());

    }
    catch (MessagingException e)
    {
      e.printStackTrace();
      ErrorHandler.addError(e);
    }
  }

  public boolean send()
  {
    try
    {
      Transport.send(msg);
    }
    catch (MessagingException e)
    {
      e.printStackTrace();
      ErrorHandler.addError(e);
      return false;
    }

    return true;
  }
}
