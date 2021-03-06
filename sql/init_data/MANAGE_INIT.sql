--合作机构表
insert into PL_COOPERATION_AGENCY (ID, AGENCY_NAME, AGENCY_LIAISONS, AGENCY_LIAISONS_MOBILE, AGENCY_PRIORITY, COOPERATION_STATUS, AGENCY_TYPE, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('GONGSHANGYINHANG', '中国工商银行青岛分行', '冯靓', '18678978180', '1', '10', 'ORG01', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
--合作项目
--现金贷工行联合放款项目
insert into PL_COOPERATION_PROJECT (ID, AGENCY_ID, PROJECT_NAME, PROJECT_TYPE, PROJECT_STATUS, IS_ASSURE, COLLATERAL_ID, EFFECT_TIME, LOAN_AMOUNT, LOAN_LIMIT_MONTH, LOAN_LIMIT_DAY, PROJECT_PRIORITY, AGENCY_RATIO, AGENCY_YIELD_RATE, CREDIT_TERM, CREDIT_WAY, CUST_LOAN_START, CUST_LOAN_END, CUST_FIRST_CREDIT_AGO, NO_BUSI_TIME_START, NO_BUSI_TIME_END, MATE_RULE, COMPENSATORY_TIME, TERM_CHARGE, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('GSYHLHFK', (select ID from PL_COOPERATION_AGENCY), '现金贷工行联合放款项目', '1', '10', '1', '中国人民财产保险股份有限公司青岛分公司', to_date('24-08-2018 00:00:00', 'dd-mm-yyyy hh24:mi:ss'), 6000000000, 1500000000, 50000000, '1', 0.9,  0.06, '365', '2', 1000, 20000.00, '0', '210000', '030000', 'PCS', '45', 0.015, SYSDATE,SYSDATE, 'SYSTEM', 'NORMAL');

--合作项目支持银行表(合作项目使用ID:DtpncK0mcNTtAeOs8BB)
insert into PL_PROJECT_BANK (ID, PROJECT_ID, BANK_ID, IS_SUPPORT, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('daMamsHJTO21pfRHY0Z', (select ID from PL_COOPERATION_PROJECT), 'delxLROPVWujPUodtQu', '1', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_PROJECT_BANK (ID, PROJECT_ID, BANK_ID, IS_SUPPORT, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('FwkyGAdgizHMcSlPwVO', (select ID from PL_COOPERATION_PROJECT), 'ciGjgMEGGVZv92ZQHIP', '1', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_PROJECT_BANK (ID, PROJECT_ID, BANK_ID, IS_SUPPORT, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('4fhnSCGjI5FeTrQ43BT', (select ID from PL_COOPERATION_PROJECT), 'IG5eCYuXgUWOFoxwBfJ', '1', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_PROJECT_BANK (ID, PROJECT_ID, BANK_ID, IS_SUPPORT, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('mcony8eR2Q65e78JZMI', (select ID from PL_COOPERATION_PROJECT), 'gGOpNpguvrAwiW4UxQp', '1', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_PROJECT_BANK (ID, PROJECT_ID, BANK_ID, IS_SUPPORT, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('7VJpM4PjESUOBIJgMYI', (select ID from PL_COOPERATION_PROJECT), 'S1Yde301Znis8qNUDlr', '1', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_PROJECT_BANK (ID, PROJECT_ID, BANK_ID, IS_SUPPORT, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('LRiRrVGKbpGwKegKRKm', (select ID from PL_COOPERATION_PROJECT), '4ZHhePpNuoeS86gfQGu', '1', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_PROJECT_BANK (ID, PROJECT_ID, BANK_ID, IS_SUPPORT, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('uTFvV3S56YDT75XPMdD', (select ID from PL_COOPERATION_PROJECT), 'SQqts0bs0o2gyB4K206', '1', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_PROJECT_BANK (ID, PROJECT_ID, BANK_ID, IS_SUPPORT, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('cRziIEIpAV5zwEvKoTp', (select ID from PL_COOPERATION_PROJECT), 'gDl4mqw9TI6FxZnrhSz', '1', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_PROJECT_BANK (ID, PROJECT_ID, BANK_ID, IS_SUPPORT, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('SjpddoqiPgrpWWpZBg2', (select ID from PL_COOPERATION_PROJECT), 'NJazsmSMefoeKs278rZ', '1', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_PROJECT_BANK (ID, PROJECT_ID, BANK_ID, IS_SUPPORT, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('biwrB1eeS5WPWFIy7K7', (select ID from PL_COOPERATION_PROJECT), '5CIlih4sWBrbGSwga3g', '1', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_PROJECT_BANK (ID, PROJECT_ID, BANK_ID, IS_SUPPORT, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('EXOJgDAY8MkkEcW2FEZ', (select ID from PL_COOPERATION_PROJECT), 'REx9dOzKXK5Fr5BtugF', '1', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_PROJECT_BANK (ID, PROJECT_ID, BANK_ID, IS_SUPPORT, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('iNCXC21CbPbAH1tyqhc', (select ID from PL_COOPERATION_PROJECT), 'derdPLb7jYcG4rGwFBj', '1', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_PROJECT_BANK (ID, PROJECT_ID, BANK_ID, IS_SUPPORT, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('hqGwHHemVfGKucrGt1A', (select ID from PL_COOPERATION_PROJECT), 'iOx1hmI8ABCt4rJ6Bhj', '1', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_PROJECT_BANK (ID, PROJECT_ID, BANK_ID, IS_SUPPORT, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('EdSnQfReZsRn4lsaIDs', (select ID from PL_COOPERATION_PROJECT), 'SM0arUf68YDwNroUed3', '1', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_PROJECT_BANK (ID, PROJECT_ID, BANK_ID, IS_SUPPORT, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('0Z3ca4x9Z6AtdaZL2jE', (select ID from PL_COOPERATION_PROJECT), '5TslBKcHqccChmRMXcC', '1', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');


--合作项目期限(小于期限的都默认通过)
insert into PL_COOPERATION_PERIOD (ID, PROJECT_ID, COOPERATION_PERIOD_VALUE, COOPERATION_PERIOD_TYPE, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('5RB7iO1QXUUQPdNhxEi', (select ID from PL_COOPERATION_PROJECT), 12, 'M', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');

--合作项目还款方式
--提前结清
insert into PL_REPAYMENT_INFO (ID, PROJECT_ID, REPAYMENT_TYPE, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('lRNgzr8jR089tJHNlbu', (select ID from PL_COOPERATION_PROJECT), '1', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
--按期足额还款
insert into PL_REPAYMENT_INFO (ID, PROJECT_ID, REPAYMENT_TYPE, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('Bbn74E3KblFXOYx4uzZ', (select ID from PL_COOPERATION_PROJECT), '2', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');

--入件渠道
--嗨付
insert into PL_INSERTS_CHANNEL (ID, PROJECT_ID, INSERT_CHANNEL_TYPE, INSERT_CHANNEL_NAME, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('Fm1xOlnA7zApwAoub5h', (select ID from PL_COOPERATION_PROJECT), '19','嗨付APP', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');

--消金产品
insert into PL_CASH_PRODUCT (ID, PROJECT_ID, CASH_PRODUCT_NO, CASH_PRODUCT_NAME, ANNUALIZED_RATE, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('540ZkgyA0BOEjCjYI3M', (select ID from PL_COOPERATION_PROJECT), '20180035', '够花（嗨付分期A）-复借用户', 0.180000000, SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_CASH_PRODUCT (ID, PROJECT_ID, CASH_PRODUCT_NO, CASH_PRODUCT_NAME, ANNUALIZED_RATE, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('Xuu55qdjAzrIezdUbc4', (select ID from PL_COOPERATION_PROJECT), '20180036', '够花（嗨付分期B）-复借用户', 0.216000000, SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_CASH_PRODUCT (ID, PROJECT_ID, CASH_PRODUCT_NO, CASH_PRODUCT_NAME, ANNUALIZED_RATE, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('OP9Pbbdkp6Fj0OrJCoC', (select ID from PL_COOPERATION_PROJECT), '20180037', '够花（嗨付分期C）-复借用户', 0.234000000, SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');

--客户标签
insert into PL_CASH_CUST_SIGN (ID, PRODUCT_ID, PROJECT_ID, CASH_CUST_SIGN, CASH_CUST_SIGN_NAME, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('cKQr6SKMzxTod1CTz0f', '540ZkgyA0BOEjCjYI3M', (select ID from PL_COOPERATION_PROJECT), '0510fa9121f4432180ace8e311452c61', '够花（嗨付分期A）-复借用户', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_CASH_CUST_SIGN (ID, PRODUCT_ID, PROJECT_ID, CASH_CUST_SIGN, CASH_CUST_SIGN_NAME, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('imG5sOdZP9aUaC51XWi', 'Xuu55qdjAzrIezdUbc4', (select ID from PL_COOPERATION_PROJECT), 'a668c08a2bd64bb6805d47197c3b2396', '够花（嗨付分期B）-复借用户', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');
insert into PL_CASH_CUST_SIGN (ID, PRODUCT_ID, PROJECT_ID, CASH_CUST_SIGN, CASH_CUST_SIGN_NAME, CREATE_DATE, UPDATE_DATE, CREATE_BY, DEL_FLAG) values ('jaTSk9fXG9lh9ayN9Dh', 'OP9Pbbdkp6Fj0OrJCoC', (select ID from PL_COOPERATION_PROJECT), 'cb7a307c1776448f9c6dbb6614f7a9bf', '够花（嗨付分期C）-复借用户', SYSDATE, SYSDATE, 'SYSTEM', 'NORMAL');

--添加需求资料
insert into PL_AGENCY_DEMAND_INFO (ID, AGENCY_ID, DEMAND_TYPE, CREATE_DATE, UPDATE_DATE, CREATE_BY, UPDATE_BY, DEL_FLAG) values ('AsYIELI1tSgZeQNVDFf', (select ID from PL_COOPERATION_AGENCY), 3, SYSDATE, SYSDATE, 'SYSTEM', 'SYSTEM', 'NORMAL');

--添加需求资料明细
insert into PL_AGENCY_DEMAND_ITEM (ID, DEMAND_ID, MATERIAL_TYPE, MATERIAL_NAME, MATERIAL_VALUE, CREATE_DATE, UPDATE_DATE, CREATE_BY, UPDATE_BY, DEL_FLAG) values ('0y4lwl8Hbd9ypRjtGq7', 'AsYIELI1tSgZeQNVDFf', 'CREDIT_MODLE', '征信模板', '/static/agreement/ICBCCredit.html', SYSDATE, SYSDATE, 'SYSTEM', 'SYSTEM', 'NORMAL');