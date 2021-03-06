/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.area.entity.SysChina;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.SysOfficeInformation;
import com.thinkgem.jeesite.modules.sys.dao.SysOfficeInformationDao;

/**
 * 机构详细信息Service
 * @author zsl
 * @version 2019-01-06
 */
@Service
@Transactional(readOnly = true)
public class SysOfficeInformationService extends CrudService<SysOfficeInformationDao, SysOfficeInformation> {

	@Autowired
	private OfficeService officeService;

	public SysOfficeInformation get(String id) {
		return super.get(id);
	}

	public SysOfficeInformation findByUserId(String userId) {
		return dao.findByUserId(userId);
	}

	public List<String> findOfficeImage (String userId){
		List<String> list = Lists.newArrayList();
		SysOfficeInformation byUserId = dao.findByUserId(userId);
		if(Objects.nonNull(byUserId)){
			String officeImage = byUserId.getOfficeImage();
			if(StringUtils.isNotBlank(officeImage)){
				String[] split = officeImage.split(",");
				Arrays.stream(split).forEach(item ->{
					list.add(item);
				});
			}
		}
		return list;
	}

	public List<SysOfficeInformation> findList(SysOfficeInformation sysOfficeInformation) {
		return super.findList(sysOfficeInformation);
	}
	
	public Page<SysOfficeInformation> findPage(Page<SysOfficeInformation> page, SysOfficeInformation sysOfficeInformation) {
		return super.findPage(page, sysOfficeInformation);
	}
	
	@Transactional(readOnly = false)
	public void save(SysOfficeInformation sysOfficeInformation) {
		super.save(sysOfficeInformation);
	}

	@Transactional(readOnly = false)
	public void updateHomeInformation(SysOfficeInformation sysOfficeInformation) {
		dao.updateHomeInformation(sysOfficeInformation);
	}

	@Transactional(readOnly = false)
	public void delete(SysOfficeInformation sysOfficeInformation) {
		super.delete(sysOfficeInformation);
	}


	/**
	 *  封装企业用户数据
	 */
	@Transactional(readOnly = false)
	public SysOfficeInformation setSysOfficeInformation(Map<String,String> map,SysOfficeInformation officeInfo,User user){
		officeInfo.setName(map.get("name")); // 全称
		Office office = new Office();
		office.setName(map.get("name"));
		List<Office> listByName = officeService.findListByName(office);
		if(listByName.isEmpty()){
			office.setParent(new Office(map.get("companyId")));
			office.setParentIds("0,"+ map.get("companyId"));
			office.setArea(new Area(map.get("provence")));
			office.setGrade("1");
			office.setType("1");
			office.setCreateBy(new User("1"));
			office.setUpdateBy(new User("1"));
			officeService.save(office);
		}else{
			office = listByName.get(0);
		}
		user.setOffice(office);
		user.setName(map.get("officeName"));
		user.setPhoto(map.get("image"));
		officeInfo.setOffice(office); //  关联的officeId
		officeInfo.setOfficeType(map.get("officeType")); //企业性质
		officeInfo.setShortName(map.get("shortName")); // 简称
		officeInfo.setOfficeIntroduction(map.get("officeIntroduction"));  //机构简介
		officeInfo.setOfficeLink(map.get("officeLink"));  // 企业官网
		officeInfo.setImage(map.get("file"));   //  企业证书
		officeInfo.setProvence(new SysChina(map.get("provence")));
		officeInfo.setCity(new SysChina(map.get("city")));
		officeInfo.setDistrict(new SysChina(map.get("district")));
		return officeInfo;
	}

	/**
	 *  封装企业用户的个人数据
	 */
	public User setUserInformation(Map<String,String> map,User user){
		user.setPosition(map.get("position"));  // 职务
		user.setMobile(map.get("mobile"));   // 联系电话
		user.setPhoto(map.get("photo"));    //  用户头像
		user.setLoginFlag(Global.NO);
		return user;
	}

	public SysOfficeInformation changObject(SysOfficeInformation oldObj,SysOfficeInformation newObj){
		newObj.setUser(oldObj.getUser());
		return newObj;
	}
}