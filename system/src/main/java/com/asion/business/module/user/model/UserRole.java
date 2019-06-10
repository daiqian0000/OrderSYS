package com.asion.business.module.user.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 用户角色关系表
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@TableName("LOC_USER_ROLE")
public class UserRole extends Model<UserRole> {

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
     * 角色编号
     */
    private String roleId;


    public Long getId() {
        return id;
    }

    public UserRole setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public UserRole setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getRoleId() {
        return roleId;
    }

    public UserRole setRoleId(String roleId) {
        this.roleId = roleId;
        return this;
    }

    public static final String ID = "ID";

    public static final String USER_ID = "USER_ID";

    public static final String ROLE_ID = "ROLE_ID";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserRole{" +
        ", id=" + id +
        ", userId=" + userId +
        ", roleId=" + roleId +
        "}";
    }
}
