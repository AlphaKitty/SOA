package com.proshine.expo.campus.exam.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("tb_student_exam_planning")
public class TbStudentExamPlanning implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 客户域 ID
     */
    private String cstmId;

    /**
     * tb_exam_planning 表主键 ID
     */
    private String examPlanningId;

    /**
     * 考试标题，例如：期中考试、期 末考试、月考等。
     */
    private String examTitle;

    /**
     * 学生 id tb_student_info表主键ID
     */
    private String studentId;

    /**
     * 学生名称
     */
    private String studentName;

    /**
     * 学生学号 接口中获取的值
     */
    private String studentNo;

    /**
     * 考试座位号
     */
    private String seatNo;

    /**
     * 教室 id tb_classroom 表主键ID
     */
    private String classroomId;

    /**
     * 教室名称
     */
    private String classroomName;

    /**
     * 课程 id（考试科目） 接口获取的值
     */
    private String examCourseId;

    /**
     * 课程名称（考试)
     */
    private String examCourseName;

    /**
     * 考试节次名称
     */
    private String examSection;

    /**
     * 课程日期
     */
    private LocalDate examDate;

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
     * 实际打卡时间
     */
    private LocalDateTime accordanceDate;

    /**
     * 打卡状态 0：未知，1：正常 2：迟到，3：早退 4：事假，:5：病假 7：旷课
     */
    private Integer accordanceState;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 最近更新时间
     */
    private LocalDateTime lastUpdateDate;

    /**
     * 扩展信息 1
     */
    private String extend1;

    /**
     * 扩展信息 2
     */
    private String extend2;

    /**
     * 学生 2.4G 卡号
     */
    @TableField("stu_24G_id")
    private String stu24gId;

    /**
     * 学生 nfc 卡号
     */
    private String stuNfcId;

    /**
     * 学生人脸识别 MD5
     */
    private String stuFaceimgMd5;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 学籍 id 第三方
     */
    private String stuId;


}
