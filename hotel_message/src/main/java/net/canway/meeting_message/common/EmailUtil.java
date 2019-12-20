package net.canway.meeting_message.common;

import net.canway.meeting_message.model.Result;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

@Component
public class EmailUtil {
    public static String myEmailAccount = "eddie_yi@163.com";
    public static String myEmailPassword = "liuyi591521";
    public static String myEmailSMTPHost = "smtp.163.com";
   // public static String receiveMailAccount = "liuyi779461764@qq.com";

    public static Result createMimeMessage(String receiveMail,String code) throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", myEmailSMTPHost);
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myEmailAccount, "eddie", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "test", "UTF-8"));
        message.setSubject("找回密码", "UTF-8");
        message.setContent("验证码："+code+",请勿泄露验证码", "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        return new Result("发送成功","200",null);
    }

    public String code(){
        Random integ = new Random();
        int i = integ.nextInt(10000);
        return Integer.toString(i);
    }
}
