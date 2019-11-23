package com.proshine.base.common.constant;

import com.proshine.base.common.utils.OsCheck;
import com.proshine.expo.terminal.terminal.setting.CommonSetting;

import java.io.File;
import java.net.URLDecoder;
import java.util.Objects;

/**
 * 常量类</p>
 * 所有常量字段都存在该类里面，无其他任何业务逻辑</p>
 *
 * @author 陈江华
 */
public class CommonConstant {

    /**
     * session中保存当前用户使用的国际化信息的key
     */
    public static final String BUNNYTOUCH_LOCALE = "BUNNYTOUCH_LOCALE";

    public static final int SOFTWARE_TYPE_FIRMWARE = 0;
    public static final int SOFTWARE_TYPE_APP = 1;
    public static final int SOFTWARE_OTHER_APP = 2;

    public static final boolean TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_TERMINAL = true;
    public static final boolean TOUCHSYS_TERNIMAL_CONFIGURATION_TYPE_SERVER = false;
    /**
     * 客户域资源ID 和资源管理ID 这两个只有master客户域下的用户才给予显示
     */
    public static final String CUSTOMER_RESOURCE_ID = "2a600584ad5311e797e700163e06b7b3";
    //资源管理
    public static final String RESOURCE_MANGER_ID = "221bb860832d11e78ddc00163e06b7b3";
    //控件
    public static final String RESOURCE_WIDGET_ID = "1f2e72ca83e511e78ddc00163e06b7b3";
    public static final String SUPER_ROLE = "d8e5e3baca2d4e2fb23cfa2becd51b03";
    public static final String MASTER_ID = "master";
    public static final String DEMO = "demo";
    /**
     * 数据库VARCHAR字符串最大长度 255个字符
     */
    public static final int DB_COMMON_VARCHAR_MAX_LENGTH = 255;
    /**
     * 文件类型
     */
    public static final int FILE_TYPE_FOLDER = 0;
    public static final int FILE_TYPE_IMAGE = 1;
    public static final int FILE_TYPE_DOC = 2;
    public static final int FILE_TYPE_MP3 = 3;
    public static final int FILE_TYPE_VIDEO = 4;
    public static final int FILE_TYPE_OTHER = 99;
    /**
     * 日志模块类型
     */
    public static final int TERMINAL_LOG = 2;
    public static final int ATTACHMENT_LOG = 0;
    public static final int PROGRAM_LOG = 1;
    public static final int ADMIN_LOG = 3;
    /**
     * 色值常量
     */
    public static final String COLOR_ORANGE = "#ed6c18";
    public static final String COLOR_CBULUE = "#32bbcb";
    public static final String COLOR_GREEN = "#68bc31";
    public static final String COLOR_BLUE = "#2091cf";
    public static final String COLOR_VIOLET = "#af4e96";
    public static final String CLIENT_ACTION_DISCONNEDCTED = "client_disconnected";
    public static final String CLIENT_ACTION_CONNEDCTED = "client_connected";
    /**
     * 晓羊教育对接的关键信息
     */
    public static final String XIAOYANG_API_KEY = "634cf3f0-65ea-46a2-ab18-dfadcfb418b7";
    public static final String XIAOYANG_SESSION_KEY = "547e6dc6-0be9-43c6-839d-779897fbf4bd";
    /**
     * 审核角色
     */
    public static final String ROLE_CHECK = "d19ad63a523b4d348f6d25495ff1de71";
    public static final String ROLE_SUPER = "d8e5e3baca2d4e2fb23cfa2becd51b03";

