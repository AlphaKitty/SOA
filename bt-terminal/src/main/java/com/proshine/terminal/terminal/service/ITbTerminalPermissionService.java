package com.proshine.terminal.terminal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.proshine.expo.terminal.terminal.entity.TbTerminalPermission;

import java.util.List;

/**
 * 服务类
 *
 * @author zyl
 * @since 2019-10-21
 */
public interface ITbTerminalPermissionService extends IService<TbTerminalPermission> {

    void deletePermissionByTerminalId(String id);

    List<String> queryTerminalByUserId(String userId);

    void deleteTerminalPermission(String list);
}
