create table nd_user (
    id varchar(64) NOT NULL,
    name varchar(48) NULL DEFAULT '', -- 姓名
    email varchar(128) NULL DEFAULT '', -- 邮箱
    wechat_openid varchar(128) NULL DEFAULT '', -- 微信OpenId
    description text NULL DEFAULT '', -- 说明
    create_time timestamp NULL -- 创建时间
);

-- COMMENT ON COLUMN nd_user.name IS '姓名';
-- COMMENT ON COLUMN nd_user.email IS '邮箱';
-- COMMENT ON COLUMN nd_user.wechat_openid IS '微信OpenId';
-- COMMENT ON COLUMN nd_user.description IS '说明';
-- COMMENT ON COLUMN nd_user.create_time IS '创建时间';
