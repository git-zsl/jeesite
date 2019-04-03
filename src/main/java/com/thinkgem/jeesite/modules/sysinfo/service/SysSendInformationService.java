/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sysinfo.service;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sysinfo.entity.SysSendInformation;
import com.thinkgem.jeesite.modules.sysinfo.dao.SysSendInformationDao;

/**
 * 系统信息Service
 * @author zsl
 * @version 2019-04-02
 */
@Service
@Transactional(readOnly = true)
public class SysSendInformationService extends CrudService<SysSendInformationDao, SysSendInformation> {

	public SysSendInformation get(String id) {
		return super.get(id);
	}
	
	public List<SysSendInformation> findList(SysSendInformation sysSendInformation) {
		return super.findList(sysSendInformation);
	}
	
	public Page<SysSendInformation> findPage(Page<SysSendInformation> page, SysSendInformation sysSendInformation) {
		return super.findPage(page, sysSendInformation);
	}
	
	@Transactional(readOnly = false)
	public void save(SysSendInformation sysSendInformation) {
		super.save(sysSendInformation);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysSendInformation sysSendInformation) {
		super.delete(sysSendInformation);
	}

	/**
	 * 封装对象
	 */
	public SysSendInformation setDataObject(User user, String title, String content, String timeOut){
		SysSendInformation sysSendInformation= new SysSendInformation();
		sysSendInformation.setContent(content);
		sysSendInformation.setUser(user);
		sysSendInformation.setUserName(user.getName());
		sysSendInformation.setType("2");
		Date date = DateUtils.parseDate(timeOut);
		sysSendInformation.setTimeOut(date);
		sysSendInformation.setTitle(title);
		return sysSendInformation;
	}
}