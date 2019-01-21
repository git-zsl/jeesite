/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.attention.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.attention.entity.UserAttentionUserids;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 关注人关系DAO接口
 * @author zsl
 * @version 2019-01-20
 */
@MyBatisDao
public interface UserAttentionUseridsDao extends CrudDao<UserAttentionUserids> {
	public List<UserAttentionUserids> findByIds(@Param("attentionUserIds") String attentionUserIds);
}