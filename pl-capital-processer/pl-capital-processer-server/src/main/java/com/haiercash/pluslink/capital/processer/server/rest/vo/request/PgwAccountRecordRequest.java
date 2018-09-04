package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import com.haiercash.pluslink.capital.data.AccountEntry;
import com.haiercash.pluslink.capital.data.AccountTransaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Auther: yu jianwei
 * @Date: 2018/7/25 16:43
 * @Description:
 */
@Setter
@Getter
public class PgwAccountRecordRequest extends AccountTransaction {
    private List<AccountEntry> entryList;
}
