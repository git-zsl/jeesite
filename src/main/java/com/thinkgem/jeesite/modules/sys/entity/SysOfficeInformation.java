/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import com.thinkgem.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 机构详细信息Entity
 * @author zsl
 * @version 2019-01-06
 */
public class SysOfficeInformation extends DataEntity<SysOfficeInformation> {
	
	private static final long serialVersionUID = 1L;
	private Office office;		// 机构Id
	private String name;		// 机构名称
	private String shortName;		// 简称
	private String officeType;		// 机构类型
	private String image;		// 执照
	private String file;		// 机构相关文件
	
	public SysOfficeInformation() {
		super();
	}

	public SysOfficeInformation(String id){
		super(id);
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=0, max=255, message="机构名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="简称长度必须介于 0 和 255 之间")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@Length(min=0, max=1, message="机构类型长度必须介于 0 和 1 之间")
	public String getOfficeType() {
		return officeType;
	}

	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}
	
	@Length(min=0, max=2000, message="执照长度必须介于 0 和 2000 之间")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Length(min=0, max=2000, message="机构相关文件长度必须介于 0 和 2000 之间")
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
}