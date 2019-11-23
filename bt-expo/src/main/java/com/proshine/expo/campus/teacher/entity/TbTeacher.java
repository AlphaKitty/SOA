package com.proshine.expo.campus.teacher.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 教师信息表
 *
 * @author zyl
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbTeacher implements Serializable {

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
     * 所属校区id
     */
    private String teacherSchoolCompusId;

    /**
     * 所属学段ID
     */
    private String teacherSchoolStageId;

    /**
     * 所教课程的课程ID, 可以为空
     */
    private String teacherCourseId;

    /**
     * 教师编号, 第三方系统
     */
    private String teacherId;

    /**
     * 教师名字
     */
    private String teacherName;

    /**
     * 生日
     */
    private LocalDateTime teacherBrithday;

    /**
     * 教师性别,
     * 0: 男
     * 1: 女
     */
    private Integer teacherSex;

    /**
     * 教师邮箱
     */
    private String teacherEmail;

    /**
     * 教师手机号
     */
    private String teacherPhone;

    /**
     * 教师BS系统账号, 外键关联
     */
    private String teacherBsUser;

    /**
     * 教师头像照片MD5
     */
    private String teacherHeadimgMd5;

    /**
     * 教师人脸识别头像MD5
     */
    private String teacherFaceimgMd5;

    /**
     * 教师NFC卡号
     */
    private String teacherNfcId;

    /**
     * 教师2.4Ghz 卡号
     */
    @TableField("teacher_24G_id")
    private String teacher24gId;

    /**
     * 教师职务
     */
    private String teacherDuty;

    /**
     * 教师民族
     */
    private String teacherNation;

    /**
     * 教师状态
     * 0: 在职
     * 1: 离职
     * 2: 休假
     * 3: 退休
     */
    private Integer teacherState;

    /**
     * 入职时间
     */
    private LocalDate teacherHiredate;

    /**
     * 扩展信息
     */
    private String extend1;

    /**
     * 扩展信息
     */
    private String extend2;


}
