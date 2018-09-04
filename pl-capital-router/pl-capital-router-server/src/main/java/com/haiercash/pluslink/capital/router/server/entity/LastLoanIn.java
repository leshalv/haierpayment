package com.haiercash.pluslink.capital.router.server.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

/**
 * 用户是否二次贷款贷款(入参参数)
 * @author WDY
 * @date 2018-07-16
 * @rmk
 */
@Data
@Getter
@Setter
public class LastLoanIn implements Serializable{

    /**身份证号**/
    private String idNo;
    /**贷款品种代码**/
    private String loanTyp;
}
