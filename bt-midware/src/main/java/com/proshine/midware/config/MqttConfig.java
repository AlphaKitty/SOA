package com.proshine.midware.config;

import com.proshine.midware.mqtt.MqttProperties;
import com.proshine.midware.mqtt.service.MqttSendService;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;

/**
 * Mqtt配置类
 * ClassName: MqttConfig
 * Author: zyl
 * Date: 2019/10/15 11:41
 */
@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttConfig {

    @Autowired
    private MqttProperties mqttProperties;
    @Autowired
    private DefaultMqttPahoClientFactory clientFactory;

    /**
     * 注册MqttSendService带参构造到上下文 只能用这种方法
     *
     * @return MqttSendService带参Bean
     */
    @Bean
    public MqttSendService mqttSendService() {
        return new MqttSendService(mqttProperties.getClientId(), clientFactory);
    }

    /**
     * 注册jar包中的类到Spring上下文 只能用这种方法
     *
     * @return DefaultMqttPahoClientFactory
     */
    @Bean
    public DefaultMqttPahoClientFactory clientFactory() {
        DefaultMqttPahoClientFactory clientFactory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(mqttProperties.getUris());
        options.setCleanSession(false);
        clientFactory.setConnectionOptions(options);
        this.clientFactory = clientFactory;
        return clientFactory;
    }

}
