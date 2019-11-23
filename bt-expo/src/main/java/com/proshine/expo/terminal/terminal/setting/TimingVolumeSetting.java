package com.proshine.expo.terminal.terminal.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 定时音量
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TimingVolumeSetting extends CommonSetting {

    /**
     * 周期配置
     */
    private List<TimeRangeVal> range;

    /**
     * 固定配置
     */
    private int value;

    /**
     * true:周期
     * false:固定
     */
    private boolean useRange;
}
