package com.haiercash.pluslink.capital.processer.server.pvm;

import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.enums.dictionary.PL0506Enum;

/**
 * @author xiaobin
 * @create 2018-08-11 上午11:25
 **/
public class FlowWorkUtils {

    /**
     * 资产信息是否终态
     *
     * @return
     */
    public static boolean isFinalState(AssetsSplit assetsSplit) {
        return PL0506Enum.PL0506_5_50.getCode().equals(assetsSplit.getLoanStatus()) ||
                PL0506Enum.PL0506_6_60.getCode().equals(assetsSplit.getLoanStatus()) ||
                PL0506Enum.PL0506_7_70.getCode().equals(assetsSplit.getLoanStatus());
    }

    /**
     * 是否放款成功
     *
     * @param assetsSplit
     * @return
     */
    public static boolean isSuccess(AssetsSplit assetsSplit) {
        return PL0506Enum.PL0506_5_50.getCode().equals(assetsSplit.getLoanStatus());
    }

    public static boolean isFail(AssetsSplit assetsSplit) {
        return PL0506Enum.PL0506_6_60.getCode().equals(assetsSplit.getLoanStatus()) ||
                PL0506Enum.PL0506_7_70.getCode().equals(assetsSplit.getLoanStatus());
    }

    public static boolean isSuccess(String loanStatus) {
        return PL0506Enum.PL0506_5_50.getCode().equals(loanStatus);
    }

}
