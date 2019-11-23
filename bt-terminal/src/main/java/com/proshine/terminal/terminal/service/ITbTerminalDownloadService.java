package com.proshine.terminal.terminal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.proshine.expo.terminal.terminal.entity.TbTerminalDownload;

/**
 * 服务类
 *
 * @author zyl
 * @since 2019-10-21
 */
public interface ITbTerminalDownloadService extends IService<TbTerminalDownload> {

    void deleteByTerminalId(String id);

    TbTerminalDownload findByTermIdAndCstmId(String cstmId, String terminalId);

    void updateTerminalDown(long totalSize, long downloadedSize, String id);
}
