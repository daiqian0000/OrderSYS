package com.asion.business.module.log.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author chenboyang
 * @since 2018-01-26
 */
@TableName("LOC_LOG")
public class Log extends Model<Log> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * 系统编号
     */
    private String sysId;
    /**
     * 操作信息
     */
    private String operateInfo;
    /**
     * 请求地址
     */
    private String requestUrl;
    /**
     * 参数
     */
    private String param;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 操作用户
     */
    private String userId;
    /**
     * 异常信息
     */
    private String exception;
    /**
     * 记录时间
     */
    private Date recordTime;
    /**
     * 日志类型名称
     */
    @TableField(exist = false)
    private String logTypeName;
    /**
     * 系统名称
     */
    @TableField(exist = false)
    private String sysName;
    
    public Long getId() {
        return id;
    }

    public Log setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLogType() {
        return logType;
    }

    public Log setLogType(String logType) {
        this.logType = logType;
        return this;
    }

    public String getSysId() {
        return sysId;
    }

    public Log setSysId(String sysId) {
        this.sysId = sysId;
        return this;
    }

    public String getOperateInfo() {
        return operateInfo;
    }

    public Log setOperateInfo(String operateInfo) {
        this.operateInfo = operateInfo;
        return this;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public Log setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }

    public String getParam() {
        return param;
    }

    public Log setParam(String param) {
        this.param = param;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public Log setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Log setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getException() {
        return exception;
    }

    public Log setException(String exception) {
        this.exception = exception;
        return this;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public Log setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
        return this;
    }

    public static final String ID = "ID";

    public static final String LOG_TYPE = "LOG_TYPE";

    public static final String SYS_ID = "SYS_ID";

    public static final String OPERATE_INFO = "OPERATE_INFO";

    public static final String REQUEST_URL = "REQUEST_URL";

    public static final String PARAM = "PARAM";

    public static final String IP = "IP";

    public static final String USER_ID = "USER_ID";

    public static final String EXCEPTION = "EXCEPTION";

    public static final String RECORD_TIME = "RECORD_TIME";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Log{" +
        ", id=" + id +
        ", logType=" + logType +
        ", sysId=" + sysId +
        ", operateInfo=" + operateInfo +
        ", requestUrl=" + requestUrl +
        ", param=" + param +
        ", ip=" + ip +
        ", userId=" + userId +
        ", exception=" + exception +
        ", recordTime=" + recordTime +
        "}";
    }

	public String getLogTypeName() {
		return logTypeName;
	}

	public void setLogTypeName(String logTypeName) {
		this.logTypeName = logTypeName;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
}
