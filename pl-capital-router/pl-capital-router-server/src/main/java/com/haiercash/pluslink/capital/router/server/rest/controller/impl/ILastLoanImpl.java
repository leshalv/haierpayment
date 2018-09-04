package com.haiercash.pluslink.capital.router.server.rest.controller.impl;

import com.haiercash.pluslink.capital.common.exception.BaseException;
import com.haiercash.pluslink.capital.common.utils.BaseService;
import com.haiercash.pluslink.capital.enums.SystemFlagEnum;
import com.haiercash.pluslink.capital.router.rest.dto.request.LastLoanQueryRequest;
import com.haiercash.pluslink.capital.router.rest.dto.response.LastLoanQueryResponse;
import com.haiercash.pluslink.capital.router.server.entity.LastLoanIn;
import com.haiercash.pluslink.capital.router.server.rest.controller.ILastLoanController;
import com.haiercash.pluslink.capital.router.server.rest.controller.LastLoanQuery;
import com.haiercash.pluslink.capital.router.server.rest.controller.enums.LastLoanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 用户是否30天前贷款查询接口数据处理控制实现类
 * @author WDY
 * @date 2018-07-25
 * @rmk
 */
@Controller("lastLoanController")
public class ILastLoanImpl extends BaseService implements ILastLoanController {

    @Autowired
    private LastLoanQuery lastLoanQuery;

    public int checkLastLoanController(LastLoanIn lastLoanIn,String serNo,String applSeq){
        int dayAgo = 0;
        try{
            LastLoanQueryRequest restRequest = new LastLoanQueryRequest();
            restRequest.setIdNo(lastLoanIn.getIdNo());
            restRequest.setLoanTyp(lastLoanIn.getLoanTyp());
            restRequest.setOperator(SystemFlagEnum.PLCRT.getSystemFlag());
            LastLoanQueryResponse response = lastLoanQuery.queryLastLoan(restRequest);
            if(LastLoanEnum.SUCCESS.getCode().equals(response.getRtnCode())){//调用成功
                if(LastLoanEnum.YES.getCode().equals(response.getLoanResult())){//存在超过三十天记录
                    dayAgo = 30;
                }
            }
            logger.info("Router：serNo(" + serNo + "),applSeq(" + applSeq + ")调用用户是否30天前贷款查询接口返回结果：" + response.getLoanResult());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw new BaseException("","");
        }
        return dayAgo;
    }
}
