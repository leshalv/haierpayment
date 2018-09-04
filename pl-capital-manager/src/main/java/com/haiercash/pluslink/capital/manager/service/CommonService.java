package com.haiercash.pluslink.capital.manager.service;

import com.haiercash.pluslink.capital.common.mybatis.dao.AbstractDao;
import com.haiercash.pluslink.capital.common.mybatis.support.*;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.utils.PaginationList;
import org.mybatis.mapper.entity.EntityTable;
import org.mybatis.mapper.mapperhelper.EntityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by lihua on 2016/7/26.
 */

@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class CommonService extends BaseService {
    @Autowired
    private AbstractDao abstractDao;

    public <T> List<T> selectForCommon(QueryParam queryParam) {
        if (!StringUtils.isEmpty(queryParam.getEntityName())) {
            Select select = new Select(queryParam.getEntityName());
            if (queryParam.getLimit() != null && queryParam.getLimit() > 0) {
                return abstractDao.selectForCommon(select.init(queryParam).getParameterMap(), new RowBounds(0, queryParam.getLimit(), true));
            }
            return abstractDao.selectForCommon(select.init(queryParam).getParameterMap());
        }
        return null;
    }

    public <T> T initForCommon(QueryParam queryParam) {
        if (!StringUtils.isEmpty(queryParam.getEntityName())) {
            Select select = new Select(queryParam.getEntityName());
            return abstractDao.initForCommon(select.init(queryParam).getParameterMap());
        }
        return null;
    }

    public <T> List<T> selectByFilter(QueryParam queryParam) {
        Select select = new Select(queryParam.getEntityName());
        select.init(queryParam);
        return abstractDao.selectByFilter(select.getParameterMap());
    }

    public <T> PaginationList<T> pageByFilter(QueryParam queryParam) {
        Select select = new Select(queryParam.getEntityName());
        select.init(queryParam);
        return abstractDao.pageByFilter(select.getParameterMap(), new RowBounds(queryParam.getOffset(), queryParam.getLimit(), true));
    }

    public int insert(Entity entity) {
        if (!StringUtils.isEmpty(entity.getEntityName())) {
            EntityTable entityTable = EntityHelper.getEntityTable(entity.getEntityName());
            Insert insert = new Insert(entityTable.getEntityClass());
            insert.init(entity.getDefine(), entity.getGenerator());
            return abstractDao.insert(insert.getParameterMap());
        }
        return -1;
    }

    public int update(Entity entity) {
        if (!StringUtils.isEmpty(entity.getEntityName())) {
            EntityTable entityTable = EntityHelper.getEntityTable(entity.getEntityName());
            Update update = new Update(entityTable.getEntityClass());
            update.init(entity.getDefine(), entity.getKeyValues());
            return abstractDao.update(update.getParameterMap());
        }
        return -1;
    }

    public int deleteByIds(Entity entity) {
        if (!StringUtils.isEmpty(entity.getEntityName())) {
            EntityTable entityTable = EntityHelper.getEntityTable(entity.getEntityName());
            Delete delete = new Delete(entityTable.getEntityClass());
            delete.init(entity.getKeyValuesList());
            return abstractDao.deleteByIds(delete.getParameterMap());
        }
        return -1;
    }
}
