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