package com.proshine.terminal.terminal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proshine.expo.terminal.terminal.entity.TbTerminal;
import com.proshine.terminal.terminal.mapper.TbTerminalMapper;
import com.proshine.terminal.terminal.service.ITbTerminalService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author zyl
 * @since 2019-10-21
 */
@Service
public class TbTerminalServiceImpl extends ServiceImpl<TbTerminalMapper, TbTerminal> implements ITbTerminalService {

    @Override
    public int countByTerminalNameAndCstmId(String terminalName, String cstmId) {
        QueryWrapper<TbTerminal> wrapper = new QueryWrapper<>();
        wrapper.eq("terminal_name", terminalName);
        wrapper.eq("cstm_id", cstmId);
        return count(wrapper);
    }

    @Override
    public TbTerminal findByTerminalIdAndCstmId(String terminalId, String cstmId) {
        QueryWrapper<TbTerminal> wrapper = new QueryWrapper<>();
        wrapper.eq("terminal_id", terminalId);
        wrapper.eq("cstm_id", cstmId);
        return getOne(wrapper);
    }

    @Override
    public List<TbTerminal> findByCstmId(String cstmId) {
        QueryWrapper<TbTerminal> wrapper = new QueryWrapper<>();
        wrapper.eq("cstm_id", cstmId);
        return list(wrapper);
    }

    @Override
    public void updateTerminalOffline(String terminalId, int status) {
        UpdateWrapper<TbTerminal> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", terminalId);
        wrapper.set("online_state", status);
        update(wrapper);
    }

    @Override
    public void batchUpdateOnline(List<String> list) {
        UpdateWrapper<TbTerminal> wrapper = new UpdateWrapper<>();
        wrapper.in("terminal_id", list);
        wrapper.set("online_state", 1);
        update(wrapper);
    }

    @Override
    public void batchUpdateOffline(List<String> list) {
        UpdateWrapper<TbTerminal> wrapper = new UpdateWrapper<>();
        wrapper.in("terminal_id", list);
        wrapper.set("online_state", 0);
        update(wrapper);
    }

    @Override
    public void deleteByCstmId(String cstmId) {
        QueryWrapper<TbTerminal> wrapper = new QueryWrapper<>();
        wrapper.eq("cstm_id", cstmId);
        remove(wrapper);
    }

    @Override
    public void updateTerminalPart(TbTerminal tbTerminal) {
        UpdateWrapper<TbTerminal> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", tbTerminal.getId());
        wrapper.set("terminal_id", tbTerminal.getTerminalId());
        wrapper.set("extra_id1", tbTerminal.getExtraId1());
        wrapper.set("extra_id2", tbTerminal.getExtraId2());
        wrapper.set("terminal_name", tbTerminal.getTerminalName());
        wrapper.set("cat_id", tbTerminal.getCatId());
        wrapper.set("online_state", tbTerminal.getOnlineState());
        wrapper.set("ipaddr", tbTerminal.getIpaddr());
        wrapper.set("classroom_name", tbTerminal.getClassroomName());
        wrapper.set("device_model_id", tbTerminal.getDeviceModelId());
        update(wrapper);
    }

    @Override
    public void batchUpdateCategory(List<TbTerminal> list) {
        UpdateWrapper<TbTerminal> wrapper = new UpdateWrapper<>();
        for (TbTerminal tbTerminal : list) {
            wrapper.eq("id", tbTerminal.getId());
            wrapper.set("cat_id",tbTerminal.getCatId());
            update(tbTerminal,wrapper);
        }
    }

}
