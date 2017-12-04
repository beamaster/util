package com.steam.entity;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -7181505137826297519L;
	
	private Long id;
	
	/**创建人*/
    private String createUser;

    /**创建日期*/
    private Date createDate;

    /**修改人*/
    private String modifyUser;

    /**修改日期*/
    private Date modifyDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}
