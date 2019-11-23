package com.proshine.midware.tbClass.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 班级信息表
 *
 * @author zyl
 * @since 2019-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbClass implements Serializable {

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
     * 班级所属学段ID
     */
    private String schoolStageId;

    /**
     * 关联的教室ID
     */
    private String classRoomId;

    /**
     * 班级所属校区ID
     */
    private String schoolCompusId;

    /**
     * 关联的年级ID
     */
    private String classGradeId;

    /**
     * 班主任ID
     */
    private String classTeacherId;

    /**
     * 班级编号, 第三方系统
     */
    private String classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 学生数量, 对于行政班有意义
     */
    private Integer studentCount;

    /**
     * 班级类型 0行政班 1教学班
     */
    private Integer classType;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastUpdateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 扩展信息1
     */
    private String extend1;

    /**
     * 扩展信息2
     */
    private String extend2;


}
