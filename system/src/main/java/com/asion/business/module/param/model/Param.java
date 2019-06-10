package com.asion.business.module.param.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 参数表 : 用于配置业务参数
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@TableName("LOC_PARAM")
public class Param extends Model<Param> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 参数键
     */
    private String paramKey;
    /**
     * 参数值
     */
    private String paramValue;
    /**
     * 参数名称
     */
    private String paramLabel;
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

    public Param setId(Long id) {
        this.id = id;
        return this;
    }

    public String getParamKey() {
        return paramKey;
    }

    public Param setParamKey(String paramKey) {
        this.paramKey = paramKey;
        return this;
    }

    public String getParamValue() {
        return paramValue;
    }

    public Param setParamValue(String paramValue) {
        this.paramValue = paramValue;
        return this;
    }

    public String getParamLabel() {
        return paramLabel;
    }

    public Param setParamLabel(String paramLabel) {
        this.paramLabel = paramLabel;
        return this;
    }

    public Boolean getEnable() {
        return enable;
    }

    public Param setEnable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Param setDescription(String description) {
        this.description = description;
        return this;
    }

    public static final String ID = "ID";

    public static final String PARAM_KEY = "PARAM_KEY";

    public static final String PARAM_VALUE = "PARAM_VALUE";

    public static final String PARAM_LABEL = "PARAM_LABEL";

    public static final String ENABLE = "ENABLE";

    public static final String DESCRIPTION = "DESCRIPTION";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Param{" +
        ", id=" + id +
        ", paramKey=" + paramKey +
        ", paramValue=" + paramValue +
        ", paramLabel=" + paramLabel +
        ", enable=" + enable +
        ", description=" + description +
        "}";
    }
}
