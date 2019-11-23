package com.proshine.terminal.terminal.service;

import com.proshine.base.common.constant.CommonConstant;
import com.proshine.expo.terminal.terminal.setting.CommonSetting;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by wink on 17/9/22.
 */
@Service
public class TerminalConfigurationService {

    @Resource
    private MongoTemplate mongoTemplate;

    public <T extends CommonSetting> void insert(T object, Class<T> clazz) {
        mongoTemplate.insert(object, CommonConstant.getTerminalConfigurationMongodbCollectionName(clazz));
    }

    public <T extends CommonSetting> T findOne(Map<String, Object> params, Class<T> clazz) {

        Criteria criteria = Criteria.where("cstmId").is(params.get("cstmId"));
        criteria.and("terminalId").is(params.get("terminalId"));
        criteria.and("_class").is(clazz.getName());
        Object condition = params.get("individual");
        if (condition != null) {
            criteria.and("individual").is(condition);
        }
        condition = params.get("version");
        if (condition != null) {
            criteria.and("version").is(condition);
        }
        return mongoTemplate.findOne(new Query(criteria), clazz, CommonConstant.getTerminalConfigurationMongodbCollectionName(clazz));
    }

    public <T extends CommonSetting> void save(T entity, Class<T> clazz) {
        mongoTemplate.save(entity, CommonConstant.getTerminalConfigurationMongodbCollectionName(clazz));
    }

    public void createCollection(String name) {
        mongoTemplate.createCollection(name);
    }

    public <T extends CommonSetting> void remove(Map<String, Object> params, Class<T> clazz) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(params.get("_id"))), clazz,
                CommonConstant.getTerminalConfigurationMongodbCollectionName(clazz));
    }
}
