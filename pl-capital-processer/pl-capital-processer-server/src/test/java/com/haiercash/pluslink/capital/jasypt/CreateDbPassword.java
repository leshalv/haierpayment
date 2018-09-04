package com.haiercash.pluslink.capital.jasypt;

import com.haiercash.pluslink.capital.BaseTest;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xiaobin
 * @create 2018-08-16 下午1:17
 **/
public class CreateDbPassword extends BaseTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void encryptPwd() {
        String result = stringEncryptor.encrypt("PLLOAN_DEV");
        System.out.println("生成的密码:"+result);
    }
}
