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
@TableName("tb_meeting_visitor")
public class TbMeetingVisitor implements Serializable {

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
     * 访客姓名
     */
    private String visitorName;

    /**
     * 访客nfc卡号
     */
    private String visitorNfc;

    /**
     * 访客无感考勤卡号
     */
    private String visitor24g;

    /**
     * 访客身份证
     */
    private String visitorIdCard;

    /**
     * 访客二维码md5
     */
    private String visitorQrMd5;

    /**
     * 访客头像md5
     */
    private String visitorHeadImgMd5;

    /**
     * 备注
     */
    private String visitorDesc;

    /**
     * 创建人主键id
     */
    private String createUserId;

    /**
     * 添加时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
