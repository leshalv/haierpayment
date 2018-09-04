package com.haiercash.pluslink.capital.manager.controller;

import com.haiercash.pluslink.capital.BaseTest;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 消息平台管理后台测试类模板
 *
 * @author keliang.jiang
 * @date 2017/12/25
 */
public class ControllerTest extends BaseTest {

    @Test
    public void test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/list"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
