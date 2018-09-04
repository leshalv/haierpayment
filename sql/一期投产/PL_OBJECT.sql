--额度表
create table PL_QUOTA(
   ID                   varchar2(36)                   not null,
   CINO_MEMNO           varchar2(60),
   APPL_DATE            varchar2(10),
   COOPR_AGENCY_ID      varchar2(50),
   COOPR_AGENCY_NAME    varchar2(60),
   CERT_CODE            varchar2(50),
   COOPR_USER_ID        varchar2(50),
   STATUS               varchar2(2),
   DEF_MSG              varchar2(60),
   ORG_CORP_MSG_ID      varchar2(50),
   ASSETS_SPLIT_ITEM_ID varchar2(36)                   not null,
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
alter table PL_QUOTA add constraint PK_PL_QUOTA primary key(ID);

comment on table PL_QUOTA is '额度表';
comment on column PL_QUOTA.ID is '主键ID';
comment on column PL_QUOTA.CINO_MEMNO is '额度编号';
comment on column PL_QUOTA.APPL_DATE is '申请时间';
comment on column PL_QUOTA.COOPR_AGENCY_ID is '合作机构编号';
comment on column PL_QUOTA.COOPR_AGENCY_NAME is '合作机构名称';
comment on column PL_QUOTA.CERT_CODE is '身份证号';
comment on column PL_QUOTA.COOPR_USER_ID is '客户编号';
comment on column PL_QUOTA.STATUS is '状态：数据字典PL140(0-无效,1-有效,2-授信申请中)';
comment on column PL_QUOTA.DEF_MSG is '备注';
comment on column PL_QUOTA.ORG_CORP_MSG_ID is '原消息ID（授信查询用）';
comment on column PL_QUOTA.ASSETS_SPLIT_ITEM_ID is '资产拆分明细表主键ID';
comment on column PL_QUOTA.CREATE_DATE is '创建时间';
comment on column PL_QUOTA.UPDATE_DATE is '修改时间';
comment on column PL_QUOTA.CREATE_BY is '创建人';
comment on column PL_QUOTA.UPDATE_BY is '修改人';
comment on column PL_QUOTA.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_QUOTA.MEMO1 is '备注1';
comment on column PL_QUOTA.MEMO2 is '备注2';
comment on column PL_QUOTA.MEMO3 is '备注3';
comment on column PL_QUOTA.MEMO4 is '备注4';
comment on column PL_QUOTA.MEMO5 is '备注5';

--入账分录表
create table PL_ACCOUNT_ENTRY(
   ID                   varchar2(36)                   not null,
   TRANS_ID             varchar2(36)                   not null,
   ACCOUNT_ID           varchar2(36),
   ACCOUNT_TYPE         varchar2(32)                   not null,
   VOUCHER_NO           varchar2(32)                   not null,
   IS_CONTROL           varchar2(32)                   not null,
   CONTROL_VALUE        varchar2(128),
   CONTROL_TYPE         varchar2(32),
   AMOUNT               number(15,2)                   not null,
   BALANCE_DIRECTION    varchar2(32)                   not null,
   FREEZE_ID            varchar2(32),
   IS_CORRECT           varchar2(32)                   not null,
   REMARK               varchar2(128),
   SUMMARY              varchar2(512),
   ASS_CHECK_TYPE       varchar2(32),
   ASS_CHECK_CODE       varchar2(32),
   TRADE_FINISH_TIME    date                           not null,
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
alter table PL_ACCOUNT_ENTRY add constraint PK_PL_ACCOUNT_ENTRY primary key(ID);

comment on table PL_ACCOUNT_ENTRY is '入账分录表';
comment on column PL_ACCOUNT_ENTRY.ID is '主键ID';
comment on column PL_ACCOUNT_ENTRY.TRANS_ID is '入账事务ID';
comment on column PL_ACCOUNT_ENTRY.ACCOUNT_ID is '账户ID';
comment on column PL_ACCOUNT_ENTRY.ACCOUNT_TYPE is '账户类型';
comment on column PL_ACCOUNT_ENTRY.VOUCHER_NO is '凭证号';
comment on column PL_ACCOUNT_ENTRY.IS_CONTROL is '是否受控：数据字典PL0105(YES-是,NO-否)';
comment on column PL_ACCOUNT_ENTRY.CONTROL_VALUE is '受控条件';
comment on column PL_ACCOUNT_ENTRY.CONTROL_TYPE is '受控类型：数据字典PL1201(LOANRECEIPT-借据号)';
comment on column PL_ACCOUNT_ENTRY.AMOUNT is '金额';
comment on column PL_ACCOUNT_ENTRY.BALANCE_DIRECTION is '余额方向：数据字典PL1202(WITHHOLD-扣款,RECHARGE-充值)';
comment on column PL_ACCOUNT_ENTRY.FREEZE_ID is '冻结流水号';
comment on column PL_ACCOUNT_ENTRY.IS_CORRECT is '是否冲正：数据字典PL0105(YES-是,NO-否)';
comment on column PL_ACCOUNT_ENTRY.REMARK is '备注';
comment on column PL_ACCOUNT_ENTRY.SUMMARY is '摘要';
comment on column PL_ACCOUNT_ENTRY.ASS_CHECK_TYPE is '辅助类型：数据字典PL1203(0011-支付渠道)';
comment on column PL_ACCOUNT_ENTRY.ASS_CHECK_CODE is '辅助代码';
comment on column PL_ACCOUNT_ENTRY.TRADE_FINISH_TIME is '交易完成时间';
comment on column PL_ACCOUNT_ENTRY.CREATE_DATE is '创建时间';
comment on column PL_ACCOUNT_ENTRY.UPDATE_DATE is '修改时间';
comment on column PL_ACCOUNT_ENTRY.CREATE_BY is '创建人';
comment on column PL_ACCOUNT_ENTRY.UPDATE_BY is '修改人';
comment on column PL_ACCOUNT_ENTRY.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_ACCOUNT_ENTRY.MEMO1 is '备注1';
comment on column PL_ACCOUNT_ENTRY.MEMO2 is '备注2';
comment on column PL_ACCOUNT_ENTRY.MEMO3 is '备注3';
comment on column PL_ACCOUNT_ENTRY.MEMO4 is '备注4';
comment on column PL_ACCOUNT_ENTRY.MEMO5 is '备注5';

--入账事务表
create table PL_ACCOUNT_TRANSACTION(
   ID                   varchar2(36)                   not null,
   TRANS_NO             varchar2(32)                   not null,
   CONTEXT_TRANS_NO     varchar2(32),
   ORDER_ID             varchar2(32)                   not null,
   BIZ_TYPE             varchar2(32)                   not null,
   TRADE_LINK           varchar2(32)                   not null,
   APP_ID               varchar2(32)                   not null,
   OPERATOR_ID          varchar2(32)                   not null,
   BIZ_ID               varchar2(32),
   IS_BOUNCED           varchar2(32)                   not null,
   REQUEST_TIME         date                           not null,
   IS_CORRECT           varchar2(32),
   CRM_NO               VARCHAR2(32),
   CRM_TYPE             VARCHAR2(32),
   ACCOUNTING_STATUS    varchar2(32),
   CONTRACT_NO          varchar2(32),
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
alter table PL_ACCOUNT_TRANSACTION add constraint PK_PL_ACCOUNT_TRANSACTION primary key(ID);

comment on table PL_ACCOUNT_TRANSACTION is '入账事务表';
comment on column PL_ACCOUNT_TRANSACTION.ID is '主键ID';
comment on column PL_ACCOUNT_TRANSACTION.TRANS_NO is '报账事务号';
comment on column PL_ACCOUNT_TRANSACTION.CONTEXT_TRANS_NO is '关联报账事务号关联报账事务号';
comment on column PL_ACCOUNT_TRANSACTION.ORDER_ID is '系统跟踪号';
comment on column PL_ACCOUNT_TRANSACTION.BIZ_TYPE is '业务类型：数据字典PL1204(CASH-现金,NETB-网银,PPCD-预付费卡,POST-汇款,CARD-银行卡,EXPR-快捷,COLL-代收,TRAN-代付/付款)，数据字典PL1205(CARD_COLLECTION-还款,ACT_COLLECTION-余额还款,ACT_310_COLLECTION-310溢缴款还款,CARD_TRANSFER-放款,RTN_TRANSFER-放款撤销,ACT_TRANSFER-放款至余额,RTN_FEE_TRANSFER-退货手续费,WITHDRAW-未到账提现,SETTLEMENTWITHDRAW-已到账提现,RECHARGE-未到账充值,ARRIVALRECHARGE-已到账充值,FINISHED_AUTO_COLLECTION-已到账主动还款,COO_CARD_COLLECTION-第三方银行卡代收,COO_CARD_TRANSFER-第三方银行卡代付,COO_RTN_CARD_TRANSFER-第三方银行卡代付撤销,COO_COMPENSATORY_TRANSFER-合作机构代偿款转账)';
comment on column PL_ACCOUNT_TRANSACTION.TRADE_LINK is '交易环节';
comment on column PL_ACCOUNT_TRANSACTION.APP_ID is '应用ID：数据字典PL1101(APP-APP,AC-AC,loan_core-信贷核心,pay_core-支付网关,va-账户系统,qidai_01-七贷,aicai_01-爱财,acquirer-收单系统,crm-客户关系管理系统)';
comment on column PL_ACCOUNT_TRANSACTION.OPERATOR_ID is '录入人';
comment on column PL_ACCOUNT_TRANSACTION.BIZ_ID is '业务单号';
comment on column PL_ACCOUNT_TRANSACTION.IS_BOUNCED is '是否退票：数据字典PL0105(YES-是,NO-否)';
comment on column PL_ACCOUNT_TRANSACTION.REQUEST_TIME is '请求时间';
comment on column PL_ACCOUNT_TRANSACTION.IS_CORRECT is '事务是否冲正字段';
comment on column PL_ACCOUNT_TRANSACTION.CRM_NO is '客户编号';
comment on column PL_ACCOUNT_TRANSACTION.CRM_TYPE is '客户类型：数据字典PL1301(STORE-门店,MERCHANT-商户,PERSONAL-个人)';
comment on column PL_ACCOUNT_TRANSACTION.ACCOUNTING_STATUS is '入账状态：数据字典PL1102(NORECORDED-未入账,SUCCESS-入账成功,FAIL-入账失败)';
comment on column PL_ACCOUNT_TRANSACTION.CONTRACT_NO is '合同号';
comment on column PL_ACCOUNT_TRANSACTION.CREATE_DATE is '创建时间';
comment on column PL_ACCOUNT_TRANSACTION.UPDATE_DATE is '修改时间';
comment on column PL_ACCOUNT_TRANSACTION.CREATE_BY is '创建人';
comment on column PL_ACCOUNT_TRANSACTION.UPDATE_BY is '修改人';
comment on column PL_ACCOUNT_TRANSACTION.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_ACCOUNT_TRANSACTION.MEMO1 is '备注1';
comment on column PL_ACCOUNT_TRANSACTION.MEMO2 is '备注2';
comment on column PL_ACCOUNT_TRANSACTION.MEMO3 is '备注3';
comment on column PL_ACCOUNT_TRANSACTION.MEMO4 is '备注4';
comment on column PL_ACCOUNT_TRANSACTION.MEMO5 is '备注5';

--合作机构需求资料表
create table PL_AGENCY_DEMAND_INFO(
   ID                   varchar2(36)                   not null,
   AGENCY_ID            varchar2(36)                   not null,
   DEMAND_TYPE          number(2)                      not null,
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
alter table PL_AGENCY_DEMAND_INFO add constraint PK_PL_AGENCY_DEMAND_INFO primary key(ID);

comment on table PL_AGENCY_DEMAND_INFO is '合作机构需求资料表';
comment on column PL_AGENCY_DEMAND_INFO.ID is '主键ID:增长';
comment on column PL_AGENCY_DEMAND_INFO.AGENCY_ID is '合作机构表主键ID';
comment on column PL_AGENCY_DEMAND_INFO.DEMAND_TYPE is '类型：数据字典PL0301(1-影像,2-风险参数,3-协议模板)';
comment on column PL_AGENCY_DEMAND_INFO.CREATE_DATE is '创建时间';
comment on column PL_AGENCY_DEMAND_INFO.UPDATE_DATE is '修改时间';
comment on column PL_AGENCY_DEMAND_INFO.CREATE_BY is '创建人';
comment on column PL_AGENCY_DEMAND_INFO.UPDATE_BY is '修改人';
comment on column PL_AGENCY_DEMAND_INFO.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_AGENCY_DEMAND_INFO.MEMO1 is '备注1';
comment on column PL_AGENCY_DEMAND_INFO.MEMO2 is '备注2';
comment on column PL_AGENCY_DEMAND_INFO.MEMO3 is '备注3';
comment on column PL_AGENCY_DEMAND_INFO.MEMO4 is '备注4';
comment on column PL_AGENCY_DEMAND_INFO.MEMO5 is '备注5';

--合作机构需求资料明细表
create table PL_AGENCY_DEMAND_ITEM(
   ID                   varchar2(36)                   not null,
   DEMAND_ID            varchar2(36)                   not null,
   MATERIAL_TYPE        varchar2(20)                   not null,
   MATERIAL_NAME        varchar2(50)                   not null,
   MATERIAL_VALUE       varchar2(4000)                 not null,
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
alter table PL_AGENCY_DEMAND_ITEM add constraint PK_PL_AGENCY_DEMAND_ITEM primary key(ID);

comment on table PL_AGENCY_DEMAND_ITEM is '合作机构需求资料明细表';
comment on column PL_AGENCY_DEMAND_ITEM.ID is '主键ID:自增长';
comment on column PL_AGENCY_DEMAND_ITEM.DEMAND_ID is '渠道资料表主键ID';
comment on column PL_AGENCY_DEMAND_ITEM.MATERIAL_TYPE is '资料类型：数据字典PL0302(CREDIT_MODLE-征信模板)';
comment on column PL_AGENCY_DEMAND_ITEM.MATERIAL_NAME is '资料名称';
comment on column PL_AGENCY_DEMAND_ITEM.MATERIAL_VALUE is '资料参数值';
comment on column PL_AGENCY_DEMAND_ITEM.CREATE_DATE is '创建时间';
comment on column PL_AGENCY_DEMAND_ITEM.UPDATE_DATE is '修改时间';
comment on column PL_AGENCY_DEMAND_ITEM.CREATE_BY is '创建人';
comment on column PL_AGENCY_DEMAND_ITEM.UPDATE_BY is '修改人';
comment on column PL_AGENCY_DEMAND_ITEM.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_AGENCY_DEMAND_ITEM.MEMO1 is '备注1';
comment on column PL_AGENCY_DEMAND_ITEM.MEMO2 is '备注2';
comment on column PL_AGENCY_DEMAND_ITEM.MEMO3 is '备注3';
comment on column PL_AGENCY_DEMAND_ITEM.MEMO4 is '备注4';
comment on column PL_AGENCY_DEMAND_ITEM.MEMO5 is '备注5';

--区域编码表（省市区三级）
create table PL_AREA(
   ID                   varchar2(10)                   not null,
   AREA_NAME            varchar2(300),
   PARENT_CODE          varchar2(10)                   not null,
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_AREA add constraint PK_PL_AREA primary key(ID);

comment on table PL_AREA is '区域编码表（省市区三级）';
comment on column PL_AREA.ID is '编码';
comment on column PL_AREA.AREA_NAME is '名称';
comment on column PL_AREA.PARENT_CODE is '上级编码：根目录上级编码为-1';
comment on column PL_AREA.MEMO1 is '备注1';
comment on column PL_AREA.MEMO2 is '备注2';
comment on column PL_AREA.MEMO3 is '备注3';
comment on column PL_AREA.MEMO4 is '备注4';
comment on column PL_AREA.MEMO5 is '备注5';

--资产表
create table PL_ASSETS_SPLIT(
   ID                   varchar2(36)                   not null,
   APPL_SEQ             varchar2(50)                   not null,
   CRM_NUM              varchar2(32),
   CUST_NAME            varchar2(128)                   not null,
   LOAN_NO1             varchar2(30),
   LOAN_NO2             varchar2(30),
   CERT_CODE            varchar2(50),
   MOBILE               number(11),
   TRADE_AMOUNT         number(15,2),
   CURRENCY             varchar2(10),
   CONTRACT_NO          varchar2(50),
   ORIG_PRCP            number(15,2),
   SUB_BUSINESS_TYPE    varchar2(50),
   PAYEE_CARD_NO        varchar2(32),
   PAYEE_NAME           varchar2(128),
   CRM_TYPE             varchar2(10),
   CRM_NO               varchar2(20),
   CERT_TYPE            varchar2(10),
   CERT_NO              varchar2(20),
   BANK_CODE            varchar2(32),
   BANK_CARD_TYPE       varchar2(10),
   BANK_UNION_CODE      varchar2(32),
   CHANNEL_NATURE       varchar2(15),
   INTER_ID             varchar2(10),
   OPENING_BANK_PROVINCE varchar2(80),
   OPENING_BANK_CITY    varchar2(80),
   BALANCE_PAY_TAG      varchar2(10),
   TOTAL_AMOUNT         number(15,2),
   IS_HAS_MIDD_ACCT     varchar2(32),
   IN_ACTNO             varchar2(32),
   IN_ACTNAME           varchar2(128),
   IN_ACTNO2            varchar2(32),
   IN_ACTNAME2          varchar2(128),
   IND_COMMP_CODE       varchar2(32),
   PAY_CODE             varchar2(32),
   JKR_NAME             varchar2(128),
   ACT_CHANNEL          varchar2(10),
   PROJECT_TYPE         varchar2(10),
   PROD_BUY_OUT         varchar2(50),
   LOAN_STATUS          varchar2(10),
   CHANNEL_NO           varchar2(10),
   REQUEST_IP           varchar2(15),
   MEMO                 varchar2(300),
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
alter table PL_ASSETS_SPLIT add constraint PK_PL_ASSETS_SPLIT primary key(ID);

comment on table PL_ASSETS_SPLIT is '资产表';
comment on column PL_ASSETS_SPLIT.ID is '主键ID:4位固定标识+yyyyMMddhh24miss+8位序列(不足8位前面补0,多余8位截取后8位)';
comment on column PL_ASSETS_SPLIT.APPL_SEQ is '消金业务编号';
comment on column PL_ASSETS_SPLIT.CRM_NUM is '客户编号';
comment on column PL_ASSETS_SPLIT.CUST_NAME is '客户姓名';
comment on column PL_ASSETS_SPLIT.LOAN_NO1 is '自主借据号';
comment on column PL_ASSETS_SPLIT.LOAN_NO2 is '资方借据号';
comment on column PL_ASSETS_SPLIT.CERT_CODE is '证件号码';
comment on column PL_ASSETS_SPLIT.MOBILE is '手机号';
comment on column PL_ASSETS_SPLIT.TRADE_AMOUNT is '交易金额（斩头后）';
comment on column PL_ASSETS_SPLIT.CURRENCY is '币种';
comment on column PL_ASSETS_SPLIT.CONTRACT_NO is '合同号';
comment on column PL_ASSETS_SPLIT.ORIG_PRCP is '贷款本金';
comment on column PL_ASSETS_SPLIT.SUB_BUSINESS_TYPE is '产品代码';
comment on column PL_ASSETS_SPLIT.PAYEE_CARD_NO is '放款账户';
comment on column PL_ASSETS_SPLIT.PAYEE_NAME is '放款户名';
comment on column PL_ASSETS_SPLIT.CRM_TYPE is '客户类型：数据字典PL1301(STORE-门店,MERCHANT-商户,PERSONAL-个人)';
comment on column PL_ASSETS_SPLIT.CRM_NO is '客户ID';
comment on column PL_ASSETS_SPLIT.CERT_TYPE is '证件类型：数据字典PL0501(ID-身份证,PA-护照,HV-回乡证,TW-台胞证,OF-军官证,PD-警官证,SO-士兵证,MER-商户)';
comment on column PL_ASSETS_SPLIT.CERT_NO is '证件号';
comment on column PL_ASSETS_SPLIT.BANK_CODE is '银行代码(数字)';
comment on column PL_ASSETS_SPLIT.BANK_CARD_TYPE is '银行卡类型：数据字典PL0502(DC-借记卡)';
comment on column PL_ASSETS_SPLIT.BANK_UNION_CODE is '银行联行号';
comment on column PL_ASSETS_SPLIT.CHANNEL_NATURE is '交易属性：数据字典PL0503(P-对私业务,C-对公业务)';
comment on column PL_ASSETS_SPLIT.INTER_ID is '放款渠道';
comment on column PL_ASSETS_SPLIT.OPENING_BANK_PROVINCE is '开户行所在省';
comment on column PL_ASSETS_SPLIT.OPENING_BANK_CITY is '开户行所在市';
comment on column PL_ASSETS_SPLIT.BALANCE_PAY_TAG is '是否附后置余额支付请求：数据字典PL0105(YES-是,NO-否)';
comment on column PL_ASSETS_SPLIT.TOTAL_AMOUNT is '交易总金额';
comment on column PL_ASSETS_SPLIT.IS_HAS_MIDD_ACCT is '是否有工贸账号：数据字典PL0105(YES-是,NO-否)';
comment on column PL_ASSETS_SPLIT.IN_ACTNO is '转入账号';
comment on column PL_ASSETS_SPLIT.IN_ACTNAME is '转入户名';
comment on column PL_ASSETS_SPLIT.IN_ACTNO2 is '再转入账号';
comment on column PL_ASSETS_SPLIT.IN_ACTNAME2 is '再转入户名';
comment on column PL_ASSETS_SPLIT.IND_COMMP_CODE is '公司代码(收款方)';
comment on column PL_ASSETS_SPLIT.PAY_CODE is '客户编码付款方编码';
comment on column PL_ASSETS_SPLIT.JKR_NAME is '借款人姓名';
comment on column PL_ASSETS_SPLIT.ACT_CHANNEL is '记账方式：数据字典PL0504(JDE-JDE记账,SAP-SAP记账)';
comment on column PL_ASSETS_SPLIT.PROJECT_TYPE is '是否联合放款：数据字典PL0505(0-非联合放款,1-联合放款,2-未知)';
comment on column PL_ASSETS_SPLIT.PROD_BUY_OUT is '产品是否支持买断：数据字典PL0106(0-不支持,1-支持,2-未知)';
comment on column PL_ASSETS_SPLIT.LOAN_STATUS is '放款状态：数据字典PL0506(10-已受理,20-网关放款中,30-网关放款成功,40-联合处理中,50-放款成功,60-放款失败,70-已作废)';
comment on column PL_ASSETS_SPLIT.CHANNEL_NO is '渠道号';
comment on column PL_ASSETS_SPLIT.MEMO is '备注';
comment on column PL_ASSETS_SPLIT.CREATE_DATE is '创建时间';
comment on column PL_ASSETS_SPLIT.UPDATE_DATE is '修改时间';
comment on column PL_ASSETS_SPLIT.CREATE_BY is '创建人';
comment on column PL_ASSETS_SPLIT.UPDATE_BY is '修改人';
comment on column PL_ASSETS_SPLIT.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_ASSETS_SPLIT.MEMO1 is '备注1';
comment on column PL_ASSETS_SPLIT.MEMO2 is '备注2';
comment on column PL_ASSETS_SPLIT.MEMO3 is '备注3';
comment on column PL_ASSETS_SPLIT.MEMO4 is '备注4';
comment on column PL_ASSETS_SPLIT.MEMO5 is '备注5';

--资产拆分明细表
create table PL_ASSETS_SPLIT_ITEM(
   ID                   varchar2(36)                   not null,
   ASSETS_SPLIT_ID      varchar2(36)                   not null,
   TRANS_AMT            number(15,2)                   not null,
   STATUS               varchar2(2)                    not null,
   LOAN_NO              varchar2(30)                   not null,
   AGENCY_RATE          number(7,2),
   AGENCY_ID            varchar2(36),
   PROJECT_ID           varchar2(36),
   LOAN_TYPE            varchar2(2),
   APPL_AMT             number(15,2),
   CAP_LOAN_NO          varchar2(50),
   MEMO                 varchar2(300),
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
alter table PL_ASSETS_SPLIT_ITEM add constraint PK_PL_ASSETS_SPLIT_ITEM primary key(ID);

comment on table PL_ASSETS_SPLIT_ITEM is '资产拆分明细表';
comment on column PL_ASSETS_SPLIT_ITEM.ID is '主键ID:4位固定标识+yyyyMMddhh24miss+8位序列(不足8位前面补0,多余8位截取后8位)';
comment on column PL_ASSETS_SPLIT_ITEM.ASSETS_SPLIT_ID is '资产表主键ID';
comment on column PL_ASSETS_SPLIT_ITEM.TRANS_AMT is '放款金额';
comment on column PL_ASSETS_SPLIT_ITEM.STATUS is '状态：数据字典PL0601(10-已受理,11-头寸不足,12-非营业时间,13-资方系统异常,14-数据组装失败,20-额度审批中,21-额度审批拒绝,22-额度审批通过,23-审批额度不足,24-提现审批中,25-提现审批拒绝,26-提现审批通过,30-放款中,31-放款成功,32-放款失败';
comment on column PL_ASSETS_SPLIT_ITEM.LOAN_NO is '借据号';
comment on column PL_ASSETS_SPLIT_ITEM.AGENCY_RATE is '放款占比';
comment on column PL_ASSETS_SPLIT_ITEM.AGENCY_ID is '合作机构主键ID';
comment on column PL_ASSETS_SPLIT_ITEM.PROJECT_ID is '合作项目主键ID';
comment on column PL_ASSETS_SPLIT_ITEM.LOAN_TYPE is '借据类型：数据字典PL0602(1-自有,2-外部资方)';
comment on column PL_ASSETS_SPLIT_ITEM.APPL_AMT is '申请金额';
comment on column PL_ASSETS_SPLIT_ITEM.CAP_LOAN_NO is '资方业务编号';
comment on column PL_ASSETS_SPLIT_ITEM.MEMO is '备注';
comment on column PL_ASSETS_SPLIT_ITEM.CREATE_DATE is '创建时间';
comment on column PL_ASSETS_SPLIT_ITEM.UPDATE_DATE is '修改时间';
comment on column PL_ASSETS_SPLIT_ITEM.CREATE_BY is '创建人';
comment on column PL_ASSETS_SPLIT_ITEM.UPDATE_BY is '修改人';
comment on column PL_ASSETS_SPLIT_ITEM.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_ASSETS_SPLIT_ITEM.MEMO1 is '备注1';
comment on column PL_ASSETS_SPLIT_ITEM.MEMO2 is '备注2';
comment on column PL_ASSETS_SPLIT_ITEM.MEMO3 is '备注3';
comment on column PL_ASSETS_SPLIT_ITEM.MEMO4 is '备注4';
comment on column PL_ASSETS_SPLIT_ITEM.MEMO5 is '备注5';

--余额支付信息列表
create table PL_BALANCE_PAY_INFO(
   ID                   varchar2(36)                   not null,
   ASSETS_SPLIT_ID      varchar2(36)                   not null,
   CRM_NO               varchar2(32),
   CRM_TYPE             varchar2(10)                   not null,
   CUST_NAME            varchar2(128),
   CERT_NO              varchar2(20),
   VA_TYPE              varchar2(10)                   not null,
   PAY_AMT              number(15,2),
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
alter table PL_BALANCE_PAY_INFO add constraint PK_PL_BALANCE_PAY_INFO primary key(ID);

comment on table PL_BALANCE_PAY_INFO is '余额支付信息列表';
comment on column PL_BALANCE_PAY_INFO.ID is '主键ID:4位固定标识+yyyyMMddhh24miss+8位序列(不足8位前面补0,多余8位截取后8位)';
comment on column PL_BALANCE_PAY_INFO.ASSETS_SPLIT_ID is '资产表主键ID';
comment on column PL_BALANCE_PAY_INFO.CRM_NO is '客户编号';
comment on column PL_BALANCE_PAY_INFO.CRM_TYPE is '客户类型：数据字典PL1301(STORE-门店,MERCHANT-商户,PERSONAL-个人)';
comment on column PL_BALANCE_PAY_INFO.CUST_NAME is '客户名称';
comment on column PL_BALANCE_PAY_INFO.CERT_NO is '客户证件号';
comment on column PL_BALANCE_PAY_INFO.VA_TYPE is '收款账户类型';
comment on column PL_BALANCE_PAY_INFO.PAY_AMT is '付款金额';
comment on column PL_BALANCE_PAY_INFO.CREATE_DATE is '创建时间';
comment on column PL_BALANCE_PAY_INFO.UPDATE_DATE is '修改时间';
comment on column PL_BALANCE_PAY_INFO.CREATE_BY is '创建人';
comment on column PL_BALANCE_PAY_INFO.UPDATE_BY is '修改人';
comment on column PL_BALANCE_PAY_INFO.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_BALANCE_PAY_INFO.MEMO1 is '备注1';
comment on column PL_BALANCE_PAY_INFO.MEMO2 is '备注2';
comment on column PL_BALANCE_PAY_INFO.MEMO3 is '备注3';
comment on column PL_BALANCE_PAY_INFO.MEMO4 is '备注4';
comment on column PL_BALANCE_PAY_INFO.MEMO5 is '备注5';

--银行信息表
create table PL_BANK_INFO(
   ID                   varchar2(36)                   not null,
   BANK_NAME          	varchar2(500)                  not null,
   BANK_NO_EN           varchar2(20)                   not null,
   BANK_NO_NUM          varchar2(20)                   not null,
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
alter table PL_BANK_INFO add constraint PK_PL_BANK_INFO primary key(ID);

comment on table PL_BANK_INFO is '银行信息表:维护银行信息,便于设置合作机构支持银行卡';
comment on column PL_BANK_INFO.ID is '主键ID:自增长';
comment on column PL_BANK_INFO.BANK_NAME is '银行名称,例如:浦发银行';
comment on column PL_BANK_INFO.BANK_NO_EN is '银行英文编码,例如:SPDB';
comment on column PL_BANK_INFO.BANK_NO_NUM is '银行数字编码';
comment on column PL_BANK_INFO.CREATE_DATE is '创建时间';
comment on column PL_BANK_INFO.UPDATE_DATE is '修改时间';
comment on column PL_BANK_INFO.CREATE_BY is '创建人';
comment on column PL_BANK_INFO.UPDATE_BY is '修改人';
comment on column PL_BANK_INFO.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_BANK_INFO.MEMO1 is '备注1';
comment on column PL_BANK_INFO.MEMO2 is '备注2';
comment on column PL_BANK_INFO.MEMO3 is '备注3';
comment on column PL_BANK_INFO.MEMO4 is '备注4';
comment on column PL_BANK_INFO.MEMO5 is '备注5';

--客户标签表
create table PL_CASH_CUST_SIGN(
   ID                   varchar2(36)                   not null,
   PRODUCT_ID           varchar2(36)                   not null,
   PROJECT_ID           varchar2(36)                   not null,
   CASH_CUST_SIGN       varchar2(36)                   not null,
   CASH_CUST_SIGN_NAME  varchar2(100)                  not null,
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
alter table PL_CASH_CUST_SIGN add constraint PK_PL_CASH_CUST_SIGN primary key(ID);

comment on table PL_CASH_CUST_SIGN is '客户标签表:从信贷系统中读取';
comment on column PL_CASH_CUST_SIGN.ID is '主键ID';
comment on column PL_CASH_CUST_SIGN.PRODUCT_ID is '合作产品主键ID';
comment on column PL_CASH_CUST_SIGN.PROJECT_ID is '消金产品表主键ID';
comment on column PL_CASH_CUST_SIGN.CASH_CUST_SIGN is '客户标签';
comment on column PL_CASH_CUST_SIGN.CASH_CUST_SIGN_NAME is '客户标签名称';
comment on column PL_CASH_CUST_SIGN.CREATE_DATE is '创建时间';
comment on column PL_CASH_CUST_SIGN.UPDATE_DATE is '修改时间';
comment on column PL_CASH_CUST_SIGN.CREATE_BY is '创建人';
comment on column PL_CASH_CUST_SIGN.UPDATE_BY is '修改人';
comment on column PL_CASH_CUST_SIGN.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_CASH_CUST_SIGN.MEMO1 is '备注1';
comment on column PL_CASH_CUST_SIGN.MEMO2 is '备注2';
comment on column PL_CASH_CUST_SIGN.MEMO3 is '备注3';
comment on column PL_CASH_CUST_SIGN.MEMO4 is '备注4';
comment on column PL_CASH_CUST_SIGN.MEMO5 is '备注5';

--消金产品表
create table PL_CASH_PRODUCT(
   ID                   varchar2(36)                   not null,
   PROJECT_ID           varchar2(36)                   not null,
   CASH_PRODUCT_NO      varchar2(36)                   not null,
   CASH_PRODUCT_NAME    varchar2(100)                  not null,
   ANNUALIZED_RATE      number(16,9)                   not null,
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
alter table PL_CASH_PRODUCT add constraint PK_PL_CASH_PRODUCT primary key(ID);

comment on table PL_CASH_PRODUCT is '消金产品表:从信贷系统中读取';
comment on column PL_CASH_PRODUCT.ID is '主键ID';
comment on column PL_CASH_PRODUCT.PROJECT_ID is '合作项目表主键ID';
comment on column PL_CASH_PRODUCT.CASH_PRODUCT_NO is '产品编号';
comment on column PL_CASH_PRODUCT.CASH_PRODUCT_NAME is '产品名称';
comment on column PL_CASH_PRODUCT.ANNUALIZED_RATE is '年化收益率';
comment on column PL_CASH_PRODUCT.CREATE_DATE is '创建时间';
comment on column PL_CASH_PRODUCT.UPDATE_DATE is '修改时间';
comment on column PL_CASH_PRODUCT.CREATE_BY is '创建人';
comment on column PL_CASH_PRODUCT.UPDATE_BY is '修改人';
comment on column PL_CASH_PRODUCT.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_CASH_PRODUCT.MEMO1 is '备注1';
comment on column PL_CASH_PRODUCT.MEMO2 is '备注2';
comment on column PL_CASH_PRODUCT.MEMO3 is '备注3';
comment on column PL_CASH_PRODUCT.MEMO4 is '备注4';
comment on column PL_CASH_PRODUCT.MEMO5 is '备注5';

--合作机构表
create table PL_COOPERATION_AGENCY(
   ID                     varchar2(36)                   not null,
   AGENCY_NAME            varchar2(500)                  not null,
   AGENCY_LIAISONS        varchar2(300),
   AGENCY_LIAISONS_MOBILE varchar2(300),
   AGENCY_PRIORITY        varchar2(3)                    not null,
   COOPERATION_STATUS     varchar2(2)                    not null,
   AGENCY_TYPE            varchar2(6)                    not null,
   CREATE_DATE            date                           not null,
   UPDATE_DATE            date,
   CREATE_BY              varchar2(36)                   not null,
   UPDATE_BY              varchar2(36),
   DEL_FLAG               varchar2(6)                    not null,
   MEMO1                  varchar2(300),
   MEMO2                  varchar2(300),
   MEMO3                  varchar2(300),
   MEMO4                  varchar2(300),
   MEMO5                  varchar2(300)
);
alter table PL_COOPERATION_AGENCY add constraint PK_PL_COOPERATION_AGENCY primary key(ID);

comment on table PL_COOPERATION_AGENCY is '合作机构表：合作机构表内机构都为资方';
comment on column PL_COOPERATION_AGENCY.ID is '主键ID:uuid';
comment on column PL_COOPERATION_AGENCY.AGENCY_NAME is '合作机构名称,例如:工商银行青岛分行';
comment on column PL_COOPERATION_AGENCY.AGENCY_LIAISONS is '合作机构联系人,例如：张三。若存在多个中间逗号隔开';
comment on column PL_COOPERATION_AGENCY.AGENCY_LIAISONS_MOBILE is '合作机构联系人电话,例如：0532-XXXXXXXX。若多个则用逗号隔开。若为手机号可编辑为133XXXXXXXXX(张三)，该结果只做展示，不做解析';
comment on column PL_COOPERATION_AGENCY.AGENCY_PRIORITY is '优先级,从1开始，数值越大优先级越大';
comment on column PL_COOPERATION_AGENCY.COOPERATION_STATUS is '合作状态：数据字典PL0201(00-未启用,10-启用)';
comment on column PL_COOPERATION_AGENCY.AGENCY_TYPE is '机构类型，数据字典PL0202(ORG01-银行机构,ORG02-担保机构)';
comment on column PL_COOPERATION_AGENCY.CREATE_DATE is '创建时间';
comment on column PL_COOPERATION_AGENCY.UPDATE_DATE is '修改时间';
comment on column PL_COOPERATION_AGENCY.CREATE_BY is '创建人';
comment on column PL_COOPERATION_AGENCY.UPDATE_BY is '修改人';
comment on column PL_COOPERATION_AGENCY.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_COOPERATION_AGENCY.MEMO1 is '备注1';
comment on column PL_COOPERATION_AGENCY.MEMO2 is '备注2';
comment on column PL_COOPERATION_AGENCY.MEMO3 is '备注3';
comment on column PL_COOPERATION_AGENCY.MEMO4 is '备注4';
comment on column PL_COOPERATION_AGENCY.MEMO5 is '备注5';

--合作项目期限表
create table PL_COOPERATION_PERIOD(
   ID                   	varchar2(36)                   not null,
   PROJECT_ID           	varchar2(36)                   not null,
   COOPERATION_PERIOD_VALUE number(10)                     not null,
   COOPERATION_PERIOD_TYPE 	varchar2(2)                    not null,
   CREATE_DATE          	date                           not null,
   UPDATE_DATE         	 	date,
   CREATE_BY            	varchar2(36)                   not null,
   UPDATE_BY            	varchar2(36),
   DEL_FLAG             	varchar2(6)                    not null,
   MEMO1                	varchar2(300),
   MEMO2                	varchar2(300),
   MEMO3                	varchar2(300),
   MEMO4                	varchar2(300),
   MEMO5                	varchar2(300)
);
alter table PL_COOPERATION_PERIOD add constraint PK_PL_COOPERATION_PERIOD primary key(ID);

comment on table PL_COOPERATION_PERIOD is '合作项目期限表';
comment on column PL_COOPERATION_PERIOD.ID is '主键ID';
comment on column PL_COOPERATION_PERIOD.PROJECT_ID is '合作项目表主键ID';
comment on column PL_COOPERATION_PERIOD.COOPERATION_PERIOD_VALUE is '期限值';
comment on column PL_COOPERATION_PERIOD.COOPERATION_PERIOD_TYPE is '期限类型：数据字典PL0408(D-天,M-月)';
comment on column PL_COOPERATION_PERIOD.CREATE_DATE is '创建时间';
comment on column PL_COOPERATION_PERIOD.UPDATE_DATE is '修改时间';
comment on column PL_COOPERATION_PERIOD.CREATE_BY is '创建人';
comment on column PL_COOPERATION_PERIOD.UPDATE_BY is '修改人';
comment on column PL_COOPERATION_PERIOD.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_COOPERATION_PERIOD.MEMO1 is '备注1';
comment on column PL_COOPERATION_PERIOD.MEMO2 is '备注2';
comment on column PL_COOPERATION_PERIOD.MEMO3 is '备注3';
comment on column PL_COOPERATION_PERIOD.MEMO4 is '备注4';
comment on column PL_COOPERATION_PERIOD.MEMO5 is '备注5';

--合作项目表
create table PL_COOPERATION_PROJECT(
   ID                   varchar2(36)                   not null,
   AGENCY_ID            varchar2(36)                   not null,
   PROJECT_NAME         varchar2(500)                  not null,
   PROJECT_TYPE         varchar2(2)                    not null,
   PROJECT_STATUS       varchar2(2)                    not null,
   IS_ASSURE            varchar2(2)                    not null,
   COLLATERAL_ID        varchar2(100),
   EFFECT_TIME          date                           not null,
   LOAN_AMOUNT          number(17,2),
   LOAN_LIMIT_MONTH     number(17,2),
   LOAN_LIMIT_DAY       number(17,2),
   PROJECT_PRIORITY     varchar2(3),
   CASH_YIELD_RATE      number(16,9),
   AGENCY_RATIO         number(7,4)                    not null,
   AGENCY_YIELD_RATE    number(16,9),
   CREDIT_TERM          varchar2(10),
   CREDIT_WAY           varchar2(2),
   CUST_LIMIT_START     number(17,2),
   CUST_LIMIT_END       number(17,2),
   CUST_LOAN_START      number(17,2),
   CUST_LOAN_END        number(17,2),
   CUST_SEX_DIMENSION   varchar2(1),
   CUST_AGE_START       varchar2(3),
   CUST_AGE_END         varchar2(3),
   CUST_FIRST_CREDIT_AGO varchar2(10),
   NO_BUSI_TIME_START   varchar2(6),
   NO_BUSI_TIME_END     varchar2(6),
   MATE_RULE            varchar2(3)                    not null,
   COMPENSATORY_TIME    varchar2(10)                   not null,
   TERM_CHARGE          number(7,4)                    not null,
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
alter table PL_COOPERATION_PROJECT add constraint PK_PL_COOPERATION_PROJECT primary key(ID);

comment on table PL_COOPERATION_PROJECT is '合作项目表(包含创建合作项目时第一步创建合作方和第二步创建合作项目)';
comment on column PL_COOPERATION_PROJECT.ID is '主键ID:自增长';
comment on column PL_COOPERATION_PROJECT.AGENCY_ID is '合作机构表主键ID';
comment on column PL_COOPERATION_PROJECT.PROJECT_NAME is '合作项目名称';
comment on column PL_COOPERATION_PROJECT.PROJECT_TYPE is '合作模式：数据字典PL0402(1-联合放贷模式)';
comment on column PL_COOPERATION_PROJECT.PROJECT_STATUS is '合作项目状态：数据字典PL0401(00-未启用,10-启用)';
comment on column PL_COOPERATION_PROJECT.IS_ASSURE is '合作项目是否担保：数据字典PL0102(0-否,1-是)';
comment on column PL_COOPERATION_PROJECT.COLLATERAL_ID is '合作项目担保机构';
comment on column PL_COOPERATION_PROJECT.EFFECT_TIME is '项目生效时间:若为立即生效,时间取当前系统时间';
comment on column PL_COOPERATION_PROJECT.LOAN_AMOUNT is '放款总量';
comment on column PL_COOPERATION_PROJECT.LOAN_LIMIT_MONTH is '放款限额(月)';
comment on column PL_COOPERATION_PROJECT.LOAN_LIMIT_DAY is '放款限额(日)';
comment on column PL_COOPERATION_PROJECT.PROJECT_PRIORITY is '优先级,从1开始，数值越大优先级越大';
comment on column PL_COOPERATION_PROJECT.CASH_YIELD_RATE is '消金收益率';
comment on column PL_COOPERATION_PROJECT.AGENCY_RATIO is '合作机构资金占比';
comment on column PL_COOPERATION_PROJECT.AGENCY_YIELD_RATE is '合作机构收益率';
comment on column PL_COOPERATION_PROJECT.CREDIT_TERM is '授信有效期限:天';
comment on column PL_COOPERATION_PROJECT.CREDIT_WAY is '征信查询方式：数据字典PL0403(1-消金映射,2-机构自查)';
comment on column PL_COOPERATION_PROJECT.CUST_LIMIT_START is '用户额度区间开始:元';
comment on column PL_COOPERATION_PROJECT.CUST_LIMIT_END is '用户额度区间结束:元';
comment on column PL_COOPERATION_PROJECT.CUST_LOAN_START is '用户贷款区间开始:元';
comment on column PL_COOPERATION_PROJECT.CUST_LOAN_END is '用户贷款区间结束:元';
comment on column PL_COOPERATION_PROJECT.CUST_SEX_DIMENSION is '用户性别维度：数据字典PL0405(0-女,1-男,2-全部)';
comment on column PL_COOPERATION_PROJECT.CUST_AGE_START is '用户年龄区间开始:岁';
comment on column PL_COOPERATION_PROJECT.CUST_AGE_END is '用户年龄区间结束:岁';
comment on column PL_COOPERATION_PROJECT.CUST_FIRST_CREDIT_AGO is '用户首次用信距今天数:天';
comment on column PL_COOPERATION_PROJECT.NO_BUSI_TIME_START is '业务每日暂停开始时间hhmi24ss';
comment on column PL_COOPERATION_PROJECT.NO_BUSI_TIME_END is '业务每日暂停结束时间hhmi24ss';
comment on column PL_COOPERATION_PROJECT.MATE_RULE is '匹配规则：数据字典PL0404(P-产品,C-渠道,S-标签,PC-产品+渠道,CS-渠道+标签,PS-产品+标签,PCS-产品+渠道+标签)';
comment on column PL_COOPERATION_PROJECT.COMPENSATORY_TIME is '代偿时间:天';
comment on column PL_COOPERATION_PROJECT.TERM_CHARGE is '期限服务费率';
comment on column PL_COOPERATION_PROJECT.CREATE_DATE is '创建时间';
comment on column PL_COOPERATION_PROJECT.UPDATE_DATE is '修改时间';
comment on column PL_COOPERATION_PROJECT.CREATE_BY is '创建人';
comment on column PL_COOPERATION_PROJECT.UPDATE_BY is '修改人';
comment on column PL_COOPERATION_PROJECT.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_COOPERATION_PROJECT.MEMO1 is '备注1';
comment on column PL_COOPERATION_PROJECT.MEMO2 is '备注2';
comment on column PL_COOPERATION_PROJECT.MEMO3 is '备注3';
comment on column PL_COOPERATION_PROJECT.MEMO4 is '备注4';
comment on column PL_COOPERATION_PROJECT.MEMO5 is '备注5';

--临时表
create table PL_CURSOR(
   ID                   varchar2(36)                   not null,
   CONTRACT_NO          varchar2(50),
   STATUS               varchar2(10),
   SYSTEM_DATE          date,
   ASSETS_SPLIT_ITEM_ID varchar2(36),
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_CURSOR add constraint PK_PL_CURSOR primary key(ID);

comment on table PL_CURSOR is '临时表';
comment on column PL_CURSOR.ID is '编码';
comment on column PL_CURSOR.CONTRACT_NO is '合同号';
comment on column PL_CURSOR.STATUS is '状态';
comment on column PL_CURSOR.SYSTEM_DATE is '系统时间';
comment on column PL_CURSOR.MEMO1 is '备注1';
comment on column PL_CURSOR.MEMO2 is '备注2';
comment on column PL_CURSOR.MEMO3 is '备注3';
comment on column PL_CURSOR.MEMO4 is '备注4';
comment on column PL_CURSOR.MEMO5 is '备注5';

--数据字典主表(大分类)
create table PL_DICTIONARY(
   ID                   VARCHAR2(36)                   not null,
   DICTIONARY_NAME      VARCHAR2(200),
   TITLE                VARCHAR2(200),
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
alter table PL_DICTIONARY add constraint PK_PL_DICTIONARY primary key(ID);

comment on table PL_DICTIONARY is '数据字典主表(大分类)';
comment on column PL_DICTIONARY.ID is '主键ID';
comment on column PL_DICTIONARY.DICTIONARY_NAME is '数据字典大类名称';
comment on column PL_DICTIONARY.TITLE is '码值显示列标题';
comment on column PL_DICTIONARY.CREATE_DATE is '创建时间';
comment on column PL_DICTIONARY.UPDATE_DATE is '修改时间';
comment on column PL_DICTIONARY.CREATE_BY is '创建人';
comment on column PL_DICTIONARY.UPDATE_BY is '修改人';
comment on column PL_DICTIONARY.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_DICTIONARY.MEMO1 is '备注1';
comment on column PL_DICTIONARY.MEMO2 is '备注2';
comment on column PL_DICTIONARY.MEMO3 is '备注3';
comment on column PL_DICTIONARY.MEMO4 is '备注4';
comment on column PL_DICTIONARY.MEMO5 is '备注5';

--数据字典子表(子项)
create table PL_DICTIONARY_SUB(
   ID                   varchar2(36)                   not null,
   DICTIONARY_ID        varchar2(36),
   SUB_NAME             varchar2(300),
   SUB_VALUE            varchar2(100),
   SUB_ORDER            number(4),
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
alter table PL_DICTIONARY_SUB add constraint PK_PL_DICTIONARY_SUB primary key(ID);

comment on table PL_DICTIONARY_SUB is '数据字典子表(子项)';
comment on column PL_DICTIONARY_SUB.ID is '主键ID';
comment on column PL_DICTIONARY_SUB.DICTIONARY_ID is '数据字典主表ID';
comment on column PL_DICTIONARY_SUB.SUB_NAME is '子项名称';
comment on column PL_DICTIONARY_SUB.SUB_VALUE is '子项值';
comment on column PL_DICTIONARY_SUB.SUB_ORDER is '排序';
comment on column PL_DICTIONARY_SUB.CREATE_DATE is '创建时间';
comment on column PL_DICTIONARY_SUB.UPDATE_DATE is '修改时间';
comment on column PL_DICTIONARY_SUB.CREATE_BY is '创建人';
comment on column PL_DICTIONARY_SUB.UPDATE_BY is '修改人';
comment on column PL_DICTIONARY_SUB.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_DICTIONARY_SUB.MEMO1 is '备注1';
comment on column PL_DICTIONARY_SUB.MEMO2 is '备注2';
comment on column PL_DICTIONARY_SUB.MEMO3 is '备注3';
comment on column PL_DICTIONARY_SUB.MEMO4 is '备注4';
comment on column PL_DICTIONARY_SUB.MEMO5 is '备注5';

--入件渠道表
create table PL_INSERTS_CHANNEL(
   ID                   varchar2(36)                   not null,
   PROJECT_ID           varchar2(36)                   not null,
   INSERT_CHANNEL_TYPE varchar2(36)                   not null,
   INSERT_CHANNEL_NAME varchar2(100)                  not null,
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
alter table PL_INSERTS_CHANNEL add constraint PK_PL_INSERTS_CHANNEL primary key(ID);

comment on table PL_INSERTS_CHANNEL is '入件渠道表';
comment on column PL_INSERTS_CHANNEL.ID is '主键ID';
comment on column PL_INSERTS_CHANNEL.PROJECT_ID is '合作项目表主键ID';
comment on column PL_INSERTS_CHANNEL.INSERT_CHANNEL_TYPE is '渠道类型';
comment on column PL_INSERTS_CHANNEL.INSERT_CHANNEL_NAME is '渠道名称';
comment on column PL_INSERTS_CHANNEL.CREATE_DATE is '创建时间';
comment on column PL_INSERTS_CHANNEL.UPDATE_DATE is '修改时间';
comment on column PL_INSERTS_CHANNEL.CREATE_BY is '创建人';
comment on column PL_INSERTS_CHANNEL.UPDATE_BY is '修改人';
comment on column PL_INSERTS_CHANNEL.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_INSERTS_CHANNEL.MEMO1 is '备注1';
comment on column PL_INSERTS_CHANNEL.MEMO2 is '备注2';
comment on column PL_INSERTS_CHANNEL.MEMO3 is '备注3';
comment on column PL_INSERTS_CHANNEL.MEMO4 is '备注4';
comment on column PL_INSERTS_CHANNEL.MEMO5 is '备注5';

--前置机接口日志表
create table PL_PREPOSITION_API_LOG(
   ID                   varchar2(36)                   not null,
   API_NAME             varchar2(100),
   URL                  varchar2(150),
   REQUEST_METHOD       varchar2(50),
   SER_NO               varchar2(50),
   SYS_FLAG             varchar2(50),
   TRADE_TYPE           varchar2(20),
   TRADE_DATE           varchar2(15),
   CHANNEL_NO           varchar2(36),
   API_VERSION          varchar2(8),
   IN_PARAM             varchar2(4000),
   OUT_PARAM            varchar2(4000),
   IS_ERROR             varchar2(2),
   ERROR_INFO           CLOB,
   START_TIME           varchar2(26),
   END_TIME             varchar2(26),
   WHEN_LONG            varchar2(18),
   FD_KEY               varchar2(50) default '关键字'  not null,
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_PREPOSITION_API_LOG add constraint PK_PL_PREPOSITION_API_LOG primary key(ID);

comment on table PL_PREPOSITION_API_LOG is '前置机接口日志表';
comment on column PL_PREPOSITION_API_LOG.ID is '主键ID';
comment on column PL_PREPOSITION_API_LOG.API_NAME is '接口名称';
comment on column PL_PREPOSITION_API_LOG.URL is '接口链接';
comment on column PL_PREPOSITION_API_LOG.REQUEST_METHOD is '请求方式';
comment on column PL_PREPOSITION_API_LOG.SER_NO is '流水号';
comment on column PL_PREPOSITION_API_LOG.SYS_FLAG is '系统标识';
comment on column PL_PREPOSITION_API_LOG.TRADE_TYPE is '交易类型';
comment on column PL_PREPOSITION_API_LOG.TRADE_DATE is '交易时间';
comment on column PL_PREPOSITION_API_LOG.CHANNEL_NO is '渠道';
comment on column PL_PREPOSITION_API_LOG.API_VERSION is '接口版本';
comment on column PL_PREPOSITION_API_LOG.IN_PARAM is '入参';
comment on column PL_PREPOSITION_API_LOG.OUT_PARAM is '出参';
comment on column PL_PREPOSITION_API_LOG.IS_ERROR is '异常说明：数据字典PL0107(1-异常,0-非异常)';
comment on column PL_PREPOSITION_API_LOG.ERROR_INFO is '异常说明';
comment on column PL_PREPOSITION_API_LOG.START_TIME is '开始时间';
comment on column PL_PREPOSITION_API_LOG.END_TIME is '结束时间';
comment on column PL_PREPOSITION_API_LOG.WHEN_LONG is '时长';
comment on column PL_PREPOSITION_API_LOG.FD_KEY is '关键字';
comment on column PL_PREPOSITION_API_LOG.MEMO1 is '备注1';
comment on column PL_PREPOSITION_API_LOG.MEMO2 is '备注2';
comment on column PL_PREPOSITION_API_LOG.MEMO3 is '备注3';
comment on column PL_PREPOSITION_API_LOG.MEMO4 is '备注4';
comment on column PL_PREPOSITION_API_LOG.MEMO5 is '备注5';

--前置数据交互日志表
create table PL_PREPOSITION_ICBC_LOG(
   ID                   varchar2(36)                   not null,
   IN_PARAM             clob,
   OUT_PARAM            clob,
   BEGIN_DATE           date,
   END_DATE             date,
   INTERACTIVE_STATE_INFO clob,
   APPL_SEQ             varchar2(36),
   DOMAIN_MODEL_NAME    varchar2(200),
   MODEL_SIGN           varchar2(20),
   INTERACTIVE_STATUS   varchar2(4),
   CORP_MSG_ID          varchar2(36),
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_PREPOSITION_ICBC_LOG add constraint PK_PL_PREPOSITION_ICBC_LOG primary key(ID);

comment on table PL_PREPOSITION_ICBC_LOG is '前置数据交互日志表';
comment on column PL_PREPOSITION_ICBC_LOG.ID is '主键ID';
comment on column PL_PREPOSITION_ICBC_LOG.IN_PARAM is '入参';
comment on column PL_PREPOSITION_ICBC_LOG.OUT_PARAM is '出参';
comment on column PL_PREPOSITION_ICBC_LOG.BEGIN_DATE is '开始时间';
comment on column PL_PREPOSITION_ICBC_LOG.END_DATE is '结束时间';
comment on column PL_PREPOSITION_ICBC_LOG.INTERACTIVE_STATE_INFO is '交互状态信息';
comment on column PL_PREPOSITION_ICBC_LOG.APPL_SEQ is '业务编号';
comment on column PL_PREPOSITION_ICBC_LOG.DOMAIN_MODEL_NAME is '域模型名称';
comment on column PL_PREPOSITION_ICBC_LOG.MODEL_SIGN is '模型标示';
comment on column PL_PREPOSITION_ICBC_LOG.INTERACTIVE_STATUS is '交互状态';
comment on column PL_PREPOSITION_ICBC_LOG.CORP_MSG_ID is '消息ID';
comment on column PL_PREPOSITION_ICBC_LOG.MEMO1 is '备注1';
comment on column PL_PREPOSITION_ICBC_LOG.MEMO2 is '备注2';
comment on column PL_PREPOSITION_ICBC_LOG.MEMO3 is '备注3';
comment on column PL_PREPOSITION_ICBC_LOG.MEMO4 is '备注4';
comment on column PL_PREPOSITION_ICBC_LOG.MEMO5 is '备注5';

--处理中心接口日志表
create table PL_PROCESSER_API_LOG(
   ID                   varchar2(36)                   not null,
   API_NAME             varchar2(100),
   URL                  varchar2(150),
   REQUEST_METHOD       varchar2(50),
   SER_NO               varchar2(50),
   SYS_FLAG             varchar2(50),
   TRADE_TYPE           varchar2(20),
   TRADE_DATE           varchar2(15),
   CHANNEL_NO           varchar2(36),
   API_VERSION          varchar2(8),
   IN_PARAM             varchar2(4000),
   OUT_PARAM            varchar2(4000),
   IS_ERROR             varchar2(2),
   ERROR_INFO           CLOB,
   START_TIME           varchar2(26),
   END_TIME             varchar2(26),
   WHEN_LONG            varchar2(18),
   FD_KEY               varchar2(50) default '关键字'  not null,
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_PROCESSER_API_LOG add constraint PK_PL_PROCESSER_API_LOG primary key(ID);

comment on table PL_PROCESSER_API_LOG is '处理中心接口日志表';
comment on column PL_PROCESSER_API_LOG.ID is '主键ID';
comment on column PL_PROCESSER_API_LOG.API_NAME is '接口名称';
comment on column PL_PROCESSER_API_LOG.URL is '接口链接';
comment on column PL_PROCESSER_API_LOG.REQUEST_METHOD is '请求方式';
comment on column PL_PROCESSER_API_LOG.SER_NO is '流水号';
comment on column PL_PROCESSER_API_LOG.SYS_FLAG is '系统标识';
comment on column PL_PROCESSER_API_LOG.TRADE_TYPE is '交易类型';
comment on column PL_PROCESSER_API_LOG.TRADE_DATE is '交易时间';
comment on column PL_PROCESSER_API_LOG.CHANNEL_NO is '渠道';
comment on column PL_PROCESSER_API_LOG.API_VERSION is '接口版本';
comment on column PL_PROCESSER_API_LOG.IN_PARAM is '入参';
comment on column PL_PROCESSER_API_LOG.OUT_PARAM is '出参';
comment on column PL_PROCESSER_API_LOG.IS_ERROR is '是否出现异常:1-异常，0-非异常';
comment on column PL_PROCESSER_API_LOG.ERROR_INFO is '异常说明：数据字典PL0107(1-异常,0-非异常)';
comment on column PL_PROCESSER_API_LOG.START_TIME is '开始时间';
comment on column PL_PROCESSER_API_LOG.END_TIME is '结束时间';
comment on column PL_PROCESSER_API_LOG.WHEN_LONG is '时长';
comment on column PL_PROCESSER_API_LOG.FD_KEY is '关键字';
comment on column PL_PROCESSER_API_LOG.MEMO1 is '备注1';
comment on column PL_PROCESSER_API_LOG.MEMO2 is '备注2';
comment on column PL_PROCESSER_API_LOG.MEMO3 is '备注3';
comment on column PL_PROCESSER_API_LOG.MEMO4 is '备注4';
comment on column PL_PROCESSER_API_LOG.MEMO5 is '备注5';

--放款请求记录表
create table PL_PROCESSER_MAKE_LOANS(
   ID                   varchar2(36)                   not null,
   APPL_SEQ             varchar2(36),
   ASSETS_SPLIT_ID      varchar2(36),
   ASSETS_SPLIT_ITEM_ID varchar2(36),
   ORG_CORP_MSG_ID      varchar2(50),
   APPLY_DT             varchar2(26),
   STATUS               varchar2(2),
   CREATE_DATE          date                           not null,
   UPDATE_DATE          date,
   CREATE_BY            VARCHAR2(36)                   not null,
   UPDATE_BY            varchar2(36),
   DEL_FLAG             varchar2(6)                    not null,
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_PROCESSER_MAKE_LOANS add constraint PK_PL_PROCESSER_MAKE_LOANS primary key(ID);

comment on table PL_PROCESSER_MAKE_LOANS is '放款请求记录表';
comment on column PL_PROCESSER_MAKE_LOANS.ID is '主键ID';
comment on column PL_PROCESSER_MAKE_LOANS.APPL_SEQ is '业务编号';
comment on column PL_PROCESSER_MAKE_LOANS.ASSETS_SPLIT_ID is '资产ID';
comment on column PL_PROCESSER_MAKE_LOANS.ASSETS_SPLIT_ITEM_ID is '资产拆分ID';
comment on column PL_PROCESSER_MAKE_LOANS.ORG_CORP_MSG_ID is '工行消息ID';
comment on column PL_PROCESSER_MAKE_LOANS.APPLY_DT is '申请时间';
comment on column PL_PROCESSER_MAKE_LOANS.STATUS is '状态：数据字典PL1502(0-未放款,1-已放款)';
comment on column PL_PROCESSER_MAKE_LOANS.CREATE_DATE is '创建时间';
comment on column PL_PROCESSER_MAKE_LOANS.UPDATE_DATE is '修改时间';
comment on column PL_PROCESSER_MAKE_LOANS.CREATE_BY is '创建人';
comment on column PL_PROCESSER_MAKE_LOANS.UPDATE_BY is '修改人';
comment on column PL_PROCESSER_MAKE_LOANS.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_PROCESSER_MAKE_LOANS.MEMO1 is '备注1';
comment on column PL_PROCESSER_MAKE_LOANS.MEMO2 is '备注2';
comment on column PL_PROCESSER_MAKE_LOANS.MEMO3 is '备注3';
comment on column PL_PROCESSER_MAKE_LOANS.MEMO4 is '备注4';
comment on column PL_PROCESSER_MAKE_LOANS.MEMO5 is '备注5';

--合作项目支持银行表
create table PL_PROJECT_BANK(
   ID                   varchar2(36)                   not null,
   PROJECT_ID           varchar2(36)                   not null,
   BANK_ID              varchar2(36)                   not null,
   IS_SUPPORT           varchar2(2)                    not null,
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
alter table PL_PROJECT_BANK add constraint PK_PL_PROJECT_BANK primary key(ID);

comment on table PL_PROJECT_BANK is '合作项目支持银行表';
comment on column PL_PROJECT_BANK.ID is '主键ID:自增长';
comment on column PL_PROJECT_BANK.PROJECT_ID is '合作项目表主键ID';
comment on column PL_PROJECT_BANK.BANK_ID is '银行信息表主键ID';
comment on column PL_PROJECT_BANK.IS_SUPPORT is '是否支持：数据字典PL0106(0-不支持,1-支持,2-未知)';
comment on column PL_PROJECT_BANK.CREATE_DATE is '创建时间';
comment on column PL_PROJECT_BANK.UPDATE_DATE is '修改时间';
comment on column PL_PROJECT_BANK.CREATE_BY is '创建人';
comment on column PL_PROJECT_BANK.UPDATE_BY is '修改人';
comment on column PL_PROJECT_BANK.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_PROJECT_BANK.MEMO1 is '备注1';
comment on column PL_PROJECT_BANK.MEMO2 is '备注2';
comment on column PL_PROJECT_BANK.MEMO3 is '备注3';
comment on column PL_PROJECT_BANK.MEMO4 is '备注4';
comment on column PL_PROJECT_BANK.MEMO5 is '备注5';

--还款流水表
create table PL_REPAYMENT_FLOW(
   id                   varchar2(36)                   not null,
   coopr_user_id        varchar2(50),
   loan_memno           varchar2(60),
   ret_busino           varchar2(50),
   loan_num             varchar2(50),
   ret_ttl_amt          number(17),
   ret_prcp_amt         number(17),
   ret_intr_amt         number(17),
   dis_amt              number(17),
   dis_res              varchar2(30),
   ret_type             varchar2(2),
   ret_inst_num         number(3),
   repayment_status     varchar2(2),
   create_date          date,
   update_date          date,
   def_msg              varchar2(60),
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_REPAYMENT_FLOW add constraint PK_PL_REPAYMENT_FLOW primary key(ID);

comment on table PL_REPAYMENT_FLOW is '还款流水表';
comment on column PL_REPAYMENT_FLOW.id is '主键id';
comment on column PL_REPAYMENT_FLOW.coopr_user_id is '消金客户编号';
comment on column PL_REPAYMENT_FLOW.loan_memno is '贷款协议号';
comment on column PL_REPAYMENT_FLOW.ret_busino is '还款业务编号';
comment on column PL_REPAYMENT_FLOW.loan_num is '资方借据编号';
comment on column PL_REPAYMENT_FLOW.ret_ttl_amt is '合作方上送还款合计金额';
comment on column PL_REPAYMENT_FLOW.ret_prcp_amt is '合作方上送还款本金金额';
comment on column PL_REPAYMENT_FLOW.ret_intr_amt is '合作方上送还款利息金额';
comment on column PL_REPAYMENT_FLOW.dis_amt is '贴息金额';
comment on column PL_REPAYMENT_FLOW.dis_res is '贴息说明';
comment on column PL_REPAYMENT_FLOW.ret_type is '还款类型：数据字典PL1501(1-按期数还款,4-全额还款)';
comment on column PL_REPAYMENT_FLOW.ret_inst_num is '还款期数';
comment on column PL_REPAYMENT_FLOW.repayment_status is '还款状态：数据字典PL1502(0-未还款,1-已还款)';
comment on column PL_REPAYMENT_FLOW.create_date is '创建日期';
comment on column PL_REPAYMENT_FLOW.update_date is '更新日期';
comment on column PL_REPAYMENT_FLOW.def_msg is '备注说明';
comment on column PL_REPAYMENT_FLOW.MEMO1 is '备注1';
comment on column PL_REPAYMENT_FLOW.MEMO2 is '备注2';
comment on column PL_REPAYMENT_FLOW.MEMO3 is '备注3';
comment on column PL_REPAYMENT_FLOW.MEMO4 is '备注4';
comment on column PL_REPAYMENT_FLOW.MEMO5 is '备注5';

--还款方式表
create table PL_REPAYMENT_INFO(
   ID                   varchar2(36)                   not null,
   PROJECT_ID           varchar2(36)                   not null,
   REPAYMENT_TYPE       varchar2(2)                    not null,
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
alter table PL_REPAYMENT_INFO add constraint PK_PL_REPAYMENT_INFO primary key(ID);

comment on table PL_REPAYMENT_INFO is '还款方式表';
comment on column PL_REPAYMENT_INFO.ID is '主键ID';
comment on column PL_REPAYMENT_INFO.PROJECT_ID is '合作项目表主键ID';
comment on column PL_REPAYMENT_INFO.REPAYMENT_TYPE is '还款方式类型：数据字典PL0406(1-提前结清,2-按期足额还款,3-部分还款)';
comment on column PL_REPAYMENT_INFO.CREATE_DATE is '创建时间';
comment on column PL_REPAYMENT_INFO.UPDATE_DATE is '修改时间';
comment on column PL_REPAYMENT_INFO.CREATE_BY is '创建人';
comment on column PL_REPAYMENT_INFO.UPDATE_BY is '修改人';
comment on column PL_REPAYMENT_INFO.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_REPAYMENT_INFO.MEMO1 is '备注1';
comment on column PL_REPAYMENT_INFO.MEMO2 is '备注2';
comment on column PL_REPAYMENT_INFO.MEMO3 is '备注3';
comment on column PL_REPAYMENT_INFO.MEMO4 is '备注4';
comment on column PL_REPAYMENT_INFO.MEMO5 is '备注5';

--路由中心接口日志表
create table PL_ROUTER_API_LOG(
   ID                   varchar2(36)                   not null,
   API_NAME             varchar2(100),
   URL                  varchar2(150),
   REQUEST_METHOD       varchar2(50),
   SER_NO               varchar2(50),
   SYS_FLAG             varchar2(50),
   TRADE_TYPE           varchar2(20),
   TRADE_DATE           varchar2(15),
   CHANNEL_NO           varchar2(36),
   API_VERSION          varchar2(8),
   IN_PARAM             varchar2(4000),
   OUT_PARAM            varchar2(4000),
   IS_ERROR             varchar2(2),
   ERROR_INFO           CLOB,
   START_TIME           varchar2(26),
   END_TIME             varchar2(26),
   WHEN_LONG            varchar2(18),
   FD_KEY               varchar2(50) default '关键字'  not null,
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_ROUTER_API_LOG add constraint PK_PL_ROUTER_API_LOG primary key(ID);

comment on table PL_ROUTER_API_LOG is '路由中心接口日志表';
comment on column PL_ROUTER_API_LOG.ID is '主键ID';
comment on column PL_ROUTER_API_LOG.API_NAME is '接口名称';
comment on column PL_ROUTER_API_LOG.URL is '接口链接';
comment on column PL_ROUTER_API_LOG.REQUEST_METHOD is '请求方式';
comment on column PL_ROUTER_API_LOG.SER_NO is '流水号';
comment on column PL_ROUTER_API_LOG.SYS_FLAG is '系统标识';
comment on column PL_ROUTER_API_LOG.TRADE_TYPE is '交易类型';
comment on column PL_ROUTER_API_LOG.TRADE_DATE is '交易时间';
comment on column PL_ROUTER_API_LOG.CHANNEL_NO is '渠道';
comment on column PL_ROUTER_API_LOG.API_VERSION is '接口版本';
comment on column PL_ROUTER_API_LOG.IN_PARAM is '入参';
comment on column PL_ROUTER_API_LOG.OUT_PARAM is '出参';
comment on column PL_ROUTER_API_LOG.IS_ERROR is '异常说明：数据字典PL0107(1-异常,0-非异常)';
comment on column PL_ROUTER_API_LOG.ERROR_INFO is '异常说明';
comment on column PL_ROUTER_API_LOG.START_TIME is '开始时间';
comment on column PL_ROUTER_API_LOG.END_TIME is '结束时间';
comment on column PL_ROUTER_API_LOG.WHEN_LONG is '时长';
comment on column PL_ROUTER_API_LOG.FD_KEY is '关键字';
comment on column PL_ROUTER_API_LOG.MEMO1 is '备注1';
comment on column PL_ROUTER_API_LOG.MEMO2 is '备注2';
comment on column PL_ROUTER_API_LOG.MEMO3 is '备注3';
comment on column PL_ROUTER_API_LOG.MEMO4 is '备注4';
comment on column PL_ROUTER_API_LOG.MEMO5 is '备注5';

--路由结果记录表
create table PL_ROUTE_RESULT_RECORD(
   ID                   varchar2(36)                   not null,
   APPL_SEQ             varchar2(36)                   not null,
   CUST_ID              varchar2(36)                   not null,
   CUST_SIGN            varchar2(36)                   not null,
   CUST_ID_NO           varchar2(36)                   not null,
   TYP_CDE              varchar2(36)                   not null,
   CHANNEL_NO           varchar2(36)                   not null,
   PERIOD               number(10)                      not null,
   AGENCY_ID            varchar2(36)                   not null,
   PROJECT_ID           varchar2(36)                   not null,
   BANK_NO_NUM          varchar2(20)                   not null,
   LOAN_AMOUNT          number(17,2)                   not null,
   ROUTE_RULE_ID        varchar2(36),
   AGENCY_RATIO         number(7,4),
   CASH_LOAN_AMOUNT     number(17,2),
   AGENCY_LOAN_AMOUNT   number(17,2),
   ANGENCY_PLAN_LOAN_AMOUNT number(17,2),
   AGENCY_ALREADY_AMOUNT number(17,2),
   AGENCY_LEFT_AMOUNT   number(17,2),
   CUST_LIMIT           number(17,2),
   CUST_AGE             number(3),
   CUST_SEX             varchar2(2),
   CUST_LAST_LOAN_DAY   number(10),
   IS_UNITE_LOAN        varchar2(10)                   not null,
   IS_CREDIT            varchar2(2)                    not null,
   CREDIT_MODLE_URL     varchar2(300),
   SER_NO               varchar2(36)                   not null,
   SYS_FLAG             varchar2(10)                   not null,
   CREATE_DATE          date                           not null,
   RMK                  varchar2(1000),
   EXCUTE_IP            varchar2(50),
   EXCUTE_HOST_NAME     varchar2(50),
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_ROUTE_RESULT_RECORD add constraint PK_PL_ROUTE_RESULT_RECORD primary key(ID);

comment on table PL_ROUTE_RESULT_RECORD is '路由结果记录表';
comment on column PL_ROUTE_RESULT_RECORD.ID is '主键ID:4位固定标识+yyyyMMddhh24miss+8位序列(不足8位前面补0,多余8位截取后8位)';
comment on column PL_ROUTE_RESULT_RECORD.APPL_SEQ is '业务编号:申请流水号,幂等唯一';
comment on column PL_ROUTE_RESULT_RECORD.CUST_ID is '消金客户编号';
comment on column PL_ROUTE_RESULT_RECORD.CUST_SIGN is '客户标签';
comment on column PL_ROUTE_RESULT_RECORD.CUST_ID_NO is '消金客户身份证号';
comment on column PL_ROUTE_RESULT_RECORD.TYP_CDE is '消金产品编号';
comment on column PL_ROUTE_RESULT_RECORD.CHANNEL_NO is '消金渠道编号';
comment on column PL_ROUTE_RESULT_RECORD.PERIOD is '消金产品期数';
comment on column PL_ROUTE_RESULT_RECORD.AGENCY_ID is '合作机构表主键ID';
comment on column PL_ROUTE_RESULT_RECORD.PROJECT_ID is '合作项目表主键ID';
comment on column PL_ROUTE_RESULT_RECORD.BANK_NO_NUM is '还款银行代码';
comment on column PL_ROUTE_RESULT_RECORD.LOAN_AMOUNT is '放款金额';
comment on column PL_ROUTE_RESULT_RECORD.ROUTE_RULE_ID is '路由规则ID:可空(路由中心筛选规则改变)';
comment on column PL_ROUTE_RESULT_RECORD.AGENCY_RATIO is '合作机构资金占比';
comment on column PL_ROUTE_RESULT_RECORD.CASH_LOAN_AMOUNT is '消金放款金额';
comment on column PL_ROUTE_RESULT_RECORD.AGENCY_LOAN_AMOUNT is '合作机构放款金额';
comment on column PL_ROUTE_RESULT_RECORD.ANGENCY_PLAN_LOAN_AMOUNT is '合作机构计划放款金额';
comment on column PL_ROUTE_RESULT_RECORD.AGENCY_ALREADY_AMOUNT is '合作机构已放款金额';
comment on column PL_ROUTE_RESULT_RECORD.AGENCY_LEFT_AMOUNT is '合作机构剩余放款金额';
comment on column PL_ROUTE_RESULT_RECORD.CUST_LIMIT is '客户额度';
comment on column PL_ROUTE_RESULT_RECORD.CUST_AGE is '客户年龄';
comment on column PL_ROUTE_RESULT_RECORD.CUST_SEX is '客户性别';
comment on column PL_ROUTE_RESULT_RECORD.CUST_LAST_LOAN_DAY is '客户首次用信距今天数';
comment on column PL_ROUTE_RESULT_RECORD.IS_UNITE_LOAN is '是否联合放款：数据字典PL0505(0-非联合放款,1-联合放款,2-未知)';
comment on column PL_ROUTE_RESULT_RECORD.IS_CREDIT is '是否需要征信：数据字典PL0403(1-消金映射,2-机构自查)';
comment on column PL_ROUTE_RESULT_RECORD.CREDIT_MODLE_URL is '合作机构征信模板路径';
comment on column PL_ROUTE_RESULT_RECORD.SER_NO is '报文流水号';
comment on column PL_ROUTE_RESULT_RECORD.SYS_FLAG is '系统标识';
comment on column PL_ROUTE_RESULT_RECORD.CREATE_DATE is '创建时间';
comment on column PL_ROUTE_RESULT_RECORD.RMK is '备注';
comment on column PL_ROUTE_RESULT_RECORD.EXCUTE_IP is '执行机地址';
comment on column PL_ROUTE_RESULT_RECORD.EXCUTE_HOST_NAME is '执行机主机名称';
comment on column PL_ROUTE_RESULT_RECORD.MEMO1 is '备注1';
comment on column PL_ROUTE_RESULT_RECORD.MEMO2 is '备注2';
comment on column PL_ROUTE_RESULT_RECORD.MEMO3 is '备注3';
comment on column PL_ROUTE_RESULT_RECORD.MEMO4 is '备注4';
comment on column PL_ROUTE_RESULT_RECORD.MEMO5 is '备注5';

--调度任务流转日志表
create table PL_SCHEDULE_LOG(
   ID                   VARCHAR2(36)                   not null,
   SCHEDULE_RULE_ID     VARCHAR2(36)                   not null,
   SCHEDULE_RULE_ITEM_ID VARCHAR2(36)                   not null,
   SCHEDULE_RULE_WORK_ITEM_ID VARCHAR2(36),
   SCHEDULE_TASK_ID     VARCHAR2(36)                   not null,
   FD_START_TIME        date                           not null,
   FD_END_TIME          date                           not null,
   FD_IS_SUCESS         VARCHAR2(2)                    not null,
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_SCHEDULE_LOG add constraint PK_PL_SCHEDULE_LOG primary key(ID);

comment on table PL_SCHEDULE_LOG is '调度任务流转日志表';
comment on column PL_SCHEDULE_LOG.ID is '主键ID:自增长';
comment on column PL_SCHEDULE_LOG.SCHEDULE_RULE_ID is '调度规则表主键ID';
comment on column PL_SCHEDULE_LOG.SCHEDULE_RULE_ITEM_ID is '调度任务表主键ID';
comment on column PL_SCHEDULE_LOG.SCHEDULE_RULE_WORK_ITEM_ID is '调度任务事项';
comment on column PL_SCHEDULE_LOG.SCHEDULE_TASK_ID is '调度本次任务的定时器';
comment on column PL_SCHEDULE_LOG.FD_START_TIME is '触发时间';
comment on column PL_SCHEDULE_LOG.FD_END_TIME is '结束时间';
comment on column PL_SCHEDULE_LOG.FD_IS_SUCESS is '是否成功：数据字典PL0102(0-否,1-是)';
comment on column PL_SCHEDULE_LOG.MEMO1 is '备注1';
comment on column PL_SCHEDULE_LOG.MEMO2 is '备注2';
comment on column PL_SCHEDULE_LOG.MEMO3 is '备注3';
comment on column PL_SCHEDULE_LOG.MEMO4 is '备注4';
comment on column PL_SCHEDULE_LOG.MEMO5 is '备注5';

--任务表
create table PL_PROCESSER_JOB(
   ID                   varchar2(36)                   not null,
   MODEL_NAME           varchar2(300),
   JOB_CONTEXT          varchar2(4000),
   JOB_START_DATE       varchar2(20),
   JOB_HEART_BEAT       number(10),
   JOB_STATUS           varchar2(2),
   FD_KEY               varchar2(100),
   RUN_ERROR_STATUS     varchar2(2),
   RUN_ERROR_MSG        varchar2(4000),
   RUN_TIME             NUMBER(8),
   CREATE_DATE          date                           not null,
   UPDATE_DATE          date,
   CREATE_BY            varchar2(36)                   not null,
   UPDATE_BY            varchar2(36),
   DEL_FLAG             varchar2(6)                    not null,
   MEMO3                varchar2(300),
   MEMO2                varchar2(300),
   MEMO1                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_PROCESSER_JOB add constraint PK_PL_PROCESSER_JOB primary key(ID);

comment on table PL_PROCESSER_JOB is '任务表';
comment on column PL_PROCESSER_JOB.ID is '编码';
comment on column PL_PROCESSER_JOB.MODEL_NAME is '类路径';
comment on column PL_PROCESSER_JOB.JOB_CONTEXT is '上下文';
comment on column PL_PROCESSER_JOB.JOB_START_DATE is '启动时间';
comment on column PL_PROCESSER_JOB.JOB_HEART_BEAT is '心跳频率';
comment on column PL_PROCESSER_JOB.JOB_STATUS is '任务状态';
comment on column PL_PROCESSER_JOB.FD_KEY is '调用支付网关PAY_GATEWAY';
comment on column PL_PROCESSER_JOB.RUN_ERROR_STATUS is '执行错误状态：1-出现异常';
comment on column PL_PROCESSER_JOB.RUN_ERROR_MSG is '执行错误信息';
comment on column PL_PROCESSER_JOB.RUN_TIME is '执行次数';
comment on column PL_PROCESSER_JOB.CREATE_DATE is '创建时间';
comment on column PL_PROCESSER_JOB.UPDATE_DATE is '修改时间';
comment on column PL_PROCESSER_JOB.CREATE_BY is '创建人';
comment on column PL_PROCESSER_JOB.UPDATE_BY is '修改人';
comment on column PL_PROCESSER_JOB.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_PROCESSER_JOB.MEMO3 is '备注3';
comment on column PL_PROCESSER_JOB.MEMO2 is '备注2';
comment on column PL_PROCESSER_JOB.MEMO1 is '备注1';
comment on column PL_PROCESSER_JOB.MEMO4 is '备注4';
comment on column PL_PROCESSER_JOB.MEMO5 is '备注5';

--前置任务表
create table PL_PREPOSITION_JOB (
   ID                   varchar2(36)                   not null,
   SERVICE_NAME         varchar2(200),
   MODEL_NAME           varchar2(200),
   IN_PARAM             varchar2(4000),
   JOB_START_DATE       varchar2(20),
   JOB_FREQUENCY        number(5),
   JOB_HEART_BEAT       number(10),
   RUN_ERROR_STATUS     varchar2(2),
   RUN_ERROR_MSG        varchar2(4000),
   RUN_TIME             NUMBER(8),
   CREATE_DATE          date                           not null,
   UPDATE_DATE          date,
   DEL_FLAG             varchar2(6)                    not null,
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_PREPOSITION_JOB add constraint PK_PL_PREPOSITION_JOB primary key(ID);

comment on table PL_PREPOSITION_JOB is '前置任务表';
comment on column PL_PREPOSITION_JOB.ID is '主键ID';
comment on column PL_PREPOSITION_JOB.SERVICE_NAME is '服务提供路径名称:service全路径';
comment on column PL_PREPOSITION_JOB.MODEL_NAME is '类路径:入参类的全路径';
comment on column PL_PREPOSITION_JOB.IN_PARAM is '入参';
comment on column PL_PREPOSITION_JOB.JOB_START_DATE is '启动时间';
comment on column PL_PREPOSITION_JOB.JOB_FREQUENCY is '次数';
comment on column PL_PREPOSITION_JOB.JOB_HEART_BEAT is '频率:单位秒';
comment on column PL_PREPOSITION_JOB.RUN_ERROR_STATUS is '错误状态:执行错误状态：1-出现异常';
comment on column PL_PREPOSITION_JOB.RUN_ERROR_MSG is '错误描述:执行错误信息';
comment on column PL_PREPOSITION_JOB.RUN_TIME is '执行次数';
comment on column PL_PREPOSITION_JOB.CREATE_DATE is '创建时间';
comment on column PL_PREPOSITION_JOB.UPDATE_DATE is '修改时间';
comment on column PL_PREPOSITION_JOB.DEL_FLAG is '是否删除：数据字典是否删除(PL0101):(DEL-删除,NORMAL-正常)';
comment on column PL_PREPOSITION_JOB.MEMO1 is '备注1';
comment on column PL_PREPOSITION_JOB.MEMO2 is '备注2';
comment on column PL_PREPOSITION_JOB.MEMO3 is '备注3';
comment on column PL_PREPOSITION_JOB.MEMO4 is '备注4';
comment on column PL_PREPOSITION_JOB.MEMO5 is '备注5';

--处理中心流程记录表
create table PL_PROCESSER_FLOW_LOG(
   ID                   varchar2(36)                   not null,
   APPL_SEQ             varchar2(36),
   HANDLER_NAME         varchar2(50),
   HANDLER_ROUTING      varchar2(50),
   IS_ERROR             varchar2(2),
   EXCEPTION            varchar2(1000),
   CREATE_DATE          date                           not null,
   FD_INDEX             number(2),
   MEMO1                varchar2(300),
   MEMO2                varchar2(300),
   MEMO3                varchar2(300),
   MEMO4                varchar2(300),
   MEMO5                varchar2(300)
);
alter table PL_PROCESSER_FLOW_LOG add constraint PK_PL_PROCESSER_FLOW_LOG primary key(ID);

comment on table PL_PROCESSER_FLOW_LOG is '处理中心流程记录表';
comment on column PL_PROCESSER_FLOW_LOG.ID is '主键ID';
comment on column PL_PROCESSER_FLOW_LOG.APPL_SEQ is '业务编号';
comment on column PL_PROCESSER_FLOW_LOG.HANDLER_NAME is '操作名称';
comment on column PL_PROCESSER_FLOW_LOG.HANDLER_ROUTING is '即将走向';
comment on column PL_PROCESSER_FLOW_LOG.IS_ERROR is '是否异常:数据字典PL0102(0-否,1-是)';
comment on column PL_PROCESSER_FLOW_LOG.EXCEPTION is '异常信息';
comment on column PL_PROCESSER_FLOW_LOG.FD_INDEX is '顺序';
comment on column PL_PROCESSER_FLOW_LOG.CREATE_DATE is '创建时间';
comment on column PL_PROCESSER_FLOW_LOG.MEMO1 is '备注1';
comment on column PL_PROCESSER_FLOW_LOG.MEMO2 is '备注2';
comment on column PL_PROCESSER_FLOW_LOG.MEMO3 is '备注3';
comment on column PL_PROCESSER_FLOW_LOG.MEMO4 is '备注4';
comment on column PL_PROCESSER_FLOW_LOG.MEMO5 is '备注5';

--日志操作记录表
create table PL_LOG_INFO(
   ID                   VARCHAR2(36)                   not null,
   BUSINESS_TYPE        varchar2(2)                    not null,
   LOG_NAME             varchar2(300)                  not null,
   LOG_TYPE             varchar2(2),
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

--入账分录表主键ID使用序列
create sequence PL_ACCOUNT_ENTRY_SEQUENCE  minvalue 1 maxvalue 99999999 start with 1 increment by 1 cache 100 cycle;
--入账事务表主键ID使用序列
create sequence PL_ACCOUNT_TRANS_SEQUENCE  minvalue 1 maxvalue 99999999 start with 1 increment by 1 cache 100 cycle;
--资产拆分明细表主键ID使用序列
create sequence PL_ASSETS_SPLIT_ITEM_SEQUENCE  minvalue 1 maxvalue 99999999 start with 1 increment by 1 cache 100 cycle;
--资产表主键ID使用序列
create sequence PL_ASSETS_SPLIT_SEQUENCE  minvalue 1 maxvalue 99999999 start with 1 increment by 1 cache 100 cycle;
--余额支付信息列表主键ID使用序列
create sequence PL_BALANCE_PAY_INFO_SEQUENCE  minvalue 1 maxvalue 99999999 start with 1 increment by 1 cache 100 cycle;
--路由结果记录表主键ID使用序列
create sequence PL_ROUTER_RESULT_SEQUENCE minvalue 1 maxvalue 99999999 start with 1 increment by 1 cache 100 cycle;

--路由结果表:业务编号
create INDEX IND_ROUTE_RESULT_RECORD on PL_ROUTE_RESULT_RECORD (APPL_SEQ);--tablespace XPAD_SXJ_IDX

--处理中心任务
create INDEX IND_PROCESSER_JOB_MODEL_NAME on PL_PROCESSER_JOB (MODEL_NAME);
create INDEX IND_PROCESSER_JOB_STATUS on PL_PROCESSER_JOB (JOB_STATUS);
create INDEX IND_PROCESSER_JOB_DEL_FLAG on PL_PROCESSER_JOB (DEL_FLAG);
create INDEX IND_PROCESSER_JOB_START_DATE on PL_PROCESSER_JOB (JOB_START_DATE);

--处理中心放款记录
create INDEX IND_PROCESSER_MAKE_LOANS_SEQ on PL_PROCESSER_MAKE_LOANS (APPL_SEQ);
create INDEX IND_MAKE_LOANS_DEL_FLAG on PL_PROCESSER_MAKE_LOANS (DEL_FLAG);
create INDEX IND_MAKE_SPLIT_ID on PL_PROCESSER_MAKE_LOANS (ASSETS_SPLIT_ID);

--处理中心额度
create INDEX IND_MAKE_SPLIT_ITEM_ID on PL_PROCESSER_MAKE_LOANS (ASSETS_SPLIT_ITEM_ID);

--处理中心资产
create INDEX IND_ASSETS_SPLIT_APPL_SEQ on PL_ASSETS_SPLIT (APPL_SEQ);
create INDEX IND_ASSETS_SPLIT_APPL_DEL_FLAG on PL_ASSETS_SPLIT (DEL_FLAG);
create INDEX IND_SPLIT_CONTRACT_NO on PL_ASSETS_SPLIT (CONTRACT_NO);

--处理中心资产明细
create INDEX IND_ASSETS_SPLIT_ITEM_MAIN_ID on PL_ASSETS_SPLIT_ITEM (ASSETS_SPLIT_ID);
create INDEX IND_ASSETS_SPLIT_DEL_FLAG on PL_ASSETS_SPLIT_ITEM (DEL_FLAG);
create INDEX IND_ASSETS_SPLIT_LOAN_NO on PL_ASSETS_SPLIT_ITEM (LOAN_NO);

--处理中心日志
create INDEX IND_ROUTER_API_LOG_KEY on PL_ROUTER_API_LOG (FD_KEY);
--前置日志
create INDEX IND_PROCESSER_API_LOG_KEY on PL_PROCESSER_API_LOG (FD_KEY);
--路由日志
create INDEX IND_PREPOSITION_API_LOG_KEY on PL_PREPOSITION_API_LOG (FD_KEY);

--任务表索引
create INDEX IND_PROCESSER_JOB_RUN_TIME on PL_PROCESSER_JOB (FD_KEY);

alter table PL_ASSETS_SPLIT add constraint UNIQUE_APPL_SEQ unique (APPL_SEQ);
create INDEX IND_ASSETS_SPLIT_LOAN_STATUS on PL_ASSETS_SPLIT (LOAN_STATUS);
create INDEX IND_ASSETS_SPLIT_STATUS on PL_ASSETS_SPLIT_ITEM (STATUS);