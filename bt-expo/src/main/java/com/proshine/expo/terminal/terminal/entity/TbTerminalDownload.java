package com.proshine.expo.terminal.terminal.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zyl
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_terminal_download")
public class TbTerminalDownload implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String cstmId;

    private String terminalId;

    /**
     * 主题已下载大小
     */
    private Long themeDownloadedSize;

    /**
     * 主题总大小
     */
    private Long themeTotalSize;

    /**
     * 主题名称
     */
    private String themeName;

    /**
     * 主题id
     */
    private String themeId;

    /**
     * 主题版本号
     */
    private Integer themeVersion;

    private Long loopDownloadedSize;

    private Long loopTotalSize;

    private String loopName;

    private String loopProgramId;

    private Integer loopVersion;

    private Long pluginDownloadedSize;

    private Long pluginTotalSize;

    private String pluginName;

    private String pluginProgramId;

    private Integer pluginVersion;

    private Long daemonDownloadedSize;

    private String daemonId;

    private Long daemonTotalSize;

    private Integer daemonVersion;

    private String touchId;

    private Long touchDownloadedSize;

    private Long touchTotalSize;

    private Integer touchVersion;

    private Long otherAppDownloadedSize;

    private Long otherAppTotalSize;

    private String otherAppVersion;

    private String otherAppId;

    private Long firmwareDownloadedSize;

    private Long firmwareTotalSize;

    private Integer firmwareVersion;

    private String firmwareId;


}
