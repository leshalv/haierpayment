package com.haiercash.pluslink.capital.manager.dao;

import com.haiercash.pluslink.capital.data.DictionarySub;
import com.haiercash.pluslink.capital.manager.data.AgencyType;
import com.haiercash.pluslink.capital.manager.data.AgencyTypeDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * > 机构类别Dao
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 14:37
 */
@Repository
public interface AgencyTypeDao {
    List<AgencyType> getAgencyTypeList(@Param("dictionaryId") String dictionaryId, @Param("delFlag") String delFlag);
    Integer insert(DictionarySub dictionarySub);
    String getMaxAgencyTypeId(@Param("dictionaryId") String dictionaryId, @Param("delFlag") String delFlag);
    Integer getAgencyTypeId(@Param("dictionaryId") String dictionaryId,@Param("agencyTypeId")  String agencyTypeId, @Param("delFlag") String delFlag);
    Integer update(AgencyType agencyType);
    List<AgencyTypeDetail> getAgencyTypeDetail(String delFlag, String agencyType);
}
