package com.proshine.expo.terminal.terminal.setting;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 节目下发配置
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProgramSetting extends CommonSetting {

    /**
     * 互动节目
     */
    private VersionSetting demandProgram;

    /**
     * 轮播节目
     */
    private VersionSetting loopProgram;

    /**
     * 插播节目
     */
    private VersionSetting pluginProgram;

}
