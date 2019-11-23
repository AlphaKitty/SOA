package com.proshine.expo.campus.school.entity;

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
public class TbSchool implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 客户域ID
     */
    private String cstmId;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 学校英文名称
     */
    private String schoolEnName;

    /**
     * 学校描述
     */
    private String schoolDescription;

    /**
     * 学校logo的MD5
     */
    private String schoolLogoMd5;

    /**
     * 学校地址
     */
    private String schoolAddress;

    /**
     * 实例创建时间
     */
    private LocalDateTime createTime;

    /**
     * 学校官网地址
     */
    private String schoolUrl;

    /**
     * 学校 邮编
     */
    private String schollPostcode;

    /**
     * 学校联系电话
     */
    private String schoolTelephone;

    /**
     * 学校传真
     */
    private String schoolFax;

    /**
     * 学校邮箱
     */
    private String schoolEmail;

    /**
     * 校长姓名
     */
    private String schoolMaster;

    /**
     * 扩展信息1
     */
    private String extend1;

    /**
     * 扩展信息2
     */
    private String extend2;


}
