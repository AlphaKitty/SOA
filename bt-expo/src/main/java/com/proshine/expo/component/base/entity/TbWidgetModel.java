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
public class TbWidgetModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String icons;

    private String widgetName;

    private String groupId;

    private String descs;

    private Integer sortField;

    /**
     * 控件双击类型
     */
    private String onDoubleClickType;

    /**
     * 属性更改类型
     */
    private String propertyChangeType;

    /**
     * 控件中文名称
     */
    private String widgetNameZh;

    /**
     * 控件中文名称
     */
    private String widgetNameEn;


}
