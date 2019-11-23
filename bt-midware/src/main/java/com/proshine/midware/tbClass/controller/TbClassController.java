package com.proshine.midware.tbClass.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.proshine.expo.ExpoService;
import com.proshine.expo.midware.mqtt.entity.MqttCmd;
import com.proshine.expo.midware.mqtt.entity.enumer.EnumUniformID;
import com.proshine.midware.mongodb.MongoHandler;
import com.proshine.midware.mqtt.MqttProperties;
import com.proshine.midware.mqtt.service.MqttSendService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;

/**
 * 班级信息表 前端控制器
 *
 * @author zyl
 * @since 2019-10-08
 */
@RestController
@RequestMapping("/tbClass/tb-class")
public class TbClassController {
    @Reference
    private ExpoService expoService;
    @Autowired
    private MqttSendService mqttSendService;
    @Autowired
    private MqttProperties mqttProperties;
    @Autowired
    private MongoHandler mongoHandler;

    @RequestMapping("/sayHello")
    public String sayHello(String name) {
        MqttCmd cmd = new MqttCmd();
        cmd.setId("id");
        cmd.setOperation("operation");
        cmd.setParams(new HashMap(1));
        cmd.setService("service");
        Message<String> msg = MessageBuilder.withPayload(JSON.toJSONString(cmd))
                .setHeader(MqttHeaders.QOS, mqttProperties.getQos())
                .setHeader(MqttHeaders.TOPIC, EnumUniformID.TOPIC_PREFIX + UUID.randomUUID())
                .build();
        // for (int i = 0; i < 100; i++) {
        //     mqttSendService.handleMessage(msg);
        // }
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(mqttSendService);
            thread.start();
        }
        return expoService.sayHello(name);
    }

    @GetMapping("/sayBye")
    public Object sayBye(HttpServletResponse response) {
        // response.reset();
        // response.setContentType("application/octet-stream");
        return mongoHandler.getOne("runoob", "test_file", new ObjectId("5d75b8bc462a2f5cc070f87f"));
        // return mongoHandler.exists("runoob", "test_file", "5d75b8bc462a2f5cc070f87f");
        // return mongoHandler.all("runoob");
    }
}
