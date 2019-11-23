package com.proshine.gateway.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.proshine.expo.gateway.security.entity.TbResource;

import java.util.List;

/**
 * 服务类
 *
 * @author zyl
 * @since 2019-10-11
 */
public interface ITbResourceService extends IService<TbResource> {

    List<TbResource> getResourceByIds(List<String> resourceIds);
}
