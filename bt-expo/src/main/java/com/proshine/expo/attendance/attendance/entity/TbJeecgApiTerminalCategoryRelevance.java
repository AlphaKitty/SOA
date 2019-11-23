package com.proshine.expo.attendance.attendance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 班级所属分组与终端的绑定关系，通过终端ID可以获取到其对应的班级分组ID，从而可以获取班级课表和上课的学生
 *
 * @author zyl
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbJeecgApiTerminalCategoryRelevance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 客户域id
     */
    private String cstmId;

    /**
     * tb_terminal主键id
     */
    private String terminalId;

    /**
     * 班级所属分类
     */
    private String categoryId;


}
