package com.proshine.expo.terminal.terminal.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (日期+时间)区间
 * 时间格式: 2019-08-10 10:10:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateTimeRange {

    /**
     * 起始或开机时间
     */
    private String startDateTime;

    /**
     * 截止活关机时间
     */
    private String endDateTime;
}
