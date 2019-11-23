package com.proshine.expo.campus.exam.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author zyl
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_exam_planning")
public class TbExamPlanning implements Serializable {

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
     * 考试课程ID 接口中获取的值
     */
    private String examCourseId;

    /**
     * 考试课程名称
     */
    private String examCourseName;

    /**
     * 考试标题，例如：期中考试、期 末考试、月考等。
     */
    private String examTitle;

    /**
     * 考试节次名称，例如：第1场
     */
    private String examSection;

    /**
     * 教室 ID tb_classroom 主键ID
     */
    private String classRoomId;

    /**
     * 教室名称
     */
    private String classRoomName;

    /**
     * 考试开始时间
     */
    private LocalDateTime examStartTime;

    /**
     * 考试结束时间
     */
    private LocalDateTime examEndTime;

    /**
     * 考勤开始时间
     */
    private LocalDateTime punchCardStartTime;

    /**
     * 考勤结束时间
     */
    private LocalDateTime punchCardEndTime;

    /**
     * 考试日期
     */
    private LocalDate examDate;

    /**
     * 周几，例如：周一
     */
    private String weekday;

    /**
     * 教师表主键 ID,我方数据库 表主键 ID
     */
    private String examTeacherIds;

    /**
     * 监考老师姓名，多个使用逗 号分隔
     */
    private String examTeacher;

    /**
     * 考试时长，以分钟为单位 ，例如：120 分钟
     */
    private String examLength;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最近更新时间
     */
    private LocalDateTime lastUpdateTime;

    /**
     * 考场考试学生人数
     */
    private Integer shouldCount;

    /**
     * 每列人数
     */
    private Integer colNumber;

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
     * 扩展信息 1
     */
    private String extend1;

    /**
     * 扩展信息 2
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
