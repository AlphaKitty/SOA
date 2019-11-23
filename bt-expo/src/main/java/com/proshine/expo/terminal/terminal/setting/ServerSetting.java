package com.proshine.expo.terminal.terminal.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 服务器信息设置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ServerSetting extends CommonSetting {

    /**
     * 服务器IP地址/域名
     */
    private String ipaddr = "";

    /**
     * 服务器HTTP端口
     */
    private String httpPort = "";

    /**
     * 服务器控制端口
     */
    private String controlPort = "";

}
