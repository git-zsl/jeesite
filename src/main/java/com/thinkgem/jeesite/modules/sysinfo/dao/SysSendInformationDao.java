/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sysinfo.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sysinfo.entity.SysSendInformation;

/**
 * 系统信息DAO接口
 * @author zsl
 * @version 2019-04-02
 */
@MyBatisDao
public interface SysSendInformationDao extends CrudDao<SysSendInformation> {
	
}