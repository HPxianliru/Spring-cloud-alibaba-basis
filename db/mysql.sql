

CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';


CREATE TABLE `sys_user_agency` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `iphone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `user_id` bigint(20) DEFAULT not null COMMENT '用户表id',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `valid` tinyint(1) NOT NULL COMMENT '有效 1有效 0 无效',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',

  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='登录账号';



insert into `sys_zuul_route` ( `update_time`, `del_flag`, `enabled`, `retryable`, `service_id`, `create_time`, `path`, `strip_prefix`, `sensitiveHeaders_list`, `url`) values ( '2019-10-30 08:11:51', '0', '1', '1', 'cloud-discovery-client', '2018-05-21 11:40:38', '/client/**', '1', 'X-ABC,X-Foo', '');