/* 系统权限初始化脚本 */

INSERT INTO LOC_ORG VALUES (NULL, 'asion100000', '亚讯星科', 'platform', '0', 1, 0, NULL, NULL, NULL, NULL, NULL, NULL);

-- 用户
INSERT INTO LOC_USER VALUES (NULL, 'admin', 'e99a18c428cb38d5f260853678922e03', '系统管理员', '男', '1999-12-31', 'asion100000', 0, NULL, NULL, NULL, 1);

-- 角色
INSERT INTO LOC_ROLE VALUES (NULL, 'admin', '系统管理员', 1, '系统管理员为整个系统最高权限者');

-- 用户角色
INSERT INTO LOC_USER_ROLE VALUES (NULL, 'admin', 'admin');

-- 菜单
INSERT INTO LOC_MENU VALUES (NULL, '1', '系统管理', 'dir', '0', 'manager', NULL, NULL, NULL, NULL, 1, 1);
INSERT INTO LOC_MENU VALUES (NULL, '2', '机构管理', 'menu', '1', 'manager', NULL, '/main/sys/org', NULL, NULL, 1, 1);
INSERT INTO LOC_MENU VALUES (NULL, '3', '用户管理', 'menu', '1', 'manager', NULL, '/main/sys/user', NULL, NULL, 2, 1);
INSERT INTO LOC_MENU VALUES (NULL, '4', '角色管理', 'menu', '1', 'manager', NULL, '/main/sys/role', NULL, NULL, 3, 1);
INSERT INTO LOC_MENU VALUES (NULL, '5', '权限管理', 'menu', '1', 'manager', NULL, '/main/sys/menu', NULL, NULL, 4, 1);
INSERT INTO LOC_MENU VALUES (NULL, '6', '地区管理', 'menu', '1', 'manager', NULL, '/main/sys/area', NULL, NULL, 5, 1);
INSERT INTO LOC_MENU VALUES (NULL, '7', '字典管理', 'menu', '1', 'manager', NULL, '/main/sys/dict', NULL, NULL, 6, 1);
INSERT INTO LOC_MENU VALUES (NULL, '8', '系统参数', 'menu', '1', 'manager', NULL, '/main/sys/param', NULL, NULL, 7, 1);
INSERT INTO LOC_MENU VALUES (NULL, '9', '系统日志', 'menu', '1', 'manager', NULL, '/main/sys/log', NULL, NULL, 8, 1);
INSERT INTO LOC_MENU VALUES (NULL, '15', '新增', 'btn', '2', 'manager', NULL, NULL, NULL, 'sys:org:add', 2, 1);
INSERT INTO LOC_MENU VALUES (NULL, '16', '查询', 'btn', '2', 'manager', NULL, NULL, NULL, 'sys:org:query', 1, 1);
INSERT INTO LOC_MENU VALUES (NULL, '17', '查询', 'btn', '3', 'manager', NULL, NULL, NULL, 'sys:user:query', 1, 1);
INSERT INTO LOC_MENU VALUES (NULL, '18', '新增', 'btn', '4', 'manager', NULL, NULL, NULL, 'sys:role:add', 1, 1);
INSERT INTO LOC_MENU VALUES (NULL, '20', '删除', 'btn', '4', 'manager', NULL, NULL, NULL, 'sys:role:del', 2, 1);
INSERT INTO LOC_MENU VALUES (NULL, '21', '修改', 'btn', '4', 'manager', NULL, NULL, NULL, 'sys:role:mod', 3, 1);
INSERT INTO LOC_MENU VALUES (NULL, '22', '查询', 'btn', '4', 'manager', NULL, NULL, NULL, 'sys:role:query', 4, 1);
INSERT INTO LOC_MENU VALUES (NULL, '23', '新增', 'btn', '8', 'manager', NULL, NULL, NULL, 'sys:param:add', 1, 1);
INSERT INTO LOC_MENU VALUES (NULL, '24', '删除', 'btn', '8', 'manager', NULL, NULL, NULL, 'sys:param:del', 2, 1);
INSERT INTO LOC_MENU VALUES (NULL, '25', '修改', 'btn', '8', 'manager', NULL, NULL, NULL, 'sys:param:mod', 3, 1);
INSERT INTO LOC_MENU VALUES (NULL, '26', '查询', 'btn', '8', 'manager', NULL, NULL, NULL, 'sys:param:query', 4, 1);
INSERT INTO LOC_MENU VALUES (NULL, '27', '查询', 'btn', '9', 'manager', NULL, NULL, NULL, 'sys:log:query', 1, 1);
INSERT INTO LOC_MENU VALUES (NULL, '36', '删除', 'btn', '2', 'manager', NULL, NULL, NULL, 'sys:org:del', 3, 1);
INSERT INTO LOC_MENU VALUES (NULL, '37', '修改', 'btn', '2', 'manager', NULL, NULL, NULL, 'sys:org:mod', 4, 1);
INSERT INTO LOC_MENU VALUES (NULL, '38', '删除', 'btn', '3', 'manager', NULL, NULL, NULL, 'sys:user:del', 2, 1);
INSERT INTO LOC_MENU VALUES (NULL, '39', '增加', 'btn', '3', 'manager', NULL, NULL, NULL, 'sys:user:add', 3, 1);
INSERT INTO LOC_MENU VALUES (NULL, '40', '修改', 'btn', '3', 'manager', NULL, NULL, NULL, 'sys:user:mod', 4, 1);
INSERT INTO LOC_MENU VALUES (NULL, '41', '增加', 'btn', '5', 'manager', NULL, NULL, NULL, 'sys:menu:add', 1, 1);
INSERT INTO LOC_MENU VALUES (NULL, '42', '查询', 'btn', '6', 'manager', NULL, NULL, NULL, 'sys:area:query', 1, 1);
INSERT INTO LOC_MENU VALUES (NULL, '43', '新增', 'btn', '7', 'manager', NULL, NULL, NULL, 'sys:dict:add', 1, 1);
INSERT INTO LOC_MENU VALUES (NULL, '44', '删除', 'btn', '5', 'manager', NULL, NULL, NULL, 'sys:menu:del', 2, 1);
INSERT INTO LOC_MENU VALUES (NULL, '45', '修改', 'btn', '5', 'manager', NULL, NULL, NULL, 'sys:menu:mod', 3, 1);
INSERT INTO LOC_MENU VALUES (NULL, '46', '查询', 'btn', '5', 'manager', NULL, NULL, NULL, 'sys:menu:query', 4, 1);
INSERT INTO LOC_MENU VALUES (NULL, '47', '删除', 'btn', '7', 'manager', NULL, NULL, NULL, 'sys:dict:del', 2, 1);
INSERT INTO LOC_MENU VALUES (NULL, '48', '修改', 'btn', '7', 'manager', NULL, NULL, NULL, 'sys:dict:mod', 3, 1);
INSERT INTO LOC_MENU VALUES (NULL, '49', '查询', 'btn', '7', 'manager', NULL, NULL, NULL, 'sys:dict:query', 4, 1);
INSERT INTO LOC_MENU VALUES (NULL, '58', '重置密码', 'btn', '3', 'manager', NULL, NULL, NULL, 'sys:user:resetPwd', 5, 1);
INSERT INTO LOC_MENU VALUES (NULL, '64', '权限分配', 'btn', '4', 'manager', NULL, NULL, NULL, 'sys:role:per', 5, 1);
INSERT INTO LOC_MENU VALUES (NULL, '74', '附件管理', 'menu', '1', 'manager', NULL, '/main/sys/attach', NULL, NULL, 9, 1);
INSERT INTO LOC_MENU VALUES (NULL, '75', '板块', 'menu', '74', 'manager', NULL, '/main/sys/attach/bucket', NULL, NULL, 1, 1);
INSERT INTO LOC_MENU VALUES (NULL, '76', '附件', 'menu', '74', 'manager', NULL, '/main/sys/attach/attach', NULL, NULL, 2, 1);
INSERT INTO LOC_MENU VALUES (NULL, '77', '新增', 'btn', '75', 'manager', NULL, NULL, NULL, 'sys:bucket:add', 1, 1);
INSERT INTO LOC_MENU VALUES (NULL, '78', '删除', 'btn', '75', 'manager', NULL, NULL, NULL, 'sys:bucket:del', 2, 1);
INSERT INTO LOC_MENU VALUES (NULL, '79', '修改', 'btn', '75', 'manager', NULL, NULL, NULL, 'sys:bucket:mod', 3, 1);
INSERT INTO LOC_MENU VALUES (NULL, '80', '查询', 'btn', '75', 'manager', NULL, NULL, NULL, 'sys:bucket:query', 4, 1);
INSERT INTO LOC_MENU VALUES (NULL, '81', '新增', 'btn', '76', 'manager', NULL, NULL, NULL, 'sys:attach:add', 1, 1);
INSERT INTO LOC_MENU VALUES (NULL, '82', '删除', 'btn', '76', 'manager', NULL, NULL, NULL, 'sys:attach:del', 2, 1);
INSERT INTO LOC_MENU VALUES (NULL, '83', '修改', 'btn', '76', 'manager', NULL, NULL, NULL, 'sys:attach:mod', 3, 1);
INSERT INTO LOC_MENU VALUES (NULL, '84', '查询', 'btn', '76', 'manager', NULL, NULL, NULL, 'sys:attach:query', 4, 1);

