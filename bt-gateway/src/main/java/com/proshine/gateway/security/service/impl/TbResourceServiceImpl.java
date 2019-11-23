package com.proshine.gateway.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proshine.expo.gateway.security.entity.TbResource;
import com.proshine.gateway.security.mapper.TbResourceMapper;
import com.proshine.gateway.security.service.ITbResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author zyl
 * @since 2019-10-11
 */
@Service
public class TbResourceServiceImpl extends ServiceImpl<TbResourceMapper, TbResource> implements ITbResourceService {

    @Override
    public List<TbResource> getResourceByIds(List<String> resourceIds) {
        QueryWrapper<TbResource> wrapper = new QueryWrapper<>();
        wrapper.in("id", resourceIds);
        return list(wrapper);
    }
}
