package com.haiercash.pluslink.capital.router.server;
import com.haiercash.pluslink.capital.common.utils.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 测试基本类，其他测试类需继承此类
 *
 * @author keliang.jiang
 * @date 2017/12/15.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CapitalRouterApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class BaseTest extends BaseService{

    @Autowired
    private WebApplicationContext webApplicationConnect;

    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationConnect).build();
    }
}
