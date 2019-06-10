SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS LOC_APP_VERSION;
DROP TABLE IF EXISTS LOC_USER_ROLE;
DROP TABLE IF EXISTS LOC_USER;
DROP TABLE IF EXISTS LOC_ORG;
DROP TABLE IF EXISTS LOC_AREA;
DROP TABLE IF EXISTS LOC_ATTACH;
DROP TABLE IF EXISTS LOC_BUCKET;
DROP TABLE IF EXISTS LOC_DICT;
DROP TABLE IF EXISTS LOC_LOG;
DROP TABLE IF EXISTS LOC_ROLE_MENU;
DROP TABLE IF EXISTS LOC_MENU;
DROP TABLE IF EXISTS LOC_PARAM;
DROP TABLE IF EXISTS LOC_ROLE;
DROP TABLE IF EXISTS LOC_VERTICAL_DOMAIN;




/* Create Tables */

-- 应用版本表
CREATE TABLE LOC_APP_VERSION
(
	-- 主键
	ID int NOT NULL AUTO_INCREMENT COMMENT '编号 : 主键',
	APP_ID varchar(50) COMMENT '应用编号',
	VERSION varchar(50) COMMENT '版本号',
	CONTENT varchar(4000) COMMENT '内容描述',
	ENFORCEABLE boolean COMMENT '是否强制更新',
	PUBLISHER varchar(50) COMMENT '发布人',
	PUBLISH_TIME datetime COMMENT '发布时间',
	PRIMARY KEY (ID),
	UNIQUE (VERSION)
) COMMENT = '应用版本表';


-- 地区表
CREATE TABLE LOC_AREA
(
	ID int NOT NULL COMMENT '地区编码',
	NAME varchar(50) COMMENT '地区名称',
	PARENT_ID int COMMENT '上级地区编码',
	LEVEL int COMMENT '地区等级',
	X double COMMENT '经度',
	Y double COMMENT '纬度',
	SHAPE geometry COMMENT '空间几何数据',
	PRIMARY KEY (ID),
	UNIQUE (ID)
) COMMENT = '地区表';


-- 附件表 : 用于存放系统各业务的附件信息
CREATE TABLE LOC_ATTACH
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '编号',
	DOMAIN_ID varchar(50) COMMENT '模型标识',
	-- （对象的全局唯一标识：主键UUID）
	TARGET_ID varchar(50) COMMENT '所属目标编号 : （对象的全局唯一标识：主键UUID）',
	FIELD_ID varchar(50) COMMENT '字段标识',
	NAME varchar(50) COMMENT '附件名称',
	URL varchar(1000) COMMENT '附件地址',
	-- 可能为附件的后缀名
	TYPE varchar(50) COMMENT '附件类型 : 可能为附件的后缀名',
	-- （单位：字节）
	SIZE bigint COMMENT '附件大小 : （单位：字节）',
	-- 存储分区的模块标识
	BUCKET varchar(50) COMMENT '存储板块 : 存储分区的模块标识',
	-- 第三方存储服务签名地址
	PRESIGNED_URL varchar(1000) COMMENT '预签名地址 : 第三方存储服务签名地址',
	PRIMARY KEY (ID)
) COMMENT = '附件表 : 用于存放系统各业务的附件信息';


-- 附件存储模块表 : 用于存放系统各业务的附件信息
CREATE TABLE LOC_BUCKET
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '编号',
	-- 存储分区的模块标识
	BUCKET varchar(50) COMMENT '存储板块 : 存储分区的模块标识',
	COLLECTION varchar(50) COMMENT '存储集合名称',
	NAME varchar(50) COMMENT '描述名称',
	DESCRIPTION varchar(1000) COMMENT '描述',
	PRIMARY KEY (ID),
	UNIQUE (BUCKET)
) COMMENT = '附件存储模块表 : 用于存放系统各业务的附件信息';


-- 字典表 : 用于存放各业务的类型字典
CREATE TABLE LOC_DICT
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '编号',
	CODE varchar(50) COMMENT '字典代码',
	NAME varchar(50) COMMENT '字典名称',
	PARENT_CODE varchar(50) COMMENT '上级代码',
	SORT int COMMENT '排序',
	DESCRIPTION varchar(1000) COMMENT '描述',
	PRIMARY KEY (ID),
	UNIQUE (CODE)
) COMMENT = '字典表 : 用于存放各业务的类型字典';


