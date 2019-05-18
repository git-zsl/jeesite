package com.thinkgem.jeesite.modules.cms.entity;

import com.thinkgem.jeesite.common.config.Global;

public class NumberVo {

    private String articleNum;
    private String attentionNum;
    private String fansNum;

    public NumberVo(){
        this.articleNum = Global.NO;
        this.attentionNum = Global.NO;
        this.fansNum = Global.NO;
    }
    public String getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(String articleNum) {
        this.articleNum = articleNum;
    }

    public String getAttentionNum() {
        return attentionNum;
    }

    public void setAttentionNum(String attentionNum) {
        this.attentionNum = attentionNum;
    }

    public String getFansNum() {
        return fansNum;
    }

    public void setFansNum(String fansNum) {
        this.fansNum = fansNum;
    }
}
