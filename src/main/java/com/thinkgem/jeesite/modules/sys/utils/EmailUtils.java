package com.thinkgem.jeesite.modules.sys.utils;

import com.thinkgem.jeesite.modules.sys.entity.Email;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

public class EmailUtils {
    /**
     * 进行base64加密，防止中文乱码
     */
    private static Class clazz = EmailUtils.class;
    private static String changeEncode(String str) {
        try {
            // "B"代表Base64
            str = MimeUtility.encodeText(new String(str.getBytes(), "UTF-8"), "UTF-8", "B");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static boolean sendHtmlMail(Email email) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", email.getSmtpServer());
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // 使用JSSE的SSL
        properties.put("mail.smtp.socketFactory.fallback", "false"); // 只处理SSL的连接,对于非SSL的连接不做处理
        properties.put("mail.smtp.port", email.getPort());
        properties.put("mail.smtp.socketFactory.port", email.getPort());
        Session session = Session.getInstance(properties);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);

        try {
            // 发件人
            Address address = new InternetAddress(email.getFromUserName());
            message.setFrom(address);
            // 收件人
            Address toAddress = new InternetAddress(email.getToUser());
            message.setRecipient(MimeMessage.RecipientType.TO, toAddress); // 设置收件人,并设置其接收类型为TO

            // 主题message.setSubject(changeEncode(emailInfo.getSubject()));
            message.setSubject(email.getSubject());
            // 时间
            message.setSentDate(new Date());

            Multipart multipart = new MimeMultipart();

            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(email.getContent(), "text/html; charset=utf-8");
            multipart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容
            message.setContent(multipart);
            message.saveChanges();
            StringBuffer sb = new StringBuffer();
            sb.append(" SMTP服务器地址:"+email.getSmtpServer())
                    .append("端口:"+email.getPort())
                    .append("发送人邮箱地址:"+email.getFromUserName())
                    .append("登录SMTP服务器的密码:"+email.getFromUserPassword())
                    .append("收件人"+email.getToUser())
                    .append("邮件主题"+email.getSubject())
                    .append("邮件正文"+email.getContent());
            LogUtils.getLogInfo(clazz).info(sb.toString());
            LogUtils.getLogInfo(clazz).info("发送邮件成功");
        } catch (Exception e) {
            LogUtils.getLogInfo(clazz).info("发邮件失败",e);
            e.printStackTrace();
            return false;
        }

        try {
            Transport transport = session.getTransport("smtp");
            transport.connect(email.getSmtpServer(), email.getFromUserName(), email.getFromUserPassword());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void unGzipFile(String sourcedir) {
        String ouputfile = "F:\\config";
        try {
            //建立gzip压缩文件输入流
            FileInputStream fin = new FileInputStream(sourcedir);
            //建立gzip解压工作流
            GZIPInputStream gzin = new GZIPInputStream(fin);
            //建立解压文件输出流
            ouputfile = sourcedir.substring(0,sourcedir.lastIndexOf('.'));
            ouputfile = ouputfile.substring(0,ouputfile.lastIndexOf('.'));
            FileOutputStream fout = new FileOutputStream(ouputfile);

            int num;
            byte[] buf=new byte[1024];
            while ((num = gzin.read(buf,0,buf.length)) != -1)
            {
                fout.write(buf,0,num);
            }
            gzin.close();
            fout.close();
            fin.close();
        } catch (Exception ex){
            System.err.println(ex.toString());
        }
        return;
    }





    public static void main(String[] args) {
       /* String content = "这是一个测试邮件内容%s";
        content = content.format(content, "111", "222");
        System.out.println(content);
        Email info = new Email("328875024@qq.com", "这是测试标题", "<p>这是一个测试邮件</p>");
        sendHtmlMail(info);*/
        String filePath = "F:\\config.tar.gz";
        unGzipFile(filePath);
    }
}
