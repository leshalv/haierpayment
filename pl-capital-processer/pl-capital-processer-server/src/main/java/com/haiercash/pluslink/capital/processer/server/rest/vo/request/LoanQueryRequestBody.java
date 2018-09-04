package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * > 放款状态查询Body
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/31 10:16
 */
@Getter
@Setter
public class LoanQueryRequestBody {

    @NotBlank(message = "原消息ID不能为空")
    private String orgCorpMsgId;
}