-- 日志表
CREATE TABLE LOC_LOG
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '编号',
	LOG_TYPE varchar(50) COMMENT '日志类型',
	SYS_ID varchar(50) COMMENT '系统编号',
	OPERATE_INFO varchar(1000) COMMENT '操作信息',
	REQUEST_URL varchar(200) COMMENT '请求地址',
	PARAM varchar(1000) COMMENT '参数',
	IP varchar(100) COMMENT 'IP地址',
	USER_ID varchar(50) COMMENT '操作用户',
	EXCEPTION varchar(1000) COMMENT '异常信息',
	RECORD_TIME timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
	PRIMARY KEY (ID)
) COMMENT = '日志表';


-- 菜单表
CREATE TABLE LOC_MENU
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '编号',
	MENU_ID varchar(50) COMMENT '菜单编号',
	NAME varchar(50) COMMENT '菜单名称',
	TYPE varchar(50) COMMENT '菜单类型',
	PARENT_ID varchar(50) COMMENT '上级菜单编号',
	SYS_ID varchar(50) COMMENT '系统编号',
	ICON varchar(100) COMMENT '图标',
	HREF varchar(500) COMMENT '链接地址',
	TARGET varchar(20) COMMENT '连接目标',
	PERMISSION varchar(100) COMMENT '权限标识',
	SORT int COMMENT '排序',
	DISPLAY boolean DEFAULT '1' COMMENT '是否显示',
	PRIMARY KEY (ID),
	UNIQUE (MENU_ID)
) COMMENT = '菜单表';


-- 机构表 : 系统操作员的机构管理
CREATE TABLE LOC_ORG
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '编号',
	ORG_ID varchar(50) COMMENT '机构代码',
	ORG_NAME varchar(50) COMMENT '机构名称',
	ORG_TYPE varchar(50) COMMENT '机构类型',
	PARENT_ID varchar(50) COMMENT '上级机构编号',
	SORT int COMMENT '排序',
	AREA_ID int COMMENT '所属地区编码',
	PERSON varchar(50) COMMENT '联系人',
	TELEPHONE varchar(50) COMMENT '联系电话',
	ADDRESS varchar(200) COMMENT '地址',
	EMAIL varchar(100) COMMENT '电子邮箱',
	FAX varchar(20) COMMENT '传真',
	DESCRIPTION varchar(1000) COMMENT '描述',
	PRIMARY KEY (ID),
	UNIQUE (ORG_ID)
) COMMENT = '机构表 : 系统操作员的机构管理';


-- 参数表 : 用于配置业务参数
CREATE TABLE LOC_PARAM
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '编号',
	PARAM_KEY varchar(50) COMMENT '参数键',
	PARAM_VALUE varchar(1000) COMMENT '参数值',
	PARAM_LABEL varchar(50) COMMENT '参数名称',
	ENABLE boolean DEFAULT '1' COMMENT '是否启用',
	DESCRIPTION varchar(1000) COMMENT '描述',
	PRIMARY KEY (ID),
	UNIQUE (PARAM_KEY)
) COMMENT = '参数表 : 用于配置业务参数';


-- 角色表
CREATE TABLE LOC_ROLE
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '编号',
	ROLE_ID varchar(50) COMMENT '角色编号',
	ROLE_NAME varchar(50) COMMENT '角色名称',
	ENABLE boolean DEFAULT '1' COMMENT '是否启用',
	DESCRIPTION varchar(1000) COMMENT '描述',
	PRIMARY KEY (ID),
	UNIQUE (ROLE_ID)
) COMMENT = '角色表';


-- 角色菜单关系表
CREATE TABLE LOC_ROLE_MENU
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '编号',
	ROLE_ID varchar(50) COMMENT '角色编号',
	MENU_ID varchar(50) COMMENT '菜单编号',
	PRIMARY KEY (ID)
) COMMENT = '角色菜单关系表';


