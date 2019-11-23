package com.proshine.expo.terminal.terminal.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 时间区间, 增加 value属性
 * 时间格式: 10:10:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TimeRangeVal extends TimeRange {

    /**
     * 数值, exp: 音量/亮度/下载速度
     */
    private int value;

}
