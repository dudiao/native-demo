-- create table nd_user
-- (
--     id            IDENTITY NOT NULL PRIMARY KEY,
--     name          varchar(48) NULL DEFAULT '',  -- 姓名
--     email         varchar(128) NULL DEFAULT '', -- 邮箱
--     wechat_openid varchar(128) NULL DEFAULT '', -- 微信OpenId
--     description   text NULL DEFAULT '',         -- 说明
--     create_time   timestamp NULL                -- 创建时间
-- );

COMMENT ON COLUMN nd_user.name IS '姓名';
COMMENT ON COLUMN nd_user.email IS '邮箱';
COMMENT ON COLUMN nd_user.wechat_openid IS '微信OpenId';
COMMENT ON COLUMN nd_user.description IS '说明';
COMMENT ON COLUMN nd_user.create_time IS '创建时间';

insert into nd_user(id, name, email, wechat_openid, description, create_time) values ('1', '系统管理员', 'admin@nboot.cn', 'xxx', '内置用户', CURRENT_TIMESTAMP);
insert into nd_user(id, name, email, wechat_openid, description, create_time) values ('2', '测试用户', 'test@nboot.cn', 'xxx', '内置用户', CURRENT_TIMESTAMP);
