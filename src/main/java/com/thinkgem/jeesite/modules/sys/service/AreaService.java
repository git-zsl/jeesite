/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import com.thinkgem.jeesite.modules.cms.entity.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 区域Service
 * @author ThinkGem
 * @version
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}

	public List<Area> findTopArea(){
		return dao.findTopArea();
	}

	public List<Area> findCurrentArea(Area area){
		return dao.findCurrentArea(area);
	}
	public List<Area> getByParentId(String parentId){
		return dao.getByParentId(parentId);
	}
	public List<Area> findByName(String name){
		return dao.findByName(name);
	}

	public List<Area> findByParentId(){
		return dao.findByParentId();
	}

	public List<Area> findCityList(Area area){
		return dao.findCityList(area);
	}


	public List<Area> findChildArea(Area area) {
		findChild(area.getChildList(),area);
		return area.getChildList();
	}

	public void findChild(List<Area> allChildNavigationBars,Area area){
		List<Area> navigationBar = dao.findChildArea(area);
		allChildNavigationBars.addAll(navigationBar);
		if(!navigationBar.isEmpty()){
			for (Area c : navigationBar) {
				findChild(c.getChildList(),c);
			}
		}
	}
}