    /**
     * 获取默认图片处理库cmd路径
     * bt_v320_tjdx01_dev
     *
     * @return String: tools/ImageMagick/convertXXXX
     */
    public static String getDefaultImageMagick() {
        try {
            String ex = Objects.requireNonNull(CommonConstant.class.getClassLoader().getResource("/")).getPath();
            ex = URLDecoder.decode(ex, "UTF-8");
            File f = new File(ex);
            f = f.getParentFile().getParentFile();
            if (OsCheck.getOperatingSystemType() == OsCheck.OSType.Linux) {
                return f.getAbsolutePath() + File.separatorChar
                        + "tools" + File.separatorChar
                        + "ImageMagick" + File.separatorChar + "convert";
            }
            return f.getAbsolutePath() + File.separatorChar
                    + "tools" + File.separatorChar
                    + "ImageMagick" + File.separatorChar + "convert_ImageMagick.exe";

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 获取默认的ffmpeg cmd工具路径
     * bt_v320_tjdx01_dev
     *
     * @return String: tools/ffmpeg/xxx/ffmpegxx
     */
    public static String getDefaultFfmpeg() {
        try {
            String ex = Objects.requireNonNull(CommonConstant.class.getClassLoader().getResource("/")).getPath();
            ex = URLDecoder.decode(ex, "UTF-8");
            File f = new File(ex);
            f = f.getParentFile().getParentFile();
            switch (OsCheck.getOperatingSystemType()) {
                case Linux:
                    return f.getAbsolutePath() + File.separatorChar + "tools"
                            + File.separatorChar + "ffmpeg"
                            + File.separatorChar + "linux_x86_x64"
                            + File.separatorChar + "ffmpeg";
                default:
                    return f.getAbsolutePath() + File.separatorChar + "tools"
                            + File.separatorChar + "ffmpeg"
                            + File.separatorChar + "win_x86"
                            + File.separatorChar + "ffmpeg.exe";
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static <T extends CommonSetting> String getTerminalConfigurationMongodbCollectionName(Class<T> clazz) {
        return MongoDBCollections.MANAGERSYS_CONFIGURATIONS_PREFIX + "_" + clazz.getSimpleName();
    }

    /**
     * MongoDB 存储数据时集合分类类型定义
     */
    public static class MongoDBCollections {
        /**
         * 互动系统--云盘模块数据集合
         */
        public static final String TOUCHSYS_CLOUD = "touchsys_cloud";
        /**
         * 考勤系统----人脸图片
         */
        public static final String MANAGERSYS_STU_FACEIMAGE = "touchsys_cloud" + "." + "files";
        /**
         * 互动系统--节目模块缩略图数据集合
         */
        public static final String TOUCHSYS_PROGRAM = "touchsys_program";
        /**
         * 互动系统--终端模块截屏数据集合
         */
        public static final String TOUCHSYS_TERMINAL = "touchsys_terminal";
        /**
         * 管理系统--用户管理模块数据集合
         */
        public static final String MANAGERSYS_USER = "managersys_user";
        /**
         * 管理系统--用户管理用户头像数据集合
         */
        public static final String MANAGERSYS_USER_IMAGE = MANAGERSYS_USER + "_" + "userimage";
        /**
         * 管理系统--软件管理模块数据集合
         */
        public static final String MANAGERSYS_SOFTWARE = "managersys_software";
        /**
         * 人脸识别
         */
        public static final String FACE_IMAGE = "faceImage";
        /**
         * 管理系统--终端管理配置总集合前缀
         */
        public static final String MANAGERSYS_CONFIGURATIONS_PREFIX = "touchsys_configuration";
        /**
         * 管理系统--控件管理模块数据集合
         */
        public static final String MANAGERSYS_WIDGET = "managersys_widget";

        /**
         * 会议模式--人员头像
         */
        public static final String MEETING_HEAD_IMG = "meeting_head_img";

        /**
         * 会议模式--二维码
         */
        public static final String MEETING_QR = "meeting_qr";

        /**
         * 会议模式--现场照片
         */
        public static final String MEETING_TAKE_PHOTOS = "meeting_take_photos";

        /**
         * 同济大学图片上传
         */
        public static final String TJDXSIGN_FACEIMAGE = "tjdxsign_faceimage";
    }

}
