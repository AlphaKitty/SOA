package com.proshine.expo.campus.student.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学生课程表
 *
 * @author zyl
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_student_planning_history")
public class TbStudentPlanningHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 学生计划主键ID
     */
    private String studentPlanningId;

    /**
     * 客户域ID, 外键
     */
    private String cstmId;

    /**
     * 学生ID, 外键
     */
    private String studentId;

    /**
     * 学生名称, 冗余字段
     */
    private String studentName;

    /**
     * 上课所在教室ID, 外键
     */
    private String classRoomId;

    /**
     * 上课所在教室名称, 冗余字段
     */
    private String classRoomName;

    /**
     * 课程ID, 外键
     */
    private String courseId;

    /**
     * 课程名称, 冗余字段
     */
    private String courseName;

    /**
     * 课程节次
     */
    private Integer courseSection;

    /**
     * 课程日期
     */
    private LocalDate courseDate;

    /**
     * 选修课时班级名称
     */
    private String optionalClassName;

    /**
     * 课程类型
     * 0: 选修课
     * 1: 必修课
     */
    private Integer courseType;

    private String coursePlanningId;

    /**
     * 任课老师ID
     */
    private String teacherId;

    /**
     * 任课老师名称, 冗余字段
     */
    private String teacherName;

    /**
     * 课程开始时间, 冗余字段
     */
    private LocalDateTime courseStartTime;

    /**
     * 课程结束时间, 冗余字段
     */
    private LocalDateTime courseEndTime;

    /**
     * 刷卡时间, 冗余字段
     */
    private LocalDateTime punchCartStartTime;

    /**
     * 刷卡结束时间, 冗余字段
     */
    private LocalDateTime punchCartEndTime;

    /**
     * 实际考勤时间
     */
    private LocalDateTime accordanceDate;

    /**
     * 0: 未知
     * 1: 正常
     * 2: 迟到
     * 3: 早退
     * 4: 事假
     * 5: 病假
     * 7: 旷课
     */
    private Integer accordanceState;

    /**
     * 周次从1开始
     */
    private Integer weekDay;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 最近更新时间
     */
    private LocalDateTime lastUpdateDate;

    /**
     * 扩展信息1
     */
    private String extend1;

    /**
     * 扩展信息3
     */
    private String extend2;

    /**
     * 学生24卡号
     */
    @TableField("stu_24G_id")
    private String stu24gId;

    /**
     * 学生NFC卡号
     */
    private String stuNfcId;

    /**
     * 学生人脸识别照片MD5
     */
    private String stuFaceimgMd5;

    /**
     * 年级名字
     */
    private String gradeName;

    /**
     * 学籍id第三方
     */
    private String stuId;


}
