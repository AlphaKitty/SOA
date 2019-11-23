package com.proshine.expo.midware.mqtt.entity.enumer;

/**
 * MQTT统一ID
 */
public class EnumUniformID {
    /**
     * 所有终端在发送单播时, 需要增加前缀
     */
    public static final String TOPIC_PREFIX = "bt_client/";

    // 广播Topic
    public static final String BROADCAST_TOPIC = "broadcast";

    // 服务器客户ID
    public static final String SERVER_CLIENT_ID = "CID_BT_SERVER";
}
