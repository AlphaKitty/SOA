package com.proshine.expo.campus.student.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Blob;

/**
 * 学生信息表
 *
 * @author zyl
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_student_info")
public class TbStudentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 外键, 关联到客户域表
     */
    private String cstmId;

    /**
     * 学生学籍ID, 来自第三方系统
     */
    private String stuId;

    /**
     * 学生姓名
     */
    private String stuName;

    /**
     * 学生性别 0男 1女
     */
    private Integer stuSex;

    /**
     * 生日
     */
    private String stuBirthday;

    /**
     * 所属班级ID
     */
    private String stuClassId;

    /**
     * 邮箱
     */
    private String stuEmail;

    /**
     * 手机号码
     */
    private String stuPhone;

    /**
     * 学生民族
     */
    private String stuNation;

    /**
     * 关联bunnytouch账号ID
     */
    private String stuBsUser;

    /**
     * 家长关联BS账号ID
     */
    private String parentBsUser;

    /**
     * 学生头像照片ID
     */
    private String stuHeadimgMd5;

    /**
     * 学生人脸识别照片MD5
     */
    private String stuFaceimgMd5;

    /**
     * 学生NFC卡号
     */
    private String stuNfcId;

    /**
     * 学生2.4G卡号
     */
    @TableField("stu_24G_id")
    private String stu24gId;

    /**
     * 学生状态
     * 0: 正常
     * 1: 休学
     * 2: 退学
     * 3: 未入学
     */
    private Integer stuState;

    /**
     * 紧急联系人姓名
     */
    private String emergencyContactName;

    /**
     * 紧急联系人电话
     */
    private String emergencyContactPhone;

    /**
     * 扩展数据1, 用于存储学生人脸特征值
     */
    private Blob extend1;

    /**
     * 扩展信息2
     */
    private String extend2;

    /**
     * 个性签名
     */
    private String stuDesc;

    /**
     * 学号
     */
    private String stuNo;


}
