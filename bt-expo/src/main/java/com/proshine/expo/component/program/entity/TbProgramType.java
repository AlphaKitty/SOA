package com.proshine.expo.component.program.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zyl
 * @since 2019-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_program_type")
public class TbProgramType implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String parentId;

    private String programType;

    private String typeName;

    private Integer permission;


}
