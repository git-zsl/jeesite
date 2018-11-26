package com.thinkgem.jeesite.modules.Quartz.web;
import com.thinkgem.jeesite.modules.Quartz.service.QuartzSerivce;
import com.thinkgem.jeesite.modules.sys.service.QuartzJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "${adminPath}/quartz/quartzmanager")
public class quartzController {
    @Autowired
    private QuartzSerivce q;
    //添加一个倒计时任务
    @RequestMapping("add")
    public Object add(String name) {
        return q.addJob(name, QuartzJob.class, "测试", 20, "001");
    }

    //删除一个倒计时任务
    @RequestMapping("remove")
    public Object remove(String name) {
        return q.closeJob(name, "001");
    }

    //从数据库加载还未执行的任务（spring容器初始化的时候会自动加载）
    @RequestMapping("resume")
    public Object resume(String name) {
        q.resumeJob();
        return "OK";
    }
}
