package com.haiercash.pluslink.capital.processer.server.mq.consumer;

import com.haiercash.pluslink.capital.common.channel.ConsumerChannelName;
import com.haiercash.pluslink.capital.processer.server.service.AssetsSplitItemService;
import com.haiercash.pluslink.capital.processer.server.utils.threading.ThreadPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lzh
 * @Title: LoanConsumService
 * @ProjectName pl-capital
 * @Description: 消费放款请求接口的mq
 * @date 2018/7/1117:21
 */
@Service
@Transactional(readOnly = true)
public class LoanConsumService {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private AssetsSplitItemService assetsSplitItemService;

    @StreamListener(ConsumerChannelName.PL_LENDING_EXCHANGE_CONSUMER)
    @Transactional
    @Scope("prototype")
    public void consumLoan(String message) {
        //StringUtils.(message.getBody(), Charset.defaultCharset());
        logger.info("LoanConsumSuccess:" + message);
        assetsSplitItemService.assetsSplit(message);
    }
}
