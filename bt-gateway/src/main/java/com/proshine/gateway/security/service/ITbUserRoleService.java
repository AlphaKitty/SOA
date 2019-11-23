package com.proshine.gateway.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.proshine.expo.gateway.security.entity.TbUserRole;

import java.util.List;

/**
 * 服务类
 *
 * @author zyl
 * @since 2019-10-10
 */
public interface ITbUserRoleService extends IService<TbUserRole> {

    List<String> getRoleIdsByUserId(String id);
}
