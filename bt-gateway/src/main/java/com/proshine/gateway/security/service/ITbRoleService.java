package com.proshine.gateway.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.proshine.expo.gateway.security.entity.TbRole;

import java.util.List;

/**
 * 服务类
 *
 * @author zyl
 * @since 2019-10-10
 */
public interface ITbRoleService extends IService<TbRole> {

    List<TbRole> getRolesByUserId(List<String> id);
}
