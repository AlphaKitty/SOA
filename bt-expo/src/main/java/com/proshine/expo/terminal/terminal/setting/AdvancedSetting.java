package com.proshine.expo.terminal.terminal.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 高级设置
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AdvancedSetting extends CommonSetting {

    /**
     * 0: 横屏 90: 纵屏 180:反向横屏 270:反向纵屏
     */
    private int displayRotation;

    /**
     * internalSD: 内部SD卡   externalSD: 外部SD卡 usbDisk: U盘
     */
    private String storageLocation;

    /**
     * 返回到节目页的返回时间 单位秒
     */
    private String progPageBackTime;
    /**
     * 返回到节目首页的返回时间 单位秒
     */
    private String progMainBackTime;
}
