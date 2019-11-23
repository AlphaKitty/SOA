package com.proshine.terminal.terminal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.proshine.expo.terminal.terminal.entity.TbTerminal;

import java.util.List;

/**
 * 服务类
 *
 * @author zyl
 * @since 2019-10-21
 */
public interface ITbTerminalService extends IService<TbTerminal> {

    int countByTerminalNameAndCstmId(String terminalName, String cstmId);

    TbTerminal findByTerminalIdAndCstmId(String terminalId, String cstmId);

    List<TbTerminal> findByCstmId(String cstmId);

    void updateTerminalOffline(String terminalId, int status);

    void batchUpdateOnline(List<String> list);

    void batchUpdateOffline(List<String> list);

    void deleteByCstmId(String cstmId);

    void updateTerminalPart(TbTerminal tbTerminal);

    void batchUpdateCategory(List<TbTerminal> list);
}
