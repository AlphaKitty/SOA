package com.proshine.expo.terminal.terminal.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * Wifi 设置
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WifiSetting extends CommonSetting {

    /**
     * WIFI AP名
     */
    private String apName;

    /**
     * AP密码
     */
    private String apPassword;

    /**
     * IP获取方式
     * dhcp: 动态获取  static: 静态模式
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
