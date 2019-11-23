package com.proshine.expo.campus.grade.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 年级表
 *
 * @author zyl
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbGrade implements Serializable {

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
     * 是否启用 0否1是
     */
    private Integer locked;

    /**
     * 届名称
     */
    private String gradeName;

    /**
     * 届编号
     */
    private String snGrade;

    /**
     * 入学时间
     */
    private LocalDateTime startTime;

    /**
     * 毕业时间
     */
    private LocalDateTime graduateTime;

    /**
     * 校区ID
     */
    private String compusId;

    /**
     * 扩展信息1
     */
    private String extend1;

    /**
     * 扩展信息2
     */
    private String extend2;


}
