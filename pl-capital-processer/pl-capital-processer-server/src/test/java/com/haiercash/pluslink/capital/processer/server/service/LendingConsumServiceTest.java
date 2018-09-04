package com.haiercash.pluslink.capital.processer.server.service;

import com.haiercash.pluslink.capital.BaseTest;
import com.haiercash.pluslink.capital.processer.server.dao.AssetsSplitItemDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author lzh
 * @Title: LendingConsumServiceTest
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1119:05
 */

public class LendingConsumServiceTest extends BaseTest {
    @Autowired
    AccountService accountService;
    @Autowired
    private AssetsSplitItemDao assetsSplitItemDao;
    @Test
    public void Test() {
        accountService.accountRecord("");
    }
}