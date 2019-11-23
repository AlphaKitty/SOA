package com.proshine.midware.mqtt;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.proshine.expo.midware.mqtt.MqttExpo;
import com.proshine.expo.midware.mqtt.entity.MqttCmd;
import com.proshine.expo.midware.mqtt.entity.enumer.EnumUniformID;
import com.proshine.midware.mqtt.service.MqttSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 从终端模块脱离出来的Mqtt消息发送句柄
 * ClassName: MqttHandler
 * Author: zyl
 * Date: 2019/10/24 17:01
 */
@Component
@Service
public class MqttHandler implements MqttExpo {

    @Autowired
    private MqttSendService mqttSendService;

    /**
     * 发送广播消息, 所有设备都需要接受到的Message
     *
     * @param cmd MqttCmd 对象
     * @param qos 消息送达质量
     * @return 0: 发送成功
     * -1: 发送失败
     */
    @Override
    public int broadcastCmd(MqttCmd cmd, int qos) {
        Message<String> msg = MessageBuilder.withPayload(JSON.toJSONString(cmd))
                .setHeader(MqttHeaders.QOS, qos)
                .setHeader(MqttHeaders.TOPIC, EnumUniformID.BROADCAST_TOPIC)
                .build();
        mqttSendService.handleMessage(msg);
        return 0;
    }

    /**
     * 发送单播, 特定设备可以接收到
     *
     * @param cmd        MqttCmd 对象
     * @param terminalId 终端id
     * @param qos        消息送达质量
     * @return 0: 发送成功 -1: 发送失败
     */
    @Override
    public int unicastCmd(MqttCmd cmd, String terminalId, int qos) {
        Message<String> msg = MessageBuilder.withPayload(JSON.toJSONString(cmd))
                .setHeader(MqttHeaders.QOS, qos)
                .setHeader(MqttHeaders.TOPIC, EnumUniformID.TOPIC_PREFIX + terminalId)
                .build();
        mqttSendService.handleMessage(msg);
        return 0;
    }

    /**
     * 组播, 发送指令给多个设备
     *
     * @param cmd         MqttCmd 对象
     * @param terminalIds 终端ids
     * @param qos         消息送达质量
     * @return 0: 发送成功 1: 发送失败
     */
    @Override
    public int multicastCmd(MqttCmd cmd, List<String> terminalIds, int qos) {
        for (String terminalId : terminalIds) {
            unicastCmd(cmd, terminalId, qos);
        }
        return 0;
    }

}
