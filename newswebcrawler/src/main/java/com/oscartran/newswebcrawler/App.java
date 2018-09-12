package com.oscartran.newswebcrawler;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hello world!
 */
public final class App {
   
    private void getArticles(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            String emailContent = "";
            emailContent += "Title: " + doc.title() + "\n";
            emailContent += "--------------------" + "\n";

            Elements elements = doc.select("h4[class=\"story__heading\"]");
            for (Element e : elements) {
                emailContent += e.text() + "\n";
                emailContent += e.select("a[href]").first().attr("abs:href") + "\n";
            }
            sendEmail(emailContent);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    private void sendEmail(String emailContent) {

        final String username = "something@gmail.com";
        final String password = "password";
        final String fromEmail = "fromemail@gmail.com";
        final String toEmail = "toemail@gmail.com";
        
        Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
          });
          
        try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(toEmail));
			message.setSubject("Hottest articles from Baomoi");
			message.setText(emailContent);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    }

    public static void main(String[] args) {
        new App().getArticles("https://baomoi.com");
    }
}
