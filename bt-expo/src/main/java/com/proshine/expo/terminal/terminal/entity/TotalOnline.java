package com.proshine.expo.terminal.terminal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 终端在线情况统计
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalOnline {

    private String isOnline;
    private Integer total;
    private String color;

}
