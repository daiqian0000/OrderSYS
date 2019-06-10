package com.asion.business.module.role.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@TableName("LOC_ROLE")
public class Role extends Model<Role> {

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
     * 角色名称
     */
    private String roleName;
    /**
     * 是否启用
     */
    private Boolean enable;
    /**
     * 描述
     */
    private String description;


    public Long getId() {
        return id;
    }

    public Role setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRoleId() {
        return roleId;
    }

    public Role setRoleId(String roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public Role setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public Boolean getEnable() {
        return enable;
    }

    public Role setEnable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Role setDescription(String description) {
        this.description = description;
        return this;
    }

    public static final String ID = "ID";

    public static final String ROLE_ID = "ROLE_ID";

    public static final String ROLE_NAME = "ROLE_NAME";

    public static final String ENABLE = "ENABLE";

    public static final String DESCRIPTION = "DESCRIPTION";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Role{" +
        ", id=" + id +
        ", roleId=" + roleId +
        ", roleName=" + roleName +
        ", enable=" + enable +
        ", description=" + description +
        "}";
    }
}
