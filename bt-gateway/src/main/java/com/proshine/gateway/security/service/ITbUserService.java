package com.proshine.gateway.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.proshine.expo.gateway.security.entity.TbUser;

/**
 * 服务类
 *
 * @author zyl
 * @since 2019-10-10
 */
public interface ITbUserService extends IService<TbUser> {

    TbUser findUserInfoByUsnameAndCstmId(String username, String cstmId);
}
