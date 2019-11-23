package com.proshine.expo.midware.mqtt;

import com.proshine.expo.midware.mqtt.entity.MqttCmd;

import java.util.List;

public interface MqttExpo {

    /**
     * 发送广播消息, 所有设备都需要接受到的Message
     *
     * @param cmd MqttCmd 对象
     * @param qos 消息送达质量
     * @return 0: 发送成功
     * -1: 发送失败
     */
    int broadcastCmd(MqttCmd cmd, int qos);

    /**
     * 发送单播, 特定设备可以接收到
     *
     * @param cmd   MqttCmd 对象
     * @param topic 指定的Topic String对象, 一般为设备DEV_ID
     * @param qos   消息送达质量
     * @return 0: 发送成功
     * -1: 发送失败
     */
    int unicastCmd(MqttCmd cmd, String topic, int qos);

    /**
     * 组播, 发送指令给多个设备
     *
     * @param cmd    MqttCmd 对象
     * @param topics 指定的Topic String对象, 一般为设备DEV_ID
     * @param qos    消息送达质量
     * @return 0: 发送成功
     * -1: 发送失败
     */
    int multicastCmd(MqttCmd cmd, List<String> topics, int qos);

}
