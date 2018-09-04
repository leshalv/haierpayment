package com.haiercash.pluslink.capital.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * > 临时表
 *
 * @author : dreamer-otw
 * @email : dreamers_otw@163.com
 * @date : 2018/07/20 17:21
 */
@Entity
@Getter
@Setter
@Table(name = "PL_CURSOR")
public class Cursor {
    @Id
    private String id;
	/**
	 * 合同号
	 */
	private String contractNo;
	/**
	 * 资产拆分明细表ID
	 */
	private String assetsSplitItemId;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 系统时间
	 */
	private Date systemDate;
}
