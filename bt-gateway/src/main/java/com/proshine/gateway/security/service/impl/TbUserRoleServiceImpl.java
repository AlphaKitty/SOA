package com.proshine.gateway.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proshine.expo.gateway.security.entity.TbUserRole;
import com.proshine.gateway.security.mapper.TbUserRoleMapper;
import com.proshine.gateway.security.service.ITbUserRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务实现类
 *
 * @author zyl
 * @since 2019-10-10
 */
@Service
public class TbUserRoleServiceImpl extends ServiceImpl<TbUserRoleMapper, TbUserRole> implements ITbUserRoleService {

    @Override
    public List<String> getRoleIdsByUserId(String id) {
        List<String> stringList = new ArrayList<>();
        QueryWrapper<TbUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        wrapper.select("role_id");
        getMap(wrapper).forEach((str, obj) -> stringList.add(obj.toString()));
        return stringList;
    }
}
