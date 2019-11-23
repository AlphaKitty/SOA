package com.proshine.expo.component.base.entity;

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
public class TbWidgetGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String groupName;

    private String descs;

    private Integer sortField;


}
