package com.proshine.expo.midware.mqtt.entity.enumer;

/**
 * 消息送达质量
 */
public class EnumCmdQuality {
    /**
     * 至多一次 0~1 次
     */
    public static final int QOS_0 = 0;
    /**
     * 至少一次 >=1 次
     */
    public static final int QOS_1 = 1;
    /**
     * 只一次 =1 次
     */
    public static final int QOS_2 = 2;
}
