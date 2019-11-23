package com.proshine.expo.campus.discipline.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 科目表
 *
 * @author zyl
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_discipline_info")
public class TbDisciplineInfo implements Serializable {

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
     * 所属学校ID, 外键
     */
    private String schoolId;

    /**
     * 科目名称
     */
    private String disciplineName;

    /**
     * 科目类型
     * 0: 选考
     * 1: 学考
     */
    private Integer courseType;

    /**
     * 扩展字段1
     */
    private String extend1;

    /**
     * 扩展字段2
     */
    private String extend2;


}
