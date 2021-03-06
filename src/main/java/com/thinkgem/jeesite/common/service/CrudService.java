/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.persistence.Page;

/**
 * Service基类
 * @author ThinkGem
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {
	
	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return dao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		return dao.get(entity);
	}
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		return dao.findList(entity);
	}

	/**
	 * 查询属于文章类型的列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findArticleList(T entity) {
		return dao.findArticleList(entity);
	}

	public List<T> findTopArticleList(T entity) {
		return dao.findTopArticleList(entity);
	}

	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findConfigList(T entity) {
		return dao.findConfigList(entity);
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findconsultationArticlePage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findconsultationArticlePage(entity));
		return page;
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findList(entity));
		return page;
	}

	public Page<T> findAllPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findAllPageList(entity));
		return page;
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findArticlePage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findArticleList(entity));
		return page;
	}

	/**
	 * 主页搜索所有数据
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> searchArticlePage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.searchArticlePage(entity));
		return page;
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findHomeCollectPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findHomeCollects(entity));
		return page;
	}
	/**
	 * 查询评论分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findconsultationPage(Page<T> page, T entity) {
		entity.setPage(page);
		List<T> ts = dao.findconsultationList(entity);
		page.setList(ts);
		return page;
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findNewPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findNewList(entity));
		return page;
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findJobList(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findJobList(entity));
		return page;
	}

	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(T entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	/**
	 * 删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(T entity) {
		dao.delete(entity);
	}

}
