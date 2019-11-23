package com.proshine.midware.mqtt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName: MqttProperties
 * Description: Mqtt配置类
 * Author: zyl
 * Date: 2019/10/15 10:41
 */
@Data
@Component
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

    private String clientId;
    private String[] uris;
    private int qos;

}
