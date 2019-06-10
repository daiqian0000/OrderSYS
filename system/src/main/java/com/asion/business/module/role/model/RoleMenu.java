package com.asion.business.module.role.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 角色菜单关系表
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@TableName("LOC_ROLE_MENU")
public class RoleMenu extends Model<RoleMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 角色编号
     */
    private String roleId;
    /**
     * 菜单编号
     */
    private String menuId;


    public Long getId() {
        return id;
    }

    public RoleMenu setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRoleId() {
        return roleId;
    }

    public RoleMenu setRoleId(String roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getMenuId() {
        return menuId;
    }

    public RoleMenu setMenuId(String menuId) {
        this.menuId = menuId;
        return this;
    }

    public static final String ID = "ID";

    public static final String ROLE_ID = "ROLE_ID";

    public static final String MENU_ID = "MENU_ID";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "RoleMenu{" +
        ", id=" + id +
        ", roleId=" + roleId +
        ", menuId=" + menuId +
        "}";
    }
}
