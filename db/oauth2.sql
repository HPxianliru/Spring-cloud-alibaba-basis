-- 官方sql地址 https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql


-- used in tests that use HSQL
create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY COMMENT '用于唯一标识每一个客户端(client); 在注册时必须填写(也可由服务端自动生成).',
  resource_ids VARCHAR(256) COMMENT '客户端所能访问的资源id集合,多个资源时用逗号(,)分隔,如: "unity-resource,mobile-resource".',
  client_secret VARCHAR(256) COMMENT '用于指定客户端(client)的访问密匙; 在注册时必须填写(也可由服务端自动生成).',
  scope VARCHAR(256) COMMENT '指定客户端申请的权限范围,可选值包括read,write,trust;若有多个权限范围用逗号(,)分隔,如: "read,write".',
  authorized_grant_types VARCHAR(256) COMMENT '指定客户端支持的grant_type,可选值包括authorization_code,password,refresh_token,implicit,client_credentials, 若支持多个grant_type用逗号(,)分隔,如: "authorization_code,password".',
  web_server_redirect_uri VARCHAR(256) COMMENT '客户端的重定向URI,可为空, 当grant_type为authorization_code或implicit时, 在Oauth的流程中会使用并检查与注册时填写的redirect_uri是否一致',
  authorities VARCHAR(256) COMMENT '指定客户端所拥有的Spring Security的权限值,可选, 若有多个权限值,用逗号(,)分隔, 如: "ROLE_UNITY,ROLE_USER".',
  access_token_validity INTEGER COMMENT '设定客户端的access_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时).',
  refresh_token_validity INTEGER COMMENT '设定客户端的refresh_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 24 * 30, 30天).',
  additional_information VARCHAR(4096) COMMENT '这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据,如:{"country":"CN","country_code":"086"}',
  autoapprove VARCHAR(256) COMMENT '设置用户是否自动Approval操作, 默认值为 false, 可选值包括 true,false, read,write'
);

create table oauth_client_token (
  token_id VARCHAR(256) COMMENT '从服务器端获取到的access_token的值.',
  token LONGVARBINARY COMMENT '这是一个二进制的字段, 存储的数据是OAuth2AccessToken.java对象序列化后的二进制数据',
  authentication_id VARCHAR(256) PRIMARY KEY COMMENT '该字段具有唯一性, 是根据当前的username(如果有),client_id与scope通过MD5加密生成的.具体实现请参考DefaultClientKeyGenerator.java类',
  user_name VARCHAR(256) COMMENT '登录时的用户名',
  client_id VARCHAR(256)
);

create table oauth_access_token (
  token_id VARCHAR(256) COMMENT '该字段的值是将access_token的值通过MD5加密后存储的.',
  token LONGVARBINARY COMMENT '	存储将OAuth2AccessToken.java对象序列化后的二进制数据, 是真实的AccessToken的数据值',
  authentication_id VARCHAR(256) PRIMARY KEY COMMENT '该字段具有唯一性, 其值是根据当前的username(如果有),client_id与scope通过MD5加密生成的. 具体实现请参考DefaultAuthenticationKeyGenerator.java类.',
  user_name VARCHAR(256) COMMENT '登录时的用户名, 若客户端没有用户名(如grant_type="client_credentials"),则该值等于client_id',
  client_id VARCHAR(256) COMMENT '主键ID',
  authentication LONGVARBINARY COMMENT '存储将OAuth2Authentication.java对象序列化后的二进制数据.',
  refresh_token VARCHAR(256) COMMENT '该字段的值是将refresh_token的值通过MD5加密后存储的.'
);

create table oauth_refresh_token (
  token_id VARCHAR(256) COMMENT '该字段的值是将refresh_token的值通过MD5加密后存储的.',
  token LONGVARBINARY COMMENT '存储将OAuth2RefreshToken.java对象序列化后的二进制数据.',
  authentication LONGVARBINARY
);

create table oauth_code (
  code VARCHAR(256) COMMENT '存储服务端系统生成的code的值(未加密).',
   authentication LONGVARBINARY
);

create table oauth_approvals (
	userId VARCHAR(256) COMMENT '主键ID',
	clientId VARCHAR(256) COMMENT '主键ID',
	scope VARCHAR(256) COMMENT '主键ID',
	status VARCHAR(10) COMMENT '主键ID',
	expiresAt TIMESTAMP COMMENT '主键ID',
	lastModifiedAt TIMESTAMP
);


-- customized oauth_client_details table
create table ClientDetails (
  appId VARCHAR(256) PRIMARY KEY COMMENT '主键ID',
  resourceIds VARCHAR(256) COMMENT '主键ID',
  appSecret VARCHAR(256) COMMENT '主键ID',
  scope VARCHAR(256) COMMENT '主键ID',
  grantTypes VARCHAR(256) COMMENT '主键ID',
  redirectUrl VARCHAR(256) COMMENT '主键ID',
  authorities VARCHAR(256) COMMENT '主键ID',
  access_token_validity INTEGER COMMENT '主键ID',
  refresh_token_validity INTEGER COMMENT '主键ID',
  additionalInformation VARCHAR(4096) COMMENT '主键ID',
  autoApproveScopes VARCHAR(256)
);
