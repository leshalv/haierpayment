package com.haiercash.pluslink.capital.processer.server.service;

import cn.jbinfo.api.context.ApiContextManager;
import cn.jbinfo.api.exception.SystemException;
import cn.jbinfo.cloud.core.utils.MyBeanUtils;
import cn.jbinfo.common.utils.JsonUtils;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.haiercash.pluslink.capital.common.exception.PlCapitalException;
import com.haiercash.pluslink.capital.common.mybatis.dao.BaseCommonDao;
import com.haiercash.pluslink.capital.common.redis.IRedisCommonService;
import com.haiercash.pluslink.capital.common.utils.PositionSplitUtils;
import com.haiercash.pluslink.capital.common.utils.SequenceUtil;
import com.haiercash.pluslink.capital.constant.CommonConstant;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.data.CooperationProject;
import com.haiercash.pluslink.capital.data.RouteResultRecord;
import com.haiercash.pluslink.capital.entity.AlreadyPositionIn;
import com.haiercash.pluslink.capital.entity.AlreadyPositionOut;
import com.haiercash.pluslink.capital.entity.PositionSplitIn;
import com.haiercash.pluslink.capital.entity.PositionSplitOut;
import com.haiercash.pluslink.capital.enums.RedisCacheEnum;
import com.haiercash.pluslink.capital.enums.SerialNoEnum;
import com.haiercash.pluslink.capital.enums.dictionary.*;
import com.haiercash.pluslink.capital.processer.server.cache.AssetsSplitItemCache;
import com.haiercash.pluslink.capital.processer.server.config.AccountIdConfig;
import com.haiercash.pluslink.capital.processer.server.dao.AssetsSplitItemDao;
import com.haiercash.pluslink.capital.processer.server.enums.AssetsSplitItemLoanTypeEnum;
import com.haiercash.pluslink.capital.processer.server.enums.CommonReturnCodeEnum;
import com.haiercash.pluslink.capital.processer.server.enums.ResponseStatusEnum;
import com.haiercash.pluslink.capital.processer.server.exception.BusinessException;
import com.haiercash.pluslink.capital.processer.server.pvm.FlowWorkerServer;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.CreditApplContext;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.PaymentGatewayBackContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yu jianwei
 * @date 2018/7/13 13:35
 * 资产拆分业务处理
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class AssetsSplitItemService extends BaseService implements Serializable {


    @Autowired
    private BaseCommonDao baseCommonDao;
    @Autowired
    private IRedisCommonService redisCommonService;

    @Autowired
    private AssetsSplitItemDao assetsSplitItemDao;

    @Autowired
    private ProcesserJobService processerJobService;

    @Autowired
    private AssetsSplitItemCache assetsSplitItemCache;

    @Autowired
    private CommonDaoService commonDaoService;
    @Autowired
    private AccountIdConfig accountIdConfig;

    /**
     * assetsSplit
     * 资产拆分
     *
     * @param json
     * @return void
     * @author yu jianwei
     * @date 2018/7/13 13:34
     */
    @Transactional
    public void assetsSplit(String json) {
        log.info("===============<处理中心>-资产拆分开始===============");
        /*
         * (1).接收资产表信息, 主键ID，资方、自有 借据号，交易金额（后），贷款本金
         */
        log.info("===============<处理中心>-资产拆分接收mq信息->{}", json);
        AssetsSplit assetsSplit = JsonUtils.readObjectByJson(json, AssetsSplit.class);
        log.info("=============<处理中心>-资产拆分->业务号：{}", assetsSplit.getApplSeq());
        assetsSplit.setProdBuyOut(PL0106Enum.PL0106_1_NONSUPPORT.getCode());
        /* 业务编号*/
        String applSeq = assetsSplit.getApplSeq();
        /*贷款状态*/
        String loanStatus = assetsSplit.getLoanStatus();
        /*是否附后置余额支付请求 为YES时为非联合放款*/
        String balancePayTag = assetsSplit.getBalancePayTag();
        String projectId = "";
        /*
         * (2).根据业务编号查询<>路由结果记录表</> 获取资方放款占比，合作机构表主键id ,合作项目表主键id,渠道号channelNo
         */

        RouteResultRecord routeResultRecord = commonDaoService.selectByApplSeq(applSeq);
        AssetsSplitItem assetsSplitItem = new AssetsSplitItem();
        MyBeanUtils.copyProperties(assetsSplit, assetsSplitItem);
        assetsSplitItem.setAssetsSplitId(assetsSplit.getId());
        Date date = new Date();
        assetsSplitItem.setCreateDate(date);
        assetsSplitItem.setUpdateDate(date);
        /*路由表中无记录处理逻辑货值*/
        if (!StringUtils.isEmpty(routeResultRecord)) {
            projectId = routeResultRecord.getProjectId();
        }
        if (StringUtils.isEmpty(routeResultRecord) || CommonConstant.CASH_LOAN_AMOUNT.equals(projectId) || PL0105Enum.PL0105_1_YES.getCode().equals(balancePayTag)) {
            log.info("=============<处理中心>-资产拆分->业务号：{},独立放款", applSeq);
            independentLending(assetsSplit, assetsSplitItem, applSeq, loanStatus, projectId, balancePayTag);
            return;
        }
        /*合作机构编号*/
        MyBeanUtils.copyProperties(routeResultRecord, assetsSplitItem);
        try {
            /*
             * (3).根据合作项目表主键projectId 查询<>合作项目表</>获取 限额
             */
            CooperationProject cooperationProject = new CooperationProject();
            if ("true".equals(accountIdConfig.getSplitSwitch())) {
                Map<String, CooperationProject> cooperationProjectMap = redisCommonService.selectRedis(RedisCacheEnum.cooperation_project.getCode(), new TypeReference<Map<String, CooperationProject>>() {
                });

                if (cooperationProjectMap != null && !StringUtils.isEmpty(cooperationProjectMap)) {
                    cooperationProject = cooperationProjectMap.get(projectId);
                }
            }
            if (StringUtils.isEmpty(cooperationProject) || StringUtils.isEmpty(cooperationProject.getId())) {
                cooperationProject = commonDaoService.selectById(projectId);
            }
            if (StringUtils.isEmpty(cooperationProject) || StringUtils.isEmpty(cooperationProject.getId())) {
                log.info("===============<处理中心>-资产拆分->业务编号{},查询合作项目表无数据->projectId:{}", applSeq, projectId);
                throw new SystemException(CommonReturnCodeEnum.DATA_NOT_EXIST.getDesc());
            }
            log.info("===============<资产拆分>->业务编号{},合作项目信息：{}", applSeq, JsonUtils.writeObjectToJson(cooperationProject));
            /*放款占比*/
            BigDecimal agencyRatio = cooperationProject.getAgencyRatio();
            assetsSplitItem.setAgencyRate(agencyRatio);
            log.info("===============<资产拆分>->业务编号{}，资产占比：{}", applSeq, agencyRatio);
            /*60放款失败*/
            if (loanStatus.equals(PL0506Enum.PL0506_6_60.getCode()) || loanStatus.equals(PL0506Enum.PL0506_7_70.getCode())) {
                /*交易总金额*/
                BigDecimal origPrcp = assetsSplit.getTotalAmount();
                PositionSplitOut positionSplitOut = PositionSplitUtils.splitAmount(origPrcp, agencyRatio);
                ArrayList<AssetsSplitItem> assetsSplitItems = new ArrayList<>();
                BigDecimal cashRatio = positionSplitOut.getCashRatio();
                BigDecimal agencyLoanAmount = positionSplitOut.getAgencyLoanAmount();
                /* 放款失败 资方拆分信息*/
                AssetsSplitItem assetsSplitItemExternal = getAssetsSplitItemExternal(assetsSplitItem, assetsSplit.getLoanNo2(), agencyLoanAmount, agencyRatio);
                assetsSplitItemExternal.setApplAmt(agencyLoanAmount);
                assetsSplitItemExternal.setStatus(PL0601Enum.PL0601_1_10.getCode());
                log.info("===============<处理中心>-资产拆分，业务编号{}，放款失败存入信息，此时明细表资方放款状态{}===============", applSeq, PL0601Enum.PL0601_1_10.getCode());
                /* 放款失败 自有拆分信息*/
                BigDecimal cashAmount = positionSplitOut.getCashLoanAmount();
                AssetsSplitItem assetsSplitItemInternal = getAssetsSplitItemInternal(assetsSplitItem, assetsSplit.getLoanNo1(), cashAmount, cashRatio);
                assetsSplitItem.setApplAmt(cashAmount);
                assetsSplitItemInternal.setStatus(PL0601Enum.PL0601_12_32.getCode());
                log.info("===============<处理中心>-资产拆分，业务编号{}，放款失败存入信息，此时明细表自有放款状态{}===============", applSeq, PL0601Enum.PL0601_12_32.getCode());
                assetsSplitItems.add(assetsSplitItemInternal);
                assetsSplitItems.add(assetsSplitItemExternal);
                log.info("===============<处理中心>-资产拆分,业务编号{}，放款失败存入信息===============", applSeq);
                commonDaoService.insertAssetsSplitItemList(assetsSplitItems);
                log.info("===============<处理中心>-资产拆分.业务编号：{}，放款失败结束===============", applSeq);
                assetsSplit.setProjectType(PL0505Enum.PL0505_1_UNION.getCode());
                assetsSplit.setUpdateDate(new Date());
                commonDaoService.updateProjectTypeById(assetsSplit);
                log.info("===============<处理中心>-资产拆分,业务编号{}，放款失败，资产表是否联合放款设置为是{}===============", applSeq, PL0505Enum.PL0505_1_UNION.getCode() + "】");
                PaymentGatewayBackContext paymentGatewayBackContext = new PaymentGatewayBackContext(assetsSplit, ResponseStatusEnum.FAIL.getCode(), assetsSplit.getContractNo(), assetsSplit.getContractNo());
                flowWorkerServer.paymentGatewayBackHandler(paymentGatewayBackContext, this.getClass().getName());
                log.info("===============<处理中心>-资产拆分业务编号{}，放款失败存储结束", applSeq);
                return;
            }
            /*
             *(4).根据查询 合作结构编号agencyId 和 状态 查询<>资产拆分明细表</> 获取已受理的金额总和
             */
            AlreadyPositionIn alreadyPositionIn = new AlreadyPositionIn(projectId, new Date());
            AlreadyPositionOut alreadyPositionOut = baseCommonDao.selecAlreadyPosition(alreadyPositionIn);
            /*公共方法返回 消金金额，资方金额，消金占比，是否超头寸*/
            PositionSplitIn positionSplitIn = new PositionSplitIn();
            MyBeanUtils.copyProperties(cooperationProject, positionSplitIn);
            MyBeanUtils.copyProperties(assetsSplitItem, positionSplitIn);
            positionSplitIn.setApplSeq(applSeq);
            positionSplitIn.setAgencyRatio(agencyRatio);
            positionSplitIn.setOrigPrcp(assetsSplit.getTotalAmount());
            positionSplitIn.setAlreadyLoanAmount(alreadyPositionOut.getAlreadyLoanAmount() == null ? BigDecimal.ZERO : alreadyPositionOut.getAlreadyLoanAmount());
            positionSplitIn.setAlreadyLoanLimitMonth(alreadyPositionOut.getAlreadyLoanLimitMonth() == null ? BigDecimal.ZERO : alreadyPositionOut.getAlreadyLoanLimitMonth());
            positionSplitIn.setAlreadyLoanLimitDay(alreadyPositionOut.getAlreadyLoanLimitDay() == null ? BigDecimal.ZERO : alreadyPositionOut.getAlreadyLoanLimitDay());
            PositionSplitOut positionSplitOut = PositionSplitUtils.dealPositionSplit(positionSplitIn);
            /*
             * (5). 根据是否超头寸，进行 资产拆分存储并更新资产表-非联合放款状态
             */
            if (PL0601Enum.PL0601_2_11.equals(positionSplitOut.getStatus())) {
                log.info("===============<处理中心>-资产拆分资产编号{}，放款失败，资产表是否联合放款设置为否{}===============", applSeq, PL0505Enum.PL0505_2_NOT_UNION.getCode());
                /*菲联合方放款*/
                assetsSplit.setProjectType(PL0505Enum.PL0505_2_NOT_UNION.getCode());
                assetsSplit.setUpdateDate(new Date());
                List<AssetsSplitItem> assetsSplitItems = Lists.newArrayList();
                /* 超头寸 资方信息 11头寸不足 资方*/
                AssetsSplitItem assetsSplitItemExternal = getAssetsSplitItemExternal(assetsSplitItem, assetsSplit.getLoanNo2(), positionSplitOut.getAgencyLoanAmount(), agencyRatio);
                assetsSplitItemExternal.setApplAmt(positionSplitOut.getAgencyLoanAmount());
                assetsSplitItemExternal.setStatus(PL0601Enum.PL0601_2_11.getCode());
                log.info("===============<处理中心>-资产拆分,业务编号{}，头寸不足插入一条工行明细，工行数据放款状态头寸不足{}===============", applSeq, PL0601Enum.PL0601_2_11.getCode());
                /*修改资产状态为成功，插入一条海尔明细（全部自有放款），状态放款成功 自有*/
                AssetsSplitItem assetsSplitItemInternal = getAssetsSplitItemInternal(assetsSplitItem, assetsSplit.getLoanNo1(), assetsSplit.getTotalAmount(), BigDecimal.ONE);
                assetsSplitItemInternal.setApplAmt(assetsSplit.getTotalAmount());
                if (loanStatus.equals(PL0506Enum.PL0506_3_30.getCode())) {
                    /*放款通知 assetsSplitItems 更改资产状态为成功*/
                    log.info("===============<处理中心>-资产拆分,业务编号{},头寸不足插入一条海尔明细，海尔数据放款状态成功{}===============", applSeq, PL0601Enum.PL0601_11_31.getCode());
                    assetsSplitItemInternal.setStatus(PL0601Enum.PL0601_11_31.getCode());
                    assetsSplitItems.add(assetsSplitItemExternal);
                    assetsSplitItems.add(assetsSplitItemInternal);
                    log.info("===============<处理中心>-业务编号{}，资产拆分头寸不足存入信息{}===============", applSeq, assetsSplitItems);
                    commonDaoService.insertAssetsSplitItemList(assetsSplitItems);
                    log.info("===============<处理中心>-业务编号{}，资产拆分头寸不足存入数据库信息结束===============", applSeq);
                    commonDaoService.updateProjectTypeById(assetsSplit);
                    PaymentGatewayBackContext paymentGatewayBackContext = new PaymentGatewayBackContext(assetsSplit, ResponseStatusEnum.SUCCESS.getCode(), assetsSplit.getContractNo(), assetsSplit.getContractNo());
                    flowWorkerServer.paymentGatewayBackHandler(paymentGatewayBackContext, this.getClass().getName());
                    return;
                }
                if (loanStatus.equals(PL0506Enum.PL0506_1_10.getCode()) || loanStatus.equals(PL0506Enum.PL0506_2_20.getCode())) {
                    log.info("===============<处理中心>-资产拆分,业务编号{}，头寸不足插入一条海尔明细，海尔数据放款状态处理中{}===============", applSeq, PL0601Enum.PL0601_10_30.getCode());
                    assetsSplitItemInternal.setStatus(PL0601Enum.PL0601_10_30.getCode());
                    assetsSplitItems.add(assetsSplitItemExternal);
                    assetsSplitItems.add(assetsSplitItemInternal);
                    log.info("===============<处理中心>-资产拆分,业务编号{},头寸不足存入信息{}===============", applSeq, assetsSplitItems);
                    commonDaoService.insertAssetsSplitItemList(assetsSplitItems);
                    log.info("===============<处理中心>-资产拆分业务编号{}，头寸不足存入数据库信息结束===============", applSeq);
                    commonDaoService.updateProjectTypeById(assetsSplit);
                    /*轮询*/
                    actFlowWorkerServer(assetsSplit);
                    return;
                }
            }
            /* 满足条件 资方信息 10 已受理*/
            AssetsSplitItem assetsSplitItemExternal = getAssetsSplitItemExternal(assetsSplitItem, assetsSplit.getLoanNo2(), positionSplitOut.getAgencyLoanAmount(), agencyRatio);
            assetsSplitItemExternal.setStatus(PL0601Enum.PL0601_1_10.getCode());
            log.info("===============<处理中心>-资产正常拆分,业务编号{}，资方信息状态=====插一条工行明细，放款状态已受理{}:", applSeq, PL0601Enum.PL0601_1_10.getCode());
            commonDaoService.insertAssetsSplitItem(assetsSplitItemExternal);
            assetsSplit.setProjectType(PL0505Enum.PL0505_1_UNION.getCode());
            assetsSplit.setUpdateDate(new Date());
            log.info("===============<处理中心>-业务编号{}，更改资产表=====================projectType:{}，prodBuyOut:{}", applSeq, assetsSplit.getProjectType(), assetsSplit.getProdBuyOut());
            logger.info("===============<处理中心>-资产拆分->联合放款->更新资产表状态====assetsSplit：" + JsonUtils.writeObjectToJson(assetsSplit));
            commonDaoService.updateProjectTypeById(assetsSplit);
            log.info("===============<处理中心>-业务编号{}，资产正常拆分生产事件=====================", applSeq);
            event(assetsSplit, assetsSplitItemExternal);
            log.info("===============<处理中心>-业务编号{}，资产正常拆分存入数据库信息结束=====================", applSeq);
        } catch (BusinessException e) {
            log.info("===========业务编号{}，数组重组异常走独立放款=============", applSeq);
            independentLending(assetsSplit, assetsSplitItem, applSeq, loanStatus, projectId, balancePayTag);
            return;
        } catch (SystemException e) {
            log.error("业务编号{}，资产拆分出现System异常:", applSeq, e);
            throw new SystemException(e.getCode(), e.getMessage(), ApiContextManager.getSerNo(), e);
        } catch (PlCapitalException e) {
            log.error("业务编号{}，资产拆分出现PlCapital异常", applSeq, e);
            throw new SystemException(e.getDefineCode(), e.getDefineMessage(), ApiContextManager.getSerNo(), e);
        } catch (Exception e) {
            log.error("业务编号{}，资产拆分出现未知异常", applSeq, e);
            throw new SystemException(CommonReturnCodeEnum.DATA_INSERT_EXCEPTION.getCode(), CommonReturnCodeEnum.DATA_INSERT_EXCEPTION.getDesc(), ApiContextManager.getSerNo(), e);
        }
    }

    @Transactional
    public void insertAssetsSplitItem(AssetsSplitItem assetsSplitItem) {
        if (assetsSplitItem.getCreateDate() == null)
            assetsSplitItem.setCreateDate(new Date());
        if (assetsSplitItem.getUpdateDate() == null)
            assetsSplitItem.setUpdateDate(new Date());
        if (org.apache.commons.lang3.StringUtils.isBlank(assetsSplitItem.getCreateBy()))
            assetsSplitItem.setCreateBy("SYSTEM");
        assetsSplitItemCache.put(assetsSplitItem);
        commonDaoService.insertAssetsSplitItem(assetsSplitItem);
    }

    /**
     * 插入一条自有放款明细记录
     * <p>
     * 针对非联合放款
     *
     * @param assetsSplit
     */
    @Transactional
    public AssetsSplitItem insertAssetsSplitItemInternal(AssetsSplit assetsSplit, PL0601Enum pl0601Enum, String memo) {
        AssetsSplitItem assetsSplitItem = new AssetsSplitItem();
        String assetsSplitItemID = SequenceUtil.getSequence(SerialNoEnum.PL_ASSETS_SPLIT.getTypeName(), commonDaoService.getSequence(SerialNoEnum.PL_ASSETS_SPLIT_ITEM.getSeqName()));
        assetsSplitItem.setId(assetsSplitItemID);
        assetsSplitItem.setCreateDate(new Date());
        assetsSplitItem.setStatus(PL0601Enum.PL0601_11_31.getCode());
        assetsSplitItem.setTransAmt(assetsSplit.getTotalAmount());
        /*非联合放款 申请金额 为资产本金*/
        assetsSplitItem.setApplAmt(assetsSplit.getOrigPrcp());
        assetsSplitItem.setAgencyRate(BigDecimal.ONE);
        assetsSplitItem.setLoanNo(assetsSplit.getLoanNo1());
        assetsSplitItem.setUpdateDate(new Date());
        assetsSplitItem.setCapLoanNo("");
        assetsSplitItem.setMemo(memo);
        assetsSplitItem.setLoanType(PL0602Enum.PL0602_1_OWN.getCode());
        assetsSplitItem.setDelFlag(PL0101Enum.PL0101_2_NORMAL.getCode());
        assetsSplitItem.setAssetsSplitId(assetsSplit.getId());
        assetsSplitItem.setStatus(pl0601Enum.getCode());
        assetsSplitItem.setTransAmt(assetsSplit.getTotalAmount());
        assetsSplitItem.setAgencyRate(BigDecimal.ONE);
        assetsSplitItem.setLoanNo(assetsSplit.getLoanNo1());
        assetsSplitItem.setStatus(PL0601Enum.PL0601_11_31.getCode());
        assetsSplitItem.setAssetsSplitId(assetsSplit.getId());
        assetsSplitItem.setApplAmt(assetsSplit.getTotalAmount());
        assetsSplitItem.setCapLoanNo("");
        assetsSplitItem.setAgencyId("");
        assetsSplitItem.setProjectId("");
        assetsSplitItem.setMemo(PL0601Enum.PL0601_11_31.getDesc());
        assetsSplitItem.setLoanType(PL0602Enum.PL0602_1_OWN.getCode());
        assetsSplitItem.setDelFlag(PL0101Enum.PL0101_2_NORMAL.getCode());
        insertAssetsSplitItem(assetsSplitItem);
        assetsSplitItemCache.put(assetsSplitItem);
        return assetsSplitItem;
    }

    /**
     * 插入一条自有放款明细记录
     * <p>
     * 针对联合放款
     *
     * @param assetsSplit
     * @param splitItem
     */
    @Transactional
    public void insertUnionAssetsSplitItemInternal(AssetsSplit assetsSplit, AssetsSplitItem splitItem, PL0601Enum pl0601Enum, String memo) {
        AssetsSplitItem assetsSplitItem = new AssetsSplitItem();
        MyBeanUtils.copyProperties(splitItem, assetsSplitItem);
        assetsSplitItem.setId(SequenceUtil.getSequence(SerialNoEnum.PL_ASSETS_SPLIT_ITEM.getTypeName(), commonDaoService.getSequence(SerialNoEnum.PL_ASSETS_SPLIT_ITEM.getSeqName())));
        assetsSplitItem.setCreateDate(new Date());
        assetsSplitItem.setStatus(pl0601Enum.getCode());
        assetsSplitItem.setTransAmt(assetsSplit.getTotalAmount().subtract(assetsSplitItem.getTransAmt()));
        assetsSplitItem.setApplAmt(assetsSplitItem.getTransAmt());
        assetsSplitItem.setAgencyRate(BigDecimal.ONE.subtract(assetsSplitItem.getAgencyRate()));
        assetsSplitItem.setAssetsSplitId(assetsSplit.getId());
        assetsSplitItem.setLoanNo(assetsSplit.getLoanNo1());
        assetsSplitItem.setUpdateDate(new Date());
        assetsSplitItem.setStatus(PL0601Enum.PL0601_11_31.getCode());
        assetsSplitItem.setCapLoanNo("");
        assetsSplitItem.setAgencyId("");
        assetsSplitItem.setProjectId("");
        assetsSplitItem.setMemo(memo);
        assetsSplitItem.setLoanType(PL0602Enum.PL0602_1_OWN.getCode());
        assetsSplitItem.setDelFlag(PL0101Enum.PL0101_2_NORMAL.getCode());
        insertAssetsSplitItem(assetsSplitItem);
    }

    @Transactional
    public void updateStatusById(AssetsSplitItem assetsSplitItem, PL0601Enum itemStatusEnum, String message) {
        assetsSplitItem.setStatus(itemStatusEnum.getCode());
        assetsSplitItemCache.put(assetsSplitItem);
        if (org.apache.commons.lang3.StringUtils.isBlank(message))
            assetsSplitItemDao.updateStatusById(assetsSplitItem.getId(), itemStatusEnum.getCode(), itemStatusEnum.getDesc(), PL0101Enum.PL0101_2_NORMAL.getCode());
        else
            assetsSplitItemDao.updateStatusById(assetsSplitItem.getId(), itemStatusEnum.getCode(), message, PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    /**
     * 前置放款失败，更新自有明细
     *
     * @param assetsSplitItem 自有明细
     */
    @Transactional
    public void updateBackData(AssetsSplit assetsSplit, AssetsSplitItem assetsSplitItem, String memo) {
        if (assetsSplit.getTotalAmount().compareTo(assetsSplitItem.getTransAmt()) == 0) {
            return;
        }
        assetsSplitItem.setTransAmt(assetsSplit.getTotalAmount());
        assetsSplitItem.setApplAmt(assetsSplitItem.getTransAmt());
        assetsSplitItem.setAgencyRate(BigDecimal.ONE);
        assetsSplitItem.setAssetsSplitId(assetsSplit.getId());
        assetsSplitItem.setUpdateDate(new Date());
        assetsSplitItem.setMemo(memo);
        assetsSplitItem.setStatus(PL0601Enum.PL0601_11_31.getCode());
        assetsSplitItemCache.put(assetsSplitItem);
        assetsSplitItemDao.updateBackData(assetsSplitItem);
    }


    /**
     * @param assetsSplitItem
     * @param itemStatusEnum
     * @param capLoanNo       贷款协议
     */
    @Transactional
    public void updateStatusAndCapLoanNoById(AssetsSplitItem assetsSplitItem, PL0601Enum itemStatusEnum, String capLoanNo) {
        assetsSplitItem.setStatus(itemStatusEnum.getCode());
        assetsSplitItem.setCapLoanNo(capLoanNo);
        assetsSplitItemCache.put(assetsSplitItem);
        assetsSplitItemDao.updateStatusAndCapLoanNoById(assetsSplitItem.getId(), itemStatusEnum.getCode(),
                capLoanNo, itemStatusEnum.getDesc(), PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    @Transactional
    public void updateStatusAndDelFlagById(AssetsSplitItem assetsSplitItem, PL0601Enum itemStatusEnum, String message) {
        assetsSplitItem.setStatus(itemStatusEnum.getCode());
        assetsSplitItemCache.put(assetsSplitItem);
        if (org.apache.commons.lang3.StringUtils.isBlank(message))
            assetsSplitItemDao.updateStatusAndDelFlagById(assetsSplitItem.getId(), itemStatusEnum.getCode(), itemStatusEnum.getDesc(), PL0101Enum.PL0101_2_NORMAL.getCode());
        else
            assetsSplitItemDao.updateStatusAndDelFlagById(assetsSplitItem.getId(), itemStatusEnum.getCode(), message, PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    public AssetsSplitItem get(String id) {
        return assetsSplitItemDao.selectById(id, PL0101Enum.PL0101_2_NORMAL.getCode());
    }


    /**
     * 自有放款拆分实体
     */
    private AssetsSplitItem getAssetsSplitItemInternal(AssetsSplitItem assetsSplitItemBefore, String loanNo, BigDecimal cashLoanAmount, BigDecimal cashRatio) {
        AssetsSplitItem assetsSplitItem = new AssetsSplitItem();
        MyBeanUtils.copyProperties(assetsSplitItemBefore, assetsSplitItem);
        assetsSplitItem.setId(SequenceUtil.getSequence(SerialNoEnum.PL_ASSETS_SPLIT_ITEM.getTypeName(), commonDaoService.getSequence(SerialNoEnum.PL_ASSETS_SPLIT_ITEM.getSeqName())));
        assetsSplitItem.setLoanNo(loanNo);
//        assetsSplitItem.setApplAmt(cashLoanAmount);
        assetsSplitItem.setTransAmt(cashLoanAmount);
        assetsSplitItem.setAgencyRate(cashRatio);
        assetsSplitItem.setCreateDate(new Date());
        assetsSplitItem.setUpdateDate(new Date());
        assetsSplitItem.setLoanType(PL0602Enum.PL0602_1_OWN.getCode());
        assetsSplitItem.setCreateBy(CommonConstant.SYSTEM_OPER);
        assetsSplitItemCache.put(assetsSplitItem);
        return assetsSplitItem;
    }

    //根据资产表主键查询资产明细，只查询放款状态为成功或者失败状态的明细，推送信贷专用
    public List<AssetsSplitItem> selectByAssetsSpiltIdForCredit(String assetsSpiltId) {
        return assetsSplitItemDao.selectByAssetsSpiltIdForCredit(assetsSpiltId, PL0101Enum.PL0101_2_NORMAL.getCode(), PL0601Enum.PL0601_11_31.getCode());
    }

    public AssetsSplitItem selectByAssetsSpiltId(String assetsSpiltId, String loanNo) {
        /*AssetsSplitItem assetsSplitItem = assetsSplitItemCache.getByAssetSplitIdAndLoanNo(assetsSpiltId, loanNo);
        if (assetsSplitItem != null) {
            return assetsSplitItem;
        }
        assetsSplitItem = assetsSplitItemDao.selectByAssetsSpiltIdAndLoan(assetsSpiltId, loanNo, PL0101Enum.PL0101_2_NORMAL.getCode());
        assetsSplitItemCache.put(assetsSplitItem);
        return assetsSplitItem;*/
        return assetsSplitItemDao.selectByAssetsSpiltIdAndLoan(assetsSpiltId, loanNo, PL0101Enum.PL0101_2_NORMAL.getCode());
    }


    /**
     * 资方放款拆分实体
     */
    private AssetsSplitItem getAssetsSplitItemExternal(AssetsSplitItem assetsSplitItemBefore, String loanNo, BigDecimal agencyLoanAmount, BigDecimal rate) {
        AssetsSplitItem assetsSplitItem = new AssetsSplitItem();
        MyBeanUtils.copyProperties(assetsSplitItemBefore, assetsSplitItem);
        assetsSplitItem.setId(SequenceUtil.getSequence(SerialNoEnum.PL_ASSETS_SPLIT_ITEM.getTypeName(), commonDaoService.getSequence(SerialNoEnum.PL_ASSETS_SPLIT_ITEM.getSeqName())));
        assetsSplitItem.setLoanNo(loanNo);
        assetsSplitItem.setApplAmt(agencyLoanAmount);
        assetsSplitItem.setTransAmt(agencyLoanAmount);
        assetsSplitItem.setLoanType(AssetsSplitItemLoanTypeEnum.LOAN_TYPE_EXTERNAL.getCode());
        assetsSplitItem.setAgencyRate(rate);
        assetsSplitItem.setCreateDate(new Date());
        assetsSplitItem.setUpdateDate(new Date());
        assetsSplitItem.setCreateBy(CommonConstant.SYSTEM_OPER);
        return assetsSplitItem;
    }

    /**
     * 事件处理
     */
    public void event(AssetsSplit assetsSplit, AssetsSplitItem assetsSplitItem) {
        CreditApplContext creditApplContext;
        try {
            creditApplContext = new CreditApplContext(assetsSplit, assetsSplitItem);
        } catch (Exception e) {
            throw new BusinessException("数据重组异常", e);
        }
        flowWorkerServer.creditApply(creditApplContext, this.getClass().getName());
    }


    //激活轮询开始
    public void actFlowWorkerServer(AssetsSplit assetsSplit) {
        processerJobService.addTradeStatusSearchJob(assetsSplit);
        log.info("===============<处理中心>-业务编号{}，成功通知支付网关放款状态为处理中激活定时任务成功===============", assetsSplit.getApplSeq());
    }

    @Autowired
    private FlowWorkerServer flowWorkerServer;

    /*
     * independentLending
     * 独立放款
     * @author yu jianwei
     * @date 2018/8/14 19:33
     * @param [assetsSplit, assetsSplitItem, applSeq, loanStatus]
     * @return void
     */

    public void independentLending(AssetsSplit assetsSplit, AssetsSplitItem assetsSplitItem, String applSeq, String loanStatus, String projectId, String balancePayTag) {
        log.info("===============<处理中心>-资产拆分->独立放款->applSeq:{},直接消金放款(CASH_LOAN_AMOUNT)或者()projectId:{},是否附后置余额支付请求 为YES时为非联合放款:{}", applSeq, projectId, balancePayTag);
        /*修改资产表是否联合放款为否*/
        assetsSplit.setProjectType(PL0505Enum.PL0505_2_NOT_UNION.getCode());
        assetsSplit.setUpdateDate(new Date());
        //如果斩头后金额是0，则把斩头后金额设置为交易总金额
        AssetsSplitItem assetsSplitItemInternal = getAssetsSplitItemInternal(assetsSplitItem, assetsSplit.getLoanNo1(), assetsSplit.getTotalAmount(), BigDecimal.ONE);
        /*资产本金*/
        assetsSplitItemInternal.setApplAmt(assetsSplit.getOrigPrcp());
        logger.info("===============<处理中心>-资产拆分->独立放款->更新资产表状态====assetsSplit：" + JsonUtils.writeObjectToJson(assetsSplit));
        commonDaoService.updateProjectTypeById(assetsSplit);
        if (loanStatus.equals(PL0506Enum.PL0506_6_60.getCode()) || loanStatus.equals(PL0506Enum.PL0506_7_70.getCode())) {
            /*路由结果为空且放款状态失败，插入一条海尔明细状态放款失败*/
            log.info("===============<处理中心>-业务编号：{},独立放款->插入一条海尔明细，放款状态失败{}===============", applSeq, PL0601Enum.PL0601_12_32.getCode());
            assetsSplitItemInternal.setStatus(PL0601Enum.PL0601_12_32.getCode());
            commonDaoService.insertAssetsSplitItem(assetsSplitItemInternal);
            PaymentGatewayBackContext paymentGatewayBackContext = new PaymentGatewayBackContext(assetsSplit, ResponseStatusEnum.FAIL.getCode(), assetsSplit.getContractNo(), assetsSplit.getContractNo());
            flowWorkerServer.paymentGatewayBackHandler(paymentGatewayBackContext, this.getClass().getName());
            return;
        }
        if (loanStatus.equals(PL0506Enum.PL0506_3_30.getCode())) {
            /*插入一条消金放款明细状态为放款成功*/
            log.info("===============<处理中心>-业务编号{}，资产拆分独立放款->插入一条海尔明细，网关放款状态成功{}===============", applSeq, PL0601Enum.PL0601_11_31.getCode());
            assetsSplitItemInternal.setStatus(PL0601Enum.PL0601_11_31.getCode());
            commonDaoService.insertAssetsSplitItem(assetsSplitItemInternal);
            PaymentGatewayBackContext paymentGatewayBackContext = new PaymentGatewayBackContext(assetsSplit, ResponseStatusEnum.SUCCESS.getCode(), assetsSplit.getContractNo(), assetsSplit.getContractNo());
            flowWorkerServer.paymentGatewayBackHandler(paymentGatewayBackContext, this.getClass().getName());
            return;
        }
        if (loanStatus.equals(PL0506Enum.PL0506_1_10.getCode()) || loanStatus.equals(PL0506Enum.PL0506_2_20.getCode())) {
            /*插入一条消金放款明细状态为放款中*/
            log.info("===============<处理中心>-业务编号{}，资产拆分独立放款->插入一条海尔明细，网关放款状态处理中{}===============", applSeq, PL0601Enum.PL0601_10_30.getCode());
            assetsSplitItemInternal.setStatus(PL0601Enum.PL0601_10_30.getCode());
            commonDaoService.insertAssetsSplitItem(assetsSplitItemInternal);
            /*t轮询*/
            actFlowWorkerServer(assetsSplit);
            return;
        }
    }
}
