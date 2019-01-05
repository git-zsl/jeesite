/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构DAO接口
 * @author ThinkGem
 * @version
 * */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	/**
	 * 查找部门名称
	 * @param office
	 * @return
	 */
	public List<Office> findListByName(Office office);
	/**
	 * 查找公司名称
	 * @param office
	 * @return
	 */
	public List<Office> findCompanyListByName(Office office);
}
