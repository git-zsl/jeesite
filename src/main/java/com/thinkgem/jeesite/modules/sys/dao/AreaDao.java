/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;

import java.util.List;

/**
 * 区域DAO接口
 * @author ThinkGem
 * @version
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {

    public List<Area> findChildArea(Area area);

    public List<Area> findTopArea();
}
