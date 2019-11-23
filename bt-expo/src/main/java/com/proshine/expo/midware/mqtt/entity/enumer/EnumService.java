package com.proshine.expo.midware.mqtt.entity.enumer;

/**
 * Mqtt 服务类型
 */
public class EnumService {

    /**
     * 节目控制类服务
     */
    public static final String SERVICE_PROGRAM = "SERVICE_PROGRAM";

    /**
     * 终端控制类服务
     */
    public static final String SERVICE_TERMINAL = "SERVICE_TERMINAL";

    /**
     * 艺屏控制类
     */
    public static final String SERVICE_ART_SCREEN = "EnumArtPlayOps";

    /**
     * 节目操作类型
     */
    public static class EnumProgramOps {
        /**
         * 下发节目, 有参数
         * 参数:
         * 1. 节目ID
         */
        public static final String ISSUE_PROGRAM = "ISSUE_PROGRAM";
        /**
         * 更新节目, 有参数
         * 参数:
         * 1. 节目ID
         */
        public static final String UPDATE_PROGRAM = "UPDATE_PROGRAM";
        /**
         * 清空节目, 有参数
         * 参数:
         * 1. 节目ID
         */
        public static final String CLEAN_PROGRAM = "CLEAN_PROGRAM";
    }

    public static class EnumTerminalOps {

        /**
         * 终端重启, 无参数
         */
        public static final String TERMINAL_REBOOT = "TERMINAL_REBOOT";
        /**
         * 终端关机, 无参数
         */
        public static final String TERMINAL_POWEROFF = "TERMINAL_POWEROFF";
        /**
         * 终端休眠, 无参数
         */
        public static final String TERMINAL_SLEEP = "TERMINAL_SLEEP";
        /**
         * 终端唤醒, 无参数
         */
        public static final String TERMINAL_WAKEUP = "TERMINAL_WAKEUP";
        /**
         * 终端清空存储, 无参数
         */
        public static final String TERMINAL_CLEAN_DISK = "TERMINAL_CLEAN_DISK";

        /**
         * 终端解锁
         */
        public static final String TERMINAL_DEBLOCK = "TERMINAL_DEBLOCK";

        /**
         * 终端锁屏
         */
        public static final String TERMINAL_LOCK = "TERMINAL_LOCK";

        /**
         * 程序卸载
         */
        public static final String TERMINAL_UNINSTALL = "TERMINAL_UNINSTALL";

        /**
         * 终端设置, 有参数
         * 参数:
         * 1. 设置类型
         */
        public static final String TERMINAL_SETTING = "TERMINAL_SETTING";
        /**
         * 终端上报设置, 有参数
         * 参数:
         * 1. 设置类型
         */
        public static final String TERMINAL_REPORT_SETTING = "TERMINAL_REPORT_SETTING";
        /**
         * 终端APP升级, 有参数
         * 参数:
         * 1. APP 版本号
         */
        public static final String TERMINAL_UPGRADE_APP = "TERMINAL_UPGRADE_APP";
        /**
         * 终端固件升级, 有参数
         * 参数:
         * 1. 固件版本
         */
        public static final String TERMINAL_UPGRADE_FIRMWARE = "TERMINAL_UPGRADE_FIRMWARE";
        /**
         * 终端截屏, 有参数
         * 参数:
         * 1. 截屏质量
         */
        public static final String TERMINAL_SCREEN_CAPTURE = "TERMINAL_SCREEN_CAPTURE";
        /**
         * 终端远程控制, 有参数
         * 参数:
         * 1. 按键码
         */
        public static final String TERMINAL_REMOTE_CONTROL = "TERMINAL_REMOTE_CONTROL";
        /**
         * 终端上报信息, 无参数
         */
        public static final String TERMINAL_REPORT_INFO = "TERMINAL_REPORT_INFO";
        /**
         * 更新终端信息, 无参数
         */
        public static final String TERMINAL_UPDATE_INFO = "TERMINAL_UPDATE_INFO";
        /**
         * 执行SHELL脚本, 无参数
         */
        public static final String TERMINAL_EXE_SHELL = "TERMINAL_EXE_SHELL";
        /**
         * 设置终端亮度, 有参数
         * 参数:
         * 1. 音量值
         */
        public static final String TERMINAL_SET_VOLUME = "TERMINAL_SET_VOLUME";

        /**
         * 设置终端音量
         */
        public static final String TERMINAL_SOUND_VOLUME = "TERMINAL_SOUND_VOLUME";
        /**
         * 终端远程触控控制, 有参数
         * 参数:
         * 1. X坐标
         * 2. Y坐标
         */
        public static final String TERMINAL_REMOTE_CONTROL_TOUCH = "TERMINAL_REMOTE_CONTROL_TOUCH";

        /**
         * 下发考勤数据
         */
        public static final String ACCORDANCE_STATE = "ACCORDANCE_STATE";
    }

    public static class EnumContentOps {
        /**
         * 下发内容模块数据
         */
        public static final String ISSUE_CONTENT = "ISSUE_CONTENT";

        /**
         * 删除内容模块数据
         */
        public static final String DELETE_CONTENT = "DELETE_CONTENT";
    }

    /**
     * 艺屏播放列表相关
     */
    public static class EnumArtPlayOps {
        /**
         * 下载播放列表
         */
        public static final String DOWNLOAD_PLAY_LIST = "DOWNLOAD_PLAY_LIST";
    }


}
