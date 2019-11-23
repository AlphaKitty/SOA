package com.proshine.gateway.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proshine.expo.gateway.security.entity.TbCustomer;
import com.proshine.gateway.security.mapper.TbCustomerMapper;
import com.proshine.gateway.security.service.ITbCustomerService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author zyl
 * @since 2019-10-21
 */
@Service
public class TbCustomerServiceImpl extends ServiceImpl<TbCustomerMapper, TbCustomer> implements ITbCustomerService {

    @Override
    public TbCustomer getCustomerByCstmId(String cstmId) {
        QueryWrapper<TbCustomer> wrapper = new QueryWrapper<>();
        wrapper.eq("id",cstmId);
        return getOne(wrapper);
    }
}
