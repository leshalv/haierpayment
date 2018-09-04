drop table PL_TASK_SCHEDULE;
drop table PL_TASK_SCHEDULE_PHASE;
drop table PL_SCHEDULE_RULE;
drop table PL_SCHEDULE_RULE_PHASE;
drop table PL_LOG_INFO;
drop table PL_ROUTE_RULE;
drop table PL_ROUTE_RULE_ITEM;

--任务调度表
create table PL_TASK_SCHEDULE(
   ID                   varchar2(36)                   not null,
   TASK_SCHEDULE_ID     varchar2(36)                   not null,
   ASSETS_SPLIT_ITEM_ID varchar2(36)                   not null,
   ASSETS_SPLIT_ID      varchar2(36)                   not null,
   DISPATCH_RULE_ID     varchar2(36)                   not null,
   EVENT_TYPE           varchar2(2),
   STATUS               VARCHAR2(2)                    not null,
   CREATE_DATE          date                           not null,
   UPDATE_DATE          date,
   CREATE_BY            varchar2(36)                   not null,
   UPDATE_BY            varchar2(36),
   DEL_FLAG             varchar2(6)                    not null,
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_TASK_SCHEDULE add constraint PK_PL_TASK_SCHEDULE primary key(ID);

comment on table PL_TASK_SCHEDULE is '任务调度表';
comment on column PL_TASK_SCHEDULE.ID is '主键ID:4位固定标识+yyyyMMddhh24miss+8位序列(不足8位前面补0,多余8位截取后8位)';
comment on column PL_TASK_SCHEDULE.TASK_SCHEDULE_ID is '任务调度ID';
comment on column PL_TASK_SCHEDULE.ASSETS_SPLIT_ITEM_ID is '资产拆分表ID';
comment on column PL_TASK_SCHEDULE.ASSETS_SPLIT_ID is '资产表主键ID';
comment on column PL_TASK_SCHEDULE.DISPATCH_RULE_ID is '调度规则表主键ID';
comment on column PL_TASK_SCHEDULE.EVENT_TYPE is '事件类型';
comment on column PL_TASK_SCHEDULE.STATUS is '状态:00-未启动,10-流转中,20-废弃,30- 结束';
comment on column PL_TASK_SCHEDULE.CREATE_DATE is '创建时间';
comment on column PL_TASK_SCHEDULE.UPDATE_DATE is '修改时间';
comment on column PL_TASK_SCHEDULE.CREATE_BY is '创建人';
comment on column PL_TASK_SCHEDULE.UPDATE_BY is '修改人';
comment on column PL_TASK_SCHEDULE.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_TASK_SCHEDULE.MEMO1 is '备注1';
comment on column PL_TASK_SCHEDULE.MEMO2 is '备注2';
comment on column PL_TASK_SCHEDULE.MEMO3 is '备注3';
comment on column PL_TASK_SCHEDULE.MEMO4 is '备注4';
comment on column PL_TASK_SCHEDULE.MEMO5 is '备注5';

--调度明细表
create table PL_TASK_SCHEDULE_PHASE(
   ID                   varchar2(36)                   not null,
   TASK_SCHEDULE_ID     varchar2(36)                   not null,
   ASSETS_SPLIT_ITEM_ID varchar2(36)                   not null,
   ASSETS_SPLIT_ID      varchar2(36)                   not null,
   DISPATCH_RULE_ID     varchar2(36)                   not null,
   SCHEDULE_START_TIME  date,
   SCHEDULE_NUM         varchar2(2)                    not null,
   TASK_STATUS          varchar2(2)                    not null,
   TASK_ORDER           varchar2(2),
   TASK_EXECUTION_MODEL varchar2(300)                  not null,
   PREVIOUS_AFTER_WORK_TIME number(9),
   EXCEPTION_RETRY_TIMES varchar2(2)                    not null,
   EXCEPTION_OVERTIME   number(9),
   EXCEPTION_NOTIFY     varchar2(300),
   CREATE_DATE          date                           not null,
   UPDATE_DATE          date,
   CREATE_BY            varchar2(36)                   not null,
   UPDATE_BY            varchar2(36),
   DEL_FLAG             varchar2(6)                    not null,
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_TASK_SCHEDULE_PHASE add constraint PK_PL_TASK_SCHEDULE_PHASE primary key(ID);

comment on table PL_TASK_SCHEDULE_PHASE is '调度明细表';
comment on column PL_TASK_SCHEDULE_PHASE.ID is '主键ID:4位固定标识+yyyyMMddhh24miss+8位序列(不足8位前面补0,多余8位截取后8位)';
comment on column PL_TASK_SCHEDULE_PHASE.TASK_SCHEDULE_ID is '任务调度表ID';
comment on column PL_TASK_SCHEDULE_PHASE.ASSETS_SPLIT_ITEM_ID is '拆分明细表ID';
comment on column PL_TASK_SCHEDULE_PHASE.ASSETS_SPLIT_ID is '资产表ID';
comment on column PL_TASK_SCHEDULE_PHASE.DISPATCH_RULE_ID is '调度规则编号';
comment on column PL_TASK_SCHEDULE_PHASE.SCHEDULE_START_TIME is '规则开始执行时间';
comment on column PL_TASK_SCHEDULE_PHASE.SCHEDULE_NUM is '任务执行次数';
comment on column PL_TASK_SCHEDULE_PHASE.TASK_STATUS is '任务状态:00-未启动,10-流转中,20-废弃,30- 结束';
comment on column PL_TASK_SCHEDULE_PHASE.TASK_ORDER is '执行顺序';
comment on column PL_TASK_SCHEDULE_PHASE.TASK_EXECUTION_MODEL is '任务结束方式:1-自动完成,2-驱动完成';
comment on column PL_TASK_SCHEDULE_PHASE.PREVIOUS_AFTER_WORK_TIME is '上一事件执行完多久执行本次任务:秒';
comment on column PL_TASK_SCHEDULE_PHASE.EXCEPTION_RETRY_TIMES is '异常重试次数:次';
comment on column PL_TASK_SCHEDULE_PHASE.EXCEPTION_OVERTIME is '异常超时时间:秒';
comment on column PL_TASK_SCHEDULE_PHASE.EXCEPTION_NOTIFY is '异常通知';
comment on column PL_TASK_SCHEDULE_PHASE.CREATE_DATE is '创建时间';
comment on column PL_TASK_SCHEDULE_PHASE.UPDATE_DATE is '修改时间';
comment on column PL_TASK_SCHEDULE_PHASE.CREATE_BY is '创建人';
comment on column PL_TASK_SCHEDULE_PHASE.UPDATE_BY is '修改人';
comment on column PL_TASK_SCHEDULE_PHASE.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_TASK_SCHEDULE_PHASE.MEMO1 is '备注1';
comment on column PL_TASK_SCHEDULE_PHASE.MEMO2 is '备注2';
comment on column PL_TASK_SCHEDULE_PHASE.MEMO3 is '备注3';
comment on column PL_TASK_SCHEDULE_PHASE.MEMO4 is '备注4';
comment on column PL_TASK_SCHEDULE_PHASE.MEMO5 is '备注5';

--调度规则表
create table PL_SCHEDULE_RULE(
   ID                   VARCHAR2(36)                   not null,
   PROJECT_ID           VARCHAR2(36)                   not null,
   RULE_TYPE            VARCHAR(2)                     not null,
   RULE_USER            VARCHAR2(100)                  not null,
   RULE_USER_PRODUCT_NO VARCHAR2(36)                   not null,
   CUSTOMER_TAG         VARCHAR2(36),
   CREATE_DATE          date                           not null,
   UPDATE_DATE          date,
   CREATE_BY            varchar2(36)                   not null,
   UPDATE_BY            varchar2(36),
   DEL_FLAG             varchar2(6)                    not null,
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_SCHEDULE_RULE add constraint PK_PL_SCHEDULE_RULE primary key(ID);

comment on table PL_SCHEDULE_RULE is '调度规则表';
comment on column PL_SCHEDULE_RULE.ID is '主键ID:自增长';
comment on column PL_SCHEDULE_RULE.PROJECT_ID is '合作项目表主键ID';
comment on column PL_SCHEDULE_RULE.RULE_TYPE is '规则类型:0-实时,1-异步';
comment on column PL_SCHEDULE_RULE.RULE_USER is '使用方';
comment on column PL_SCHEDULE_RULE.RULE_USER_PRODUCT_NO is '使用方产品编号';
comment on column PL_SCHEDULE_RULE.CUSTOMER_TAG is '客户标签';
comment on column PL_SCHEDULE_RULE.CREATE_DATE is '创建时间';
comment on column PL_SCHEDULE_RULE.UPDATE_DATE is '修改时间';
comment on column PL_SCHEDULE_RULE.CREATE_BY is '创建人';
comment on column PL_SCHEDULE_RULE.UPDATE_BY is '修改人';
comment on column PL_SCHEDULE_RULE.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_SCHEDULE_RULE.MEMO1 is '备注1';
comment on column PL_SCHEDULE_RULE.MEMO2 is '备注2';
comment on column PL_SCHEDULE_RULE.MEMO3 is '备注3';
comment on column PL_SCHEDULE_RULE.MEMO4 is '备注4';
comment on column PL_SCHEDULE_RULE.MEMO5 is '备注5';

--规则阶段任务表
create table PL_SCHEDULE_RULE_PHASE(
   ID                   VARCHAR2(36)                   not null,
   SCHEDULE_RULE_ID     VARCHAR2(36)                   not null,
   TRANSFER_OEDER       NUMBER(2)                      not null,
   EVENT_TYPE           VARCHAR2(20)                   not null,
   EVENT_EXECUTION_MODEL VARCHAR2(20)                   not null,
   PREVIOUS_AFTER_WORK_TIME NUMBER(10),
   EXCEPTION_RETRY_TIME NUMBER,
   EXCEPTION_OVERTIME   NUMBER,
   EXCEPTION_NOTIFY_API VARCHAR2(100),
   CREATE_DATE          date                           not null,
   UPDATE_DATE          date,
   CREATE_BY            varchar2(36)                   not null,
   UPDATE_BY            varchar2(36),
   DEL_FLAG             varchar2(6)                    not null,
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_SCHEDULE_RULE_PHASE add constraint PK_PL_SCHEDULE_RULE_PHASE primary key(ID);

comment on table PL_SCHEDULE_RULE_PHASE is '规则阶段任务表';
comment on column PL_SCHEDULE_RULE_PHASE.ID is '主键ID自增长';
comment on column PL_SCHEDULE_RULE_PHASE.SCHEDULE_RULE_ID is '调度规则表主键ID';
comment on column PL_SCHEDULE_RULE_PHASE.TRANSFER_OEDER is '流转顺序1-消金放款,2-合作机构放款';
comment on column PL_SCHEDULE_RULE_PHASE.EVENT_TYPE is '事件类型:1-主动执行,2-驱动执行';
comment on column PL_SCHEDULE_RULE_PHASE.EVENT_EXECUTION_MODEL is '事件结束方式';
comment on column PL_SCHEDULE_RULE_PHASE.PREVIOUS_AFTER_WORK_TIME is '上一任务执行完多久执行本次任务:秒';
comment on column PL_SCHEDULE_RULE_PHASE.EXCEPTION_RETRY_TIME is '异常重试次数:次';
comment on column PL_SCHEDULE_RULE_PHASE.EXCEPTION_OVERTIME is '异常超时时间:秒';
comment on column PL_SCHEDULE_RULE_PHASE.EXCEPTION_NOTIFY_API is '异常通知';
comment on column PL_SCHEDULE_RULE_PHASE.CREATE_DATE is '创建时间';
comment on column PL_SCHEDULE_RULE_PHASE.UPDATE_DATE is '修改时间';
comment on column PL_SCHEDULE_RULE_PHASE.CREATE_BY is '创建人';
comment on column PL_SCHEDULE_RULE_PHASE.UPDATE_BY is '修改人';
comment on column PL_SCHEDULE_RULE_PHASE.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_SCHEDULE_RULE_PHASE.MEMO1 is '备注1';
comment on column PL_SCHEDULE_RULE_PHASE.MEMO2 is '备注2';
comment on column PL_SCHEDULE_RULE_PHASE.MEMO3 is '备注3';
comment on column PL_SCHEDULE_RULE_PHASE.MEMO4 is '备注4';
comment on column PL_SCHEDULE_RULE_PHASE.MEMO5 is '备注5';

--日志操作记录表
create table PL_LOG_INFO(
   ID                   VARCHAR2(36)                   not null,
   BUSINESS_TYPE        varchar2(2)                    not null,
   LOG_NAME             varchar2(300)                  not null,
   LOG_TYPE             varchar2(2)                    not null,
   LOG_CONTENT          varchar2(2000)                 not null,
   CREATE_DATE          date                           not null,
   UPDATE_DATE          date,
   CREATE_BY            varchar2(36)                   not null,
   UPDATE_BY            varchar2(36),
   DEL_FLAG             varchar2(6)                    not null,
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_LOG_INFO add constraint PK_PL_LOG_INFO primary key(ID);

comment on table PL_LOG_INFO is '日志操作记录表';
comment on column PL_LOG_INFO.ID is '主键ID:4位固定标识+yyyyMMddhh24miss+8位序列(不足8位前面补0,多余8位截取后8位)';
comment on column PL_LOG_INFO.BUSINESS_TYPE is '业务类型：数据字典PL0104(1-新增,2-修改,3-删除,4-全部)';
comment on column PL_LOG_INFO.LOG_NAME is '名称';
comment on column PL_LOG_INFO.LOG_TYPE is '日志类型:1-管理端(数据字典),2-管理端(机构),3-管理端(项目),4-前置,5-处理中心,6-路由';
comment on column PL_LOG_INFO.LOG_CONTENT is '客户标签';
comment on column PL_LOG_INFO.CREATE_DATE is '创建时间';
comment on column PL_LOG_INFO.UPDATE_DATE is '修改时间';
comment on column PL_LOG_INFO.CREATE_BY is '创建人';
comment on column PL_LOG_INFO.UPDATE_BY is '修改人';
comment on column PL_LOG_INFO.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_LOG_INFO.MEMO1 is '备注1';
comment on column PL_LOG_INFO.MEMO2 is '备注2';
comment on column PL_LOG_INFO.MEMO3 is '备注3';
comment on column PL_LOG_INFO.MEMO4 is '备注4';
comment on column PL_LOG_INFO.MEMO5 is '备注5';

--路由规则表
create table PL_ROUTE_RULE(
   ID                   varchar2(36)                   not null,
   MATE_RULE            varchar2(3)                    not null,
   AGENCY_ID            varchar2(36)                   not null,
   PROJECT_ID           varchar2(36)                   not null,
   PRIORITY             varchar2(3)                    not null,
   CREATE_DATE          date                           not null,
   UPDATE_DATE          date,
   CREATE_BY            varchar2(36)                   not null,
   UPDATE_BY            varchar2(36),
   DEL_FLAG             varchar2(6)                    not null,
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_ROUTE_RULE add constraint PK_PL_ROUTE_RULE primary key(ID);

comment on table PL_ROUTE_RULE is '路由规则表';
comment on column PL_ROUTE_RULE.ID is '主键id:自增长';
comment on column PL_ROUTE_RULE.MATE_RULE is '匹配规则:P-产品,C-渠道,S-标签,PC-产品+渠道,PS-产品+标签,CS-渠道+标签,PCS-产品+渠道+标签';
comment on column PL_ROUTE_RULE.AGENCY_ID is '合作机构表主键ID';
comment on column PL_ROUTE_RULE.PROJECT_ID is '合作项目主键ID';
comment on column PL_ROUTE_RULE.PRIORITY is '优先级,从1开始，数值越大优先级越大';
comment on column PL_ROUTE_RULE.CREATE_DATE is '创建时间';
comment on column PL_ROUTE_RULE.UPDATE_DATE is '修改时间';
comment on column PL_ROUTE_RULE.CREATE_BY is '创建人';
comment on column PL_ROUTE_RULE.UPDATE_BY is '修改人';
comment on column PL_ROUTE_RULE.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_ROUTE_RULE.MEMO1 is '备注1';
comment on column PL_ROUTE_RULE.MEMO2 is '备注2';
comment on column PL_ROUTE_RULE.MEMO3 is '备注3';
comment on column PL_ROUTE_RULE.MEMO4 is '备注4';
comment on column PL_ROUTE_RULE.MEMO5 is '备注5';

--路由规则明细表
create table PL_ROUTE_RULE_ITEM(
   ID                   varchar2(36)                   not null,
   ROUTE_RULE_ID        varchar2(36)                   not null,
   BUSINESS_TYPE        varchar2(2)                    not null,
   RULE_CONTENT         varchar2(36)                   not null,
   CREATE_DATE          date                           not null,
   UPDATE_DATE          date,
   CREATE_BY            varchar2(36)                   not null,
   UPDATE_BY            varchar2(36),
   DEL_FLAG             varchar2(6)                    not null,
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_ROUTE_RULE_ITEM add constraint PK_PL_ROUTE_RULE_ITEM primary key(ID);

comment on table PL_ROUTE_RULE_ITEM is '路由规则明细表';
comment on column PL_ROUTE_RULE_ITEM.ID is '主键id:自增长';
comment on column PL_ROUTE_RULE_ITEM.ROUTE_RULE_ID is '路由规则表主键ID';
comment on column PL_ROUTE_RULE_ITEM.BUSINESS_TYPE is '业务类型：数据字典PL0404(P-产品,C-渠道,S-标签)';
comment on column PL_ROUTE_RULE_ITEM.RULE_CONTENT is '规则内容:业务类型对应的参数值';
comment on column PL_ROUTE_RULE_ITEM.CREATE_DATE is '创建时间';
comment on column PL_ROUTE_RULE_ITEM.UPDATE_DATE is '修改时间';
comment on column PL_ROUTE_RULE_ITEM.CREATE_BY is '创建人';
comment on column PL_ROUTE_RULE_ITEM.UPDATE_BY is '修改人';
comment on column PL_ROUTE_RULE_ITEM.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_ROUTE_RULE_ITEM.MEMO1 is '备注1';
comment on column PL_ROUTE_RULE_ITEM.MEMO2 is '备注2';
comment on column PL_ROUTE_RULE_ITEM.MEMO3 is '备注3';
comment on column PL_ROUTE_RULE_ITEM.MEMO4 is '备注4';
comment on column PL_ROUTE_RULE_ITEM.MEMO5 is '备注5';