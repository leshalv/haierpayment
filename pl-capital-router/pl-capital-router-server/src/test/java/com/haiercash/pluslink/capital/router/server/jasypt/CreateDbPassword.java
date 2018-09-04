package com.haiercash.pluslink.capital.router.server.jasypt;


import com.haiercash.pluslink.capital.router.server.BaseTest;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/** 生成数据库密码
 * @author xiaobin
 * @create 2018-08-16 下午1:17
 **/
public class CreateDbPassword extends BaseTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void encryptPwd() {
        String result = stringEncryptor.encrypt("ROUTER_TEST");
        System.out.println("生成的密码:"+result);
    }
}
