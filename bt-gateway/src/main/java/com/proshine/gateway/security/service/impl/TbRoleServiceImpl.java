package com.proshine.gateway.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proshine.expo.gateway.security.entity.TbRole;
import com.proshine.gateway.security.mapper.TbRoleMapper;
import com.proshine.gateway.security.service.ITbRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author zyl
 * @since 2019-10-10
 */
@Service
public class TbRoleServiceImpl extends ServiceImpl<TbRoleMapper, TbRole> implements ITbRoleService {

    @Override
    public List<TbRole> getRolesByUserId(List<String> ids) {
        QueryWrapper<TbRole> wrapper = new QueryWrapper<>();
        wrapper.in("id",ids);
        return list(wrapper);
    }
}
