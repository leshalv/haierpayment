package com.haiercash.pluslink.capital.router.server.component.impl;

import com.haiercash.pluslink.capital.common.exception.PlCapitalException;
import com.haiercash.pluslink.capital.common.utils.BaseService;
import com.haiercash.pluslink.capital.data.CooperationProject;
import com.haiercash.pluslink.capital.entity.PositionSplitIn;
import com.haiercash.pluslink.capital.enums.SerialNoEnum;
import com.haiercash.pluslink.capital.router.server.component.ICommonComponent;
import com.haiercash.pluslink.capital.router.server.entity.RouterMatchIn;
import com.haiercash.pluslink.capital.router.server.service.IPublicDataService;
import com.haiercash.pluslink.capital.router.server.utils.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 为资金路由提供基本公共接口
 * @author WDY
 * 2018-06-07
 */
@Component("routerCommonComponent")
public class CommonComponentImpl extends BaseService implements ICommonComponent {

    @Autowired
    private IPublicDataService publicDataService;

    /**生成统一的流水号**/
    public String doGeneratorCommonSerialNo(SerialNoEnum serialNoEnum,String serNo){
        logger.info("开始生成交易流水号,交易类型为:" + serialNoEnum.getTypeName());
        StringBuilder serialno = new StringBuilder(serialNoEnum.getLength());
        String seq = publicDataService.querySequence(serialNoEnum.getSeqName());
        String dateTime =  String.valueOf(ParamUtil.changeDateTime(new Date()));
        int length = serialNoEnum.getLength() - 18;
        serialno.append(serialNoEnum.getTypeName()).append(dateTime).append(ParamUtil.formatString(seq,length,3));
        if(serialno.length() > serialNoEnum.getLength()){
            logger.error("交易流水号长度超出限制或生成失败");
            return "";
        }
        logger.info("Router：serNo(" + serNo + ")交易流水号生成成功:交易类型为:" + serialNoEnum.getTypeName() + "流水号：" + serialno.toString());
        return serialno.toString();
    }

    /**获取头寸拆分的入参数据**/
    public PositionSplitIn getPositionSplitParam(RouterMatchIn routerMatch,CooperationProject project){
        PositionSplitIn positionSplitIn = publicDataService.getAlreadyLoan(project.getId());
        if(null == positionSplitIn){
            throw new PlCapitalException("获取机构已放款头寸异常");
        }
        positionSplitIn.setApplSeq(routerMatch.getApplSeq());//业务编号
        positionSplitIn.setOrigPrcp(routerMatch.getOrigPrcp());//放款金额
        positionSplitIn.setAgencyRatio(project.getAgencyRatio());//资方占比
        positionSplitIn.setAgencyId(project.getAgencyId());//合作机构ID
        positionSplitIn.setProjectId(project.getId());//合作项目ID
        if(null == project.getLoanAmount()){
            positionSplitIn.setLoanAmount(BigDecimal.ZERO);//合作项目放款总额
        }else{
            positionSplitIn.setLoanAmount(project.getLoanAmount());//合作项目放款总额
        }
        if(null == project.getLoanLimitDay()){
            positionSplitIn.setLoanLimitDay(BigDecimal.ZERO);
        }else{
            positionSplitIn.setLoanLimitDay(project.getLoanLimitDay());//合作项目限额(日)
        }
        if(null == project.getLoanLimitMonth()){
            positionSplitIn.setLoanLimitMonth(BigDecimal.ZERO);
        }else{
            positionSplitIn.setLoanLimitMonth(project.getLoanLimitMonth());//合作项目限额(月)
        }
        return positionSplitIn;
    }
}