-- 字典
INSERT INTO LOC_DICT VALUES (NULL, 'sys_flag', '系统标识', '0', 1, NULL);
INSERT INTO LOC_DICT VALUES (NULL, 'manager', '后台管理', 'sys_flag', 1, NULL);
INSERT INTO LOC_DICT VALUES (NULL, 'sys_menu_type', '系统菜单类型', '0', 2, NULL);
INSERT INTO LOC_DICT VALUES (NULL, 'dir', '目录', 'sys_menu_type', 1, NULL);
INSERT INTO LOC_DICT VALUES (NULL, 'menu', '菜单', 'sys_menu_type', 2, NULL);
INSERT INTO LOC_DICT VALUES (NULL, 'btn', '按钮', 'sys_menu_type', 3, NULL);
INSERT INTO LOC_DICT VALUES (NULL, 'sys_log_type', '日志类型', '0', 3, NULL);
INSERT INTO LOC_DICT VALUES (NULL, 'opreation', '操作日志', 'sys_log_type', 1, NULL);
INSERT INTO LOC_DICT VALUES (NULL, 'exception', '异常日志', 'sys_log_type', 2, NULL);
INSERT INTO LOC_DICT VALUES (NULL, 'org_type', '机构类型', '0', 4, NULL);
INSERT INTO LOC_DICT VALUES (NULL, 'platform', '平台机构', 'org_type', 1, NULL);
INSERT INTO LOC_DICT VALUES (NULL, 'supervise', '监管机构', 'org_type', 2, NULL);
INSERT INTO LOC_DICT VALUES (NULL, 'operate', '运营机构', 'org_type', 3, NULL);
