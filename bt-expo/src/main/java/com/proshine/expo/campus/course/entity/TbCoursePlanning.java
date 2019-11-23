package com.proshine.expo.campus.course.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 课程考勤计划表
 *
 * @author zyl
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_course_planning")
public class TbCoursePlanning implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 客户域ID, 外键
     */
    private String cstmId;

    /**
     * 课程ID, 外键
     */
    private String courseId;

    /**
     * 课程名称, 冗余字段
     */
    private String courseName;

    /**
     * 课程类型
     * 0: 选考
     * 1: 学考
     * 冗余字段
     */
    private Integer courseType;

    /**
     * 上课节次
     */
    private Integer courseSection;

    /**
     * 教师ID, 允许为空
     */
    private String teacherId;

    /**
     * 教师名称, 冗余字段
     */
    private String teacherName;

    /**
     * 教师ID, 外键
     */
    private String classRoomId;

    /**
     * 教室名称, 冗余字段
     */
    private String classRoomName;

    /**
     * 选修课班级名称
     */
    private String optionalClassName;

    /**
     * 上课开始时间
     */
    private LocalDateTime courseStartTime;

    /**
     * 课程结束时间
     */
    private LocalDateTime courseEndTime;

    /**
     * 打卡开始时间
     */
    private LocalDateTime punchCartStartTime;

    /**
     * 打卡结束时间
     */
    @TableField("punch_cart_end_Time")
    private LocalDateTime punchCartEndTime;

    /**
     * 课程日期
     */
    private LocalDateTime courseDate;

    /**
     * 课程计划创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最近更新时间
     */
    private LocalDateTime lastUpdateTime;

    /**
     * 周次
     */
    private Integer weekDay;

    /**
     * 应到人数
     */
    private Integer shoudCount;

    /**
     * 实到人数
     */
    private Integer realityCount;

    /**
     * 迟到人数
     */
    private Integer beLateCount;

    /**
     * 事假人数
     */
    private Integer personalLeaveCount;

    /**
     * 病假人数
     */
    private Integer sickLeaveCount;

    /**
     * 早退人数
     */
    private Integer leaveEarlyCount;

    /**
     * 旷课人数
     */
    private Integer truantCount;

    /**
     * 扩展信息1
     */
    private String extend1;

    /**
     * 扩展信息2
     */
    private String extend2;

    /**
     * 班级名字
     */
    private String className;

    /**
     * 年级名字
     */
    private String gradeName;


}
