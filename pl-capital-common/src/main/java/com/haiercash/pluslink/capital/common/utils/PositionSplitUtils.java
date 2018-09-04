package com.haiercash.pluslink.capital.common.utils;

import com.haiercash.pluslink.capital.common.exception.PlCapitalException;
import com.haiercash.pluslink.capital.entity.PositionSplitIn;
import com.haiercash.pluslink.capital.entity.PositionSplitOut;
import com.haiercash.pluslink.capital.enums.dictionary.PL0601Enum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;

/**
 * 头寸拆分逻辑
 * @author WDY
 * @date 2018-07-16
 */
public class PositionSplitUtils extends BaseService{

    private static Log logger = LogFactory.getLog(PositionSplitUtils.class);

    public static PositionSplitOut dealPositionSplit(PositionSplitIn positionSplitIn){

        String message = "拆分成功";
        String flagMessage = "";
        boolean flag = true;//只做异常判断,若无异常则视为成功
        PositionSplitOut positionSplitOut;
        try{

            checkAmount(positionSplitIn.getOrigPrcp(),"放款金额");
            checkAmount(positionSplitIn.getAgencyRatio(),"资方占比");
            checkAmount(positionSplitIn.getLoanAmount(),"合作项目放款总额");
            checkAmount(positionSplitIn.getAlreadyLoanAmount(),"合作项目已放款额");
            checkAmount(positionSplitIn.getLoanLimitDay(),"合作项目限额(日)");
            checkAmount(positionSplitIn.getAlreadyLoanLimitDay(),"合作项目当日放已放款额");
            checkAmount(positionSplitIn.getLoanLimitMonth(),"合作项目限额(月)");
            checkAmount(positionSplitIn.getAlreadyLoanLimitMonth(),"合作项目当月已放款额");

            //资方占比不能大于1
            if(positionSplitIn.getAgencyRatio().compareTo(BigDecimal.ONE) == 1){
                throw new PlCapitalException("资方占比大于1{" + positionSplitIn.getAgencyRatio() + "}");
            }

            positionSplitOut = splitAmount(positionSplitIn.getOrigPrcp(),positionSplitIn.getAgencyRatio());

            checkLimit :{
                //判断放款总额
                //资金方放款金额 + 合作项目已放款额 <= 放款总额
                if(//存在总放款额时候才校验
                        (
                                positionSplitOut.getAgencyLoanAmount().add(
                                        positionSplitIn.getAlreadyLoanAmount()
                                ).compareTo(
                                        positionSplitIn.getLoanAmount()
                                )
                        )   == 1

                        ){//若大于总放款额

                    flag = false;
                    flagMessage = "资金方剩余放款总金额不足";
                    break checkLimit;
                }

                //判断当日放款总额是否超限
                //资金方放款金额 + 合作项目当日已放款额 <= 合作项目限额(日)
                if(//存在合作项目限额(日)时候才校验
                        (
                                positionSplitOut.getAgencyLoanAmount().add(
                                        positionSplitIn.getAlreadyLoanLimitDay()
                                ).compareTo(
                                        positionSplitIn.getLoanLimitDay()
                                )
                        ) == 1

                        ){//若大于限额(日)

                    flag = false;
                    flagMessage = "资金方当日放款额超限";
                    break checkLimit;
                }

                //判断当月放款总额是否超限
                //资金方放款金额 + 合作项目当月已放款额 <= 合作项目限额(月)
                if(//存在合作项目限额(月)时候才校验
                        (
                                positionSplitOut.getAgencyLoanAmount().add(
                                        positionSplitIn.getAlreadyLoanLimitMonth()
                                ).compareTo(
                                        positionSplitIn.getLoanLimitMonth()
                                )
                        ) == 1

                        ){//若大于限额(日)

                    flag = false;
                    flagMessage = "资金方当月放款额超限";
                    break checkLimit;
                }
            }
        }catch(Exception e){
            logger.error("Router：applSeq(" + positionSplitIn.getApplSeq() + ")头寸拆分失败..." +
                    "param = " + JsonConverter.object2Json(positionSplitIn) + "," +
                    "Message = " + e.getMessage());
            throw new PlCapitalException("99999","头寸拆分异常");
        }

        if(flag){
            logger.info("Router：applSeq(" + positionSplitIn.getApplSeq() + ")头寸拆分成功," +
                    "param = " + JsonConverter.object2Json(positionSplitIn) + "," +
                    "return = " + JsonConverter.object2Json(positionSplitOut));
        }else{
            message = "Router：applSeq(" + positionSplitIn.getApplSeq() + ")头寸拆分失败," + flagMessage +  "...";
            positionSplitOut.setAgencyLoanAmount(BigDecimal.ZERO);//合作方放款额
            positionSplitOut.setCashLoanAmount(BigDecimal.ZERO);//消金放款额
            positionSplitOut.setCashRatio(BigDecimal.ZERO);//消金资金占比
            positionSplitOut.setStatus(PL0601Enum.PL0601_2_11);
            logger.error(message + "param = " + JsonConverter.object2Json(positionSplitIn));
        }
        positionSplitOut.setFlagMessage(flagMessage);
        positionSplitOut.setMessage(message);
        return positionSplitOut;
    }

    /**
     * 拆分资方金额和消金金额
     */
    public static PositionSplitOut splitAmount(BigDecimal amount,BigDecimal agencyRatio){
        PositionSplitOut out = new PositionSplitOut();
        BigDecimal agencyLoanAmount;//资金方放款金额
        BigDecimal cashLoanAmount;//消金放款金额
        BigDecimal cashRatio;//消金放款占比

        if(agencyRatio.compareTo(BigDecimal.ONE) == 0){//资方放款占比为1
            //资方放款额为放款金额
            agencyLoanAmount = amount;
        }else{
            //计算资金方放款额,消金放款额
            //资金方放款金额 = (放款金额 * 资方占比) 小数点后保留两位,全舍
            agencyLoanAmount = amount.multiply(
                    agencyRatio)
                    //此处四舍五入(已和产品确认)
                    .setScale(2,BigDecimal.ROUND_HALF_UP);
        }

        //消金放款占比 = 1 - 资金方放款占比
        cashRatio = BigDecimal.ONE.subtract(agencyRatio);

        //消金放款金额 = 放款金额 - 资金方放款金额
        cashLoanAmount = amount.subtract(agencyLoanAmount);
        out.setCashLoanAmount(cashLoanAmount);
        out.setAgencyLoanAmount(agencyLoanAmount);
        out.setCashRatio(cashRatio);
        out.setStatus(PL0601Enum.PL0601_1_10);
        return out;
    }

    /**
     * 判断参数是否为空或小于0
     */
    private static void checkAmount(BigDecimal param,String message){
        if(null == param || param.compareTo(BigDecimal.ZERO) < 0){//参数为空或小于0
            throw new PlCapitalException(message + "不能为空且不能小于0{" + param + "}");
        }
    }
}
