package com.proshine.expo.campus.course.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 课程表
 *
 * @author zyl
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbCourse implements Serializable {

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
     * 学校ID, 外键
     */
    private String schoolId;

    /**
     * 学科Id
     */
    private String disciplineId;

    /**
     * 任课老师ID
     */
    private String teacherId;

    /**
     * 课程编号, 第三方系统
     */
    private String courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程学分
     */
    private Integer courseScore;

    /**
     * 课时
     */
    private Integer courseNum;

    /**
     * 课程类型
     * 0: 选考
     * 1: 学考
     */
    private Integer courseType;

    /**
     * 扩展信息1
     */
    private String extend1;

    /**
     * 扩展信息2
     */
    private String extend2;


}
