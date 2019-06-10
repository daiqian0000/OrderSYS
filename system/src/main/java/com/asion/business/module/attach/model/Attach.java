package com.asion.business.module.attach.model;

import java.io.InputStream;
import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 附件表 : 用于存放系统各业务的附件信息
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@TableName("LOC_ATTACH")
public class Attach extends Model<Attach> {

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
     * 所属目标编号 : （对象的全局唯一标识：主键）
     */
    private String targetId;
    /**
     * 字段标识
     */
    private String fieldId;
    /**
     * 附件名称
     */
    private String name;
    /**
     * 附件地址
     */
    private String url;
    /**
     * 附件类型 : 可能为附件的后缀名
     */
    private String type;
    /**
     * 附件大小 : （单位：字节）
     */
    private Long size;
    /**
     * 存储板块 : 存储分区的模块标识
     */
    private String bucket;
    /**
     * 预签名地址 : 第三方存储服务签名地址
     */
    private String presignedUrl;
    /**
     * 文件输入流
     */
    @TableField(exist = false)
    private InputStream input;

    public Long getId() {
        return id;
    }

    public Attach setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDomainId() {
        return domainId;
    }

    public Attach setDomainId(String domainId) {
        this.domainId = domainId;
        return this;
    }

    public String getTargetId() {
        return targetId;
    }

    public Attach setTargetId(String targetId) {
        this.targetId = targetId;
        return this;
    }

    public String getFieldId() {
        return fieldId;
    }

    public Attach setFieldId(String fieldId) {
        this.fieldId = fieldId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Attach setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Attach setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getType() {
        return type;
    }

    public Attach setType(String type) {
        this.type = type;
        return this;
    }

    public Long getSize() {
        return size;
    }

    public Attach setSize(Long size) {
        this.size = size;
        return this;
    }

    public String getBucket() {
        return bucket;
    }

    public Attach setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public String getPresignedUrl() {
        return presignedUrl;
    }

    public Attach setPresignedUrl(String presignedUrl) {
        this.presignedUrl = presignedUrl;
        return this;
    }

    public static final String ID = "ID";

    public static final String DOMAIN_ID = "DOMAIN_ID";

    public static final String TARGET_ID = "TARGET_ID";

    public static final String FIELD_ID = "FIELD_ID";

    public static final String NAME = "NAME";

    public static final String URL = "URL";

    public static final String TYPE = "TYPE";

    public static final String SIZE = "SIZE";

    public static final String BUCKET = "BUCKET";

    public static final String PRESIGNED_URL = "PRESIGNED_URL";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Attach{" +
        ", id=" + id +
        ", domainId=" + domainId +
        ", targetId=" + targetId +
        ", fieldId=" + fieldId +
        ", name=" + name +
        ", url=" + url +
        ", type=" + type +
        ", size=" + size +
        ", bucket=" + bucket +
        ", presignedUrl=" + presignedUrl +
        "}";
    }

	public InputStream getInput() {
		return input;
	}

	public void setInput(InputStream input) {
		this.input = input;
	}

}
