/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param user
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);

	/**
	 * 更新主页个人用户信息
	 * @param user
	 * @return
	 */
	public int updateHomeUserInformation(User user);

	/**
	 * 更新主页企业用户信息
	 * @param user
	 * @return
	 */
	public int updateHomeUserOfficeInformation(User user);

	/**
	 * 更新主页企业updateBackground
	 * @param user
	 * @return
	 */
	public void updateBackground(User user);

	/**
	 * 查找个人黑名单列表
	 * @param
	 * @return
	 */
	public List<User> findBlacklist(@Param("delFalg") String delFalg,@Param("officeId") String officeId);

	/**
	 * 查找企业黑名单列表
	 * @param
	 * @return
	 */
	public List<User> findCompanyBlacklist(@Param("delFalg") String delFalg,@Param("companyId") String companyId);


	public void deleteOrrecover(User user);
}
