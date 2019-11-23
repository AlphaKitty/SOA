package com.proshine.gateway.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proshine.expo.gateway.security.entity.TbUser;
import com.proshine.gateway.security.mapper.TbUserMapper;
import com.proshine.gateway.security.service.ITbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author zyl
 * @since 2019-10-10
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements ITbUserService {

    @Autowired
    private TbCustomerServiceImpl tbCustomerServiceImpl;
    @Autowired
    private TbRoleServiceImpl tbRoleServiceImpl;
    @Autowired
    private TbUserRoleServiceImpl tbUserRoleServiceImpl;

    @Override
    public TbUser findUserInfoByUsnameAndCstmId(String username, String cstmId) {
        QueryWrapper<TbUser> wrapper = new QueryWrapper<>();
        wrapper.eq("usname", username);
        wrapper.eq("cstm_id", cstmId);
        TbUser user = getOne(wrapper);
        user.setCustomer(tbCustomerServiceImpl.getCustomerByCstmId(cstmId));
        user.setRoles(tbRoleServiceImpl.getRolesByUserId(tbUserRoleServiceImpl.getRoleIdsByUserId(user.getId())));
        // TODO: 2019/10/29 zylTodo 没有绑定和教师的关联 需要campus的教室提供dubbo服务
        return user;
    }
}
