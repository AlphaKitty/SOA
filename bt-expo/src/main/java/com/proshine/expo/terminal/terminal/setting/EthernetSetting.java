package com.proshine.expo.terminal.terminal.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 以太网设置
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EthernetSetting extends CommonSetting {

    /**
     * IP获取模式
     */
    private String ipModeSelect;

    /**
     * IP地址
     */
    private String ipaddr;

    /**
     * 子网掩码
     */
    private String netmask;

    /**
     * 域名服务器
     */
    private String dns;

    /**
     * 网关地址
     */
    private String gateway;
}
