package com.proshine.expo.component.program.entity;

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
public class TbTemplateOwner implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String programId;

    private String cstmId;


}
