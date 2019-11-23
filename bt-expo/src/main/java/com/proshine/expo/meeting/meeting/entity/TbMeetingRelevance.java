package com.proshine.expo.meeting.meeting.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("tb_meeting_relevance")
public class TbMeetingRelevance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 客户域
     */
    private String cstmId;

    /**
     * 0:未知
     * 1: 正常
     * 2:迟到 3:早退
     */
    private Integer signState;

    /**
     * 签到方式  0:人脸识别 1:nfc 2:无感 3:身份证 4:扫码
     */
    private Integer visitorSignPattern;

    /**
     * 签到号码
     */
    private String signNo;

    /**
     * 会议id
     */
    private String meetingId;

    /**
     * 会议访客id
     */
    private String meetingVisitorId;

    /**
     * 冗余字段
     */
    private String meetingVisitorName;

    /**
     * 签到时间
     */
    private LocalDateTime signTime;


}
