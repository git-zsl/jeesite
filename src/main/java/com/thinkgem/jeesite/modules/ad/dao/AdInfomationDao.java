/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ad.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ad.entity.AdInfomation;

import java.util.List;

/**
 * 广告信息DAO接口
 * @author zsl
 * @version 2019-02-23
 */
@MyBatisDao
public interface AdInfomationDao extends TreeDao<AdInfomation> {

    public List<AdInfomation> findByCategoryAndWinId(AdInfomation adInfomation);

    public List<AdInfomation> findByArticleId(AdInfomation adInfomation);
}