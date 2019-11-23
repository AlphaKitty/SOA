package com.proshine.terminal.terminal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proshine.expo.terminal.terminal.entity.TbTerminalRelevance;
import com.proshine.terminal.terminal.mapper.TbTerminalRelevanceMapper;
import com.proshine.terminal.terminal.service.ITbTerminalRelevanceService;
import org.springframework.stereotype.Service;

/**
 * tb_terminal和其余表的关联表 服务实现类
 *
 * @author zyl
 * @since 2019-10-21
 */
@Service
public class TbTerminalRelevanceServiceImpl extends ServiceImpl<TbTerminalRelevanceMapper, TbTerminalRelevance> implements ITbTerminalRelevanceService {

    @Override
    public void deleteByTerminalId(String id) {
        QueryWrapper<TbTerminalRelevance> wrapper = new QueryWrapper<>();
        wrapper.eq("terminal_id", id);
        remove(wrapper);
    }
}
