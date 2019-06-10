package com.asion.business.module.app.model;

import java.io.Serializable;
import java.util.Date;

import com.asion.business.module.attach.model.Attach;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 应用版本表
 * </p>
 *
 * @author chenboyang-123
 * @since 2018-03-21
 */
@TableName("LOC_APP_VERSION")
public class AppVersion extends Model<AppVersion> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号 : 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 应用编号
     */
    private String appId;
    /**
     * 版本号
     */
    private String version;
    /**
     * 内容描述
     */
    private String content;
    /**
     * 是否强制更新
     */
    private Boolean enforceable;
    /**
     * 发布人
     */
    private String publisher;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 版本附件信息
     */
    @TableField(exist = false)
    private Attach attach;

    public Long getId() {
        return id;
    }

    public AppVersion setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public AppVersion setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public AppVersion setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getContent() {
        return content;
    }

    public AppVersion setContent(String content) {
        this.content = content;
        return this;
    }

    public Boolean getEnforceable() {
        return enforceable;
    }

    public AppVersion setEnforceable(Boolean enforceable) {
        this.enforceable = enforceable;
        return this;
    }

    public String getPublisher() {
        return publisher;
    }

    public AppVersion setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public AppVersion setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
        return this;
    }

    public static final String ID = "ID";

    public static final String APP_ID = "APP_ID";

    public static final String VERSION = "VERSION";

    public static final String CONTENT = "CONTENT";

    public static final String ENFORCEABLE = "ENFORCEABLE";

    public static final String PUBLISHER = "PUBLISHER";

    public static final String PUBLISH_TIME = "PUBLISH_TIME";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AppVersion{" +
        ", id=" + id +
        ", appId=" + appId +
        ", version=" + version +
        ", content=" + content +
        ", enforceable=" + enforceable +
        ", publisher=" + publisher +
        ", publishTime=" + publishTime +
        "}";
    }

	public Attach getAttach() {
		return attach;
	}

	public void setAttach(Attach attach) {
		this.attach = attach;
	}
}
