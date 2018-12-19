/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.posts.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.posts.entity.CmsPosts;

import java.util.List;

/**
 * 分类DAO接口
 * @author zsl
 * @version 2018-12-19
 */
@MyBatisDao
public interface CmsPostsDao extends CrudDao<CmsPosts> {

    public List<CmsPosts> findPosts(CmsPosts cmsPosts);
}