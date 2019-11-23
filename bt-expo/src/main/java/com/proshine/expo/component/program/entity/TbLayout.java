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
public class TbLayout implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String parentId;

    private String desc;

    private String name;

    private Integer type;

    private String programId;

    private Integer width;

    private Integer height;

    private String mainId;


}
