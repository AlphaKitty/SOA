package com.proshine.expo.terminal.terminal.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 版本配置
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionSetting {

    /**
     * 当前id
     */
    private String currentId;

    /**
     * 当前名称
     */
    private String currentName;

    /**
     * 当前版本
     */
    private String currentVersion;

    /**
     * 分配id
     */
    private String newId;

    /**
     * 分配名称
     */
    private String newName;

    /**
     * 分配版本
     */
    private String newVersion;

    /**
     * 程序类型
     */
    private String appType;

}
