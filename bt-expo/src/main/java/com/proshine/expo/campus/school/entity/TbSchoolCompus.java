package com.proshine.expo.campus.school.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 校区
 *
 * @author zyl
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_school_compus")
public class TbSchoolCompus implements Serializable {

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
     * 校区序号
     */
    private Integer compusSeq;

    /**
     * 校区名称
     */
    private String compusName;

    /**
     * 校区所属学校ID, 外键
     */
    private String schoolId;


}
