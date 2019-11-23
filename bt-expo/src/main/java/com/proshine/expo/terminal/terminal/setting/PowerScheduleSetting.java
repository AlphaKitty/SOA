package com.proshine.expo.terminal.terminal.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 终端定时开关机配置
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PowerScheduleSetting extends CommonSetting {

    /**
     * 开关机模式
     * 0: A8xx 软件开关机   1: A8xx 硬件开关机
     */
    private int selectPowerMode;

    /**
     * 时间模式
     * 时间模式 rangeMode: 区间模式    weekMode: 星期模式
     */
    private String selectTimeMode;

    /**
     * 区间模式配置
     */
    private DateTimeRange rangeModeSchedule;

    /**
     * 星期模式配置
     */
    private List<TimeRange> weekModeSchedule;

    /**
     * 统一配置，一周内所有定时开关机的时间一样。
     */
    private TimeRange unifyRangeSchedule;
}
