/*
package com.thinkgem.jeesite.modules.sys.utils;


import com.hanhui.ly.po.Result;
import com.hanhui.ly.po.User;
import com.hanhui.ly.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
@RestController
@RequestMapping("/send")
public class SendController {
    public static final String SMTPSERVER = "smtp.163.com";
    public static final String SMTPPORT = "465";
    public static final String ACCOUT = "liyuhuyu2018@163.com";
    public static final String PWD = "Hanhui1234";
    @Autowired
    private UserService userService;

    @RequestMapping("/mail")
    public Object sendMail(User user) {
        // 创建邮件配置
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", SMTPSERVER); // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.port", SMTPPORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
        props.setProperty("mail.smtp.ssl.enable", "true");// 开启ssl

        try{
            if(user !=null
                    && StringUtils.isNotBlank(user.getName())
                    && StringUtils.isNotBlank(user.getAddr())
                    && StringUtils.isNotBlank(user.getManageCategory())
                    && StringUtils.isNotBlank(user.getPhone())
                    && StringUtils.isNotBlank(user.getStoreDescribe())
                    && StringUtils.isNotBlank(user.getStoreName())
                    && StringUtils.isNotBlank(user.getWeixin())
                    ){
                userService.insertUser(user);
            }else {
                return new Result(false,"输入信息格式不对");
            }
        // 根据邮件配置创建会话，注意session别导错包
        Session session = Session.getDefaultInstance(props);
        // 开启debug模式，可以看到更多详细的输入日志
        session.setDebug(true);
        //创建邮件
        MimeMessage message = createEmail(session,user);
        //获取传输通道
        Transport transport = session.getTransport();
        transport.connect(SMTPSERVER,ACCOUT, PWD);
        //连接，并发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"服务器忙，请稍后再试");
        }
        return  new Result(true,"提交成功");
    }
    public  MimeMessage createEmail(Session session,User user) throws Exception {
        // 根据会话创建邮件
        MimeMessage msg = new MimeMessage(session);
        // address邮件地址, personal邮件昵称, charset编码方式
        InternetAddress fromAddress = new InternetAddress(ACCOUT,
                "商务申请合作", "utf-8");
        // 设置发送邮件方
        msg.setFrom(fromAddress);
        InternetAddress receiveAddress = new InternetAddress(
                "18825163563@163.com", "test", "utf-8");
        // 设置邮件接收方
        msg.setRecipient(MimeMessage.RecipientType.TO, receiveAddress);

        // 设置邮件标题
        msg.setSubject("商务申请合作信息", "utf-8");
        msg.setContent("<ul>" +
                " <li>姓名 : "+user.getName()+"</li>" +
                " <li>手机 : "+user.getPhone()+"</li>" +
                " <li>微信 : "+user.getWeixin()+"</li>" +
                " <li>地址 : "+user.getAddr()+"</li>" +
                " <li>店铺名称 : "+user.getStoreName()+"</li>" +
                " <li>经营类别 : "+user.getManageCategory()+"</li>" +
                " <li>店铺描述 : "+user.getStoreDescribe()+"</li>" +
                "<ul><br/>","text/html;charset=utf-8");
        // 设置显示的发件时间
        msg.setSentDate(new Date());
        // 保存设置
        msg.saveChanges();
        return msg;
    }
}*/
