/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.crn.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.crn.entity.UserCategoryNum;
import org.apache.ibatis.annotations.Param;

/**
 * 栏目授权DAO接口
 * @author zsl
 * @version 2018-11-28
 */
@MyBatisDao
public interface UserCategoryNumDao extends CrudDao<UserCategoryNum> {

	public UserCategoryNum findByCategoryIdAndUserId(UserCategoryNum u);

}