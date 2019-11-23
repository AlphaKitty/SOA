package com.proshine.expo.camera.camera.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 摄像头分组表
 *
 * @author zyl
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    private String id;

    /**
     * 分组级次
     */
    private Integer level;

    /**
     * 父级分组id
     */
    private String pid;

    /**
     * 分组名称
     */
    private String name;


}
