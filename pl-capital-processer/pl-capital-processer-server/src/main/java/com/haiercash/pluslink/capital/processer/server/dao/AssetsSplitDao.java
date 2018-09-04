package com.haiercash.pluslink.capital.processer.server.dao;

import com.haiercash.pluslink.capital.data.AssetsSplit;
import org.apache.ibatis.annotations.Param;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author lzh
 * @Title: AssetsSplitDao
 * @ProjectName pl-capital
 * @Description: 放款接口资产表dao
 * @date 2018/7/1313:27
 */
@Repository
public interface AssetsSplitDao extends BaseMapper<AssetsSplit> {

    AssetsSplit selectById(@Param("id") String id, @Param("delFlag") String delFlag);

    //插入资产表
    void insertAssetsSplit(AssetsSplit assetsSplit);

    //查询业务编号是否存在
    String isExistApplSeq(@Param("applSeq") String applSeq, @Param("delFlag") String delFlag);

    /**
     * 根据主键id 更新是否联合放款
     */
    void updateProjectTypeById(@Param("model") AssetsSplit assetsSplit, @Param("delFlag") String delFlag);

    //根据业务编号查询资产表信息
    AssetsSplit searchAssetsSplitByApplSeqOrContractNo(@Param("applSeq") String applSeq, @Param("contractNo") String contractNo, @Param("delFlag") String delFlag);

    /**
     * 根据合同号查询放款状态
     *
     * @param contractNo
     * @return
     */
    String getLoanStatus(@Param("contractNo") String contractNo, @Param("delFlag") String delFlag);

    /**
     * 根据合同号查询
     *
     * @param contractNo
     * @return
     */
    AssetsSplit selectBycontractNo(@Param("contractNo") String contractNo, @Param("delFlag") String delFlag);

    /**
     * 根据id修改放款状态
     *
     * @param id
     * @return
     */
    int updateLoanStatusAndProjectTypeAndProdBuyOutById(@Param("id") String id,
                                                        @Param("loanStatus") String loanStatus,
                                                        @Param("projectType") String projectType,
                                                        @Param("prodBuyOut") String prodBuyOut,
                                                        @Param("delFlag") String delFlag);

    /**
     * 根据id修改放款状态
     *
     * @param id
     * @return
     */
    int updateProjectTypeAndProdBuyOutById(@Param("id") String id,
                                           @Param("projectType") String projectType,
                                           @Param("prodBuyOut") String prodBuyOut,
                                           @Param("delFlag") String delFlag);

    /**
     * 根据id修改资产表状态
     *
     * @param id
     * @param loanStatus
     * @param delFlag
     */
    void updateLoanStatusById(@Param("id") String id, @Param("loanStatus") String loanStatus, @Param("projectType") String projectType,
                              @Param("prodBuyOut") String prodBuyOut, @Param("delFlag") String delFlag);

    /**
     * 根据id只修改资产表放款状态
     *
     * @param id
     * @param loanStatus
     * @param delFlag
     */
    void updateOnlyLoanStatusById(@Param("id") String id, @Param("loanStatus") String loanStatus,@Param("delFlag") String delFlag);
}
