/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wf.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.wf.entity.UserArticleLikeCollect;

import java.util.List;

/**
 * 用户文章点赞收藏用户管理DAO接口
 * @author zsl
 * @version 2019-04-24
 */
@MyBatisDao
public interface UserArticleLikeCollectDao extends CrudDao<UserArticleLikeCollect> {

    public UserArticleLikeCollect findByUserIdAndArticleId(UserArticleLikeCollect userArticleLikeCollect);
}