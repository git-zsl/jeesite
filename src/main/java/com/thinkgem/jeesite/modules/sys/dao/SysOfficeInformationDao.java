/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysOfficeInformation;

/**
 * 机构详细信息DAO接口
 * @author zsl
 * @version 2019-01-06
 */
@MyBatisDao
public interface SysOfficeInformationDao extends CrudDao<SysOfficeInformation> {
	
}