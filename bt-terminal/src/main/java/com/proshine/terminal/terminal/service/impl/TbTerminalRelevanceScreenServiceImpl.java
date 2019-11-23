package com.proshine.terminal.terminal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proshine.expo.terminal.terminal.entity.TbTerminalRelevanceScreen;
import com.proshine.terminal.terminal.mapper.TbTerminalRelevanceScreenMapper;
import com.proshine.terminal.terminal.service.ITbTerminalRelevanceScreenService;
import org.springframework.stereotype.Service;

/**
 * tb_terminal和其余表的关联表 服务实现类
 *
 * @author zyl
 * @since 2019-10-21
 */
@Service
public class TbTerminalRelevanceScreenServiceImpl extends ServiceImpl<TbTerminalRelevanceScreenMapper, TbTerminalRelevanceScreen> implements ITbTerminalRelevanceScreenService {

}
