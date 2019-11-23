package com.proshine.gateway.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.proshine.expo.gateway.security.entity.TbCustomer;

/**
 * 服务类
 *
 * @author zyl
 * @since 2019-10-21
 */
public interface ITbCustomerService extends IService<TbCustomer> {

    TbCustomer getCustomerByCstmId(String cstmId);
}
