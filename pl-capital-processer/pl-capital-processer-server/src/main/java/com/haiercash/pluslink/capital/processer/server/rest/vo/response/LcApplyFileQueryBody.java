package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author xiaobin
 * @create 2018-08-20 下午4:41
 **/
@Getter
@Setter
@ToString
public class LcApplyFileQueryBody {

    //{"response":{"head":{"serno":"shtNnOWfIrpqmlewL4z","retMsg":"交易成功！","retFlag":"00000"},
    // "body":{"busId":"LcAppl","applSeq":8628817,"sysId":"00","reserved3":"","reserved2":"","reserved1":"",
    // "list":{"info":[
    // {"attachPath":"/testshare01/cmis/DOC/00/LcAppl/2018_08/15/8628817/app_130421193309137379_credit.pdf","crtUsr":"admin","reserved8":"","attachTyp":"pdf","attachNameNew":"58c08e44dd254e81ae495bb5135b7b7a.pdf","loseEffectUsr":"","attachName":"app_130421193309137379_credit.pdf","state":1,"reserved7":"","crtDt":"2018-08-15 16:33:08","reserved6":"DOC014"},
    // {"attachPath":"/testshare01/cmis/DOC/00/LcAppl/2018_08/15/8628817/app_130421193309137379_ICBCCredit.pdf","crtUsr":"admin","reserved8":"","attachTyp":"pdf","attachNameNew":"f9a5012256844bd9954e200e6a5e5820.pdf","loseEffectUsr":"","attachName":"app_130421193309137379_ICBCCredit.pdf","state":1,"reserved7":"","crtDt":"2018-08-15 16:33:08","reserved6":"DOC014"},
    // {"attachPath":"/testshare01/cmis/DOC/00/LcAppl/2018_08/15/8628817/f1f1efc35182419f82c4079ac916570d.jpg","crtUsr":"admin","reserved8":"","attachTyp":"jpg","attachNameNew":"f1f1efc35182419f82c4079ac916570d.jpg","loseEffectUsr":"","attachName":"f1f1efc35182419f82c4079ac916570d.jpg","state":1,"reserved7":"","crtDt":"2018-08-15 16:33:11","reserved6":"DOC023"},
    // {"attachPath":"/testshare01/cmis/DOC/00/LcAppl/2018_08/15/8628817/5d76f5a1f1f24476a24ea74b064b6a07.jpg","crtUsr":"admin","reserved8":"","attachTyp":"jpg","attachNameNew":"5d76f5a1f1f24476a24ea74b064b6a07.jpg","loseEffectUsr":"","attachName":"5d76f5a1f1f24476a24ea74b064b6a07.jpg","state":1,"reserved7":"","crtDt":"2018-08-15 16:33:11","reserved6":"DOC53"},
    // {"attachPath":"/testshare01/cmis/DOC/00/LcAppl/2018_08/15/8628817/3701f61f8e704796a13b8335fdb5ea2c.jpg","crtUsr":"admin","reserved8":"","attachTyp":"jpg","attachNameNew":"3701f61f8e704796a13b8335fdb5ea2c.jpg","loseEffectUsr":"","attachName":"3701f61f8e704796a13b8335fdb5ea2c.jpg","state":1,"reserved7":"","crtDt":"2018-08-15 16:33:11","reserved6":"DOC53"},
    // {"attachPath":"/testshare01/cmis/DOC/00/LcAppl/2018_08/15/8628817/126c9fa56a3a49e3999d579e582a6fb5.jpg","crtUsr":"admin","reserved8":"","attachTyp":"jpg","attachNameNew":"126c9fa56a3a49e3999d579e582a6fb5.jpg","loseEffectUsr":"","attachName":"126c9fa56a3a49e3999d579e582a6fb5.jpg","state":1,"reserved7":"","crtDt":"2018-08-15 16:33:11","reserved6":"DOC54"},
    // {"attachPath":"/testshare01/cmis/DOC/00/LcAppl/2018_08/15/8628817/app_130421193309137379_HCFC-HFFQ-B.pdf","crtUsr":"admin","reserved8":"","attachTyp":"pdf","attachNameNew":"b4eb4de6a6a349e6a907b50ffe96e938.pdf","loseEffectUsr":"","attachName":"app_130421193309137379_HCFC-HFFQ-B.pdf","state":1,"reserved7":"","crtDt":"2018-08-15 16:33:11","reserved6":"DOC014"}]},"reserved5":"","reserved4":""}}}
    /**
     * 系统标识
     */
    private String sysId;

    /**
     * 业务标识
     */
    private String busId;

    /**
     * 业务流水
     */
    private String applSeq;

    /**
     * 文件列表
     */
    private Map<String, List<LcApplyFileQueryList>> list;
}
