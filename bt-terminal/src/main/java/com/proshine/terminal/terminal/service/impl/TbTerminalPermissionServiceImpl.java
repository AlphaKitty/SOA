package com.proshine.terminal.terminal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proshine.base.common.utils.StringUtils;
import com.proshine.expo.terminal.terminal.entity.TbTerminalPermission;
import com.proshine.terminal.terminal.mapper.TbTerminalPermissionMapper;
import com.proshine.terminal.terminal.service.ITbTerminalPermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务实现类
 *
 * @author zyl
 * @since 2019-10-21
 */
@Service
public class TbTerminalPermissionServiceImpl extends ServiceImpl<TbTerminalPermissionMapper, TbTerminalPermission> implements ITbTerminalPermissionService {

    @Override
    public void deletePermissionByTerminalId(String id) {
        QueryWrapper<TbTerminalPermission> wrapper = new QueryWrapper<>();
        wrapper.eq("terminal_id",id);
        remove(wrapper);
    }

    @Override
    public List<String> queryTerminalByUserId(String userId) {
        List<String> stringList = new ArrayList<>();
        QueryWrapper<TbTerminalPermission> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.select("ter_id");
        getMap(wrapper).forEach((str,obj) -> stringList.add(obj.toString()));
        return stringList;
    }

    @Override
    public void deleteTerminalPermission(String ids) {
        QueryWrapper<TbTerminalPermission> wrapper = new QueryWrapper<>();
        wrapper.in("id", StringUtils.splitByChar(ids,","));
    }
}
