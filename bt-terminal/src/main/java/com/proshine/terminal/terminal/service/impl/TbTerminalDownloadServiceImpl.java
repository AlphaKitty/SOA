package com.proshine.terminal.terminal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.proshine.expo.terminal.terminal.entity.TbTerminalDownload;
import com.proshine.terminal.terminal.mapper.TbTerminalDownloadMapper;
import com.proshine.terminal.terminal.service.ITbTerminalDownloadService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author zyl
 * @since 2019-10-21
 */
@Service
public class TbTerminalDownloadServiceImpl extends ServiceImpl<TbTerminalDownloadMapper, TbTerminalDownload> implements ITbTerminalDownloadService {

    @Override
    public void deleteByTerminalId(String id) {
        QueryWrapper<TbTerminalDownload> wrapper = new QueryWrapper<>();
        wrapper.eq("terminal_id",id);
        remove(wrapper);
    }

    @Override
    public TbTerminalDownload findByTermIdAndCstmId(String cstmId, String terminalId) {
        QueryWrapper<TbTerminalDownload> wrapper = new QueryWrapper<>();
        wrapper.eq("terminal_id",terminalId);
        wrapper.eq("cstm_id",cstmId);
        return getOne(wrapper);
    }

    @Override
    public void updateTerminalDown(long totalSize, long downloadedSize, String id) {
        UpdateWrapper<TbTerminalDownload> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        wrapper.set("downloaded_size", downloadedSize);
        wrapper.set("total_size", totalSize);
        update(wrapper);
    }
}
