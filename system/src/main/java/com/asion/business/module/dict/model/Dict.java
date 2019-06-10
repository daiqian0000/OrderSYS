package com.asion.business.module.dict.model;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 字典表 : 用于存放各业务的类型字典
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@TableName("LOC_DICT")
public class Dict extends Model<Dict> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 字典代码
     */
    private String code;
    /**
     * 字典名称
     */
    private String name;
    /**
     * 上级代码
     */
    private String parentCode;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String description;

    @TableField(exist = false)
    private Boolean hasChildren;

    @TableField(exist = false)
    private Dict parent;

    @TableField(exist = false)
    private List<Dict> children;

    public Long getId() {
        return id;
    }

    public Dict setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Dict setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public Dict setName(String name) {
        this.name = name;
        return this;
    }

    public String getParentCode() {
        return parentCode;
    }

    public Dict setParentCode(String parentCode) {
        this.parentCode = parentCode;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public Dict setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Dict setDescription(String description) {
        this.description = description;
        return this;
    }

    public static final String ID = "ID";

    public static final String CODE = "CODE";

    public static final String NAME = "NAME";

    public static final String PARENT_CODE = "PARENT_CODE";

    public static final String SORT = "SORT";

    public static final String DESCRIPTION = "DESCRIPTION";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Dict{" +
        ", id=" + id +
        ", code=" + code +
        ", name=" + name +
        ", parentCode=" + parentCode +
        ", sort=" + sort +
        ", description=" + description +
        "}";
    }

	public Boolean getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public Dict getParent() {
		return parent;
	}

	public void setParent(Dict parent) {
		this.parent = parent;
	}

	public List<Dict> getChildren() {
		return children;
	}

	public void setChildren(List<Dict> children) {
		this.children = children;
	}

}
