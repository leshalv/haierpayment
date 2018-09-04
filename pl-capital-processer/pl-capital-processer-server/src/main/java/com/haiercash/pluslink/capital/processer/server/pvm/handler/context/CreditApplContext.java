package com.haiercash.pluslink.capital.processer.server.pvm.handler.context;

import cn.jbinfo.cloud.core.utils.IdGen;
import cn.jbinfo.cloud.core.utils.SpringContextHolder;
import com.haiercash.pluslink.capital.data.Area;
import com.haiercash.pluslink.capital.data.AssetsSplit;
import com.haiercash.pluslink.capital.data.AssetsSplitItem;
import com.haiercash.pluslink.capital.processer.server.cache.AreaCache;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.ApplInfoAppRequestBody;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.CreditApplRequestVo;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.LoanDetailResponse;
import com.haiercash.pluslink.capital.processer.server.service.LoanDetailService;

import java.math.BigDecimal;

/**
 * 授信申请事件上下文
 *
 * @author xiaobin
 * @create 2018-07-16 下午5:12
 **/
public class CreditApplContext {

    //资产拆分主表
    private AssetsSplit assetsSplit;
    //资产拆分明细表（资方明细）
    private AssetsSplitItem assetsSplitItem;

    private boolean interrupting = false;

    public boolean isInterrupting() {
        return interrupting;
    }

    /**
     * 授信申请
     */
    private CreditApplRequestVo creditApplRequestVo;

    public CreditApplRequestVo getCreditApplRequestVo() {
        return creditApplRequestVo;
    }

    public AssetsSplit getAssetsSplit() {
        return assetsSplit;
    }

    public AssetsSplitItem getAssetsSplitItem() {
        return assetsSplitItem;
    }

    public CreditApplContext(AssetsSplit assetsSplit, AssetsSplitItem assetsSplitItem) {

        LoanDetailService loanDetailService = SpringContextHolder.getBean(LoanDetailService.class);
        ApplInfoAppRequestBody requestBody = new ApplInfoAppRequestBody(assetsSplit.getApplSeq(), assetsSplit.getCertNo(), assetsSplit.getChannelNo(), assetsSplit.getRequestIp());

        //拉取贷款详情
        LoanDetailResponse  loanDetailResponse = loanDetailService.selectApplInfoApp(requestBody);

        AreaCache areaCache = SpringContextHolder.getBean(AreaCache.class);

        this.assetsSplit = assetsSplit;
        this.assetsSplitItem = assetsSplitItem;

        this.creditApplRequestVo = new CreditApplRequestVo();
        creditApplRequestVo.setAppName(assetsSplit.getCustName());
        creditApplRequestVo.setCertCode(assetsSplit.getCertCode());
        creditApplRequestVo.setCardNo(assetsSplit.getBankUnionCode());
        creditApplRequestVo.setMvblno(assetsSplit.getMobile() + "");

        creditApplRequestVo.setCooprUserId(assetsSplit.getCertCode());
        //---------------证件有效期-----------
        creditApplRequestVo.setCertCodeValidDt("");
        //---------------性别---------------
        creditApplRequestVo.setSex(loanDetailResponse.getApptIndivSex());
        //---------------出生日期---------------
        creditApplRequestVo.setBirthdate(loanDetailResponse.getApptStartDate());

        creditApplRequestVo.setEmpStartDate("");
        //---------------职务---------------
        creditApplRequestVo.setDuty(loanDetailResponse.getIndivPosition());
        //---------------单位性质---------------
        creditApplRequestVo.setCompNature(loanDetailResponse.getIndivEmpTyp());
        //个人年收入
        creditApplRequestVo.setIncY(loanDetailResponse.getAnnualEarn());

        creditApplRequestVo.setFamY(new BigDecimal(0));
        //教育程度
        creditApplRequestVo.setEdu(loanDetailResponse.getIndivEdu());

        creditApplRequestVo.setMarry(loanDetailResponse.getIndivMarital());
        creditApplRequestVo.setNoDepm(0);
        //---------------省---------------
        Area province = areaCache.getByCode(loanDetailResponse.getLiveProvince());
        Area city = areaCache.getByCode(loanDetailResponse.getLiveCity());
        Area county = areaCache.getByCode(loanDetailResponse.getLiveArea());

        creditApplRequestVo.setProvince(province.getAreaName());
        creditApplRequestVo.setCity(city.getAreaName());
        creditApplRequestVo.setCounty(county.getAreaName());

        creditApplRequestVo.setHomeAddress(loanDetailResponse.getLiveAddr());
        creditApplRequestVo.setCompAddress(loanDetailResponse.getEmpAddr());
        //邮政编号
        creditApplRequestVo.setPostCde(loanDetailResponse.getIndivEmpZip());
        creditApplRequestVo.setAddrIp(loanDetailResponse.getAddrIp());
        creditApplRequestVo.setContactATel(loanDetailResponse.getRelMobile());
        creditApplRequestVo.setContactAName(loanDetailResponse.getRelName());

        //合作方建议授信金额（拆分后金额）
        creditApplRequestVo.setPromAmt(assetsSplitItem.getTransAmt());
        //申请人期望金额（总金额）
        creditApplRequestVo.setPromAmtCust(assetsSplitItem.getApplAmt());
        //消息ID
        creditApplRequestVo.setCorpMsgId(IdGen.uuid());

    }

}
