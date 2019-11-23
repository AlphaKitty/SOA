package com.proshine.expo.campus.teacher.entity;

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
@TableName("tb_teacher_work")
public class TbTeacherWork implements Serializable {

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
     * 0:上午 1:下午
     */
    private Integer workSection;

    /**
     * 教师ID, 允许为空
     */
    private String teacherId;

    /**
     * 教师名称, 冗余字段
     */
    private String teacherName;

    /**
     * 上课开始时间
     */
    private String workStartTime;

    /**
     * 课程结束时间
     */
    private String workEndTime;

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
    private LocalDateTime workDate;

    /**
     * 课程计划创建时间
     */
    private LocalDateTime createTime;

    /**
     * 周次
     */
    private String weekDay;

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
     * 旷工人数
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


}
