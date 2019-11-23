package com.proshine.expo.gateway.security.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zyl
 * @since 2019-10-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String uri;

    private String names;

    private String descs;

    private String parentId;

    private String icons;

    private Integer resourceType;

    /**
     * 菜单排序
     */
    private Integer resourceSort;

    /**
     * master使用权限
     */
    private Boolean isSuper;


}
