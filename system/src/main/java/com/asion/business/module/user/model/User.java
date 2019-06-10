package com.asion.business.module.user.model;

import java.io.Serializable;
import java.util.Date;

import com.asion.business.module.org.model.Org;
import com.asion.common.util.DateUtil;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * 用户表 : 系统管理用户表
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@TableName("LOC_USER")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 密码
     */
    private String password;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 性别 : （值：男/女）
     */
    private String gender;
    /**
     * 出生年月
     */
    @JsonFormat(pattern = DateUtil.DATE_FORMAT, timezone = DateUtil.GMT_E_8)
    private Date birthday;
    /**
     * 机构代码
     */
    private String orgId;
    /**
     * 所属地区编码
     */
    private Integer areaId;
    /**
     * 手机
     */
    private String mobilePhone;
    /**
     * 家庭住址
     */
    private String address;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 是否启用
     */
    private Boolean enable;
    /**
     * 角色编号
     */
    @TableField(exist = false)
    private String roleId;
    /**
     * 所属机构
     */
    @TableField(exist = false)
    private Org org;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public User setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public User setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public User setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public User setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getOrgId() {
        return orgId;
    }

    public User setOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public User setAreaId(Integer areaId) {
        this.areaId = areaId;
        return this;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public User setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public Boolean getEnable() {
        return enable;
    }

    public User setEnable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public static final String ID = "ID";

    public static final String USER_ID = "USER_ID";

    public static final String PASSWORD = "PASSWORD";

    public static final String REAL_NAME = "REAL_NAME";

    public static final String GENDER = "GENDER";

    public static final String BIRTHDAY = "BIRTHDAY";

    public static final String ORG_ID = "ORG_ID";

    public static final String AREA_ID = "AREA_ID";

    public static final String MOBILE_PHONE = "MOBILE_PHONE";

    public static final String ADDRESS = "ADDRESS";

    public static final String EMAIL = "EMAIL";

    public static final String ENABLE = "ENABLE";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "User{" +
        ", id=" + id +
        ", userId=" + userId +
        ", password=" + password +
        ", realName=" + realName +
        ", gender=" + gender +
        ", birthday=" + birthday +
        ", orgId=" + orgId +
        ", areaId=" + areaId +
        ", mobilePhone=" + mobilePhone +
        ", address=" + address +
        ", email=" + email +
        ", enable=" + enable +
        "}";
    }

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
