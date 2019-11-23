package com.proshine.midware.mqtt.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * 消费端服务
 */
@Log4j2
@Service
public class MqttReceiveService {
    //
    // @Autowired
    // private TerminalManageService terminalManageService;
    //
    // public void handleMessage(String message) {
    //
    //     if (message.contains("disconnected")) { // 客户端断开
    //         // 1537283230: Client AndroidExampleClient disconnected.
    //         System.out.println("客户端断开：" + message);
    //
    //         String startStr = "-";
    //         int startIndex = message.indexOf(startStr) + startStr.length();
    //         int endIndex = startIndex + 32;
    //
    //         String clientId = message.substring(startIndex, endIndex);
    //
    //         updateTerminalOffline(clientId);
    //     }
    //
    //     if (message.contains("disconnecting")) { // 客户端异常断开
    //         // 客户端异常断开：1555048309: Client 955ac-63e1bb0801254ce7a9cb9537ba6ebc5e has exceeded timeout, disconnecting.(拔网线)
    //         // 客户端异常断开：1555048581: Socket error on client 955ac-63e1bb0801254ce7a9cb9537ba6ebc5e, disconnecting.(点保存)
    //         System.out.println("客户端异常断开：" + message);
    //
    //         String startStr = "-";
    //         int startIndex = message.indexOf(startStr) + startStr.length();
    //         int endIndex = startIndex + 32;
    //
    //         String clientId = message.substring(startIndex, endIndex);
    //
    //         updateTerminalOffline(clientId);
    //     }
    // }
    //
    //
    // /**
    //  * 更新设备的在线状态
    //  */
    // private void updateTerminalOnline(String terminalId) {
    //     //先查找一下是否为关机,如果为关机则改成1,因为上报需要从数据库拉取状态,如果是0则不让上报,判断为mqtt有没有和
    //     //设备进行连接,没有此步骤,关机后,机器状态永远无法更改
    //     //刚开机状态不稳定,会一直断开客户端链接,设置成3,虽然mqtt一直在变化,后台看不出来,不会影响很大.设置成1,正常一会断开了会显示关机.影响体验.
    //     terminalManageService.updateTerminalOffline(terminalId, 3);
    // }
    //
    // /**
    //  * 更新设备的离线状态
    //  */
    // private void updateTerminalOffline(String terminalId) {
    //     terminalManageService.updateTerminalOffline(terminalId, 0);
    // }

}