package com.proshine.expo.meeting.meeting.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zyl
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbMeeting implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 客户域id
     */
    private String cstmId;

    /**
     * 会议名称
     */
    private String meetingName;

    /**
     * 会议简介
     */
    private String meetingDesc;

    /**
     * 签到方式用,隔开 0:人脸识别 1:nfc 2:无感 3:身份证 4:扫码
     */
    private String signPattern;

    /**
     * 签到页面选择
     */
    private Integer signPage;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 提前时间单位分钟
     */
    private Integer earlyMinute;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String createUserId;


}
