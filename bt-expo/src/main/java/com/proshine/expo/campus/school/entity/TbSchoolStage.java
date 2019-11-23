package com.proshine.expo.campus.school.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 学段信息
 *
 * @author zyl
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_school_stage")
public class TbSchoolStage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 客户域ID
     */
    private String cstmId;

    /**
     * 学段名称
     */
    private String stageName;

    /**
     * 学段简称
     */
    private String stageShortName;

    /**
     * 学段所属学校ID
     */
    private String schoolId;

    /**
     * 入学年龄
     */
    private Integer stageEnterAge;

    /**
     * 学制
     */
    private Integer stagePlan;

    /**
     * 序号
     */
    private Integer stageSeq;

    /**
     * 备注
     */
    private String stageDesp;


}
