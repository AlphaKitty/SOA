package com.proshine.terminal.terminal.service;

import java.util.Map;

/**
 * 终端控制服务接口, 提供对终端的远程控制API
 * Exp:
 * 重启
 * 休眠
 * 唤醒
 * 关机
 * etc
 */
public interface TerminalControlService {
    /**
     * 重启终端
     *
     * @param terminalId 终端ID
     */
    void reboot(String terminalId);

    /**
     * 终端关机
     *
     * @param terminalId 终端ID
     */
    void powerOff(String terminalId);

    /**
     * 终端休眠
     *
     * @param terminalId 终端ID
     */
    void sleep(String terminalId);

    /**
     * 终端唤醒
     *
     * @param terminalId 终端ID
     */
    void wakeup(String terminalId);

    /**
     * 终端解锁
     *
     * @param terminalId 终端ID
     */
    void deLock(String terminalId);

    /**
     * 终解锁屏
     *
     * @param terminalId 终端ID
     */
    void lock(String terminalId);

    /**
     * 广播更新节目
     *
     * @param programId 节目ID
     */
    void updateProgram(String programId);

    /**
     * 指定终端更新节目
     *
     * @param programId  节目ID
     * @param terminalId 终端ID
     */
    void issueProgram(String programId, String terminalId);

    /**
     * 清空磁盘
     *
     * @param terminalId 终端ID
     */
    void cleanDisk(String terminalId);

    /**
     * 配置终端
     *
     * @param terminalId  终端ID
     * @param settingType 配置类型
     */
    void settingTerminal(String terminalId, String settingType);

    /**
     * 终端APP升级
     *
     * @param terminalId 终端ID
     * @param version    App 版本号
     * @param softwareId softwareId
     * @param name       name
     */
    void upgradeApp(String terminalId, String version, String md5, String appType, String softwareId, String name);

    /**
     * 终端固件升级
     *
     * @param terminalId 终端ID
     * @param version    固件版本
     */
    void upgradeFirmware(String terminalId, String version, String md5, String id, String name);

    /**
     * 修改终端基础信息,
     * exp:
     * 终端名
     *
     * @param terminalId 终端ID
     */
    void modifyTerminal(String terminalId, String id);

    /**
     * 终端截屏
     *
     * @param terminalId 终端ID
     */
    void captureScreen(String terminalId);

    /**
     * 终端远程控制
     *
     * @param terminalId 终端ID
     * @param keyCode    按键码
     */
    void remoteControl(String terminalId, int keyCode);

    /**
     * 上报终端设置
     *
     * @param terminalId  终端ID
     * @param settingType 配置类型
     */
    void reportTerminalSetting(String terminalId, String settingType);

    /**
     * 终端远程触控控制
     *
     * @param terminalId 终端ID
     * @param x          X坐标
     * @param y          Y坐标
     */
    void touchScreen(String terminalId, int x, int y);

    void studentPlanState(String terminalId, Map<String, String> param);

    void volume(String terminalId, String lv);

    /**
     * 内容模块发送消息
     */
    void sendContent(String terminalId, Map<String, String> param);

    /**
     * 删除内容信息通知班牌(目前只有会议模式使用)
     *
     * @param terminalId 终端ID
     * @param param      param
     */
    void deleteContent(String terminalId, Map<String, String> param);

    /**
     * 向终端下方艺屏播放列表
     *
     * @param terminalIds 终端ID字符串，多个用逗号隔开
     */
    void pushArtScreenPlayList(String terminalIds);
}
