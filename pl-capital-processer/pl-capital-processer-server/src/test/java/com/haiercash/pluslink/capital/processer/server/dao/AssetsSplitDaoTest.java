package com.haiercash.pluslink.capital.processer.server.dao;


import com.haiercash.pluslink.capital.BaseTest;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.enums.dictionary.PL0101Enum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * @author lzh
 * @Title: AssetsSplitDaoTest
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/8/111:17
 */
public class AssetsSplitDaoTest  extends BaseTest {
    @Autowired
    private  AssetsSplitDao assetsSplitDao;
    @Test
    public void selectById() {
        AssetsSplit assetsSplit=new AssetsSplit();
        assetsSplit.setId("111");
        assetsSplitDao.selectById("123",PL0101Enum.PL0101_2_NORMAL.getCode());
        assetsSplitDao.updateProjectTypeById(assetsSplit,PL0101Enum.PL0101_2_NORMAL.getCode());
        assetsSplitDao.searchAssetsSplitByApplSeqOrContractNo("12","12",PL0101Enum.PL0101_2_NORMAL.getCode());
        System.out.println("测试成功");
    }
}