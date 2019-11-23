package com.proshine.expo.terminal.terminal.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 时间区间
 * 时间格式:
 * 10:10:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeRange {
    /**
     * 起始活开机时间
     */
    private String startTime;

    /**
     * 截止或关机时间
     */
    private String endTime;

    /**
     * 是否开启
     */
    private Boolean enable;
}
