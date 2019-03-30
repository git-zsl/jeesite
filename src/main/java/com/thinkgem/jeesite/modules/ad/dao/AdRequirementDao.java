/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.ad.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ad.entity.AdRequirement;

/**
 * 广告需求DAO接口
 * @author zsl
 * @version 2019-03-30
 */
@MyBatisDao
public interface AdRequirementDao extends CrudDao<AdRequirement> {
	
}