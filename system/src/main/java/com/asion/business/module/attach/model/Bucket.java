package com.asion.business.module.attach.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 附件存储模块表 : 用于存放系统各业务的附件信息
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@TableName("LOC_BUCKET")
public class Bucket extends Model<Bucket> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 存储板块 : 存储分区的模块标识
     */
    private String bucket;
    /**
     * 存储集合名称
     */
    private String collection;
    /**
     * 描述名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 附件数量
     */
    @TableField(exist = false)
    private long count;
    
    public Long getId() {
        return id;
    }

    public Bucket setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBucket() {
        return bucket;
    }

    public Bucket setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public String getCollection() {
        return collection;
    }

    public Bucket setCollection(String collection) {
        this.collection = collection;
        return this;
    }

    public String getName() {
        return name;
    }

    public Bucket setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Bucket setDescription(String description) {
        this.description = description;
        return this;
    }

    public static final String ID = "ID";

    public static final String BUCKET = "BUCKET";

    public static final String COLLECTION = "COLLECTION";

    public static final String NAME = "NAME";

    public static final String DESCRIPTION = "DESCRIPTION";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Bucket{" +
        ", id=" + id +
        ", bucket=" + bucket +
        ", collection=" + collection +
        ", name=" + name +
        ", description=" + description +
        "}";
    }

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
}
