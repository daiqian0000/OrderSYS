package com.asion.business.module.org.model;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 机构表 : 系统操作员的机构管理
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@TableName("LOC_ORG")
public class Org extends Model<Org> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 机构代码
     */
    private String orgId;
    /**
     * 机构名称
     */
    private String orgName;
    /**
     * 机构类型
     */
    private String orgType;
    /**
     * 上级机构编号
     */
    private String parentId;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 所属地区编码
     */
    private Integer areaId;
    /**
     * 联系人
     */
    private String person;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 地址
     */
    private String address;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 传真
     */
    private String fax;
    /**
     * 描述
     */
    private String description;

    @TableField(exist = false)
    private Boolean hasChildren;

    @TableField(exist = false)
    private Org parent;

    @TableField(exist = false)
    private List<Org> children;

    public Long getId() {
        return id;
    }

    public Org setId(Long id) {
        this.id = id;
        return this;
    }

    public String getOrgId() {
        return orgId;
    }

    public Org setOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getOrgName() {
        return orgName;
    }

    public Org setOrgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public String getOrgType() {
        return orgType;
    }

    public Org setOrgType(String orgType) {
        this.orgType = orgType;
        return this;
    }

    public String getParentId() {
        return parentId;
    }

    public Org setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public Org setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public Org setAreaId(Integer areaId) {
        this.areaId = areaId;
        return this;
    }

    public String getPerson() {
        return person;
    }

    public Org setPerson(String person) {
        this.person = person;
        return this;
    }

    public String getTelephone() {
        return telephone;
    }

    public Org setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Org setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Org setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFax() {
        return fax;
    }

    public Org setFax(String fax) {
        this.fax = fax;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Org setDescription(String description) {
        this.description = description;
        return this;
    }

    public static final String ID = "ID";

    public static final String ORG_ID = "ORG_ID";

    public static final String ORG_NAME = "ORG_NAME";

    public static final String ORG_TYPE = "ORG_TYPE";

    public static final String PARENT_ID = "PARENT_ID";

    public static final String SORT = "SORT";

    public static final String AREA_ID = "AREA_ID";

    public static final String PERSON = "PERSON";

    public static final String TELEPHONE = "TELEPHONE";

    public static final String ADDRESS = "ADDRESS";

    public static final String EMAIL = "EMAIL";

    public static final String FAX = "FAX";

    public static final String DESCRIPTION = "DESCRIPTION";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Org{" +
        ", id=" + id +
        ", orgId=" + orgId +
        ", orgName=" + orgName +
        ", orgType=" + orgType +
        ", parentId=" + parentId +
        ", sort=" + sort +
        ", areaId=" + areaId +
        ", person=" + person +
        ", telephone=" + telephone +
        ", address=" + address +
        ", email=" + email +
        ", fax=" + fax +
        ", description=" + description +
        "}";
    }

	public Boolean getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public Org getParent() {
		return parent;
	}

	public void setParent(Org parent) {
		this.parent = parent;
	}

	public List<Org> getChildren() {
		return children;
	}

	public void setChildren(List<Org> children) {
		this.children = children;
	}

}
