package net.canway.meeting_message;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class email {
    public static String myEmailAccount = "eddie_yi@163.com";
    public static String myEmailPassword = "liuyi591521";
    public static String myEmailSMTPHost = "smtp.163.com";
    public static String receiveMailAccount = "liuyi779461764@qq.com";
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", myEmailSMTPHost);
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props);
        session.setDebug(true);
        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount);
        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(sendMail, "eddie", "UTF-8"));

        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "liuyi", "UTF-8"));

        message.setSubject("主题", "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent("邮件正文", "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
}
