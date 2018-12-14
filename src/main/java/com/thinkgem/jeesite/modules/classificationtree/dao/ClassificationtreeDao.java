/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classificationtree.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.classificationtree.entity.Classificationtree;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 分类DAO接口
 * @author zsl
 * @version 2018-12-08
 */
@MyBatisDao
public interface ClassificationtreeDao extends TreeDao<Classificationtree> {

	public List<Classificationtree> findByFirstClassificationId(@Param("parentId") String parentId);

	public List<Map<String,Object>> findListCount();
}