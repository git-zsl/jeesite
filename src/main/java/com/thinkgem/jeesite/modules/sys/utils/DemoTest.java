package com.thinkgem.jeesite.modules.sys.utils;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoTest {
    public static void main(String[] args) {
        // 启动Spring 容器
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:application1.xml");
        System.out.println("initContext successfully");
    }
}
