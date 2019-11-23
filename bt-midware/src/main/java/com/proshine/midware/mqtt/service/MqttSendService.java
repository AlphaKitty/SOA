package com.proshine.midware.mqtt.service;

import com.alibaba.fastjson.JSON;
import com.proshine.expo.midware.mqtt.entity.MqttCmd;
import com.proshine.expo.midware.mqtt.entity.enumer.EnumUniformID;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;
import java.util.UUID;

/**
 * 消息发送服务
 */
@Log4j2
// TODO: 2019/10/16 zylTodo Runnable接口后面要删掉
public class MqttSendService extends MqttPahoMessageHandler implements Runnable {

    public MqttSendService(String clientId, MqttPahoClientFactory clientFactory) {
        super(clientId, clientFactory);
    }

    public MqttSendService(String url, String clientId, MqttPahoClientFactory clientFactory) {
        super(url, clientId, clientFactory);
    }

    public MqttSendService(String url, String clientId) {
        super(url, clientId);
    }

    @Override
    public synchronized void connectionLost(Throwable cause) {
        super.connectionLost(cause);
        log.warn(cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        super.messageArrived(topic, message);
        log.debug(topic + "\n\t" + message.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        super.deliveryComplete(token);
        log.debug(token.toString());
    }

    @Override
    public void run() {
        MqttCmd cmd = new MqttCmd();
        cmd.setId("id");
        cmd.setOperation("operation");
        cmd.setParams(new HashMap(1));
        cmd.setService("service");
        Message<String> msg = MessageBuilder.withPayload(JSON.toJSONString(cmd))
                .setHeader(MqttHeaders.QOS, 1)
                .setHeader(MqttHeaders.TOPIC, EnumUniformID.TOPIC_PREFIX + UUID.randomUUID())
                .build();
        super.handleMessage(msg);
    }
}
