package com.proshine.expo.terminal.terminal.setting;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 终端升级配置
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UpgradeSetting extends CommonSetting {

    /**
     * daemon程序
     */
    private VersionSetting daemonSoftware;

    /**
     * 其他程序
     */
    private VersionSetting otherSoftware;

    /**
     * touch程序
     */
    private VersionSetting touchSoftware;
    /**
     * 固件
     */
    private VersionSetting firmwareSoftware;

}
