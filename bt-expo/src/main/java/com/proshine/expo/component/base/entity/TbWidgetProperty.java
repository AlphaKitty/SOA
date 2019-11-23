package com.proshine.expo.component.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class TbWidgetProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String filed;

    private String defaultValue;

    private String typeId;

    @TableField("svgKey")
    private String svgKey;

    private String options;

    private String descs;

    private String modelId;

    private Integer sortField;

    /**
     * 中文名称
     */
    private String nameZh;

    /**
     * 英文名称
     */
    private String nameEn;

    /**
     * 备选项中文，与options字段格式保持一致
     */
    private String optionsZh;


}