-- 用户表 : 系统管理用户表
CREATE TABLE LOC_USER
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '编号',
	USER_ID varchar(50) COMMENT '用户编号',
	PASSWORD varchar(50) COMMENT '密码',
	REAL_NAME varchar(50) COMMENT '真实姓名',
	-- （值：男/女）
	GENDER varchar(1) COMMENT '性别 : （值：男/女）',
	BIRTHDAY date COMMENT '出生年月',
	ORG_ID varchar(50) COMMENT '机构代码',
	AREA_ID int COMMENT '所属地区编码',
	MOBILE_PHONE varchar(20) COMMENT '手机',
	ADDRESS varchar(200) COMMENT '家庭住址',
	EMAIL varchar(100) COMMENT '电子邮箱',
	ENABLE boolean DEFAULT '1' COMMENT '是否启用',
	PRIMARY KEY (ID),
	UNIQUE (USER_ID)
) COMMENT = '用户表 : 系统管理用户表';


-- 用户角色关系表
CREATE TABLE LOC_USER_ROLE
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '编号',
	USER_ID varchar(50) COMMENT '用户编号',
	ROLE_ID varchar(50) COMMENT '角色编号',
	PRIMARY KEY (ID)
) COMMENT = '用户角色关系表';


-- 纵向数据模型表 : 各业务基础表的纵向数据模型扩展表
CREATE TABLE LOC_VERTICAL_DOMAIN
(
	ID int NOT NULL AUTO_INCREMENT COMMENT '编号',
	DOMAIN_ID varchar(50) COMMENT '模型标识',
	ROW_ID varchar(50) COMMENT '行标识',
	FIELD_ID varchar(50) COMMENT '字段标识',
	FIELD_VALUE varchar(1000) COMMENT '字段值',
	PRIMARY KEY (ID)
) COMMENT = '纵向数据模型表 : 各业务基础表的纵向数据模型扩展表';



/* Create Indexes */

CREATE INDEX APP_ID ON LOC_APP_VERSION (APP_ID ASC);
CREATE INDEX PUBLISH_TIME ON LOC_APP_VERSION (PUBLISH_TIME DESC);
-- 上级地区编码索引
CREATE INDEX PARENT_ID ON LOC_AREA (PARENT_ID ASC);
-- 地区级别索引
CREATE INDEX LEVEL ON LOC_AREA (LEVEL ASC);
CREATE INDEX TARGET_ID ON LOC_ATTACH (TARGET_ID ASC);
CREATE INDEX BUCKET ON LOC_ATTACH (BUCKET ASC);
CREATE INDEX DOMAIN_ID ON LOC_ATTACH (DOMAIN_ID ASC);
-- 字典代码索引
CREATE INDEX PARENT_CODE ON LOC_DICT (PARENT_CODE ASC);
CREATE INDEX USER_ID ON LOC_LOG (USER_ID ASC);
CREATE INDEX RECORD_TIME ON LOC_LOG (RECORD_TIME DESC);
-- 系统编号索引
CREATE INDEX SYS_ID ON LOC_LOG (SYS_ID ASC);
CREATE INDEX TYPE ON LOC_MENU (TYPE ASC);
CREATE INDEX PARENT_ID ON LOC_MENU (PARENT_ID ASC);
CREATE INDEX PERMISSION ON LOC_MENU (PERMISSION ASC);
-- 系统编号索引
CREATE INDEX SYS_ID ON LOC_MENU (SYS_ID ASC);
CREATE INDEX PARENT_ID ON LOC_ORG (PARENT_ID ASC);
-- 所属地区编码索引
CREATE INDEX AREA_ID ON LOC_ORG (AREA_ID ASC);
CREATE INDEX ROLE_ID ON LOC_ROLE_MENU (ROLE_ID ASC);
CREATE INDEX MENU_ID ON LOC_ROLE_MENU (MENU_ID ASC);
CREATE INDEX ORG_ID ON LOC_USER (ORG_ID ASC);
-- 所属地区编码索引
CREATE INDEX AREA_ID ON LOC_USER (AREA_ID ASC);
CREATE INDEX USER_ID ON LOC_USER_ROLE (USER_ID ASC);
CREATE INDEX ROLE_ID ON LOC_USER_ROLE (ROLE_ID ASC);
-- 纵向模型索引
CREATE INDEX DOMAIN_ID ON LOC_VERTICAL_DOMAIN (DOMAIN_ID ASC);
CREATE INDEX ROW_ID ON LOC_VERTICAL_DOMAIN (ROW_ID ASC);



