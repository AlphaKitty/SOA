package com.proshine.terminal.terminal.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.proshine.expo.midware.mqtt.MqttExpo;
import com.proshine.expo.midware.mqtt.entity.MqttCmd;
import com.proshine.expo.midware.mqtt.entity.enumer.EnumCmdQuality;
import com.proshine.expo.midware.mqtt.entity.enumer.EnumParamKey;
import com.proshine.expo.midware.mqtt.entity.enumer.EnumService;
import com.proshine.terminal.terminal.service.TerminalControlService;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

// TODO: 2019/10/24 zylTodo 这里最后还是得改成Service
@Controller
public class TerminalControlServiceImpl implements TerminalControlService {

    @Reference
    private MqttExpo mqttExpo;

    @Override
    public void reboot(String terminalId) {
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.TERMINAL_REBOOT),
                terminalId,
                EnumCmdQuality.QOS_0
        );
    }

    @Override
    public void powerOff(String terminalId) {
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.TERMINAL_POWEROFF),
                terminalId,
                EnumCmdQuality.QOS_0
        );
    }

    @Override
    public void deLock(String terminalId) {
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.TERMINAL_DEBLOCK),
                terminalId,
                EnumCmdQuality.QOS_2
        );
    }

    @Override
    public void lock(String terminalId) {
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.TERMINAL_LOCK),
                terminalId,
                EnumCmdQuality.QOS_2
        );
    }

    @Override
    public void sleep(String terminalId) {
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.TERMINAL_SLEEP),
                terminalId,
                EnumCmdQuality.QOS_2
        );
    }

    @Override
    public void wakeup(String terminalId) {
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.TERMINAL_WAKEUP),
                terminalId,
                EnumCmdQuality.QOS_2
        );
    }

    @Override
    public void updateProgram(String programId) {
        HashMap<String, String> param = new HashMap<>(16);
        param.put(EnumParamKey.VALUE, programId);
        mqttExpo.broadcastCmd(
                MqttCmd.create(EnumService.SERVICE_PROGRAM, EnumService.EnumProgramOps.UPDATE_PROGRAM, param),
                EnumCmdQuality.QOS_1
        );
    }

    @Override
    public void issueProgram(String programId, String terminalId) {
        HashMap<String, String> param = new HashMap<>(16);
        param.put(EnumParamKey.VALUE, programId);
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_PROGRAM, EnumService.EnumProgramOps.ISSUE_PROGRAM, param),
                terminalId,
                EnumCmdQuality.QOS_1
        );
    }

    @Override
    public void cleanDisk(String terminalId) {
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.TERMINAL_CLEAN_DISK),
                terminalId,
                EnumCmdQuality.QOS_2
        );

    }

    @Override
    public void settingTerminal(String terminalId, String settingType) {
        HashMap<String, String> param = new HashMap<>();
        param.put(EnumParamKey.VALUE, settingType);
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.TERMINAL_SETTING, param),
                terminalId,
                EnumCmdQuality.QOS_2
        );
    }

    @Override
    public void upgradeApp(String terminalId, String version, String md5, String appType, String softwareId, String name) {
        HashMap<String, String> param = new HashMap<>();
        param.put(EnumParamKey.VALUE, version);
        param.put(EnumParamKey.MD5, md5);
        param.put(EnumParamKey.APP_TYPR, appType);
        param.put(EnumParamKey.APP_ID, softwareId);
        param.put(EnumParamKey.APP_NAME, name);
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.TERMINAL_UPGRADE_APP, param),
                terminalId,
                EnumCmdQuality.QOS_1
        );
    }

    @Override
    public void upgradeFirmware(String terminalId, String version, String md5, String id, String name) {
        HashMap<String, String> param = new HashMap<>();
        param.put(EnumParamKey.VALUE, version);
        param.put(EnumParamKey.APP_ID, id);
        param.put(EnumParamKey.MD5, md5);
        param.put(EnumParamKey.APP_NAME, name);
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.TERMINAL_UPGRADE_FIRMWARE, param),
                terminalId,
                EnumCmdQuality.QOS_1
        );
    }

    @Override
    public void modifyTerminal(String terminalId, String id) {
        Map<String, String> param = new HashMap<>();
        param.put("id", id);
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.TERMINAL_UPDATE_INFO, param),
                terminalId,
                EnumCmdQuality.QOS_1
        );
    }

    @Override
    public void captureScreen(String terminalId) {
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.TERMINAL_SCREEN_CAPTURE),
                terminalId,
                EnumCmdQuality.QOS_1
        );
    }

    @Override
    public void remoteControl(String terminalId, int keyCode) {
        HashMap<String, String> param = new HashMap<>();
        param.put(EnumParamKey.VALUE, String.valueOf(keyCode));
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.TERMINAL_REMOTE_CONTROL, param),
                terminalId,
                EnumCmdQuality.QOS_1
        );
    }

    @Override
    public void reportTerminalSetting(String terminalId, String settingType) {
        HashMap<String, String> param = new HashMap<>();
        param.put(EnumParamKey.VALUE, settingType);
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.TERMINAL_REPORT_SETTING, param),
                terminalId,
                EnumCmdQuality.QOS_1
        );
    }

    @Override
    public void touchScreen(String terminalId, int x, int y) {
        HashMap<String, String> param = new HashMap<>();
        param.put(EnumParamKey.X, String.valueOf(x));
        param.put(EnumParamKey.Y, String.valueOf(y));
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.TERMINAL_REMOTE_CONTROL_TOUCH, param),
                terminalId,
                EnumCmdQuality.QOS_1
        );
    }

    @Override
    public void studentPlanState(String terminalId, Map<String, String> param) {
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.ACCORDANCE_STATE, param),
                terminalId,
                EnumCmdQuality.QOS_1
        );
    }

    @Override
    public void volume(String terminalId, String lv) {
        Map<String, String> map = new HashMap<>();
        map.put("lv", lv);
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumTerminalOps.TERMINAL_SOUND_VOLUME, map),
                terminalId,
                EnumCmdQuality.QOS_1
        );
    }

    @Override
    public void sendContent(String terminalId, Map<String, String> param) {
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumContentOps.ISSUE_CONTENT, param),
                terminalId,
                EnumCmdQuality.QOS_1
        );
    }

    @Override
    public void deleteContent(String terminalId, Map<String, String> param) {
        mqttExpo.unicastCmd(
                MqttCmd.create(EnumService.SERVICE_TERMINAL, EnumService.EnumContentOps.DELETE_CONTENT, param),
                terminalId,
                EnumCmdQuality.QOS_1
        );
    }

    @Override
    public void pushArtScreenPlayList(String terminalIds) {
        String[] arr = terminalIds.split(",");
        for (String terminalId : arr) {
            mqttExpo.unicastCmd(
                    MqttCmd.create(EnumService.SERVICE_PROGRAM, EnumService.EnumArtPlayOps.DOWNLOAD_PLAY_LIST),
                    terminalId,
                    EnumCmdQuality.QOS_1
            );
        }
    }
}
