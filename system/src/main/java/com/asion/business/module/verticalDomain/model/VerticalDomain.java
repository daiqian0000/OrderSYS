package com.asion.business.module.verticalDomain.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 纵向数据模型表 : 各业务基础表的纵向数据模型扩展表
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@TableName("LOC_VERTICAL_DOMAIN")
public class VerticalDomain extends Model<VerticalDomain> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 模型标识
     */
    private String domainId;
    /**
     * 行标识
     */
    private String rowId;
    /**
     * 字段标识
     */
    private String fieldId;
    /**
     * 字段值
     */
    private String fieldValue;


    public Long getId() {
        return id;
    }

    public VerticalDomain setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDomainId() {
        return domainId;
    }

    public VerticalDomain setDomainId(String domainId) {
        this.domainId = domainId;
        return this;
    }

    public String getRowId() {
        return rowId;
    }

    public VerticalDomain setRowId(String rowId) {
        this.rowId = rowId;
        return this;
    }

    public String getFieldId() {
        return fieldId;
    }

    public VerticalDomain setFieldId(String fieldId) {
        this.fieldId = fieldId;
        return this;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public VerticalDomain setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
        return this;
    }

    public static final String ID = "ID";

    public static final String DOMAIN_ID = "DOMAIN_ID";

    public static final String ROW_ID = "ROW_ID";

    public static final String FIELD_ID = "FIELD_ID";

    public static final String FIELD_VALUE = "FIELD_VALUE";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VerticalDomain{" +
        ", id=" + id +
        ", domainId=" + domainId +
        ", rowId=" + rowId +
        ", fieldId=" + fieldId +
        ", fieldValue=" + fieldValue +
        "}";
    }
}
