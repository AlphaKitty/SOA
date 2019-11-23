package com.proshine.expo.camera.camera.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 教师和分组(教室)关联关系表
 *
 * @author zyl
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbTeacherGroupRel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 教师NFC卡号
     */
    private String nfcId;

    /**
     * 分组id
     */
    private String groupId;

    /**
     * 教职工id
     */
    private String teacherId;


}
