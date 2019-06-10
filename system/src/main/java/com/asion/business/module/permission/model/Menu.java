package com.asion.business.module.permission.model;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@TableName("LOC_MENU")
public class Menu extends Model<Menu> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId(value = "ID", type = IdType.AUTO)
	private Long id;
	/**
	 * 菜单编号
	 */
	private String menuId;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单类型
	 */
	private String type;
	/**
	 * 上级菜单编号
	 */
	private String parentId;
	/**
	 * 系统编号
	 */
	private String sysId;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 链接地址
	 */
	private String href;
	/**
	 * 连接目标
	 */
	private String target;
	/**
	 * 权限标识
	 */
	private String permission;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 是否显示
	 */
	private Boolean display;
	/**
	 * 类型名称
	 */
	@TableField(exist = false)
	private String typeName;
	/**
	 * 系统编号
	 */
	@TableField(exist = false)
	private String sysName;
	/**
	 * 是否有子集
	 */
	@TableField(exist = false)
	private Boolean hasChildren;
	/**
	 * 上级菜单
	 */
	@TableField(exist = false)
	private Menu parent;

	/**
	 * 子集菜单
	 */
	@TableField(exist = false)
	private List<Menu> children;

	public Long getId() {
        return id;
    }

    public Menu setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMenuId() {
        return menuId;
    }

    public Menu setMenuId(String menuId) {
        this.menuId = menuId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Menu setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public Menu setType(String type) {
        this.type = type;
        return this;
    }

    public String getParentId() {
        return parentId;
    }

    public Menu setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getSysId() {
        return sysId;
    }

    public Menu setSysId(String sysId) {
        this.sysId = sysId;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Menu setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getHref() {
        return href;
    }

    public Menu setHref(String href) {
        this.href = href;
        return this;
    }

    public String getTarget() {
        return target;
    }

    public Menu setTarget(String target) {
        this.target = target;
        return this;
    }

    public String getPermission() {
        return permission;
    }

    public Menu setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public Menu setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public Boolean getDisplay() {
        return display;
    }

    public Menu setDisplay(Boolean display) {
        this.display = display;
        return this;
    }

    public static final String ID = "ID";

    public static final String MENU_ID = "MENU_ID";

    public static final String NAME = "NAME";

    public static final String TYPE = "TYPE";

    public static final String PARENT_ID = "PARENT_ID";

    public static final String SYS_ID = "SYS_ID";

    public static final String ICON = "ICON";

    public static final String HREF = "HREF";

    public static final String TARGET = "TARGET";

    public static final String PERMISSION = "PERMISSION";

    public static final String SORT = "SORT";

    public static final String DISPLAY = "DISPLAY";

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Menu{" + ", id=" + id + ", menuId=" + menuId + ", name=" + name + ", type=" + type + ", parentId="
				+ parentId + ", sysId=" + sysId + ", icon=" + icon + ", href=" + href + ", target=" + target
				+ ", permission=" + permission + ", sort=" + sort + ", display=" + display + "}";
	}

	public Boolean getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
}
