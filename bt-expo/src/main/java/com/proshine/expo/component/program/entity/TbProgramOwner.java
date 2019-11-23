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
@TableName("tb_program_owner")
public class TbProgramOwner implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ownerId;

    /**
     * 对应节目Id
     */
    private String programId;

    /**
     * 拥有者之一的Id
     */
    private String userId;

    /**
     * 0只读 1编辑
     */
    private Integer power;

    private String cstmId;


}
