package com.proshine.expo.campus.teacher.entity;

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
@TableName("tb_teacher_work_planning")
public class TbTeacherWorkPlanning implements Serializable {

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
     * 课程节次
     */
    private String workSection;

    /**
     * 课程日期
     */
    private LocalDate workDate;

    private String workId;

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
    private String workStartTime;

    /**
     * 课程结束时间, 冗余字段
     */
    private String workEndTime;

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
    private String weekDay;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 扩展信息1
     */
    private String extend1;

    /**
     * 扩展信息3
     */
    private String extend2;

    /**
     * 学生NFC卡号
     */
    private String teaNfcId;


}
