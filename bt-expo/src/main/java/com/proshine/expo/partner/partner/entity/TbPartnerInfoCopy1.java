package com.proshine.expo.partner.partner.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 第三方合作厂商信息
 *
 * @author zyl
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_partner_info_copy1")
public class TbPartnerInfoCopy1 implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 客户域ID
     */
    private String cstmId;

    private String partnerName;

    /**
     * 合作厂商英文名称, 固定值
     */
    private String partnerEnName;

    /**
     * 合作厂商密码
     */
    private String partnerPwd;

    /**
     * 合作厂商账号
     */
    private String partnerAccount;

    /**
     * 合作厂商调用地址
     */
    private String partnerUrl;

    /**
     * 实例创建时间
     */
    private LocalDateTime createTime;

    /**
     * 上次更新时间
     */
    private LocalDateTime lastUpdateTime;

    /**
     * 合作厂商类型
     * 0: 排课系统
     * 1: 单点登录
     * 2: 支付系统
     * 3: 第三方登录
     * 4: 一卡通
     * 5: 流媒体
     * 6: 视频监控
     */
    private Integer partnerType;

    /**
     * 扩展信息1
     */
    private String extend1;

    /**
     * 扩展信息2
     */
    private String extend2;

    /**
     * 扩展信息3
     */
    private String extend3;

    /**
     * 扩展信息4
     */
    private String extend4;


}
