package com.haiercash.pluslink.capital.processer.server.dao;

import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import org.apache.ibatis.annotations.Param;
import org.mybatis.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yu jianwei
 * @date 2018/7/13 14:18
 * 资产拆分明细持久层
 */
@Repository
public interface AssetsSplitItemDao extends BaseMapper<AssetsSplitItem> {


    AssetsSplitItem selectById(@Param("id") String id, @Param("delFlag") String delFlag);

    /**
     * 存入资产拆分明细
     */
    void insertAssetsSplitItemList(List<AssetsSplitItem> assetsSplitItemList);

    /**
     * 获取序列
     */
    String getSequence();

    /**
     * 根据id更新业务状态
     *
     * @param id
     * @param status
     */
    void updateStatusById(@Param("id") String id, @Param("status") String status, @Param("memo") String memo, @Param("delFlag") String delFlag);


    void updateStatusAndCapLoanNoById(@Param("id") String id, @Param("status") String status, @Param("capLoanNo") String capLoanNo,
                                      @Param("memo") String memo, @Param("delFlag") String delFlag);

    void updateBackData(@Param("model") AssetsSplitItem item);

    /**
     * 根据id更新业务状态，并逻辑删除
     *
     * @param id
     * @param status
     */
    void updateStatusAndDelFlagById(@Param("id") String id, @Param("status") String status, @Param("memo") String memo, @Param("delFlag") String delFlag);

    /**
     * 根据资产表主键获取资产明细
     */
    List<AssetsSplitItem> selectByAssetsSpiltId(@Param("assetsSpiltId") String assetsSpiltId, @Param("delFlag") String delFlag);

    /**
     * 根据资产表主键查询资产明细，只查询放款状态为成功的明细，推送信贷专用
     */
    List<AssetsSplitItem> selectByAssetsSpiltIdForCredit(@Param("assetsSpiltId") String assetsSpiltId, @Param("delFlag") String delFlag, @Param("status") String status);

    /**
     * 根据资产表主键获取资产明细
     */
    AssetsSplitItem selectByAssetsSpiltIdAndLoan(@Param("assetsSpiltId") String assetsSpiltId, @Param("loanNo") String loanNo, @Param("delFlag") String delFlag);

    /**
     * 根据资产表合同号获取资方拆分明细状态
     *
     * @param contractNo
     * @return
     */
    String getStatusByContractNo(@Param("contractNo") String contractNo, @Param("delFlag") String delFlag);

    AssetsSplitItem selectByAssetsSpiltIdAndLoanType(String assetsSpiltId, String loanType, String delFlag);
}
