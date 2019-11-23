package com.proshine.gateway.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proshine.expo.gateway.security.entity.TbRoleResource;
import com.proshine.gateway.security.mapper.TbRoleResourceMapper;
import com.proshine.gateway.security.service.ITbRoleResourceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 服务实现类
 *
 * @author zyl
 * @since 2019-10-10
 */
@Service
public class TbRoleResourceServiceImpl extends ServiceImpl<TbRoleResourceMapper, TbRoleResource> implements ITbRoleResourceService {

    @Override
    public List<String> getResourceIdsByRoleIds(List<String> roleIds) {
        List<String> stringList = new ArrayList<>();
        QueryWrapper<TbRoleResource> wrapper = new QueryWrapper<>();
        wrapper.in("role_id", roleIds);
        wrapper.select("resource_id");
        listMaps(wrapper).forEach(
                (Map<String, Object> map) -> stringList.add(map.get("resource_id").toString())
        );
        return stringList;
    }
}
