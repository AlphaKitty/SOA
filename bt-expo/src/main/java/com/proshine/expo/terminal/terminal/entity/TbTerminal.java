package com.proshine.expo.terminal.terminal.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zyl
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbTerminal implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String cstmId;

    private String terminalId;

    private String extraId1;

    private String extraId2;

    private String terminalName;

    private String terminalDesc;

    private String catId;

    private LocalDateTime createTime;

    private String createUser;

    private String programConfig;

    private String terminalConfig;

    /**
     * 0:离线 1:开机 2:关机
     */
    private Integer onlineState;

    private LocalDateTime logonTime;

    private LocalDateTime logoffTime;

    private LocalDateTime lastAliveTime;

    private String boardType;

    private String ipaddr;

    private Integer displayWidth;

    private Integer displayHeight;

    private Integer volume;

    private Integer networkType;

    /**
     * 1-2-3 ； 1表示daemon 2表示touch程序 3第三方程序
     */
    private String appVersion;

    private String firmwareVersion;

    private Long totalSpace;

    private Long freeSpace;

    /**
     * 节目的版本
     */
    private String programVersion;

    private String deviceModelId;

    private String classroomName;

    private String className;


}
