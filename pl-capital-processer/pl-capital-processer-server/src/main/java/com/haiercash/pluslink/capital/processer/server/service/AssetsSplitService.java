package com.haiercash.pluslink.capital.processer.server.service;

import com.haiercash.pluslink.capital.common.utils.SequenceUtil;
import com.haiercash.pluslink.capital.common.utils.StringUtils;
import com.haiercash.pluslink.capital.constant.CommonConstant;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.BalancePayInfo;
import com.haiercash.pluslink.capital.enums.SerialNoEnum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0101Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0106Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0505Enum;
import com.haiercash.pluslink.capital.enums.dictionary.PL0506Enum;
import com.haiercash.pluslink.capital.processer.server.cache.AssetsSplitCache;
import com.haiercash.pluslink.capital.processer.server.dao.AssetsSplitDao;
import com.haiercash.pluslink.capital.processer.server.dao.BalancePayRequestInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author lzh
 * @date 2018/7/13 13:35
 * 插入资产表业务处理
 */
@Service
@Transactional(readOnly = true)
public class AssetsSplitService extends BaseService {

    @Autowired
    private AssetsSplitDao assetsSplitDao;

    @Autowired
    private AssetsSplitCache assetsSplitCache;

    @Autowired
    private BalancePayRequestInfoDao balancePayRequestInfoDao;
    @Autowired
    private CommonDaoService commonDaoServic;

    public AssetsSplit get(String id) {
        AssetsSplit assetsSplit = assetsSplitCache.get(id);
        if (assetsSplit != null) {
            return assetsSplit;
        }
        assetsSplit = assetsSplitDao.selectById(id, PL0101Enum.PL0101_2_NORMAL.getCode());
        assetsSplitCache.put(assetsSplit);
        return assetsSplit;
    }

