package com.thinkgem.jeesite.modules.sys.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.thinkgem.jeesite.modules.sys.entity.Email;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.SimpleFormatter;
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


    public static String setEmailPage(String userName,String url){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss日");
        String systemTime = format.format(new Date());
       // String contentData = "USEIDEA响创意立足华语创意圈，以记录、传播创造者的闪现灵感，发掘精准洞见为主旨。现诚意邀你合力营造独属创造者的创意平台，一同雕刻创造人的生长轨迹，传播让社会更美好的BIG IDEA。";
        String contentData = "USEIDEA响创意，记录创造者的洞见，发掘让改变发生的灵感思考。即刻出发，正式踏上灵感旅程。";
        String content ="<style type=\"text/css\"> body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px} img{ border:0}  </style>  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse; background-color: rgb(238, 238, 245); font-family: 微软雅黑, 黑体, arial, sans-serif; width:100%; height: 100%;\"> <tbody>   <tr>      <td>        <table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"width: 760px;\">          <tbody>             <tr>              <td>              <table cellpadding=\"0\" cellspacing=\"0\" style=\"width: 640px;\">                 <tbody>                   <tr style=\"line-height: 30px;\">                       <td width=\"80\" style=\"padding-top:50px;padding-left: 335px;\">                         <a href=\"http://www.useidea.com\" target=\"_blank\"；><img src=\"http://47.94.253.240:8083/zsl/userfiles/logo.png\" width=\"70\"></a>                       </td>                     </tr>                 </tbody>              </table>              </td>             </tr>               <tr>              <td height=\"30\"></td>             </tr>           <tr>              <td style=\"background-color: rgb(255, 255, 255); border-radius: 6px; padding: 40px 40px 0px;\">                <table>                   <tbody><tr height=\"35\">                   <td style=\"padding-left: 25px; padding-right: 25px; font-family: 微软雅黑, 黑体, arial;font-size:17px; \">尊敬的" +
                userName + "，您好：</td></tr><tr height=\"20\"><td></td></tr><tr height=\"50\"><td style=\"padding-left: 50px; padding-right: 50px; font-family: 微软雅黑, 黑体, arial; font-size: 15px; line-height: 20px;\"> " +
                "您于 " + systemTime +" 成功申请加入响创意 ，点击按钮即可完成开通：<br><br><br><a href=" +
                url +" style=\"display: inline-block; color: rgb(255, 255, 255); line-height: 40px; background-color: rgb(229, 0 , 18); border-radius: 5px; text-align: center; text-decoration: none; font-size: 16px; padding: 1px 30px;\">点击验证</a> </td></tr><tr height=\"80\"><td style=\"padding-left: 55px; padding-right: 55px; font-family: 微软雅黑, 黑体, arial; font-size: 14px; line-height: 25px;\">                       <div style=\"clear: both;\"><br></div>                    <div style=\"clear: both;\"><br></div>                    <div style=\"clear: both; line-height: 28px;\"> " +
                contentData + "<div style=\"clear: both;\"><br></div>                  </div>                  </td>                 </tr>                   <tr height=\"40\">                  <td style=\"padding-left: 55px; padding-right: 55px; font-family: 微软雅黑, 黑体, arial; font-size: 14px;\">                      如有其他问题或建议，请 <a href=\"https://mp.weixin.qq.com/s/mnRGbpdk7i_GIeIG37Vz3Q\" target=\"_blank\" style=\"color: rgb(12, 148, 222);\">联系主编</a>。                   </td>                 </tr>                   <tr height=\"20\">                  <td></td>                 </tr>                   <tr>                  <td style=\"padding-left: 55px; font-family: 微软雅黑, 黑体, arial; font-size: 14px;line-height: 25px;\">此致<br>响创意团队</td></tr><tr height=\"50\"><td></td></tr></tbody></table></td></tr><tr><td></td></tr><tr><td style=\"color: rgb(122, 133, 153); font-size: 12px;\" align=\"center\"><p style=\"margin-top: 18px; margin-bottom: 12px;\"><a style=\"padding: 0px 5px;\" href=\"https://mp.weixin.qq.com/s/mnRGbpdk7i_GIeIG37Vz3Q\" target=\"_blank\"><img src=\"http://47.94.253.240:8083/zsl/userfiles/wechat.png\" width=\"22\" alt=\"微信公众平台\"></a><a style=\"padding: 0px 5px;\" href=\"https://weibo.com/279077228\" target=\"_blank\"><img src=\"http://47.94.253.240:8083/zsl/userfiles/weibo.png\" width=\"22\" alt=\"\"></a></p><p style=\"line-height: 20px; margin-top: 6px; margin-bottom: 10px;\"><a style=\"text-decoration: none; color: rgb(122, 133, 153); padding: 0px 4px;\" href=\"http://www.useidea.com\" target=\"_blank\">首页</a><a style=\"text-decoration: none; color: rgb(122, 133, 153); padding: 0px 4px;\" href=\"https://shop107209379.taobao.com\" target=\"_blank\">自营书店</a><a style=\"text-decoration: none; color: rgb(122, 133, 153); padding: 0px 4px;\" href=\"https://www.zhihu.com/people/copywriter360\" target=\"_blank\">知乎</a><a style=\"text-decoration: none; color: rgb(122, 133, 153); padding: 0px 4px;\" href=\"https://www.douban.com/people/82158793/\" target=\"_blank\">豆瓣</a></p></td></tr><tr height=\"30\"><td></td></tr></tbody></table></td></tr></tbody> </table>";
       return content;
    }


public static void  aliSendMailUtil(){
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIcpHt3ountPgh", "kcRXGPpg8Pqm9VzmowpdUEbAfJARe2");
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            request.setAccountName("ideateam@mail.useidea.com");
            request.setAddressType(1);
            request.setTagName("USEIDEA");
            request.setReplyToAddress(true);
            request.setToAddress("328875024@qq.com");
            request.setSubject("阿里云邮箱推送");
            request.setHtmlBody("推送测试");
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
}

    public static void main(String[] args) {
        String content = "这是一个测试邮件内容%s";
        content = content.format(content, "111", "222");
        System.out.println(content);
        Email info = new Email("328875024@qq.com", "这是测试标题", "<p>这是一个测试邮件</p>");
        sendHtmlMail(info);
    }
}