    public AssetsSplit get(String id, boolean cache) {
        if (cache) {
            return get(id);
        }
        return assetsSplitDao.selectById(id, PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    /**
     * 插入数据资产表及相关的余额支付消息表
     *
     * @param assetsSplit     资产表对象
     * @param balancePayInfos 余额支付信息对象
     * @return
     */

    @Transactional
    public AssetsSplit insertAssetsSplitAndBalancePayRequestInfo(AssetsSplit assetsSplit, List<BalancePayInfo> balancePayInfos) {
        /**
         * 生成符合资产表规则的id主键
         *规则主键ID:4位固定标识+yyyyMMddhh24miss+8位序列(不足8位前面补0,多余8位截取后8位)
         */
        String assetsSplitID = SequenceUtil.getSequence(SerialNoEnum.PL_ASSETS_SPLIT.getTypeName(), commonDaoServic.getSequence(SerialNoEnum.PL_ASSETS_SPLIT.getSeqName()));
        //设置主键资产表主键
        assetsSplit.setId(assetsSplitID);
        //设置创建时间，更新时间,创建人
        assetsSplit.setCreateDate(new Date());
        assetsSplit.setCreateBy(CommonConstant.SYSTEM_OPER);
        assetsSplit.setUpdateDate(new Date());
        //初始化放款状态
        assetsSplit.setLoanStatus(PL0506Enum.PL0506_1_10.getCode());
        //初始化是否联合放款，是否支持买断s
        assetsSplit.setProjectType(PL0505Enum.PL0505_3_UNKNOW.getCode());
        assetsSplit.setProdBuyOut(PL0106Enum.PL0106_3_UNKNOW.getCode());
        assetsSplitDao.insertAssetsSplit(assetsSplit);

        if (balancePayInfos != null && balancePayInfos.size() > 0) {
            for (BalancePayInfo bpy : balancePayInfos) {
                //设置创建时间，更新时间
                bpy.setCreateDate(new Date());
                bpy.setUpdateDate(new Date());
                //设置资产表主键id
                bpy.setAssetsSplitId(assetsSplitID);
                bpy.setCreateBy(CommonConstant.SYSTEM_OPER);
                //设置主键id
                bpy.setId(SequenceUtil.getSequence(SerialNoEnum.PL_BALANCE_PAY_INFO.getTypeName(), commonDaoServic.getSequence(SerialNoEnum.PL_BALANCE_PAY_INFO.getSeqName())));
            }
            balancePayRequestInfoDao.insertBalancePayRequestInfo(balancePayInfos);
        }
        assetsSplitCache.put(assetsSplit);
        return assetsSplit;
    }

    /**
     * 根据id更新
     *
     * @param assetsSplit 资产信息
     * @param projectType 是否是联合放款
     * @param prodBuyOut  是否支持买断
     */
    @Transactional
    public void updateProjectTypeAndProdBuyOutById(AssetsSplit assetsSplit, String projectType, String prodBuyOut) {
        if (StringUtils.isBlank(prodBuyOut)) {
            if (PL0505Enum.PL0505_2_NOT_UNION.getCode().equals(assetsSplit.getProjectType())) {
                assetsSplit.setProdBuyOut(PL0106Enum.PL0106_1_NONSUPPORT.getCode());
            } else {
                assetsSplit.setProdBuyOut(PL0106Enum.PL0106_2_SUPPORT.getCode());
            }
        }
        assetsSplit.setProjectType(projectType);
        assetsSplit.setProdBuyOut(prodBuyOut);
        assetsSplitCache.put(assetsSplit);
        assetsSplitDao.updateProjectTypeAndProdBuyOutById(assetsSplit.getId(), projectType, assetsSplit.getProdBuyOut(), PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    /**
     * 根据id更新
     *
     * @param assetsSplit 资产信息
     * @param loanStatus  放款状态
     * @param projectType 是否是联合放款
     * @param prodBuyOut  是否支持买断
     */
    @Transactional
    public void updateLoanStatusAndProjectTypeAndProdBuyOutById(AssetsSplit assetsSplit,
                                                                String loanStatus, String projectType, String prodBuyOut) {
        assetsSplit.setLoanStatus(loanStatus);
        assetsSplit.setProjectType(projectType);
        if (StringUtils.isBlank(prodBuyOut)) {
            if (PL0505Enum.PL0505_2_NOT_UNION.getCode().equals(assetsSplit.getProjectType())) {
                assetsSplit.setProdBuyOut(PL0106Enum.PL0106_1_NONSUPPORT.getCode());
            } else {
                assetsSplit.setProdBuyOut(PL0106Enum.PL0106_2_SUPPORT.getCode());
            }
        } else {
            assetsSplit.setProdBuyOut(prodBuyOut);
        }

        assetsSplitCache.put(assetsSplit);
        assetsSplitDao.updateLoanStatusAndProjectTypeAndProdBuyOutById(assetsSplit.getId(), loanStatus, projectType, assetsSplit.getProdBuyOut(), PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    /**
     * 根据合同号查询资金表
     *
     * @param contractNo
     * @return
     */
    public AssetsSplit selectBycontractNo(String contractNo) {
        return assetsSplitDao.selectBycontractNo(contractNo, PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    /**
     * 查询业务编号是否已存在
     *
     * @param applSeq
     * @return
     */
    public String isExistApplSeq(String applSeq) {
        return assetsSplitDao.isExistApplSeq(applSeq, PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    /**
     * 根据id更新
     *
     * @param assetsSplit 资产主信息
     * @param pl0506Enum  放款状态
     */
    @Transactional
    public void updateLoanStatusById(AssetsSplit assetsSplit, PL0506Enum pl0506Enum, PL0505Enum pl0505Enum) {
        assetsSplit.setLoanStatus(pl0506Enum.getCode());
        assetsSplit.setProjectType(pl0505Enum.getCode());
        if (PL0505Enum.PL0505_2_NOT_UNION.getCode().equals(assetsSplit.getProjectType())) {
            assetsSplit.setProdBuyOut(PL0106Enum.PL0106_1_NONSUPPORT.getCode());
            assetsSplitDao.updateLoanStatusById(assetsSplit.getId(), pl0506Enum.getCode(),
                    pl0505Enum.getCode(), PL0106Enum.PL0106_1_NONSUPPORT.getCode(), PL0101Enum.PL0101_2_NORMAL.getCode());
        } else {
            if (assetsSplit.getLoanStatus().equals(PL0506Enum.PL0506_6_60.getCode())) {
                assetsSplit.setProdBuyOut(PL0106Enum.PL0106_1_NONSUPPORT.getCode());
            } else {
                assetsSplit.setProdBuyOut(PL0106Enum.PL0106_2_SUPPORT.getCode());
            }
            assetsSplitDao.updateLoanStatusById(assetsSplit.getId(), pl0506Enum.getCode(),
                    pl0505Enum.getCode(), assetsSplit.getProdBuyOut(), PL0101Enum.PL0101_2_NORMAL.getCode());

        }
        assetsSplitCache.put(assetsSplit);
    }

    /**
     * 更新放款状态
     *
     * @param assetsSplit 资产主信息
     * @param pl0506Enum  放款状态
     */
    public void updateLoanStatusById(AssetsSplit assetsSplit, PL0506Enum pl0506Enum) {
        assetsSplit.setLoanStatus(pl0506Enum.getCode());
        if (PL0505Enum.PL0505_2_NOT_UNION.getCode().equals(assetsSplit.getProjectType())) {
            updateLoanStatusById(assetsSplit, pl0506Enum, PL0505Enum.PL0505_2_NOT_UNION);
        } else {
            updateLoanStatusById(assetsSplit, pl0506Enum, PL0505Enum.PL0505_1_UNION);
        }
    }

    /**
     * 只更新放款状态根据id
     *
     * @param assetsSplit 资产主信息
     * @param pl0506Enum  放款状态
     */
    public void updateOnlyLoanStatusById(AssetsSplit assetsSplit, PL0506Enum pl0506Enum) {
        assetsSplit.setLoanStatus(pl0506Enum.getCode());
        assetsSplitDao.updateOnlyLoanStatusById(assetsSplit.getId(), assetsSplit.getLoanStatus(), PL0101Enum.PL0101_2_NORMAL.getCode());
    }

    /**
     * 根据合同号和业务编号查询放款信息
     *
     * @param applSeq    资产主信息
     * @param contractNo 放款状态
     */
    public AssetsSplit searchAssetsSplitByApplSeqOrContractNo(String applSeq, String contractNo) {
        return assetsSplitDao.searchAssetsSplitByApplSeqOrContractNo(applSeq, contractNo, PL0101Enum.PL0101_2_NORMAL.getCode());
    }

}
